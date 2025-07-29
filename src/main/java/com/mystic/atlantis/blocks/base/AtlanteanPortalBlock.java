package com.mystic.atlantis.blocks.base;

import com.mystic.atlantis.blocks.blockentities.AtlanteanPortalBlockEntity;
import com.mystic.atlantis.dimension.AtlanteanPortalForcer;
import com.mystic.atlantis.dimension.AtlantisDimensions;
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
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.TheEndPortalBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.portal.DimensionTransition;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

import static com.mystic.atlantis.blocks.plants.Seabloom.WATERLOGGED;

public class AtlanteanPortalBlock extends EndPortalBlock implements SimpleWaterloggedBlock {
    protected static final VoxelShape SHAPE = Block.box(0.0D, 6.0D, 0.0D, 16.0D, 12.0D, 16.0D);

    public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.AXIS;

    @Override
    public BlockEntity newBlockEntity(BlockPos p_153196_, BlockState p_153197_) {
        return new AtlanteanPortalBlockEntity(p_153196_, p_153197_);
    }

    public AtlanteanPortalBlock(Properties settings) {
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
        if (pLevel instanceof ServerLevel && Shapes.joinIsNotEmpty(Shapes.create(pEntity.getBoundingBox().move(-pPos.getX(), -pPos.getY(), -pPos.getZ())), pState.getShape(pLevel, pPos), BooleanOp.AND)) {
            ResourceKey<Level> resourcekey = AtlantisDimensions.isAtlantisDimension(pLevel) ? Level.OVERWORLD : AtlantisDimensions.ATLANTIS_WORLD;
            ResourceKey<Level> resourcekey2 = !AtlantisDimensions.isAtlantisDimension(pLevel) ? Level.OVERWORLD : AtlantisDimensions.ATLANTIS_WORLD;
            ServerLevel serverlevel = ((ServerLevel) pLevel).getServer().getLevel(resourcekey);
            ServerLevel serverlevel2 = ((ServerLevel) pLevel).getServer().getLevel(resourcekey2);
            if (serverlevel == null && serverlevel2 == null) {
                return;
            }

            assert serverlevel2 != null;
            assert serverlevel != null;

            AtlanteanPortalForcer portalForcer = new AtlanteanPortalForcer(serverlevel);

            if (pEntity.canChangeDimensions(serverlevel, serverlevel2)) {

                if (pEntity instanceof ServerPlayer player) {
                    if (resourcekey.equals(AtlantisDimensions.ATLANTIS_WORLD) && pEntity.getPortalCooldown() == 0) {
                        player.changeDimension(new DimensionTransition(serverlevel, player, DimensionTransition.PLAY_PORTAL_SOUND));
                        player.setPortalCooldown(300);
                        if(portalForcer.findPortalAround(player.blockPosition(), false, serverlevel.getWorldBorder()).equals(Optional.empty())) {
                            portalForcer.createPortal(player.blockPosition(), Direction.Axis.X);
                        } else {
                            portalForcer.teleportToPortal(player.blockPosition(), false, serverlevel.getWorldBorder(), player);
                        }
                    } else if (player.getPortalCooldown() == 0) {
                        player.changeDimension(new DimensionTransition(serverlevel, player, DimensionTransition.PLAY_PORTAL_SOUND));
                        player.setPortalCooldown(300);
                        if(portalForcer.findPortalAround(player.blockPosition(), true, serverlevel.getWorldBorder()).equals(Optional.empty())) {
                            portalForcer.createPortal(player.blockPosition(), Direction.Axis.X);
                        } else {
                            portalForcer.teleportToPortal(player.blockPosition(), true, serverlevel.getWorldBorder(), player);
                        }
                    }
                } else {
                    if (resourcekey.equals(AtlantisDimensions.ATLANTIS_WORLD) && pEntity.getPortalCooldown() == 0) {
                        pEntity.changeDimension(new DimensionTransition(serverlevel, pEntity, DimensionTransition.PLAY_PORTAL_SOUND));
                        pEntity.setPortalCooldown(300);
                        if(portalForcer.findPortalAround(pEntity.blockPosition(), false, serverlevel.getWorldBorder()).equals(Optional.empty())) {
                            portalForcer.createPortal(pEntity.blockPosition(), Direction.Axis.X);
                        } else {
                            portalForcer.teleportToPortal(pEntity.blockPosition(), false, serverlevel.getWorldBorder(), pEntity);
                        }
                    } else if (pEntity.getPortalCooldown() == 0) {
                        pEntity.changeDimension(new DimensionTransition(serverlevel, pEntity, DimensionTransition.PLAY_PORTAL_SOUND));
                        pEntity.setPortalCooldown(300);
                        if(portalForcer.findPortalAround(pEntity.blockPosition(), true, serverlevel.getWorldBorder()).equals(Optional.empty())) {
                            portalForcer.createPortal(pEntity.blockPosition(), Direction.Axis.X);
                        } else {
                            portalForcer.teleportToPortal(pEntity.blockPosition(), true, serverlevel.getWorldBorder(), pEntity);
                        }
                    }
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