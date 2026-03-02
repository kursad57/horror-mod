package com.example.horrormod.sanity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

public class SanityUtils {
    private static final String SANITY_KEY = "horrormod_sanity";

    public static int getSanity(Player player) {
        CompoundTag data = player.getPersistentData();
        return data.getInt(SANITY_KEY);
    }

    public static void setSanity(Player player, int value) {
        int v = Math.max(0, Math.min(100, value));
        CompoundTag data = player.getPersistentData();
        data.putInt(SANITY_KEY, v);
    }

    public static void changeSanity(Player player, int delta) {
        setSanity(player, getSanity(player) + delta);
    }
}
