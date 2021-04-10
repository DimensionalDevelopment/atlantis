package com.mystic.atlantis.event;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.mystic.atlantis.init.BlockInit;
import com.mystic.atlantis.init.ItemInit;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;

public class PositionEvent implements UseBlockCallback {
    @Override
    public ActionResult interact(PlayerEntity playerEntity, World world, Hand hand, BlockHitResult blockHitResult) {
        if (!world.isClient && playerEntity.getStackInHand(hand).getItem() == ItemInit.ORB_OF_ATLANTIS) {
            BlockPos pos;

            switch (blockHitResult.getSide()) {
                case UP:
                    pos = new BlockPos(blockHitResult.getBlockPos().getX(), blockHitResult.getBlockPos().getY() + 1, blockHitResult.getBlockPos().getZ());
                    break;
                case DOWN:
                    pos = new BlockPos(blockHitResult.getBlockPos().getX(), blockHitResult.getBlockPos().getY() - 1, blockHitResult.getBlockPos().getZ());
                    break;
                case WEST:
                    pos = new BlockPos(blockHitResult.getBlockPos().getX() - 1, blockHitResult.getBlockPos().getY(), blockHitResult.getBlockPos().getZ());
                    break;
                case EAST:
                    pos = new BlockPos(blockHitResult.getBlockPos().getX() + 1, blockHitResult.getBlockPos().getY(), blockHitResult.getBlockPos().getZ());
                    break;
                case SOUTH:
                    pos = new BlockPos(blockHitResult.getBlockPos().getX(), blockHitResult.getBlockPos().getY(), blockHitResult.getBlockPos().getZ() + 1);
                    break;
                case NORTH:
                    pos = new BlockPos(blockHitResult.getBlockPos().getX(), blockHitResult.getBlockPos().getY(), blockHitResult.getBlockPos().getZ() - 1);
                    break;
                default:
                    pos = new BlockPos(blockHitResult.getBlockPos().getX(), blockHitResult.getBlockPos().getY(), blockHitResult.getBlockPos().getZ());
            }

            world.setBlockState(pos, BlockInit.ATLANTIS_PORTAL.getDefaultState());

            ItemStack itemstack = playerEntity.getStackInHand(hand);
            if (!playerEntity.getAbilities().creativeMode) {
                itemstack.decrement(1);
            }

            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }
}
