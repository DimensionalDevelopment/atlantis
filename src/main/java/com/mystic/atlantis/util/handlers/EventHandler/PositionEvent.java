package com.mystic.atlantis.util.handlers.EventHandler;

import com.mystic.atlantis.init.ModBlocks;
import com.mystic.atlantis.init.ModItems;
import com.mystic.atlantis.util.Reference;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Reference.MODID)
public class PositionEvent {

    @SubscribeEvent
    public void onRightClick(PlayerInteractEvent.RightClickBlock e) {

        if (e.isCanceled()) {
            return;
        }

        if (!e.getWorld().isRemote && e.getEntityPlayer().getHeldItem(e.getHand()).getItem() == ModItems.ORB_OF_ATLANTIS) {
            Vec3d vec3d = e.getHitVec();
            BlockPos pos = new BlockPos(vec3d.x, vec3d.y, vec3d.z);
            System.out.println(e.getWorld().getBlockState(pos).getBlock());
            e.getWorld().getBlockState(pos).getBlock();
            e.getWorld().setBlockState(pos, ModBlocks.PORTAL.getDefaultState());

        }
    }
}
