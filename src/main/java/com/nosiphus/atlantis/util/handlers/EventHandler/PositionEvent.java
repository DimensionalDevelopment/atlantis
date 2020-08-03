package com.nosiphus.atlantis.util.handlers.EventHandler;

import com.nosiphus.atlantis.init.ModBlocks;
import com.nosiphus.atlantis.init.ModItems;
import com.nosiphus.atlantis.util.reference;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod.EventBusSubscriber(modid = reference.MODID)
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
