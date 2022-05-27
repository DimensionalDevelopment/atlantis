package com.mystic.atlantis.mixin;

import com.mystic.atlantis.config.AtlantisConfig;
import com.mystic.atlantis.dimension.DimensionAtlantis;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Player.class)
public abstract class CanBreatheInDimension extends LivingEntity {

    protected CanBreatheInDimension(EntityType<? extends LivingEntity> entityType, Level world) {
        super(entityType, world);
    }

    @Unique
    @Override
    public boolean canBreatheUnderwater() {
        if (AtlantisConfig.INSTANCE.turnOnDimensionalWaterBreathing.get()) {
            if (level.dimension() == DimensionAtlantis.ATLANTIS_WORLD) {
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
        if (AtlantisConfig.INSTANCE.turnOnDimensionalWaterBreathing.get()) {
            if (level.dimension() == DimensionAtlantis.ATLANTIS_WORLD) {
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
