package com.mystic.atlantis.mixin;

import com.mystic.atlantis.dimension.AtlantisDimensions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import com.mystic.atlantis.config.AtlantisConfig;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

@Mixin(Player.class)
public abstract class CanBreatheInDimension extends LivingEntity {

    protected CanBreatheInDimension(EntityType<? extends LivingEntity> entityType, Level world) {
        super(entityType, world);
    }

    @Unique
    @Override
    public boolean canBreatheUnderwater() {
        if (AtlantisConfig.CONFIG.turnOnDimensionalWaterBreathing.get()) {
            if (level().dimension() == AtlantisDimensions.ATLANTIS_WORLD) {
                return true;
            } else {
                return super.canBreatheUnderwater();
            }
        } else {
            return super.canBreatheUnderwater();
        }
    }

    @Unique
    protected void tickWaterBreathingAir(int air) {
        if (AtlantisConfig.CONFIG.turnOnDimensionalWaterBreathing.get()) {
            if (level().dimension() == AtlantisDimensions.ATLANTIS_WORLD) {
                this.setAirSupply(increaseAirSupply(air));
            }
        }
    }

    @Override
    public void baseTick() {
        int i = this.getAirSupply();
        super.baseTick();
        this.tickWaterBreathingAir(i);
    }
}