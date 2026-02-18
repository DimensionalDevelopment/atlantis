package com.mystic.atlantis.items;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

public class HammerItem extends PickaxeItem {

    private final int sizeX, sizeY, sizeZ;

    public HammerItem(Tier tier, int sizeX, int sizeY, int sizeZ, Properties props) {
        super(tier, 1, -2.8f, props);
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.sizeZ = sizeZ;
    }

    public void minePlaneAround(ServerLevel level, BlockPos origin, Direction face, ServerPlayer player, ItemStack tool) {
        Direction normal = face.getOpposite();

        PlaneAxes axes = planeAxesForFace(face);

        int uStart = -(axes.sizeU % 2 == 0 ? axes.sizeU / 2 - 1 : axes.sizeU / 2);
        int vStart = -(axes.sizeV % 2 == 0 ? axes.sizeV / 2 - 1 : axes.sizeV / 2);

        int forwardDepth = switch (normal) {
            case UP, DOWN -> this.sizeY;
            case NORTH, SOUTH -> this.sizeZ;
            case WEST, EAST -> this.sizeX;
        };

        for (int du = 0; du < axes.sizeU; du++) {
            for (int dv = 0; dv < axes.sizeV; dv++) {
                for (int dn = 0; dn < forwardDepth; dn++) {
                    int offU = uStart + du;
                    int offV = vStart + dv;

                    BlockPos target = offsetInPlane(origin, axes, offU, offV, normal, dn);
                    if (target.equals(origin)) continue;
                    if (!level.isLoaded(target)) continue;

                    BlockState state = level.getBlockState(target);
                    if (state.isAir() || state.getDestroySpeed(level, target) < 0) continue;
                    if (!tool.isCorrectToolForDrops(state)) continue;

                    if (player.gameMode.destroyBlock(target)) {
                        tool.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(EquipmentSlot.MAINHAND));
                        level.gameEvent(GameEvent.BLOCK_DESTROY, target, GameEvent.Context.of(player, state));
                    }
                }
            }
        }
    }

    private record PlaneAxes(Direction uAxis, Direction vAxis, int sizeU, int sizeV) {}

    private PlaneAxes planeAxesForFace(Direction face) {
        return switch (face) {
            case UP, DOWN     -> new PlaneAxes(Direction.EAST,  Direction.SOUTH, sizeX, sizeZ);
            case NORTH, SOUTH -> new PlaneAxes(Direction.EAST,  Direction.UP,    sizeX, sizeY);
            case WEST, EAST   -> new PlaneAxes(Direction.SOUTH, Direction.UP,    sizeZ, sizeY);
        };
    }

    private BlockPos offsetInPlane(BlockPos origin, PlaneAxes axes, int offU, int offV, Direction normal, int depth) {
        int x = origin.getX(), y = origin.getY(), z = origin.getZ();

        x += offU * axes.uAxis.getStepX();
        y += offU * axes.uAxis.getStepY();
        z += offU * axes.uAxis.getStepZ();

        x += offV * axes.vAxis.getStepX();
        y += offV * axes.vAxis.getStepY();
        z += offV * axes.vAxis.getStepZ();

        x += depth * normal.getStepX();
        y += depth * normal.getStepY();
        z += depth * normal.getStepZ();

        return new BlockPos(x, y, z);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        if (!level.isClientSide && context.getPlayer() instanceof ServerPlayer player) {
            BlockPos pos = context.getClickedPos();
            Direction face = context.getClickedFace();
            ItemStack stack = context.getItemInHand();

            // Mine the center block too
            player.gameMode.destroyBlock(pos);
            stack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(EquipmentSlot.MAINHAND));

            minePlaneAround((ServerLevel) level, pos, face, player, stack);
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }
}