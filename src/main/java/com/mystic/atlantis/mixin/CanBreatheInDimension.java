package com.mystic.atlantis.mixin;

import com.mystic.atlantis.dimension.DimensionAtlantis;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.apache.logging.log4j.core.util.Loader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(PlayerEntity.class)
public abstract class CanBreatheInDimension extends LivingEntity {

    protected CanBreatheInDimension(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Unique
    @Override
    public boolean canBreatheInWater() {
        if (world.getRegistryKey() == DimensionAtlantis.ATLANTIS_WORLD) {
            return true;
        }

        return super.canBreatheInWater();
    }

    @Unique
    protected void tickWaterBreathingAir(int air) {
        if (world.getRegistryKey() == DimensionAtlantis.ATLANTIS_WORLD) {
            this.setAir(getNextAirOnLand(air));
        }
    }

    @Override
    public void baseTick() {
        int i = this.getAir();
        super.baseTick();
        this.tickWaterBreathingAir(i);
    }
}
