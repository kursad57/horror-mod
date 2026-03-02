package com.example.horrormod;

import com.mojang.logging.LogUtils;
import com.example.horrormod.config.HorrorConfig;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(HorrorMod.MODID)
public class HorrorMod {
    public static final String MODID = "horrormod";
    private static final Logger LOGGER = LogUtils.getLogger();

    public HorrorMod() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        // register mod event listeners and deferred registers
        com.example.horrormod.entity.ModEntityTypes.ENTITIES.register(bus);
        // register config
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, HorrorConfig.SPEC);
        bus.addListener(this::commonSetup);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("HorrorMod initialized");
    }
}
