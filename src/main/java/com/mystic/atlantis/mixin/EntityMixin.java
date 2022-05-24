package com.mystic.atlantis.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Shadow public abstract void setDeltaMovement(double x, double y, double z);

    @Shadow public abstract Vec3 getDeltaMovement();
}
