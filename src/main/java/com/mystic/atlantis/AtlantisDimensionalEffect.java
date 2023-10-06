package com.mystic.atlantis;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

import javax.annotation.Nullable;

public class AtlantisDimensionalEffect extends DimensionSpecialEffects {
    public static AtlantisDimensionalEffect INSTANCE = new AtlantisDimensionalEffect();
    private static final ResourceLocation SUN_TEXTURES = new ResourceLocation("atlantis:textures/environment/sun.png");
    private static final ResourceLocation MOON_PHASES_TEXTURES = new ResourceLocation("atlantis:textures/environment/moon_phases.png");

    @Nullable
    private VertexBuffer starBuffer;

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
        createStars();
        matrixStack.popPose();
        RenderSystem.depthMask(true);
        RenderSystem.disableBlend();
        RenderSystem.enableDepthTest();
        return true;
    }

    private void createStars() {
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferbuilder = tesselator.getBuilder();
        RenderSystem.setShader(GameRenderer::getPositionShader);
        if (this.starBuffer != null) {
            this.starBuffer.close();
        }

        this.starBuffer = new VertexBuffer(VertexBuffer.Usage.STATIC);
        BufferBuilder.RenderedBuffer bufferbuilder$renderedbuffer = this.drawStars(bufferbuilder);
        this.starBuffer.bind();
        this.starBuffer.upload(bufferbuilder$renderedbuffer);
        VertexBuffer.unbind();
    }

    private BufferBuilder.RenderedBuffer drawStars(BufferBuilder pBuilder) {
        RandomSource randomsource = RandomSource.create(10842L);
        pBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION);

        for(int i = 0; i < 1500; ++i) {
            double d0 = randomsource.nextFloat() * 2.0F - 1.0F;
            double d1 = randomsource.nextFloat() * 2.0F - 1.0F;
            double d2 = randomsource.nextFloat() * 2.0F - 1.0F;
            double d3 = 0.15F + randomsource.nextFloat() * 0.1F;
            double d4 = d0 * d0 + d1 * d1 + d2 * d2;
            if (d4 < 1.0D && d4 > 0.01D) {
                d4 = 1.0D / Math.sqrt(d4);
                d0 *= d4;
                d1 *= d4;
                d2 *= d4;
                double d5 = d0 * 100.0D;
                double d6 = d1 * 100.0D;
                double d7 = d2 * 100.0D;
                double d8 = Math.atan2(d0, d2);
                double d9 = Math.sin(d8);
                double d10 = Math.cos(d8);
                double d11 = Math.atan2(Math.sqrt(d0 * d0 + d2 * d2), d1);
                double d12 = Math.sin(d11);
                double d13 = Math.cos(d11);
                double d14 = randomsource.nextDouble() * Math.PI * 2.0D;
                double d15 = Math.sin(d14);
                double d16 = Math.cos(d14);

                for(int j = 0; j < 4; ++j) {
                    double d18 = (double)((j & 2) - 1) * d3;
                    double d19 = (double)((j + 1 & 2) - 1) * d3;
                    double d21 = d18 * d16 - d19 * d15;
                    double d22 = d19 * d16 + d18 * d15;
                    double d23 = d21 * d12 + 0.0D * d13;
                    double d24 = 0.0D * d12 - d21 * d13;
                    double d25 = d24 * d9 - d22 * d10;
                    double d26 = d22 * d9 + d24 * d10;
                    pBuilder.vertex(d5 + d25, d6 + d23, d7 + d26).endVertex();
                }
            }
        }

        return pBuilder.end();
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
        return 450.0f;
    }

    @Override
    public @NotNull Vec3 getBrightnessDependentFogColor(Vec3 p_108908_, float p_108909_) {
        return p_108908_.multiply(p_108909_ * 0.94F + 0.06F, p_108909_ * 0.94F + 0.06F, p_108909_ * 0.91F + 0.09F);
    }

    @Override
    public boolean isFoggyAt(int p_108905_, int p_108906_) {
        return false;
    }
}
