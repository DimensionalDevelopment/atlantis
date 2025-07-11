package com.mystic.atlantis.entities.renders;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mystic.atlantis.entities.AquaielJellyfishEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class AquaielJellyfishEntityRenderer extends GeoEntityRenderer<AquaielJellyfishEntity> {

    public AquaielJellyfishEntityRenderer(EntityRendererProvider.Context renderManager, GeoModel<AquaielJellyfishEntity> modelProvider) {
        super(renderManager, modelProvider);
    }

    @Override
    public void preRender(PoseStack poseStack, AquaielJellyfishEntity animatable, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        poseStack.pushPose();
        super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, 0.5f);
        poseStack.popPose();
    }
}
