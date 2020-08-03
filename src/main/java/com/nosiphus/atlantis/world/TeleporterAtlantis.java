package com.nosiphus.atlantis.world;

import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ITeleporter;

public class TeleporterAtlantis implements ITeleporter {
    public final double posX, posY, posZ;
    public final int dim;

    public static TeleporterAtlantis of(double x, double y, double z, int dim)
    {
        return new TeleporterAtlantis(x, y, z, dim);
    }

    public static TeleporterAtlantis of(Entity entity)
    {
        return new TeleporterAtlantis(entity.posX, entity.posY, entity.posZ, entity.dimension);
    }

    public static TeleporterAtlantis of(BlockPos pos, int dim)
    {
        return new TeleporterAtlantis(pos.getX() + 0.5D, pos.getY() + 0.1D, pos.getZ() + 0.5D, dim);
    }

    private TeleporterAtlantis(double x, double y, double z, int d)
    {
        posX = x;
        posY = y;
        posZ = z;
        dim = d;
    }

    @Override
    public void placeEntity(World world, Entity entity, float yaw)
    {
        entity.motionX = entity.motionY = entity.motionZ = 0D;
        entity.fallDistance = 0F;

        if (entity instanceof EntityPlayerMP && ((EntityPlayerMP) entity).connection != null)
        {
            ((EntityPlayerMP) entity).connection.setPlayerLocation(posX, posY, posZ, yaw, entity.rotationPitch);
        }
        else
        {
            entity.setLocationAndAngles(posX, posY, posZ, yaw, entity.rotationPitch);
        }
    }

    @Nullable
    public Entity teleport(@Nullable Entity entity)
    {
        if (entity == null || entity.world.isRemote)
        {
            return entity;
        }

        if (dim != entity.dimension)
        {
            return entity.changeDimension(dim, this);
        }

        placeEntity(entity.world, entity, entity.rotationYaw);
        return entity;
    }
}