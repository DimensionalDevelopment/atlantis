package com.nosiphus.atlantis.util.handlers.EventHandler;


import com.nosiphus.atlantis.init.ModBlocks;
import net.minecraft.entity.monster.EntityElderGuardian;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class ElderPortalEvent {
	
	@SubscribeEvent
    public static void onDeath(LivingDeathEvent event) {

		BlockPos BiomePos = event.getEntity().getPosition();

			if (event.getEntity() instanceof EntityElderGuardian && event.getEntity().world.getEntitiesWithinAABB(EntityElderGuardian.class, event.getEntity().getEntityBoundingBox().expand(100, 100, 100)).stream().filter(entity -> !entity.isDead && entity != event.getEntity()).count() == 0) {

				System.out.println("entity is dead");
				World world = event.getEntity().world;
				BlockPos pos = event.getEntity().getPosition();
				world.getBlockState(pos).getBlock();
				world.setBlockState(pos, ModBlocks.PORTAL.getDefaultState());
			}

    }
}
