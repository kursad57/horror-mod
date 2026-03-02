package com.example.horrormod.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class HorrorConfig {
    public static final ForgeConfigSpec.IntValue SANITY_LOW_THRESHOLD;
    public static final ForgeConfigSpec.DoubleValue MOB_AGGRO_MULTIPLIER;
    public static final ForgeConfigSpec.BooleanValue ENABLE_VISUAL_JUMPSCARE;
        public static final ForgeConfigSpec.IntValue AMBIENT_MESSAGE_CHANCE;
        public static final ForgeConfigSpec.BooleanValue ENABLE_AMBIENT_WHISPER;
    public static final ForgeConfigSpec SPEC;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        builder.comment("Horror Mod common settings");
        builder.push("general");

        SANITY_LOW_THRESHOLD = builder
                .comment("Sanity value below which jump-scares and mob buffs become more likely")
                .defineInRange("sanityLowThreshold", 30, 0, 100);

        MOB_AGGRO_MULTIPLIER = builder
                .comment("Multiplier applied to mob health and damage when players nearby have low sanity")
                .defineInRange("mobAggroMultiplier", 1.5D, 0.1D, 5.0D);

        ENABLE_VISUAL_JUMPSCARE = builder
                .comment("If true, a visual (cute duck) will briefly appear during jump-scares")
                .define("enableVisualJumpscare", true);

        AMBIENT_MESSAGE_CHANCE = builder
                .comment("Denominator chance for ambient creepy messages (1 in X ticks check). Larger = rarer. Default 1200.")
                .defineInRange("ambientMessageChance", 1200, 1, 100000);

        ENABLE_AMBIENT_WHISPER = builder
                .comment("If true, play a soft whisper sound when ambient messages trigger")
                .define("enableAmbientWhisper", true);

        builder.pop();
        SPEC = builder.build();
    }
}
