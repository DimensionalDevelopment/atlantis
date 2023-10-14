package com.mystic.atlantis.blocks.base;

import com.mystic.atlantis.dimension.AtlanteanPortalForcer;
import com.mystic.atlantis.dimension.DimensionAtlantis;
import net.minecraft.BlockUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

import static com.mystic.atlantis.blocks.plants.UnderwaterFlower.WATERLOGGED;

public class AtlantisClearPortalBlock extends EndPortalBlock implements SimpleWaterloggedBlock {
    protected static final VoxelShape SHAPE = Block.box(0.0D, 6.0D, 0.0D, 16.0D, 12.0D, 16.0D);

    public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.AXIS;

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

            AtlanteanPortalForcer atlanteanPortalForcer = new AtlanteanPortalForcer(serverlevel);

            if(pEntity instanceof ServerPlayer player) {
                if (resourcekey.equals(DimensionAtlantis.ATLANTIS_WORLD) && pEntity.getPortalCooldown() == 0) {
                    player.changeDimension(serverlevel, atlanteanPortalForcer);
                    player.setPortalCooldown(300);
                } else if (player.getPortalCooldown() == 0) {
                    player.changeDimension(serverlevel, atlanteanPortalForcer);
                    player.setPortalCooldown(300);
                }
            } else {
                if (resourcekey.equals(DimensionAtlantis.ATLANTIS_WORLD) && pEntity.getPortalCooldown() == 0) {
                    pEntity.changeDimension(serverlevel, atlanteanPortalForcer);
                    pEntity.setPortalCooldown(300);
                } else if (pEntity.getPortalCooldown() == 0) {
                    pEntity.changeDimension(serverlevel, atlanteanPortalForcer);
                    pEntity.setPortalCooldown(300);
                }
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