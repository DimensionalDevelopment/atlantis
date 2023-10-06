package com.mystic.atlantis.dimension;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.portal.PortalForcer;
import net.minecraft.world.level.portal.PortalInfo;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.ITeleporter;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public interface AtlanteanITeleporter extends ITeleporter {
    @Nullable
    @Override
    default PortalInfo getPortalInfo(Entity entity, ServerLevel destWorld, Function<ServerLevel, PortalInfo> defaultPortalInfo)
    {
        return this.isAtlantean() ? defaultPortalInfo.apply(destWorld) : new PortalInfo(entity.position(), Vec3.ZERO, entity.getYRot(), entity.getXRot());
    }

    default boolean isAtlantean()
    {
        return this.getClass() == AtlanteanPortalForcer.class;
    }

}
