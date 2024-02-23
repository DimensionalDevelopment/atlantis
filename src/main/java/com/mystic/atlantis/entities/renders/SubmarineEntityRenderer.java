package com.mystic.atlantis.entities.renders;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.mystic.atlantis.entities.SubmarineEntity;
import com.mystic.atlantis.entities.models.SubmarineEntityModel;
import mod.azure.azurelib.common.api.client.renderer.GeoEntityRenderer;
import mod.azure.azurelib.common.internal.common.cache.object.BakedGeoModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.util.Mth;

public class SubmarineEntityRenderer extends GeoEntityRenderer<SubmarineEntity> {
    public SubmarineEntityRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new SubmarineEntityModel());
    }

    @Override
    public void preRender(PoseStack poseStack, SubmarineEntity animatable, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
        poseStack.translate(0, -0.5, 0);
    }

    @Override
    protected void applyRotations(SubmarineEntity animatable, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTick) {
        poseStack.mulPose(Axis.YN.rotationDegrees(Mth.rotLerp(partialTick, animatable.yRotO, animatable.getYRot())));
    }
}