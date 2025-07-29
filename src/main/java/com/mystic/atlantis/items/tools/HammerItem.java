package com.mystic.atlantis.items.tools;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class HammerItem extends TieredItem {

    private final int sizeX;
    private final int sizeY;
    private final int sizeZ;

    public HammerItem(Tier tier, int sizeX, int sizeY, int sizeZ, Properties properties) {
        super(tier, properties);
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.sizeZ = sizeZ;
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity player) {
        if (!level.isClientSide && !player.isShiftKeyDown()) {
            mineSmartArea(level, player, pos);
        }
        return super.mineBlock(stack, level, state, pos, player);
    }

    private void mineSmartArea(Level level, LivingEntity player, BlockPos origin) {
        Direction facing = player.getDirection();

        // Determine forward direction offsets
        int xForward = 0;
        int zForward = 0;
        switch (facing) {
            case NORTH -> zForward = -1;
            case SOUTH -> zForward = 1;
            case WEST -> xForward = -1;
            case EAST -> xForward = 1;
        }

        // Compute offset start positions (smart center vs corner)
        int xStart = sizeX % 2 == 0 ? 0 : -(sizeX / 2);
        int yStart = sizeY % 2 == 0 ? 0 : -(sizeY / 2);
        int zStart = 0; // Always start from mined block forward

        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                for (int z = 0; z < sizeZ; z++) {
                    int relativeX = xStart + x;
                    int relativeY = yStart + y;
                    int offsetX, offsetZ;

                    if (facing.getAxis() == Direction.Axis.Z) {
                        offsetX = relativeX;
                        offsetZ = zForward * (zStart + z);
                    } else {
                        offsetX = xForward * (zStart + z);
                        offsetZ = relativeX;
                    }

                    BlockPos targetPos = origin.offset(offsetX, relativeY, offsetZ);
                    if (targetPos.equals(origin)) continue;

                    BlockState targetState = level.getBlockState(targetPos);
                    if (!targetState.isAir() && targetState.getDestroySpeed(level, targetPos) >= 0) {
                        if (player instanceof ServerPlayer serverPlayer) {
                            serverPlayer.gameMode.destroyBlock(targetPos);
                        } else {
                            level.destroyBlock(targetPos, true, player);
                        }
                    }
                }
            }
        }
    }
}
