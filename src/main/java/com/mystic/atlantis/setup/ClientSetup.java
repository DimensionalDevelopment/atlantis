package com.mystic.atlantis.setup;

import static com.mystic.atlantis.init.BlockInit.DYED_LINGUISTICS;
import static com.mystic.atlantis.init.BlockInit.NON_LINGUISTICS;

import java.util.Map;
import java.util.stream.Stream;

import org.jetbrains.annotations.NotNull;

import com.google.common.collect.ArrayListMultimap;
import com.modularmods.mcgltf.MCglTF;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import com.mystic.atlantis.blocks.blockentities.renderers.BlueLilyTileRenderer;
import com.mystic.atlantis.blocks.blockentities.renderers.BurntDeepTileRenderer;
import com.mystic.atlantis.blocks.blockentities.renderers.EnenmomyTileRenderer;
import com.mystic.atlantis.blocks.blockentities.renderers.SodiumBombRenderer;
import com.mystic.atlantis.blocks.blockentities.renderers.TuberUpTileRenderer;
import com.mystic.atlantis.blocks.blockentities.renderers.UnderwaterShroomTileRenderer;
import com.mystic.atlantis.dimension.DimensionAtlantis;
import com.mystic.atlantis.entities.blockbenchentities.models.CrabEntityModel;
import com.mystic.atlantis.entities.blockbenchentities.models.Jellyfish2EntityModel;
import com.mystic.atlantis.entities.blockbenchentities.models.JellyfishEntityModel;
import com.mystic.atlantis.entities.blockbenchentities.models.LeviathanEntityModel;
import com.mystic.atlantis.entities.blockbenchentities.models.SeahorseEntityModel;
import com.mystic.atlantis.entities.blockbenchentities.models.ShrimpEntityModel;
import com.mystic.atlantis.entities.blockbenchentities.models.StarfishEntityModel;
import com.mystic.atlantis.entities.blockbenchentities.models.StarfishZomEntityModel;
import com.mystic.atlantis.entities.blockbenchentities.renders.AtlanteanBoatRenderer;
import com.mystic.atlantis.entities.blockbenchentities.renders.CrabEntityRenderer;
import com.mystic.atlantis.entities.blockbenchentities.renders.Jellyfish2EntityRenderer;
import com.mystic.atlantis.entities.blockbenchentities.renders.JellyfishEntityRenderer;
import com.mystic.atlantis.entities.blockbenchentities.renders.LeviathanEntityRenderer;
import com.mystic.atlantis.entities.blockbenchentities.renders.SeahorseEntityRenderer;
import com.mystic.atlantis.entities.blockbenchentities.renders.ShrimpEntityRenderer;
import com.mystic.atlantis.entities.blockbenchentities.renders.StarfishEntityRenderer;
import com.mystic.atlantis.entities.blockbenchentities.renders.StarfishZomEntityRenderer;
import com.mystic.atlantis.entities.blockbenchentities.renders.SubmarineEntityRenderer;
import com.mystic.atlantis.init.AtlantisEntityInit;
import com.mystic.atlantis.init.BlockInit;
import com.mystic.atlantis.init.FluidInit;
import com.mystic.atlantis.init.MenuTypeInit;
import com.mystic.atlantis.init.TileEntityInit;
import com.mystic.atlantis.particles.PushBubbleStreamParticle;
import com.mystic.atlantis.screen.CrystalGeneratorScreen;
import com.mystic.atlantis.util.Reference;

