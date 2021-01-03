package com.mystic.atlantis.dimension;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.client.ISkyRenderHandler;

public class AltantisSkyRenderer implements ISkyRenderHandler {


    private static final ResourceLocation SUN_TEXTURES = new ResourceLocation("atlantis:textures/environment/atlantis/sun.png");
    private static final ResourceLocation MOON_PHASES_TEXTURES = new ResourceLocation("atlantis:textures/environment/atlantis/moon_phases.png");

    @Override
    public void render(int ticks, float partialTicks, MatrixStack matrix, ClientWorld world, Minecraft mc) {

        RenderSystem.disableAlphaTest();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.depthMask(false);
        matrix.push();
        drawSun(ticks, partialTicks, matrix, world, mc);
        drawMoonPhases(ticks, partialTicks, matrix, world, mc);
        matrix.pop();
        RenderSystem.depthMask(true);
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
        RenderSystem.enableAlphaTest();
    }

    public void drawSun(int ticks, float partialTicks, MatrixStack matrix, ClientWorld world, Minecraft mc){
        BufferBuilder bufferbuilder = Tessellator.getInstance().getBuffer();
        Tessellator tessellator = Tessellator.getInstance();

        float size = 30.0F;
        mc.getTextureManager().bindTexture(SUN_TEXTURES);
        matrix.rotate(Vector3f.YP.rotationDegrees(-90.0F));
        matrix.rotate(Vector3f.XP.rotationDegrees(world.func_242415_f(partialTicks) * 360));
        Matrix4f matrix4f = matrix.getLast().getMatrix();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos(matrix4f, (-size), 100.0F, (-size)).tex(0.0F, 0.0F).endVertex();
        bufferbuilder.pos(matrix4f, size, 100.0F, (-size)).tex(1.0F, 0.0F).endVertex();
        bufferbuilder.pos(matrix4f, size, 100.0F, size).tex(1.0F, 1.F).endVertex();
        bufferbuilder.pos(matrix4f, (-size), 100.0F, size).tex(0.0F, 1.0F).endVertex();
        tessellator.draw();
    }

    public void drawMoonPhases(int ticks, float partialTicks, MatrixStack matrix, ClientWorld world, Minecraft mc){
        BufferBuilder bufferbuilder = Tessellator.getInstance().getBuffer();
        Tessellator tessellator = Tessellator.getInstance();

        float size = 30.0F;
        mc.getTextureManager().bindTexture(MOON_PHASES_TEXTURES);
        matrix.rotate(Vector3f.XP.rotationDegrees((world.func_242415_f(partialTicks) * 360) * 0.0015F));
        int k1 = world.getMoonPhase();
        int i2 = k1 % 4;
        int k2 = k1 / 4 % 2;
        float f22 = (float)(i2 + 0) / 4.0F;
        float f23 = (float)(k2 + 0) / 2.0F;
        float f24 = (float)(i2 + 1) / 4.0F;
        float f14 = (float)(k2 + 1) / 2.0F;
        Matrix4f matrix4f = matrix.getLast().getMatrix();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos(matrix4f, (-size), -100.0F, size).tex(f24, f14).endVertex();
        bufferbuilder.pos(matrix4f, size, -100.0F, size).tex(f22, f14).endVertex();
        bufferbuilder.pos(matrix4f, size, -100.0F, (-size)).tex(f22, f23).endVertex();
        bufferbuilder.pos(matrix4f, (-size), -100.0F, (-size)).tex(f24, f23).endVertex();
        tessellator.draw();
    }
}
