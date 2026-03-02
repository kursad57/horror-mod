package com.example.horrormod.client;

import com.example.horrormod.HorrorMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = HorrorMod.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ClientEvents {
    private static int cooldown = 0;

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (!(event.player instanceof LocalPlayer)) return;
        LocalPlayer player = (LocalPlayer) event.player;
        if (!player.level.isClientSide()) return;

        long dayTime = player.level.getDayTime() % 24000L;
        boolean isNight = dayTime >= 13000L && dayTime <= 23000L;

        if (isNight) {
            if (cooldown <= 0) {
                // Play cave ambient as a placeholder for custom night ambience
                player.playSound(SoundEvents.AMBIENT_CAVE, SoundSource.AMBIENT, 0.7F, 1.0F);

                // Spawn fog-like particles around the player
                for (int i = 0; i < 12; i++) {
                    double dx = player.getX() + (player.level.random.nextDouble() - 0.5D) * 8.0D;
                    double dy = player.getY() + player.level.random.nextDouble() * 2.0D;
                    double dz = player.getZ() + (player.level.random.nextDouble() - 0.5D) * 8.0D;
                    player.level.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, dx, dy, dz, 0.0D, 0.01D, 0.0D);
                }

                cooldown = 1200; // cooldown in ticks (60 seconds)
            } else {
                cooldown--;
            }
        } else {
            if (cooldown > 0) cooldown--;
        }
    }
}
