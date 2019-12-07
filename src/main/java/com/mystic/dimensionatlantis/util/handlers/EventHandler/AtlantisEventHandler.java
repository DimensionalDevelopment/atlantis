package com.mystic.dimensionatlantis.util.handlers.EventHandler;


import com.mystic.dimensionatlantis.Main;
import com.mystic.dimensionatlantis.blocks.AtlantisPortal;
import com.mystic.dimensionatlantis.blocks.portal.BlocksAtlantis;
import com.mystic.dimensionatlantis.items.ItemPortalAtlantis;
import com.mystic.dimensionatlantis.util.Reference;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.monster.EntityElderGuardian;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureStart;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class AtlantisEventHandler {
	
	
	
    public static Block withName(Block block, String name)
    {
        block.setCreativeTab(Main.ATLANTISTAB);
        block.setRegistryName(name);
        return block;
    }

    public static Item withName(Item item, String name)
    {
        item.setCreativeTab(Main.ATLANTISTAB);
        item.setRegistryName(name);
        return item;
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event)
    {
        event.getRegistry().register(withName(new BlocksAtlantis(), "portal"));

    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event)
    {
        event.getRegistry().register(new ItemBlock(AtlantisPortal.PORTAL).setRegistryName("portal"));
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event)
    {
        ModelLoader.setCustomModelResourceLocation(ItemPortalAtlantis.PORTAL, 0, new ModelResourceLocation(ItemPortalAtlantis.PORTAL.getRegistryName(), "normal"));

    }
    
    public static net.minecraft.world.gen.structure.MapGenStructure field_75053_d ,structureMap;
    public static void onDeath(LivingDeathEvent event) {
    	
    	if (event.getEntity() instanceof EntityElderGuardian && event.getEntityLiving().world.getEntitiesWithinAABB(EntityElderGuardian.class, event.getEntity().getEntityBoundingBox().expand(100, 100, 100)).isEmpty()) {
    		for (StructureStart start :  field_75053_d.structureMap.values()) {
    			
    			World world = event.getEntity().world;
    		    BlockPos pos = event.getEntity().getPosition();
    			world.getBlockState(pos).getBlock();
    			world.setBlockState(pos, AtlantisPortal.PORTAL.getDefaultState());
    		}
    	}
    }
}
