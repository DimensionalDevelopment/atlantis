package com.mystic.atlantis.mixin;

import net.minecraft.world.entity.vehicle.Boat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Boat.class)
public interface BoatEntityAccessor {
    @Accessor
    void setTicksUnderwater(float ticksUnderwater);
    @Accessor
    Boat.Status getLocation();
}