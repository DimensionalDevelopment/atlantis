package com.mystic.atlantis;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

public class AtlantisDimensionalEffect extends DimensionSpecialEffects {
    public static AtlantisDimensionalEffect INSTANCE = new AtlantisDimensionalEffect();
    private static final ResourceLocation SUN_TEXTURES = new ResourceLocation("atlantis:textures/environment/atlantis/sun.png");
    private static final ResourceLocation MOON_PHASES_TEXTURES = new ResourceLocation("atlantis:textures/environment/atlantis/moon_phases.png");

    private AtlantisDimensionalEffect() {
        super(255.0F, true, DimensionSpecialEffects.SkyType.NORMAL, false, false);
    }

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
//        RenderSystem.enableTexture();
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
        matrix.mulPose(Axis.YP.rotationDegrees(-90.0F));
        matrix.mulPose(Axis.XP.rotationDegrees(world.getTimeOfDay(partialTicks) * 360));
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
        matrix.mulPose(Axis.XP.rotationDegrees((world.getTimeOfDay(partialTicks) * 360) * 0.0015F));
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
    public boolean renderSnowAndRain(ClientLevel level, int ticks, float partialTick, LightTexture lightTexture, double camX, double camY, double camZ) {
        return false;
    }

    @Override
    public boolean isFoggyAt(int i, int i1) {
        return false;
    }
}