import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = Reference.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup {
    public static DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Reference.MODID);
    public static final RegistryObject<SimpleParticleType> PUSH_BUBBLESTREAM = PARTICLES.register("push_bubblestream", () -> new SimpleParticleType(false));

    @SubscribeEvent
    public static void onInitializeClient(FMLClientSetupEvent event) {
        MenuScreens.register(MenuTypeInit.CRYSTAL_GENERATOR_MENU.get(), CrystalGeneratorScreen::new);

        ItemBlockRenderTypes.setRenderLayer(FluidInit.JETSTREAM_WATER.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(FluidInit.FLOWING_JETSTREAM_WATER.get(), RenderType.translucent());

        ItemBlockRenderTypes.setRenderLayer(FluidInit.SALTY_SEA_WATER.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(FluidInit.FLOWING_SALTY_SEA_WATER.get(), RenderType.translucent());

        BlockEntityRenderers.register(TileEntityInit.UNDERWATER_SHROOM_TILE.get(),
                UnderwaterShroomTileRenderer::new);

        BlockEntityRenderers.register(TileEntityInit.TUBER_UP_TILE.get(),
                TuberUpTileRenderer::new);

        BlockEntityRenderers.register(TileEntityInit.BLUE_LILY_TILE.get(),
                BlueLilyTileRenderer::new);

        BlockEntityRenderers.register(TileEntityInit.BURNT_DEEP_TILE.get(),
                BurntDeepTileRenderer::new);

        BlockEntityRenderers.register(TileEntityInit.ENENMOMY_TILE.get(),
                EnenmomyTileRenderer::new);

        registerBlockRenderLayers(RenderType.cutout(),
                BlockInit.ATLANTEAN_FIRE_MELON_FRUIT.get(),
                BlockInit.ATLANTEAN_FIRE_MELON_FRUIT_SPIKED.get(),
                BlockInit.ATLANTEAN_FIRE_MELON_STEM.get(),
                BlockInit.ATLANTEAN_FIRE_MELON_TOP.get(),
                BlockInit.ATLANTEAN_DOOR.get(),
                BlockInit.ATLANTEAN_TRAPDOOR.get(),
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

        DimensionSpecialEffects skyeffect = new DimensionSpecialEffects(255.0F, true, DimensionSpecialEffects.SkyType.NORMAL, false, false) {
            private static final ResourceLocation SUN_TEXTURES = new ResourceLocation("atlantis:textures/environment/atlantis/sun.png");
            private static final ResourceLocation MOON_PHASES_TEXTURES = new ResourceLocation("atlantis:textures/environment/atlantis/moon_phases.png");

            @Override
            public boolean renderSky(@NotNull ClientLevel world, int ticks, float tickDelta, PoseStack matrixStack, @NotNull Camera camera, @NotNull Matrix4f projectionMatrix, boolean isFoggy, @NotNull Runnable setupFog) {
                RenderSystem.disableDepthTest();
                RenderSystem.enableBlend();
                RenderSystem.defaultBlendFunc();
                RenderSystem.depthMask(false);
                matrixStack.pushPose();
                drawSun(tickDelta, matrixStack, world);
                drawMoonPhases(tickDelta, matrixStack, world);
                matrixStack.popPose();
                RenderSystem.depthMask(true);
                RenderSystem.enableTexture();
                RenderSystem.disableBlend();
                RenderSystem.enableDepthTest();
                return true;
            }

            public void drawSun(float partialTicks, PoseStack matrix, ClientLevel world){
                BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
                Tesselator tessellator = Tesselator.getInstance();

                float size = 30.0F;
                VertexFormat.Mode drawMode = VertexFormat.Mode.QUADS;
                RenderSystem.setShader(GameRenderer::getPositionTexShader);
                RenderSystem.setShaderTexture(0, SUN_TEXTURES);
                matrix.mulPose(Vector3f.YP.rotationDegrees(-90.0F));
                matrix.mulPose(Vector3f.XP.rotationDegrees(world.getTimeOfDay(partialTicks) * 360));
                Matrix4f matrix4f = matrix.last().pose();
                bufferbuilder.begin(drawMode, DefaultVertexFormat.POSITION_TEX);
                bufferbuilder.vertex(matrix4f, (-size), 100.0F, (-size)).uv(0.0F, 0.0F).endVertex();
                bufferbuilder.vertex(matrix4f, size, 100.0F, (-size)).uv(1.0F, 0.0F).endVertex();
                bufferbuilder.vertex(matrix4f, size, 100.0F, size).uv(1.0F, 1.F).endVertex();
                bufferbuilder.vertex(matrix4f, (-size), 100.0F, size).uv(0.0F, 1.0F).endVertex();
                tessellator.end();
            }

            public void drawMoonPhases(float partialTicks, PoseStack matrix, ClientLevel world){
                BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
                Tesselator tessellator = Tesselator.getInstance();

                float size = 30.0F;
                VertexFormat.Mode drawMode = VertexFormat.Mode.QUADS;
                RenderSystem.setShader(GameRenderer::getPositionTexShader);
                RenderSystem.setShaderTexture(0, MOON_PHASES_TEXTURES);
                matrix.mulPose(Vector3f.XP.rotationDegrees((world.getTimeOfDay(partialTicks) * 360) * 0.0015F));
                int k1 = world.getMoonPhase();
                int i2 = k1 % 4;
                int k2 = k1 / 4 % 2;
                float f22 = (float)(i2 + 0) / 4.0F;
                float f23 = (float)(k2 + 0) / 2.0F;
                float f24 = (float)(i2 + 1) / 4.0F;
                float f14 = (float)(k2 + 1) / 2.0F;
                Matrix4f matrix4f = matrix.last().pose();
                bufferbuilder.begin(drawMode, DefaultVertexFormat.POSITION_TEX);
                bufferbuilder.vertex(matrix4f, (-size), -100.0F, size).uv(f24, f14).endVertex();
                bufferbuilder.vertex(matrix4f, size, -100.0F, size).uv(f22, f14).endVertex();
                bufferbuilder.vertex(matrix4f, size, -100.0F, (-size)).uv(f22, f23).endVertex();
                bufferbuilder.vertex(matrix4f, (-size), -100.0F, (-size)).uv(f24, f23).endVertex();
                tessellator.end();
            }

            @Override
            public float getCloudHeight() {
                return 400.0f;
            }

            @Override
            public @NotNull Vec3 getBrightnessDependentFogColor(@NotNull Vec3 vector3d, float v) {
                return vector3d;
            }

            @Override
            public boolean isFoggyAt(int i, int i1) {
                return false;
            }
        };

        DimensionSpecialEffects.EFFECTS.put(DimensionAtlantis.ATLANTIS_DIMENSION_EFFECT, skyeffect);
    }

    @SubscribeEvent
    public static void entityRegisterEvent(EntityRenderersEvent.RegisterRenderers bus) {
        bus.registerEntityRenderer(AtlantisEntityInit.CRAB.get(), entityRenderDispatcher -> new CrabEntityRenderer(entityRenderDispatcher, new CrabEntityModel()));
        bus.registerEntityRenderer(AtlantisEntityInit.JELLYFISH.get(), entityRenderDispatcher -> new JellyfishEntityRenderer(entityRenderDispatcher, new JellyfishEntityModel()));
        bus.registerEntityRenderer(AtlantisEntityInit.JELLYFISH2.get(), entityRenderDispatcher -> new Jellyfish2EntityRenderer(entityRenderDispatcher, new Jellyfish2EntityModel()));
        bus.registerEntityRenderer(AtlantisEntityInit.SHRIMP.get(), entityRenderDispatcher -> new ShrimpEntityRenderer(entityRenderDispatcher, new ShrimpEntityModel()));
        bus.registerEntityRenderer(AtlantisEntityInit.SUBMARINE.get(), SubmarineEntityRenderer::new);
        bus.registerEntityRenderer(AtlantisEntityInit.ATLANTEAN_BOAT.get(), AtlanteanBoatRenderer::new);
        bus.registerEntityRenderer(AtlantisEntityInit.LEVIATHAN.get(), entityRenderDispatcher -> new LeviathanEntityRenderer(entityRenderDispatcher, new LeviathanEntityModel()));
        bus.registerEntityRenderer(AtlantisEntityInit.SEAHORSE.get(), entityRenderDispatcher -> new SeahorseEntityRenderer(entityRenderDispatcher, new SeahorseEntityModel()));
        bus.registerEntityRenderer(AtlantisEntityInit.STARFISH.get(), entityRenderDispatcher -> new StarfishEntityRenderer(entityRenderDispatcher, new StarfishEntityModel()));
        bus.registerEntityRenderer(AtlantisEntityInit.STARFISH_ZOM.get(), entityRenderDispatcher -> new StarfishZomEntityRenderer(entityRenderDispatcher, new StarfishZomEntityModel()));

        bus.registerEntityRenderer(AtlantisEntityInit.BOMB.get(), SodiumBombRenderer::new);
    }
    @SubscribeEvent
    public static void init(RegisterParticleProvidersEvent bus) {
        Minecraft.getInstance().particleEngine.register(PUSH_BUBBLESTREAM.get(), PushBubbleStreamParticle.Factory::new);
    }

    private static void registerBlockRenderLayers(RenderType layer, Block... blocks) {
        Stream.of(blocks).forEach(block -> ItemBlockRenderTypes.setRenderLayer(block, layer));
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerBlockColor(RegisterColorHandlersEvent.Block event) {
        ArrayListMultimap<DyeColor, Block> map = ArrayListMultimap.create();

        for(Map<DyeColor, RegistryObject<Block>> colorMap : DYED_LINGUISTICS.values()) {
            colorMap.forEach((k,v) -> map.put(k, v.get()));
        }

        BlockColors blockColors = event.getBlockColors();

        BlockColor WHITE = (arg, arg2, arg3, i) -> 0xf9fffe;
        map.get(DyeColor.WHITE).forEach(block -> blockColors.register(WHITE, block));
        BlockColor ORANGE = (arg, arg2, arg3, i) -> 0xf9801d;
        map.get(DyeColor.ORANGE).forEach(block -> blockColors.register(ORANGE, block));
        BlockColor MAGENTA = (arg, arg2, arg3, i) -> 0xc74ebd;
        map.get(DyeColor.MAGENTA).forEach(block -> blockColors.register(MAGENTA, block));
        BlockColor LIGHT_BLUE = (arg, arg2, arg3, i) -> 0x3ab3da;
        map.get(DyeColor.LIGHT_BLUE).forEach(block -> blockColors.register(LIGHT_BLUE, block));
        BlockColor YELLOW = (arg, arg2, arg3, i) -> 0xfed83d;
        map.get(DyeColor.YELLOW).forEach(block -> blockColors.register(YELLOW, block));
        BlockColor LIME = (arg, arg2, arg3, i) -> 0x80c71f;
        map.get(DyeColor.LIME).forEach(block -> blockColors.register(LIME, block));
        BlockColor PINK = (arg, arg2, arg3, i) -> 0xf38baa;
        map.get(DyeColor.PINK).forEach(block -> blockColors.register(PINK, block));
        BlockColor GRAY = (arg, arg2, arg3, i) -> 0x474f52;
        map.get(DyeColor.GRAY).forEach(block -> blockColors.register(GRAY, block));
        BlockColor LIGHT_GRAY = (arg, arg2, arg3, i) -> 0x9d9d97;
        map.get(DyeColor.LIGHT_GRAY).forEach(block -> blockColors.register(LIGHT_GRAY, block));
        BlockColor CYAN = (arg, arg2, arg3, i) -> 0x169c9c;
        map.get(DyeColor.CYAN).forEach(block -> blockColors.register(CYAN, block));
        BlockColor PURPLE = (arg, arg2, arg3, i) -> 0x8932b8;
        map.get(DyeColor.PURPLE).forEach(block -> blockColors.register(PURPLE, block));
        BlockColor BLUE = (arg, arg2, arg3, i) -> 0x3c44aa;
        map.get(DyeColor.BLUE).forEach(block -> blockColors.register(BLUE, block));
        BlockColor BROWN = (arg, arg2, arg3, i) -> 0x835432;
        map.get(DyeColor.BROWN).forEach(block -> blockColors.register(BROWN, block));
        BlockColor GREEN = (arg, arg2, arg3, i) -> 0x5e7c16;
        map.get(DyeColor.GREEN).forEach(block -> blockColors.register(GREEN, block));
        BlockColor RED = (arg, arg2, arg3, i) -> 0xb02e26;
        map.get(DyeColor.RED).forEach(block -> blockColors.register(RED, block));
        BlockColor BLACK = (arg, arg2, arg3, i) -> 0x1d1d21;
        map.get(DyeColor.BLACK).forEach(block -> blockColors.register(BLACK, block));

        BlockColor REGULAR = (arg, arg2, arg3, i) -> 0x8caed2; NON_LINGUISTICS.values().stream().map(RegistryObject::get).forEach(block -> blockColors.register(REGULAR, block));

        BlockColor SaltySeaWaterColor = (arg, arg2, arg3, i) -> 0x100a60D0;
        blockColors.register(SaltySeaWaterColor, BlockInit.SALTY_SEA_WATER_BLOCK.get());

        BlockColor JetstreamWaterColor = (arg, arg2, arg3, i) -> 0x52A9FFD0;
        blockColors.register(JetstreamWaterColor, BlockInit.JETSTREAM_WATER_BLOCK.get());
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerItemColor(RegisterColorHandlersEvent.Item event) {
        ArrayListMultimap<DyeColor, Block> map = ArrayListMultimap.<DyeColor, Block>create();

        for(Map<DyeColor, RegistryObject<Block>> colorMap : DYED_LINGUISTICS.values()) {
            colorMap.forEach((k,v) -> map.put(k, v.get()));
        }

        ItemColors blockColors = event.getItemColors();

        ItemColor WHITE = (arg, i) -> 0xf9fffe;
        map.get(DyeColor.WHITE).forEach(block -> blockColors.register(WHITE, block));
        ItemColor ORANGE = (arg, i) -> 0xf9801d;
        map.get(DyeColor.ORANGE).forEach(block -> blockColors.register(ORANGE, block));
        ItemColor MAGENTA = (arg, i) -> 0xc74ebd;
        map.get(DyeColor.MAGENTA).forEach(block -> blockColors.register(MAGENTA, block));
        ItemColor LIGHT_BLUE = (arg, i) -> 0x3ab3da;
        map.get(DyeColor.LIGHT_BLUE).forEach(block -> blockColors.register(LIGHT_BLUE, block));
        ItemColor YELLOW = (arg, i) -> 0xfed83d;
        map.get(DyeColor.YELLOW).forEach(block -> blockColors.register(YELLOW, block));
        ItemColor LIME = (arg, i) -> 0x80c71f;
        map.get(DyeColor.LIME).forEach(block -> blockColors.register(LIME, block));
        ItemColor PINK = (arg, i) -> 0xf38baa;
        map.get(DyeColor.PINK).forEach(block -> blockColors.register(PINK, block));
        ItemColor GRAY = (arg, i) -> 0x474f52;
        map.get(DyeColor.GRAY).forEach(block -> blockColors.register(GRAY, block));
        ItemColor LIGHT_GRAY = (arg, i) -> 0x9d9d97;
        map.get(DyeColor.LIGHT_GRAY).forEach(block -> blockColors.register(LIGHT_GRAY, block));
        ItemColor CYAN = (arg, i) -> 0x169c9c;
        map.get(DyeColor.CYAN).forEach(block -> blockColors.register(CYAN, block));
        ItemColor PURPLE = (arg, i) -> 0x8932b8;
        map.get(DyeColor.PURPLE).forEach(block -> blockColors.register(PURPLE, block));
        ItemColor BLUE = (arg, i) -> 0x3c44aa;
        map.get(DyeColor.BLUE).forEach(block -> blockColors.register(BLUE, block));
        ItemColor BROWN = (arg, i) -> 0x835432;
        map.get(DyeColor.BROWN).forEach(block -> blockColors.register(BROWN, block));
        ItemColor GREEN = (arg, i) -> 0x5e7c16;
        map.get(DyeColor.GREEN).forEach(block -> blockColors.register(GREEN, block));
        ItemColor RED = (arg, i) -> 0xb02e26;
        map.get(DyeColor.RED).forEach(block -> blockColors.register(RED, block));
        ItemColor BLACK = (arg, i) -> 0x1d1d21;
        map.get(DyeColor.BLACK).forEach(block -> blockColors.register(BLACK, block));

        ItemColor REGULAR = (arg, i) -> 0x8caed2; NON_LINGUISTICS.values().stream().map(RegistryObject::get).forEach(block -> blockColors.register(REGULAR, block));
    }
}
