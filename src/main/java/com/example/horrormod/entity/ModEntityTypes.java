package com.example.horrormod.entity;

import com.example.horrormod.HorrorMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, HorrorMod.MODID);

    public static final RegistryObject<EntityType<GhostEntity>> GHOST = ENTITIES.register("ghost",
            () -> EntityType.Builder.<GhostEntity>of(GhostEntity::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.8F)
                    .build(new ResourceLocation(HorrorMod.MODID, "ghost").toString()));

    public static final RegistryObject<EntityType<SpecialMonster>> SPECIAL_MONSTER = ENTITIES.register("special_monster",
            () -> EntityType.Builder.<SpecialMonster>of(SpecialMonster::new, MobCategory.MONSTER)
                    .sized(1.2F, 2.8F)
                    .build(new ResourceLocation(HorrorMod.MODID, "special_monster").toString()));
}
