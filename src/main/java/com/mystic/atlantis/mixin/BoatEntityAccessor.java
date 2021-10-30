package com.mystic.atlantis.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.entity.vehicle.BoatEntity;

@Mixin(BoatEntity.class)
public interface BoatEntityAccessor {
    @Accessor
    void setTicksUnderwater(float ticksUnderwater);
    @Accessor
    BoatEntity.Location getLocation();
}