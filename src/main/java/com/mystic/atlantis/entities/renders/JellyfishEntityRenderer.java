package com.mystic.atlantis.entities.renders;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mystic.atlantis.entities.JellyfishEntity;
import mod.azure.azurelib.common.internal.common.cache.object.BakedGeoModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import mod.azure.azurelib.common.api.client.model.GeoModel;
import mod.azure.azurelib.common.api.client.renderer.GeoEntityRenderer;

public class JellyfishEntityRenderer extends GeoEntityRenderer<JellyfishEntity> {

    public JellyfishEntityRenderer(EntityRendererProvider.Context renderManager, GeoModel<JellyfishEntity> modelProvider) {
        super(renderManager, modelProvider);
    }

    @Override
    public void preRender(PoseStack poseStack, JellyfishEntity animatable, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        poseStack.pushPose();
        super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, 0.5f);
        poseStack.popPose();
    }
}
