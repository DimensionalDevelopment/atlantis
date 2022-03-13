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
        ItemBlockRenderTypes.setRenderLayer(FluidInit.STILL_JETSTREAM_WATER.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(FluidInit.FLOWING_JETSTREAM_WATER.get(), RenderType.translucent());

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
                BlockInit.ATLANTEAN_LEAVES.get(),
                BlockInit.ATLANTEAN_SAPLING.get(),
                BlockInit.UNDERWATER_FLOWER.get(),
                BlockInit.ALGAE.get(),
                BlockInit.ATLANTEAN_POWER_TORCH.get(),
                BlockInit.WALL_ATLANTEAN_POWER_TORCH.get(),
                BlockInit.ATLANTEAN_POWER_DUST_WIRE.get(),
                BlockInit.ATLANTEAN_POWER_REPEATER.get(),
                BlockInit.ATLANTEAN_TRIPWIRE.get(),
                BlockInit.ATLANTEAN_TRIPWIRE_HOOK.get(),
                BlockInit.YELLOW_UNDERWATER_FLOWER.get(),
                BlockInit.RED_UNDERWATER_FLOWER.get(),
                BlockInit.ATLANTEAN_POWER_COMPARATOR.get(),
                BlockInit.ANCIENT_ACACIA_WOOD_MOSS_DOOR.get(),
                BlockInit.ANCIENT_BIRCH_WOOD_MOSS_DOOR.get(),
                BlockInit.ANCIENT_DARK_OAK_WOOD_MOSS_DOOR.get(),
                BlockInit.ANCIENT_JUNGLE_WOOD_MOSS_DOOR.get(),
                BlockInit.ANCIENT_OAK_WOOD_MOSS_DOOR.get(),
                BlockInit.ANCIENT_SPRUCE_WOOD_MOSS_DOOR.get(),
                BlockInit.ANCIENT_ACACIA_WOOD_MOSS_TRAPDOOR.get(),
                BlockInit.ANCIENT_BIRCH_WOOD_MOSS_TRAPDOOR.get(),
                BlockInit.ANCIENT_DARK_OAK_WOOD_MOSS_TRAPDOOR.get(),
                BlockInit.ANCIENT_JUNGLE_WOOD_MOSS_TRAPDOOR.get(),
                BlockInit.ANCIENT_OAK_WOOD_MOSS_TRAPDOOR.get(),
                BlockInit.ANCIENT_SPRUCE_WOOD_MOSS_TRAPDOOR.get(),
                BlockInit.PURPLE_GLOWING_MUSHROOM.get(),
                BlockInit.YELLOW_GLOWING_MUSHROOM.get());
        registerBlockRenderLayers(RenderType.translucent(),
                BlockInit.BLACK_PEARL_BLOCK.get(),
                BlockInit.GRAY_PEARL_BLOCK.get(),
                BlockInit.WHITE_PEARL_BLOCK.get(),
                BlockInit.LIGHT_GRAY_PEARL_BLOCK.get(),
                BlockInit.BLUE_PEARL_BLOCK.get(),
                BlockInit.LIGHT_BLUE_PEARL_BLOCK.get(),
                BlockInit.RED_PEARL_BLOCK.get(),
                BlockInit.ORANGE_PEARL_BLOCK.get(),
                BlockInit.PINK_PEARL_BLOCK.get(),
                BlockInit.YELLOW_PEARL_BLOCK.get(),
                BlockInit.GREEN_PEARL_BLOCK.get(),
                BlockInit.LIME_PEARL_BLOCK.get(),
                BlockInit.PURPLE_PEARL_BLOCK.get(),
                BlockInit.MAGENTA_PEARL_BLOCK.get(),
                BlockInit.CYAN_PEARL_BLOCK.get(),
                BlockInit.BROWN_PEARL_BLOCK.get(),
                BlockInit.ATLANTIS_CLEAR_PORTAL.get());

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
