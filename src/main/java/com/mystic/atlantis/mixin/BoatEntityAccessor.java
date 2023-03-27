package com.mystic.atlantis.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.world.entity.vehicle.Boat;

@Mixin(Boat.class)
public interface BoatEntityAccessor {
    @Accessor
    void setOutOfControlTicks(float outOfControlTicks);
    @Accessor
    Boat.Status getStatus();
    @Accessor("status")
    Boat.Status getStatusField();
    @Accessor("status")
    void setStatusField(Boat.Status status);
}