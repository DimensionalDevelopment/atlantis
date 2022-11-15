package com.mystic.atlantis.blocks.blockentities.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.mystic.atlantis.blocks.power.atlanteanstone.SodiumPrimedBomb;
import com.mystic.atlantis.init.BlockInit;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.TntMinecartRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class SodiumBombRenderer
extends EntityRenderer<SodiumPrimedBomb> {
    private final BlockRenderDispatcher blockRenderer;

    public SodiumBombRenderer(EntityRendererProvider.Context arg) {
        super(arg);
        this.shadowRadius = 0.5f;
        this.blockRenderer = arg.getBlockRenderDispatcher();
    }

    @Override
    public void render(SodiumPrimedBomb arg, float f, float g, PoseStack arg2, @NotNull MultiBufferSource arg3, int i) {
        arg2.pushPose();
        arg2.translate(0.0, 0.5, 0.0);
        int j = arg.getFuse();
        if ((float)j - g + 1.0f < 10.0f) {
            float h = 1.0f - ((float)j - g + 1.0f) / 10.0f;
            h = Mth.clamp(h, 0.0f, 1.0f);
            h *= h;
            h *= h;
            float k = 1.0f + h * 0.3f;
            arg2.scale(k, k, k);
        }
        arg2.mulPose(Vector3f.YP.rotationDegrees(-90.0f));
        arg2.translate(-0.5, -0.5, 0.5);
        arg2.mulPose(Vector3f.YP.rotationDegrees(90.0f));
        TntMinecartRenderer.renderWhiteSolidBlock(this.blockRenderer, BlockInit.SODIUM_BOMB.get().defaultBlockState(), arg2, arg3, i, j / 5 % 2 == 0);
        arg2.popPose();
        super.render(arg, f, g, arg2, arg3, i);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull SodiumPrimedBomb arg) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}