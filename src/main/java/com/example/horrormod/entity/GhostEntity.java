package com.example.horrormod.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.damagesource.DamageSource;

public class GhostEntity extends Monster {
    public GhostEntity(EntityType<? extends Monster> type, Level level) {
        super(type, level);
        this.setNoGravity(true);
        this.setInvisible(true);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        // stay invisible and float slightly
        this.setInvisible(true);
        this.setNoGravity(true);
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        // partially immune: ignore fire and fall damage
        if (pSource.isFire() || pSource == DamageSource.FALL) return false;
        return super.hurt(pSource, pAmount);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.2D)
                .add(Attributes.ATTACK_DAMAGE, 2.0D);
    }
}
