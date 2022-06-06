package com.mystic.atlantis.mixin;

import com.mystic.atlantis.dimension.DimensionAtlantis;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BucketItem.class)
public class BucketItemMixin extends ItemMixin{

    @Shadow @Final private Fluid content;

    @Inject(method = "use", at = @At(value = "FIELD", target = "Lnet/minecraft/world/level/Level;isClientSide:Z"))
    public void onBucketUse(Level level, Player player, InteractionHand usedHand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {
        BlockHitResult blockhitresult = getPlayerPOVHitResult(level, player, content == Fluids.EMPTY ? ClipContext.Fluid.SOURCE_ONLY : ClipContext.Fluid.NONE);
        BlockPos blockpos = blockhitresult.getBlockPos();
        ItemStack itemstack1;
        ItemStack itemStack = player.getItemInHand(usedHand);
        BlockState blockstate1 = level.getBlockState(blockpos);
        if(DimensionAtlantis.isAtlantisDimension(level)) {
            cir.cancel();
            if(blockstate1.getBlock() instanceof BucketPickup && !(itemstack1 = ((BucketPickup) blockstate1.getBlock()).pickupBlock(level, blockpos, blockstate1)).isEmpty()) {
                if(itemstack1.getItem() != Blocks.WATER.asItem()){
                    CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayer) player, itemstack1);
                    cir.setReturnValue(InteractionResultHolder.success(itemStack));
                }
            }
        }
    }
}
