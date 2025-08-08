package com.mystic.atlantis.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.world.entity.vehicle.Boat;

@Mixin(Boat.class)
public interface BoatEntityAccessor {
    @Accessor("outOfControlTicks")
    void atlantis$setOutOfControlTicks(float outOfControlTicks);

    @Accessor("status")
    Boat.Status atlantis$getStatus();

    @Accessor("status")
    void atlantis$setStatusField(Boat.Status status);
}