package com.mystic.atlantis.blocks.base;

import com.mystic.atlantis.dimension.DimensionAtlantis;
import com.mystic.atlantis.init.BlockInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.RelativeMovement;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

import static com.mystic.atlantis.blocks.plants.UnderwaterFlower.WATERLOGGED;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.AXIS;

public class AtlantisClearPortalBlock extends EndPortalBlock implements SimpleWaterloggedBlock {
    private static final DirectionProperty FACING = AtlanteanCoreFrame.FACING;
    private static final BooleanProperty HAS_EYE = AtlanteanCoreFrame.HAS_EYE;
    protected static final VoxelShape SHAPE = Block.box(0.0D, 6.0D, 0.0D, 16.0D, 12.0D, 16.0D);

    public AtlantisClearPortalBlock(Properties settings) {
        super(settings
                .noCollission()
                .noOcclusion()
                .sound(SoundType.GLASS)
                .strength(0.2F, 0.4F)
        );
        this.registerDefaultState(this.stateDefinition.any().setValue(AXIS, Direction.Axis.Y).setValue(WATERLOGGED, Boolean.FALSE));
    }

    @Override
    public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
        if (pLevel instanceof ServerLevel && pEntity.canChangeDimensions() && Shapes.joinIsNotEmpty(Shapes.create(pEntity.getBoundingBox().move(-pPos.getX(), -pPos.getY(), -pPos.getZ())), pState.getShape(pLevel, pPos), BooleanOp.AND)) {
            ResourceKey<Level> resourcekey = DimensionAtlantis.isAtlantisDimension(pLevel) ? Level.OVERWORLD : DimensionAtlantis.ATLANTIS_WORLD;
            ServerLevel serverlevel = ((ServerLevel) pLevel).getServer().getLevel(resourcekey);
            if (serverlevel == null) {
                return;
            }

            if (resourcekey.equals(DimensionAtlantis.ATLANTIS_WORLD) && pEntity.getPortalCooldown() == 0) {
                //pEntity.teleportTo(serverlevel, pPos.getX(), pPos.getY() + 100, pPos.getZ(), Set.of(RelativeMovement.Y), pEntity.getYRot(), pEntity.getXRot());
                makePortalOverworld(serverlevel, pPos);
                pEntity.changeDimension(serverlevel);
                pEntity.setPortalCooldown();
            } else if (pEntity.getPortalCooldown() == 0) {
                //pEntity.teleportTo(serverlevel, pPos.getX(), pPos.getY() - 98, pPos.getZ(), Set.of(RelativeMovement.Y), pEntity.getYRot(), pEntity.getXRot());
                makePortalAtlantis(serverlevel, pPos);
                pEntity.changeDimension(serverlevel);
                pEntity.setPortalCooldown();
            }
        }
    }

    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if (pLevel.dimensionType().natural() && pLevel.getGameRules().getBoolean(GameRules.RULE_DOMOBSPAWNING) && pRandom.nextInt(2000) < pLevel.getDifficulty().getId()) {
            while (pLevel.getBlockState(pPos).is(this)) {
                pPos = pPos.below();
            }

            if (pLevel.getBlockState(pPos).isValidSpawn(pLevel, pPos, EntityType.DROWNED)) {
                Entity entity = EntityType.DROWNED.spawn(pLevel, pPos.above(), MobSpawnType.STRUCTURE);
                if (entity != null) {
                    entity.setPortalCooldown();
                }
            }
        }
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

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState blockstate = context.getLevel().getBlockState(context.getClickedPos());
        if (blockstate.is(this)) {
            return blockstate;
        } else {
            FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
            boolean flag = fluidstate.getType() == Fluids.WATER;
            return super.getStateForPlacement(context).setValue(WATERLOGGED, Boolean.valueOf(flag));
        }
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AXIS, WATERLOGGED);
    }
}