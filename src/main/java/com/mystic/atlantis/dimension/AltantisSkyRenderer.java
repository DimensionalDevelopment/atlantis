package com.mystic.atlantis.dimension;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;

public class AltantisSkyRenderer {


    private static final ResourceLocation SUN_TEXTURES = new ResourceLocation("atlantis:textures/environment/atlantis/sun.png");
    private static final ResourceLocation MOON_PHASES_TEXTURES = new ResourceLocation("atlantis:textures/environment/atlantis/moon_phases.png");

    public void render(ClientLevel world, PoseStack matrixStack, float tickDelta) {
        RenderSystem.disableDepthTest();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.depthMask(false);
        matrixStack.pushPose();
        assert world != null;
        drawSun(tickDelta, matrixStack, world, Minecraft.getInstance());
        drawMoonPhases(tickDelta, matrixStack, world, Minecraft.getInstance());
        matrixStack.popPose();
        RenderSystem.depthMask(true);
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
        RenderSystem.enableDepthTest();
    }

    public void drawSun(float partialTicks, PoseStack matrix, ClientLevel world, Minecraft mc){
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

    public void drawMoonPhases(float partialTicks, PoseStack matrix, ClientLevel world, Minecraft mc){
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

    public void disableClouds(){

    }
}
