package com.mystic.atlantis.mixin;

import com.mystic.atlantis.dimension.DimensionAtlantis;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tag.FluidTags;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(PlayerEntity.class)
public abstract class CanBreatheInDimension extends LivingEntity {

    protected CanBreatheInDimension(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    boolean FISH = false; //FabricLoader.getInstance().isModLoaded("youre-a-fish-now");

    @Unique
    @Override
    public boolean canBreatheInWater() {
        if (world.getRegistryKey() == DimensionAtlantis.ATLANTIS_WORLD) {
            return true;
        } else if (FISH) {
            return true;
        } else {
            return super.canBreatheInWater();
        }
    }

    @Unique
    protected void tickWaterBreathingAir(int air) {
        if (world.getRegistryKey() == DimensionAtlantis.ATLANTIS_WORLD) {
            this.setAir(getNextAirOnLand(air));
        } else {
            if (FISH) {
                if (this.isAlive() && !this.isSubmergedIn(FluidTags.WATER)) {
                    this.setAir(air - 1);
                    if (this.getAir() == -20) {
                        this.setAir(0);
                        this.damage(DamageSource.DROWN, 2.0F);
                    }
                } else {
                    this.setAir(getNextAirOnLand(air));
                }
            }
        }
    }

    @Override
    public void baseTick() {
        int i = this.getAir();
        super.baseTick();
        this.tickWaterBreathingAir(i);
    }
}
