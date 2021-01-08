package com.mystic.atlantis.event;

import com.mystic.atlantis.init.BlockInit;
import com.mystic.atlantis.init.ItemInit;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class PositionEvent
{

    @SubscribeEvent
    public void onRightClick(PlayerInteractEvent.RightClickBlock event) {

        if (event.isCanceled()) {
            return;
        }

        if (!event.getWorld().isClient && event.getPlayer().getStackInHand(event.getHand()).getItem() == ItemInit.ORB_OF_ATLANTIS.get()) {
            BlockHitResult vec3d = event.getHitVec();
            BlockPos pos;

            switch(vec3d.getSide()){
                case UP:
                    pos = new BlockPos(vec3d.getBlockPos().getX(), vec3d.getBlockPos().getY() + 1, vec3d.getBlockPos().getZ());
                    break;
                case DOWN:
                    pos = new BlockPos(vec3d.getBlockPos().getX(), vec3d.getBlockPos().getY() - 1, vec3d.getBlockPos().getZ());
                    break;
                case WEST:
                    pos = new BlockPos(vec3d.getBlockPos().getX() - 1, vec3d.getBlockPos().getY(), vec3d.getBlockPos().getZ());
                    break;
                case EAST:
                    pos = new BlockPos(vec3d.getBlockPos().getX() + 1, vec3d.getBlockPos().getY(), vec3d.getBlockPos().getZ());
                    break;
                case SOUTH:
                    pos = new BlockPos(vec3d.getBlockPos().getX(), vec3d.getBlockPos().getY(), vec3d.getBlockPos().getZ() + 1);
                    break;
                case NORTH:
                    pos = new BlockPos(vec3d.getBlockPos().getX(), vec3d.getBlockPos().getY(), vec3d.getBlockPos().getZ() - 1);
                    break;
                default:
                    pos = new BlockPos(vec3d.getBlockPos().getX(), vec3d.getBlockPos().getY(), vec3d.getBlockPos().getZ());
            }

            event.getWorld().getBlockState(pos).getBlock();
            event.getWorld().setBlockState(pos, BlockInit.ATLANTIS_PORTAL.get().getDefaultState());

        }
    }
}
