package com.mystic.atlantis.dimension;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Matrix4f;

import com.mojang.blaze3d.systems.RenderSystem;

import io.github.waterpicker.openworlds.renderer.SkyRenderer;

public class AltantisSkyRenderer implements SkyRenderer {


    private static final Identifier SUN_TEXTURES = new Identifier("atlantis:textures/environment/atlantis/sun.png");
    private static final Identifier MOON_PHASES_TEXTURES = new Identifier("atlantis:textures/environment/atlantis/moon_phases.png");

    @Override
    public void render(MinecraftClient mc, MatrixStack matrix, float partialTicks) {
        ClientWorld world = mc.world;

        RenderSystem.disableAlphaTest();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.depthMask(false);
        matrix.push();
        drawSun(partialTicks, matrix, world, mc);
        drawMoonPhases(partialTicks, matrix, world, mc);
        matrix.pop();
        RenderSystem.depthMask(true);
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
        RenderSystem.enableAlphaTest();
    }

    public void drawSun(float partialTicks, MatrixStack matrix, ClientWorld world, MinecraftClient mc){
        BufferBuilder bufferbuilder = Tessellator.getInstance().getBuffer();
        Tessellator tessellator = Tessellator.getInstance();

        float size = 30.0F;
        mc.getTextureManager().bindTexture(SUN_TEXTURES);
        matrix.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(-90.0F));
        matrix.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(world.getSkyAngle(partialTicks) * 360));
        Matrix4f matrix4f = matrix.peek().getModel();
        bufferbuilder.begin(7, VertexFormats.POSITION_TEXTURE);
        bufferbuilder.vertex(matrix4f, (-size), 100.0F, (-size)).texture(0.0F, 0.0F).next();
        bufferbuilder.vertex(matrix4f, size, 100.0F, (-size)).texture(1.0F, 0.0F).next();
        bufferbuilder.vertex(matrix4f, size, 100.0F, size).texture(1.0F, 1.F).next();
        bufferbuilder.vertex(matrix4f, (-size), 100.0F, size).texture(0.0F, 1.0F).next();
        tessellator.draw();
    }

    public void drawMoonPhases(float partialTicks, MatrixStack matrix, ClientWorld world, MinecraftClient mc){
        BufferBuilder bufferbuilder = Tessellator.getInstance().getBuffer();
        Tessellator tessellator = Tessellator.getInstance();

        float size = 30.0F;
        mc.getTextureManager().bindTexture(MOON_PHASES_TEXTURES);
        matrix.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion((world.getSkyAngle(partialTicks) * 360) * 0.0015F));
        int k1 = world.getMoonPhase();
        int i2 = k1 % 4;
        int k2 = k1 / 4 % 2;
        float f22 = (float)(i2 + 0) / 4.0F;
        float f23 = (float)(k2 + 0) / 2.0F;
        float f24 = (float)(i2 + 1) / 4.0F;
        float f14 = (float)(k2 + 1) / 2.0F;
        Matrix4f matrix4f = matrix.peek().getModel();
        bufferbuilder.begin(7, VertexFormats.POSITION_TEXTURE);
        bufferbuilder.vertex(matrix4f, (-size), -100.0F, size).texture(f24, f14).next();
        bufferbuilder.vertex(matrix4f, size, -100.0F, size).texture(f22, f14).next();
        bufferbuilder.vertex(matrix4f, size, -100.0F, (-size)).texture(f22, f23).next();
        bufferbuilder.vertex(matrix4f, (-size), -100.0F, (-size)).texture(f24, f23).next();
        tessellator.draw();
    }
}
