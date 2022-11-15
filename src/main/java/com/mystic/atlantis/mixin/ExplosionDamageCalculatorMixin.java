package com.mystic.atlantis.mixin;

import com.mystic.atlantis.blocks.power.atlanteanstone.SodiumPrimedBomb;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(ExplosionDamageCalculator.class)
public class ExplosionDamageCalculatorMixin {

    @Inject(method = "getBlockExplosionResistance", at = @At("HEAD"), cancellable = true)
    public void getBlockExplosionResistance(Explosion arg, BlockGetter arg2, BlockPos arg3, BlockState arg4, FluidState arg5, CallbackInfoReturnable<Optional<Float>> cir) {
        cir.cancel();
        if (arg.getExploder() instanceof SodiumPrimedBomb) {
            if(arg4.isAir() && arg5.isEmpty()) {
                cir.setReturnValue(arg4.isAir() && arg5.isEmpty() ? Optional.of(Math.max(arg4.getExplosionResistance(arg2, arg3, arg) + 25.0f, arg5.getExplosionResistance(arg2, arg3, arg) + 25.0f)) : Optional.empty());
            } else {
                cir.setReturnValue(!arg4.isAir() && !arg5.isEmpty() ? Optional.empty() : Optional.of(Math.max(arg4.getExplosionResistance(arg2, arg3, arg), arg5.getExplosionResistance(arg2, arg3, arg))));
            }
        } else {
            cir.setReturnValue(arg4.isAir() && arg5.isEmpty() ? Optional.empty() : Optional.of(Math.max(arg4.getExplosionResistance(arg2, arg3, arg), arg5.getExplosionResistance(arg2, arg3, arg))));
        }
    }
}
