package com.example.horrormod.events;

import com.example.horrormod.HorrorMod;
import com.example.horrormod.config.HorrorConfig;
import com.example.horrormod.entity.ModEntityTypes;
import com.example.horrormod.entity.SpecialMonster;
import com.example.horrormod.sanity.SanityUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;

@Mod.EventBusSubscriber(modid = HorrorMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ServerMonsterEvents {
    // track per-player state
    private static class State {
        long lastBossSpawn = -100000L;
        long waveEndTick = -1L;
        boolean praised = false;
    }

    private static final Map<UUID, State> states = new ConcurrentHashMap<>();
        private static final String[] AMBIENT_MESSAGES = new String[] {
            "Seni izliyor.",
            "Adımların arkanızda gibi...",
            "Yakalanmak üzeresin.",
            "Gözleri her zaman seni bulur.",
            "Hissediyor musun? Bir şey yaklaşıyor.",
            "Sessizlikte bir nefes daha var."
        };

    private static State getState(Player player) {
        return states.computeIfAbsent(player.getUUID(), k -> new State());
    }

    @SubscribeEvent
    public static void onServerChat(ServerChatEvent event) {
        String msg = event.getMessage().trim().toLowerCase();
        Player player = event.getPlayer();
        State s = getState(player);
        if (msg.equalsIgnoreCase("kürşad en iyi ve senden daha becerikli")) {
            s.praised = true;
            player.displayClientMessage(new net.minecraft.network.chat.TextComponent("Praise registered."), false);
        }
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        // iterate levels and players
        for (ServerLevel level : net.minecraftforge.server.ServerLifecycleHooks.getCurrentServer().getAllLevels()) {
            long time = level.getGameTime();
            boolean night = (time % 24000L) >= 13000L && (time % 24000L) <= 23000L;

            for (Player p : level.players()) {
                // occasional ambient creepy messages for low sanity players
                int sanity = SanityUtils.getSanity(p);
                if (sanity <= HorrorConfig.SANITY_LOW_THRESHOLD.get() && level.getRandom().nextInt(HorrorConfig.AMBIENT_MESSAGE_CHANCE.get()) == 0) {
                    String msg = AMBIENT_MESSAGES[level.getRandom().nextInt(AMBIENT_MESSAGES.length)];
                    p.displayClientMessage(new TextComponent(msg), false);
                    if (HorrorConfig.ENABLE_AMBIENT_WHISPER.get() && p instanceof ServerPlayer sp) {
                        SoundEvent se = new SoundEvent(new ResourceLocation(HorrorMod.MODID, "whisper"));
                        sp.playNotifySound(se, SoundSource.AMBIENT, 0.6F, 1.0F);
                    }

                    // no bundled music will be played here (user-supplied tracks removed)
                }

                State s = getState(p);
                // if wave mode active
                if (s.waveEndTick > time) {
                    // spawn small waves during nights
                    if (night && level.getRandom().nextInt(200) == 0) {
                        spawnWave(p, level);
                    }
                    continue;
                }

                // check trigger: low sanity and night
                int sanity = SanityUtils.getSanity(p);
                if (sanity <= HorrorConfig.SANITY_LOW_THRESHOLD.get() && night) {
                    // decide behavior
                    if (s.praised) {
                        // praised: spawn boss only if cooldown passed
                        long last = s.lastBossSpawn;
                        if (time - last >= 24000L) {
                            spawnBoss(p, level);
                            s.lastBossSpawn = time;
                        }
                    } else {
                        // not praised: start 2-night wave attack
                        s.waveEndTick = time + 48000L; // 2 in-game days (nights)
                        p.displayClientMessage(new net.minecraft.network.chat.TextComponent("You feel hunted..."), false);
                    }
                }
            }
        }
    }

    private static void spawnBoss(Player p, ServerLevel level) {
        EntityType<SpecialMonster> type = ModEntityTypes.SPECIAL_MONSTER.get();
        SpecialMonster boss = new SpecialMonster(type, level);
        double x = p.getX() + (level.getRandom().nextDouble() - 0.5D) * 8.0D;
        double y = p.getY();
        double z = p.getZ() + (level.getRandom().nextDouble() - 0.5D) * 8.0D;
        boss.moveTo(x, y, z);
        level.addFreshEntity(boss);
        p.displayClientMessage(new net.minecraft.network.chat.TextComponent("A monstrous presence appears..."), false);
    }

    private static void spawnWave(Player p, ServerLevel level) {
        // spawn a few zombies near player as wave
        for (int i = 0; i < 3; i++) {
            Zombie z = new Zombie(EntityType.ZOMBIE, level);
            double x = p.getX() + (level.getRandom().nextDouble() - 0.5D) * 12.0D;
            double y = p.getY();
            double zz = p.getZ() + (level.getRandom().nextDouble() - 0.5D) * 12.0D;
            z.moveTo(x, y, zz);
            level.addFreshEntity(z);
        }
        p.displayClientMessage(new net.minecraft.network.chat.TextComponent("Waves of creatures surge at you!"), false);
    }

    @SubscribeEvent
    public static void onEntityJoin(EntityJoinLevelEvent event) {
        // ensure attributes for special monster are set
        if (event.getEntity() instanceof SpecialMonster sm) {
            if (sm.getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.MAX_HEALTH) != null) {
                // ensure base attributes
                SpecialMonster.createAttributes();
            }
        }
    }
}
