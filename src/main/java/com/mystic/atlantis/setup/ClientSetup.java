package com.mystic.atlantis.setup;

import com.mystic.atlantis.blocks.blockentities.registry.TileRegistry;
import com.mystic.atlantis.blocks.blockentities.renderers.*;
import com.mystic.atlantis.dimension.AltantisSkyRenderer;
import com.mystic.atlantis.dimension.DimensionAtlantis;
import com.mystic.atlantis.entities.AtlantisEntities;
import com.mystic.atlantis.entities.models.CrabEntityModel;
import com.mystic.atlantis.entities.models.Jellyfish2EntityModel;
import com.mystic.atlantis.entities.models.JellyfishEntityModel;
import com.mystic.atlantis.entities.models.ShrimpEntityModel;
import com.mystic.atlantis.entities.renders.*;
import com.mystic.atlantis.init.BlockInit;
import com.mystic.atlantis.init.FluidInit;
import com.mystic.atlantis.particles.ModParticleTypes;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.ICloudRenderHandler;
import net.minecraftforge.client.ISkyRenderHandler;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.stream.Stream;

@OnlyIn(Dist.CLIENT)
public class ClientSetup {
    public static void onInitializeClient(FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(FluidInit.STILL_JETSTREAM_WATER, RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(FluidInit.FLOWING_JETSTREAM_WATER, RenderType.translucent());

        BlockEntityRenderers.register(TileRegistry.UNDERWATER_SHROOM_TILE,
                UnderwaterShroomTileRenderer::new);

        BlockEntityRenderers.register(TileRegistry.TUBER_UP_TILE,
                TuberUpTileRenderer::new);

        BlockEntityRenderers.register(TileRegistry.BLUE_LILY_TILE,
                BlueLilyTileRenderer::new);

        BlockEntityRenderers.register(TileRegistry.BURNT_DEEP_TILE,
                BurntDeepTileRenderer::new);

        BlockEntityRenderers.register(TileRegistry.ENENMOMY_TILE,
                EnenmomyTileRenderer::new);

        registerBlockRenderLayers(RenderType.cutout(),
                BlockInit.ATLANTEAN_LEAVES,
                BlockInit.ATLANTEAN_SAPLING,
                BlockInit.UNDERWATER_FLOWER,
                BlockInit.ALGAE,
                BlockInit.ATLANTEAN_POWER_TORCH,
                BlockInit.WALL_ATLANTEAN_POWER_TORCH,
                BlockInit.ATLANTEAN_POWER_DUST_WIRE,
                BlockInit.ATLANTEAN_POWER_REPEATER,
                BlockInit.ATLANTEAN_TRIPWIRE,
                BlockInit.ATLANTEAN_TRIPWIRE_HOOK,
                BlockInit.YELLOW_UNDERWATER_FLOWER,
                BlockInit.RED_UNDERWATER_FLOWER,
                BlockInit.ATLANTEAN_POWER_COMPARATOR,
                BlockInit.ANCIENT_ACACIA_WOOD_MOSS_DOOR,
                BlockInit.ANCIENT_BIRCH_WOOD_MOSS_DOOR,
                BlockInit.ANCIENT_DARK_OAK_WOOD_MOSS_DOOR,
                BlockInit.ANCIENT_JUNGLE_WOOD_MOSS_DOOR,
                BlockInit.ANCIENT_OAK_WOOD_MOSS_DOOR,
                BlockInit.ANCIENT_SPRUCE_WOOD_MOSS_DOOR,
                BlockInit.ANCIENT_ACACIA_WOOD_MOSS_TRAPDOOR,
                BlockInit.ANCIENT_BIRCH_WOOD_MOSS_TRAPDOOR,
                BlockInit.ANCIENT_DARK_OAK_WOOD_MOSS_TRAPDOOR,
                BlockInit.ANCIENT_JUNGLE_WOOD_MOSS_TRAPDOOR,
                BlockInit.ANCIENT_OAK_WOOD_MOSS_TRAPDOOR,
                BlockInit.ANCIENT_SPRUCE_WOOD_MOSS_TRAPDOOR,
                BlockInit.PURPLE_GLOWING_MUSHROOM,
                BlockInit.YELLOW_GLOWING_MUSHROOM);
        registerBlockRenderLayers(RenderType.translucent(),
                BlockInit.BLACK_PEARL_BLOCK,
                BlockInit.GRAY_PEARL_BLOCK,
                BlockInit.WHITE_PEARL_BLOCK,
                BlockInit.LIGHT_GRAY_PEARL_BLOCK,
                BlockInit.BLUE_PEARL_BLOCK,
                BlockInit.LIGHT_BLUE_PEARL_BLOCK,
                BlockInit.RED_PEARL_BLOCK,
                BlockInit.ORANGE_PEARL_BLOCK,
                BlockInit.PINK_PEARL_BLOCK,
                BlockInit.YELLOW_PEARL_BLOCK,
                BlockInit.GREEN_PEARL_BLOCK,
                BlockInit.LIME_PEARL_BLOCK,
                BlockInit.PURPLE_PEARL_BLOCK,
                BlockInit.MAGENTA_PEARL_BLOCK,
                BlockInit.CYAN_PEARL_BLOCK,
                BlockInit.BROWN_PEARL_BLOCK,
                BlockInit.ATLANTIS_CLEAR_PORTAL);

        DimensionSpecialEffects atlantis = new DimensionSpecialEffects(255.0F, true, DimensionSpecialEffects.SkyType.NORMAL, false, false) {
            @Override
            public void setSkyRenderHandler(ISkyRenderHandler skyRenderHandler) {
                super.setSkyRenderHandler(new AltantisSkyRenderer());
            }

            @Override
            public void setCloudRenderHandler(ICloudRenderHandler cloudRenderHandler) {
                super.setCloudRenderHandler(null);
            }

            @Override
            public Vec3 getBrightnessDependentFogColor(Vec3 vector3d, float v) {
                return vector3d;
            }

            @Override
            public boolean isFoggyAt(int i, int i1) {
                return false;
            }
        };

        DimensionSpecialEffects.EFFECTS.put(DimensionAtlantis.ATLANTIS_DIMENSION_EFFECT, atlantis);
    }

    public static void entityRegisterEvent(EntityRenderersEvent.RegisterRenderers bus) {
        bus.registerEntityRenderer(AtlantisEntities.CRAB, entityRenderDispatcher -> new CrabEntityRenderer(entityRenderDispatcher, new CrabEntityModel()));
        bus.registerEntityRenderer(AtlantisEntities.JELLYFISH, entityRenderDispatcher -> new JellyfishEntityRenderer(entityRenderDispatcher, new JellyfishEntityModel()));
        bus.registerEntityRenderer(AtlantisEntities.JELLYFISH2, entityRenderDispatcher -> new Jellyfish2EntityRenderer(entityRenderDispatcher, new Jellyfish2EntityModel()));
        bus.registerEntityRenderer(AtlantisEntities.SHRIMP, entityRenderDispatcher -> new ShrimpEntityRenderer(entityRenderDispatcher, new ShrimpEntityModel()));
        bus.registerEntityRenderer(AtlantisEntities.SUBMARINE, SubmarineEntityRenderer::new);
    }

    public static void initClientSetup(IEventBus bus) {
        ModParticleTypes.PARTICLES.register(bus);
        bus.addListener(ModParticleTypes::init);
        bus.addListener(ClientSetup::onInitializeClient);
        bus.addListener(ClientSetup::entityRegisterEvent);
    }

    private static void registerBlockRenderLayers(RenderType layer, Block... blocks) {
        Stream.of(blocks).forEach(block -> ItemBlockRenderTypes.setRenderLayer(block, layer));
    }
}
