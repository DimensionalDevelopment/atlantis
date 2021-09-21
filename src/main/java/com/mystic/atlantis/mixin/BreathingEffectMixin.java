package com.mystic.atlantis.mixin;

import com.mystic.atlantis.dimension.DimensionAtlantis;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tag.FluidTags;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
abstract class BreathingEffectMixin extends LivingEntity
{
    @Shadow
    protected boolean isSubmergedInWater;

    private BreathingEffectMixin(EntityType<? extends LivingEntity> type, World world)
    {
        super(type, world);
    }

    @Inject(method = "tick", at = @At("TAIL"))
    public void atlantisTick(CallbackInfo ci)
    {
        if (world.getRegistryKey() == DimensionAtlantis.ATLANTIS_WORLD_KEY)
        {
            if (this.isAlive() && this.isSubmergedInWater)
            {
                this.setAir(this.getMaxAir());
            }
        }
    }
}
