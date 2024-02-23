package com.mystic.atlantis.dimension;

import com.mystic.atlantis.blocks.base.AtlanteanCoreFrame;
import com.mystic.atlantis.blocks.base.AtlantisClearPortalBlock;
import com.mystic.atlantis.init.BlockInit;
import com.mystic.atlantis.init.POITypesInit;
import net.minecraft.BlockUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.TicketType;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiRecord;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.levelgen.Heightmap;

import java.util.Comparator;
import java.util.Optional;

import static com.mystic.atlantis.blocks.plants.UnderwaterFlower.WATERLOGGED;

public class AtlanteanPortalForcer implements AtlanteanITeleporter {
    private static final DirectionProperty FACING = AtlanteanCoreFrame.FACING;
    private static final BooleanProperty HAS_EYE = AtlanteanCoreFrame.HAS_EYE;
    protected final ServerLevel level;

    public AtlanteanPortalForcer(ServerLevel pLevel) {
        this.level = pLevel;
    }

    public Optional<BlockUtil.FoundRectangle> findPortalAround(BlockPos pPos, boolean isInAtlantis, WorldBorder pWorldBorder) {
        PoiManager poimanager = this.level.getPoiManager();
        int i = isInAtlantis ? 16 : 128;
        poimanager.ensureLoadedAndValid(this.level, pPos, i);
        Optional<PoiRecord> optional = poimanager.getInSquare((p_230634_) ->
                POITypesInit.ATLANTEAN_PORTAL.get() == p_230634_.value(), pPos, i, PoiManager.Occupancy.ANY).filter((p_192981_) ->
                pWorldBorder.isWithinBounds(p_192981_.getPos())).filter((p_192990_) ->
                this.level.getBlockState(p_192990_.getPos()).hasProperty(AtlantisClearPortalBlock.AXIS)).findFirst();
        return optional.map((p_192975_) -> {
            BlockPos blockpos = p_192975_.getPos();
            this.level.getChunkSource().addRegionTicket(TicketType.PORTAL, new ChunkPos(blockpos), 3, blockpos);
            BlockState blockstate = this.level.getBlockState(blockpos);
            return BlockUtil.getLargestRectangleAround(blockpos, blockstate.getValue(BlockStateProperties.AXIS), 21, Direction.Axis.Y, 21, (p_192978_) ->
                    this.level.getBlockState(p_192978_) == blockstate);
        });
    }

