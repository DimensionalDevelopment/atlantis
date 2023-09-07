package com.mystic.atlantis.blocks.blockentities.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.mystic.atlantis.blocks.power.atlanteanstone.SodiumPrimedBombBlock;
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

public class SodiumBombRenderer extends EntityRenderer<SodiumPrimedBombBlock> {
    private final BlockRenderDispatcher blockRenderer;

    public SodiumBombRenderer(EntityRendererProvider.Context rendererDispatcherIn) {
        super(rendererDispatcherIn);
        this.shadowRadius = 0.5f;
        this.blockRenderer = rendererDispatcherIn.getBlockRenderDispatcher();
    }

    @Override
    public void render(SodiumPrimedBombBlock block, float yaw, float partialTick, PoseStack stack, @NotNull MultiBufferSource arg3, int packedLight) {
        stack.pushPose();
        stack.translate(0.0, 0.5, 0.0);
        int fuse = block.getFuse();
        if ((float)fuse - partialTick + 1.0f < 10.0f) {
            float partialTickFuseOffset = 1.0f - ((float)fuse - partialTick + 1.0f) / 10.0f;
            partialTickFuseOffset = Mth.clamp(partialTickFuseOffset, 0.0f, 1.0f);
            partialTickFuseOffset *= partialTickFuseOffset;
            partialTickFuseOffset *= partialTickFuseOffset;
            float partialTickOffset = 1.0f + partialTickFuseOffset * 0.3f;
            stack.scale(partialTickOffset, partialTickOffset, partialTickOffset);
        }
        stack.mulPose(Axis.YP.rotationDegrees(-90.0f));
        stack.translate(-0.5, -0.5, 0.5);
        stack.mulPose(Axis.YP.rotationDegrees(90.0f));
        TntMinecartRenderer.renderWhiteSolidBlock(this.blockRenderer, BlockInit.SODIUM_BOMB.get().defaultBlockState(), stack, arg3, packedLight, fuse / 5 % 2 == 0);
        stack.popPose();
        super.render(block, yaw, partialTick, stack, arg3, packedLight);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull SodiumPrimedBombBlock arg) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}