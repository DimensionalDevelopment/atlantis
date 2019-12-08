package com.mystic.dimensionatlantis.util.handlers.EventHandler;

import com.mystic.dimensionatlantis.blocks.AtlantisPortal;

import net.minecraft.entity.monster.EntityElderGuardian;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureStart;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class ElderPortalEvent {

	public static net.minecraft.world.gen.structure.MapGenStructure field_75053_d ,structureMap;
	@SubscribeEvent
    public static void onDeath(LivingDeathEvent event) {
    	
    	
    	
    	if (event.getEntity() instanceof EntityElderGuardian && event.getEntity().world.getEntitiesWithinAABB(EntityElderGuardian.class, event.getEntity().getEntityBoundingBox().expand(100, 100, 100)).stream().filter(entity -> !entity.isDead && entity != event.getEntity()).count() == 0){
    		for (StructureStart start : field_75053_d.structureMap.values()) {
    			System.out.println("entity is died");
    			World world = event.getEntity().world;
    		    BlockPos pos = event.getEntity().getPosition();
    			world.getBlockState(pos).getBlock();
    			world.setBlockState(pos, AtlantisPortal.PORTAL.getDefaultState());
    		}
    	}
    }
}