    public Optional<BlockUtil.FoundRectangle> createPortal(BlockPos pPos, Direction.Axis pAxis) {
        Direction direction = Direction.get(Direction.AxisDirection.POSITIVE, pAxis);
        double d0 = -1.0D;
        BlockPos blockpos = pPos;
        double d1 = -1.0D;
        BlockPos blockpos1 = pPos;
        WorldBorder worldborder = this.level.getWorldBorder();
        int i = Math.min(this.level.getMaxBuildHeight(), this.level.getMinBuildHeight() + this.level.getLogicalHeight()) - 1;
        BlockPos.MutableBlockPos blockpos$mutableblockpos = pPos.mutable();

        for (BlockPos.MutableBlockPos blockpos$mutableblockpos1 : BlockPos.spiralAround(pPos, 16, Direction.EAST, Direction.SOUTH)) {
            int j = Math.min(i, this.level.getHeight(Heightmap.Types.MOTION_BLOCKING, blockpos$mutableblockpos1.getX(), blockpos$mutableblockpos1.getZ()));
            if (worldborder.isWithinBounds(blockpos$mutableblockpos1) && worldborder.isWithinBounds(blockpos$mutableblockpos1.move(direction, 1))) {
                blockpos$mutableblockpos1.move(direction.getOpposite(), 1);

                for (int l = j; l >= this.level.getMinBuildHeight(); --l) {
                    blockpos$mutableblockpos1.setY(l);
                    if (this.canPortalReplaceBlock(blockpos$mutableblockpos1)) {
                        int i1;
                        for (i1 = l; l > this.level.getMinBuildHeight() && this.canPortalReplaceBlock(blockpos$mutableblockpos1.move(Direction.DOWN)); --l) {
                            if (l + 4 <= i) {
                                int j1 = i1 - l;
                                if (j1 <= 0 || j1 >= 3) {
                                    blockpos$mutableblockpos1.setY(l);
                                    if (this.canHostFrame(blockpos$mutableblockpos1, blockpos$mutableblockpos, direction, 0)) {
                                        double d2 = pPos.distSqr(blockpos$mutableblockpos1);
                                        if (this.canHostFrame(blockpos$mutableblockpos1, blockpos$mutableblockpos, direction, -1) && this.canHostFrame(blockpos$mutableblockpos1, blockpos$mutableblockpos, direction, 1) && (d0 == -1.0D || d0 > d2)) {
                                            d0 = d2;
                                            blockpos = blockpos$mutableblockpos1.immutable();
                                        }

                                        if (d0 == -1.0D && (d1 == -1.0D || d1 > d2)) {
                                            d1 = d2;
                                            blockpos1 = blockpos$mutableblockpos1.immutable();
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        if (d0 == -1.0D && d1 != -1.0D) {
            blockpos = blockpos1;
        }


        if (DimensionAtlantis.isAtlantisDimension(this.level)) {
            makePortalOverworld(level, blockpos);
        } else {
            makePortalAtlantis(level, blockpos);
        }

        return Optional.of(new BlockUtil.FoundRectangle(blockpos.immutable(), 2, 3));
    }

    private boolean canPortalReplaceBlock(BlockPos.MutableBlockPos pPos) {
        BlockState blockstate = this.level.getBlockState(pPos);
        return blockstate.canBeReplaced() && blockstate.getFluidState().isEmpty();
    }

    private boolean canHostFrame(BlockPos pOriginalPos, BlockPos.MutableBlockPos pOffsetPos, Direction pDirection, int pOffsetScale) {
        for (int i = -1; i < 3; ++i) {
            for (int j = -1; j < 3; ++j) {
                pOffsetPos.setWithOffset(pOriginalPos, pDirection.getStepX() * i + pDirection.getStepX() * pOffsetScale, j, pDirection.getStepZ() * i + pDirection.getStepZ() * pOffsetScale);
                if (j < 0 && !this.level.getBlockState(pOffsetPos).isSolid()) {
                    return false;
                }

                if (j >= 0 && !this.canPortalReplaceBlock(pOffsetPos)) {
                    return false;
                }
            }
        }

        return true;
    }

    private void makePortalAtlantis(ServerLevel pServerLevel, BlockPos blockpos) {
        int i = blockpos.getX();
        int j = blockpos.getY() - 2;
        int k = blockpos.getZ();
        BlockPos.betweenClosed(i - 2, j + 1, k - 2, i + 2, j + 5, k + 2).forEach((p_207578_) -> {
            pServerLevel.setBlockAndUpdate(p_207578_, Blocks.AIR.defaultBlockState());
        });
        makePortalFrameAtlantis(pServerLevel, blockpos);
        BlockPos.betweenClosed(i - 2, j, k - 2, i + 2, j, k + 2).forEach((p_184101_) -> {
            pServerLevel.setBlockAndUpdate(p_184101_, BlockInit.CALCITE_BLOCK.get().defaultBlockState());
        });
        BlockPos.betweenClosed(i - 1, j + 1, k - 1, i + 1, j + 1, k + 1).forEach((p_184101_) -> {
            pServerLevel.setBlockAndUpdate(p_184101_, BlockInit.ATLANTIS_CLEAR_PORTAL.get().defaultBlockState());
        });
    }

    private void makePortalOverworld(ServerLevel pServerLevel, BlockPos blockpos) {
        int i = blockpos.getX();
        int j = blockpos.getY() - 2;
        int k = blockpos.getZ();
        BlockPos.betweenClosed(i - 2, j + 1, k - 2, i + 2, j + 5, k + 2).forEach((p_207578_) -> {
            pServerLevel.setBlockAndUpdate(p_207578_, Blocks.WATER.defaultBlockState());
        });
        makePortalFrameOverworld(pServerLevel, blockpos);
        BlockPos.betweenClosed(i - 2, j, k - 2, i + 2, j, k + 2).forEach((p_184101_) -> {
            pServerLevel.setBlockAndUpdate(p_184101_, BlockInit.CALCITE_BLOCK.get().defaultBlockState());
        });
        BlockPos.betweenClosed(i - 1, j + 1, k - 1, i + 1, j + 1, k + 1).forEach((p_184101_) -> {
            pServerLevel.setBlockAndUpdate(p_184101_, BlockInit.ATLANTIS_CLEAR_PORTAL.get().defaultBlockState());
        });
    }

    public static void makePortalFrameAtlantis(ServerLevel pServerLevel, BlockPos blockpos) {
        pServerLevel.setBlockAndUpdate(blockpos.offset(2, -1, -1), BlockInit.ATLANTEAN_PORTAL_FRAME.get().defaultBlockState()
                .setValue(FACING, Direction.WEST).setValue(HAS_EYE, true).setValue(WATERLOGGED, false));
        pServerLevel.setBlockAndUpdate(blockpos.offset(2, -1, 0), BlockInit.ATLANTEAN_PORTAL_FRAME.get().defaultBlockState()
                .setValue(FACING, Direction.WEST).setValue(HAS_EYE, true).setValue(WATERLOGGED, false));
        pServerLevel.setBlockAndUpdate(blockpos.offset(2, -1, 1), BlockInit.ATLANTEAN_PORTAL_FRAME.get().defaultBlockState()
                .setValue(FACING, Direction.WEST).setValue(HAS_EYE, true).setValue(WATERLOGGED, false));

        pServerLevel.setBlockAndUpdate(blockpos.offset(-1, -1, 2), BlockInit.ATLANTEAN_PORTAL_FRAME.get().defaultBlockState()
                .setValue(FACING, Direction.NORTH).setValue(HAS_EYE, true).setValue(WATERLOGGED, false));
        pServerLevel.setBlockAndUpdate(blockpos.offset(0, -1, 2), BlockInit.ATLANTEAN_PORTAL_FRAME.get().defaultBlockState()
                .setValue(FACING, Direction.NORTH).setValue(HAS_EYE, true).setValue(WATERLOGGED, false));
        pServerLevel.setBlockAndUpdate(blockpos.offset(1, -1, 2), BlockInit.ATLANTEAN_PORTAL_FRAME.get().defaultBlockState()
                .setValue(FACING, Direction.NORTH).setValue(HAS_EYE, true).setValue(WATERLOGGED, false));

        pServerLevel.setBlockAndUpdate(blockpos.offset(-2, -1, 1), BlockInit.ATLANTEAN_PORTAL_FRAME.get().defaultBlockState()
                .setValue(FACING, Direction.EAST).setValue(HAS_EYE, true).setValue(WATERLOGGED, false));
        pServerLevel.setBlockAndUpdate(blockpos.offset(-2, -1, 0), BlockInit.ATLANTEAN_PORTAL_FRAME.get().defaultBlockState()
                .setValue(FACING, Direction.EAST).setValue(HAS_EYE, true).setValue(WATERLOGGED, false));
        pServerLevel.setBlockAndUpdate(blockpos.offset(-2, -1, -1), BlockInit.ATLANTEAN_PORTAL_FRAME.get().defaultBlockState()
                .setValue(FACING, Direction.EAST).setValue(HAS_EYE, true).setValue(WATERLOGGED, false));

        pServerLevel.setBlockAndUpdate(blockpos.offset(1, -1, -2), BlockInit.ATLANTEAN_PORTAL_FRAME.get().defaultBlockState()
                .setValue(FACING, Direction.SOUTH).setValue(HAS_EYE, true).setValue(WATERLOGGED, false));
        pServerLevel.setBlockAndUpdate(blockpos.offset(0, -1, -2), BlockInit.ATLANTEAN_PORTAL_FRAME.get().defaultBlockState()
                .setValue(FACING, Direction.SOUTH).setValue(HAS_EYE, true).setValue(WATERLOGGED, false));
        pServerLevel.setBlockAndUpdate(blockpos.offset(-1, -1, -2), BlockInit.ATLANTEAN_PORTAL_FRAME.get().defaultBlockState()
                .setValue(FACING, Direction.SOUTH).setValue(HAS_EYE, true).setValue(WATERLOGGED, false));
    }

    public static void makePortalFrameOverworld(ServerLevel pServerLevel, BlockPos blockpos) {
        pServerLevel.setBlockAndUpdate(blockpos.offset(2, -1, -1), BlockInit.ATLANTEAN_PORTAL_FRAME.get().defaultBlockState()
                .setValue(FACING, Direction.WEST).setValue(HAS_EYE, true).setValue(WATERLOGGED, true));
        pServerLevel.setBlockAndUpdate(blockpos.offset(2, -1, 0), BlockInit.ATLANTEAN_PORTAL_FRAME.get().defaultBlockState()
                .setValue(FACING, Direction.WEST).setValue(HAS_EYE, true).setValue(WATERLOGGED, true));
        pServerLevel.setBlockAndUpdate(blockpos.offset(2, -1, 1), BlockInit.ATLANTEAN_PORTAL_FRAME.get().defaultBlockState()
                .setValue(FACING, Direction.WEST).setValue(HAS_EYE, true).setValue(WATERLOGGED, true));

        pServerLevel.setBlockAndUpdate(blockpos.offset(-1, -1, 2), BlockInit.ATLANTEAN_PORTAL_FRAME.get().defaultBlockState()
                .setValue(FACING, Direction.NORTH).setValue(HAS_EYE, true).setValue(WATERLOGGED, true));
        pServerLevel.setBlockAndUpdate(blockpos.offset(0, -1, 2), BlockInit.ATLANTEAN_PORTAL_FRAME.get().defaultBlockState()
                .setValue(FACING, Direction.NORTH).setValue(HAS_EYE, true).setValue(WATERLOGGED, true));
        pServerLevel.setBlockAndUpdate(blockpos.offset(1, -1, 2), BlockInit.ATLANTEAN_PORTAL_FRAME.get().defaultBlockState()
                .setValue(FACING, Direction.NORTH).setValue(HAS_EYE, true).setValue(WATERLOGGED, true));

        pServerLevel.setBlockAndUpdate(blockpos.offset(-2, -1, 1), BlockInit.ATLANTEAN_PORTAL_FRAME.get().defaultBlockState()
                .setValue(FACING, Direction.EAST).setValue(HAS_EYE, true).setValue(WATERLOGGED, true));
        pServerLevel.setBlockAndUpdate(blockpos.offset(-2, -1, 0), BlockInit.ATLANTEAN_PORTAL_FRAME.get().defaultBlockState()
                .setValue(FACING, Direction.EAST).setValue(HAS_EYE, true).setValue(WATERLOGGED, true));
        pServerLevel.setBlockAndUpdate(blockpos.offset(-2, -1, -1), BlockInit.ATLANTEAN_PORTAL_FRAME.get().defaultBlockState()
                .setValue(FACING, Direction.EAST).setValue(HAS_EYE, true).setValue(WATERLOGGED, true));

        pServerLevel.setBlockAndUpdate(blockpos.offset(1, -1, -2), BlockInit.ATLANTEAN_PORTAL_FRAME.get().defaultBlockState()
                .setValue(FACING, Direction.SOUTH).setValue(HAS_EYE, true).setValue(WATERLOGGED, true));
        pServerLevel.setBlockAndUpdate(blockpos.offset(0, -1, -2), BlockInit.ATLANTEAN_PORTAL_FRAME.get().defaultBlockState()
                .setValue(FACING, Direction.SOUTH).setValue(HAS_EYE, true).setValue(WATERLOGGED, true));
        pServerLevel.setBlockAndUpdate(blockpos.offset(-1, -1, -2), BlockInit.ATLANTEAN_PORTAL_FRAME.get().defaultBlockState()
                .setValue(FACING, Direction.SOUTH).setValue(HAS_EYE, true).setValue(WATERLOGGED, true));
    }
}

