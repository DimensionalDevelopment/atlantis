package com.mystic.atlantis.dimension;

import net.minecraft.BlockUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.portal.PortalInfo;
import net.minecraft.world.level.portal.PortalShape;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.util.ITeleporter;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Function;

import static com.mystic.atlantis.Atlantis.LOGGER;

public interface AtlanteanITeleporter extends ITeleporter {
    @Nullable
    @Override
    default PortalInfo getPortalInfo(Entity entity, ServerLevel destWorld, Function<ServerLevel, PortalInfo> defaultPortalInfo) {
        if(entity instanceof ServerPlayer player) {
            return this.getExitPortal(destWorld, player.blockPosition(), false, DimensionAtlantis.isAtlantisDimension(destWorld), destWorld.getWorldBorder(), player).map((p_258249_) -> {
                BlockState blockstate = destWorld.getBlockState(player.blockPosition());
                Direction.Axis direction$axis;
                Vec3 vec3;
                if (blockstate.hasProperty(BlockStateProperties.HORIZONTAL_AXIS)) {
                    direction$axis = blockstate.getValue(BlockStateProperties.HORIZONTAL_AXIS);
                    BlockUtil.FoundRectangle blockutil$foundrectangle = BlockUtil.getLargestRectangleAround(player.blockPosition(), direction$axis, 21, Direction.Axis.Y, 21, (p_284700_) -> destWorld.getBlockState(p_284700_) == blockstate);
                    vec3 = this.getRelativePortalPosition(direction$axis, blockutil$foundrectangle, player);
                } else {
                    direction$axis = Direction.Axis.X;
                    vec3 = new Vec3(0.5D, 0.0D, 0.0D);
                }

                return PortalShape.createPortalInfo(destWorld, p_258249_, direction$axis, vec3, player, player.getDeltaMovement(), player.getYRot(), player.getXRot());
            }).orElse(null);
        } else {
            return this.getExitPortal(destWorld, entity.blockPosition(), DimensionAtlantis.isAtlantisDimension(destWorld), destWorld.getWorldBorder(), entity).map((p_258249_) -> {
                BlockState blockstate = destWorld.getBlockState(entity.blockPosition());
                Direction.Axis direction$axis;
                Vec3 vec3;
                if (blockstate.hasProperty(BlockStateProperties.HORIZONTAL_AXIS)) {
                    direction$axis = blockstate.getValue(BlockStateProperties.HORIZONTAL_AXIS);
                    BlockUtil.FoundRectangle blockutil$foundrectangle = BlockUtil.getLargestRectangleAround(entity.blockPosition(), direction$axis, 21, Direction.Axis.Y, 21, (p_284700_) -> destWorld.getBlockState(p_284700_) == blockstate);
                    vec3 = this.getRelativePortalPosition(direction$axis, blockutil$foundrectangle, entity);
                } else {
                    direction$axis = Direction.Axis.X;
                    vec3 = new Vec3(0.5D, 0.0D, 0.0D);
                }

                return PortalShape.createPortalInfo(destWorld, p_258249_, direction$axis, vec3, entity, entity.getDeltaMovement(), entity.getYRot(), entity.getXRot());
            }).orElse(null);
        }
    }

    default Optional<BlockUtil.FoundRectangle> getExitPortal(ServerLevel pDestination, BlockPos pFindFrom, boolean doesNothing, boolean pIsToAtlantis, WorldBorder pWorldBorder, ServerPlayer serverPlayer) {
        Optional<BlockUtil.FoundRectangle> optional = this.getExitPortal(pDestination, pFindFrom, pIsToAtlantis, pWorldBorder, serverPlayer);
        if (optional.isPresent()) {
            if (serverPlayer.getPortalCooldown() == 0) {
                serverPlayer.setPortalCooldown(300);
                return optional;
            }
        } else {
            Direction.Axis direction$axis = serverPlayer.level().getBlockState(serverPlayer.blockPosition()).getOptionalValue(BlockStateProperties.AXIS).orElse(Direction.Axis.Y);
            AtlanteanPortalForcer atlanteanPortalForcer = new AtlanteanPortalForcer(pDestination);
            if (serverPlayer.getPortalCooldown() == 0) {
                serverPlayer.setPortalCooldown(300);
                Optional<BlockUtil.FoundRectangle> optional1 = atlanteanPortalForcer.createPortal(pFindFrom, direction$axis);
                if (optional1.isEmpty()) {
                    LOGGER.error("Unable to create a portal, likely target out of worldborder");
                }

                return optional1;
            }
        }
        return Optional.empty();
    }

    default Optional<BlockUtil.FoundRectangle> getExitPortal(ServerLevel pDestination, BlockPos pFindFrom, boolean pIsToAtlantis, WorldBorder pWorldBorder, Entity entity) {
        AtlanteanPortalForcer atlanteanPortalForcer = new AtlanteanPortalForcer(pDestination);
        return atlanteanPortalForcer.findPortalAround(pFindFrom, pIsToAtlantis, pWorldBorder);
    }

    default Vec3 getRelativePortalPosition(Direction.Axis pAxis, BlockUtil.FoundRectangle pPortal, Entity entity) {
        return PortalShape.getRelativePosition(pPortal, pAxis, entity.position(), entity.getDimensions(entity.getPose()));
    }
}
