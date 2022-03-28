package com.mystic.atlantis.mixin;

import net.minecraft.world.entity.vehicle.Boat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

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