package com.mystic.atlantis.mixin;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashSet;
import java.util.Set;

@Mixin(BlockEntityType.class)
public class BlockEntityTypeMixin {
    @Unique private final Set<Block> additionalValidBLock = new HashSet<>();

    public void addAdditionalValidBlock(Block... blocks) {
        additionalValidBLock.addAll(Set.of(blocks));
    }

    @Inject(method = "isValid", at = @At(value = "RETURN"), cancellable = true)
    private void isValid(BlockState state, CallbackInfoReturnable<Boolean> cir) {
        if(!cir.getReturnValue()) {
            cir.setReturnValue(additionalValidBLock.contains(state.getBlock()));
        }
    }
}
