package com.mystic.atlantis.mixin;

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

    boolean FISH = false; //FabricLoader.getInstance().isModLoaded("youre-a-fish-now");

    @Unique
    @Override
    public boolean canBreatheUnderwater() {
        if (level.dimension() == DimensionAtlantis.ATLANTIS_WORLD) {
            return true;
        } else if (FISH) {
            return true;
        } else {
            return super.canBreatheUnderwater();
        }
    }

    @Unique
    protected void tickWaterBreathingAir(int air) {
        if (level.dimension() == DimensionAtlantis.ATLANTIS_WORLD) {
            this.setAirSupply(increaseAirSupply(air));
        } else {
            if (FISH) {
                if (this.isAlive() && !this.isEyeInFluid(FluidTags.WATER)) {
                    this.setAirSupply(air - 1);
                    if (this.getAirSupply() == -20) {
                        this.setAirSupply(0);
                        this.hurt(DamageSource.DROWN, 2.0F);
                    }
                } else {
                    this.setAirSupply(increaseAirSupply(air));
                }
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
