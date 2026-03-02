package com.example.horrormod.events;

import com.example.horrormod.entity.GhostEntity;
import com.example.horrormod.sanity.SanityUtils;
import com.example.horrormod.config.HorrorConfig;
import com.example.horrormod.HorrorMod;
import net.minecraft.world.entity.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = HorrorMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ServerEvents {
    @SubscribeEvent
    public static void onPlayerHurt(LivingHurtEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        float dmg = event.getAmount();
        int reduce = Math.round(dmg * 2.0f);
        SanityUtils.changeSanity(player, -reduce);
    }

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        if (!(event.getSource().getEntity() instanceof Player player)) return;
        if (event.getEntity() instanceof Animal) {
            // killing animals reduces sanity
            SanityUtils.changeSanity(player, -5);
        }
    }

    @SubscribeEvent
    public static void onFinishUse(LivingEntityUseItemEvent.Finish event) {
        if (!(event.getEntity() instanceof Player player)) return;
        ItemStack stack = event.getItem();
        if (stack.isEdible()) {
            // eating restores sanity slightly
            SanityUtils.changeSanity(player, 6);
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (!(event.player instanceof Player player)) return;

        // If standing near fire (within 3 blocks), increase sanity slowly
        ItemStack main = player.getMainHandItem();
        if (main.getItem() == Items.TORCH || main.getItem() == Items.CAMPFIRE) {
            SanityUtils.changeSanity(player, 1);
        }
    }

    @SubscribeEvent
    public static void onEntityJoin(net.minecraftforge.event.entity.EntityJoinLevelEvent event) {
        if (!(event.getEntity() instanceof net.minecraft.world.entity.LivingEntity living)) return;

        // If a hostile mob spawns near a low-sanity player, buff it according to config
        if (living instanceof net.minecraft.world.entity.Mob) {
            java.util.List<Player> players = event.getLevel().getEntitiesOfClass(Player.class, living.getBoundingBox().inflate(16.0D), p -> SanityUtils.getSanity(p) < HorrorConfig.SANITY_LOW_THRESHOLD.get());
            if (!players.isEmpty()) {
                double mult = HorrorConfig.MOB_AGGRO_MULTIPLIER.get();
                if (living.getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.MAX_HEALTH) != null) {
                    double baseHp = living.getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.MAX_HEALTH).getBaseValue();
                    living.getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.MAX_HEALTH).setBaseValue(baseHp * mult);
                    living.setHealth((float) Math.min(living.getMaxHealth(), living.getHealth() * (float) mult));
                }
                if (living.getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_DAMAGE) != null) {
                    double baseAtk = living.getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_DAMAGE).getBaseValue();
                    living.getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_DAMAGE).setBaseValue(baseAtk * mult);
                }
            }
    }
}
