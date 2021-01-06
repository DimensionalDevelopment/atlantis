package com.mystic.atlantis.event;

import com.mystic.atlantis.init.BlockInit;
import com.mystic.atlantis.init.ItemInit;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class PositionEvent
{

    @SubscribeEvent
    public void onRightClick(PlayerInteractEvent.RightClickBlock event) {

        if (event.isCanceled()) {
            return;
        }

        if (!event.getWorld().isRemote && event.getPlayer().getHeldItem(event.getHand()).getItem() == ItemInit.ORB_OF_ATLANTIS.get()) {
            BlockRayTraceResult vec3d = event.getHitVec();
            BlockPos pos;

            switch(vec3d.getFace()){
                case UP:
                    pos = new BlockPos(vec3d.getPos().getX(), vec3d.getPos().getY() + 1, vec3d.getPos().getZ());
                    break;
                case DOWN:
                    pos = new BlockPos(vec3d.getPos().getX(), vec3d.getPos().getY() - 1, vec3d.getPos().getZ());
                    break;
                case WEST:
                    pos = new BlockPos(vec3d.getPos().getX() - 1, vec3d.getPos().getY(), vec3d.getPos().getZ());
                    break;
                case EAST:
                    pos = new BlockPos(vec3d.getPos().getX() + 1, vec3d.getPos().getY(), vec3d.getPos().getZ());
                    break;
                case SOUTH:
                    pos = new BlockPos(vec3d.getPos().getX(), vec3d.getPos().getY(), vec3d.getPos().getZ() + 1);
                    break;
                case NORTH:
                    pos = new BlockPos(vec3d.getPos().getX(), vec3d.getPos().getY(), vec3d.getPos().getZ() - 1);
                    break;
                default:
                    pos = new BlockPos(vec3d.getPos().getX(), vec3d.getPos().getY(), vec3d.getPos().getZ());
            }

            event.getWorld().getBlockState(pos).getBlock();
            event.getWorld().setBlockState(pos, BlockInit.ATLANTIS_PORTAL.get().getDefaultState());

        }
    }
}
