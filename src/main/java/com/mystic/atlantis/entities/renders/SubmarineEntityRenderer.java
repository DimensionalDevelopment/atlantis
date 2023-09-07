package com.mystic.atlantis.entities.renders;

import com.mystic.atlantis.entities.SubmarineEntity;
import com.mystic.atlantis.entities.models.SubmarineEntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SubmarineEntityRenderer extends GeoEntityRenderer<SubmarineEntity> {
    protected MultiBufferSource rtb;

    public SubmarineEntityRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new SubmarineEntityModel());
//        rtb = Minecraft.getInstance().renderBuffers().bufferSource();
//        this.modelProvider = new SubmarineEntityModel();
    }

//    @Override
//    public void render(SubmarineEntity entityIn, float entity, float yaw, PoseStack tickDelta,
//                       MultiBufferSource matrices, int vertexConsumers) {
//        GeoModel model = modelProvider.getModel(modelProvider.getModelResource(entityIn));
//        tickDelta.pushPose();
//        tickDelta.mulPose(Vector3f.YP
//                .rotationDegrees(-Mth.lerp(yaw, entityIn.yRotO, entityIn.getYRot())));
////		tickDelta.multiply(Vec3f.POSITIVE_X
////				.getDegreesQuaternion(MathHelper.lerp(yaw, entityIn.prevPitch, entityIn.getPitch())));
////		tickDelta.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(45.0F));
//        tickDelta.translate(0, -0.5, 0);
//        tickDelta.scale(3,3,3);
//
//        Minecraft.getInstance().getTextureManager().bindForSetup(getTextureLocation(entityIn));
//        Color renderColor = getRenderColor(entityIn, yaw, tickDelta, matrices, null, vertexConsumers);
//        RenderType renderType = getRenderType(entityIn, yaw, tickDelta, matrices, null, vertexConsumers,
//                getTextureLocation(entityIn));
//        render(model, entityIn, yaw, renderType, tickDelta, matrices, null, vertexConsumers,
//                getPackedOverlay(entityIn, 0), (float) renderColor.getRed() / 255f,
//                (float) renderColor.getGreen() / 255f, (float) renderColor.getBlue() / 255f,
//                (float) renderColor.getAlpha() / 255);
//
//        float lastLimbDistance = 0.0F;
//        float limbSwing = 0.0F;
//        EntityModelData entityModelData = new EntityModelData();
//        AnimationEvent<SubmarineEntity> predicate = new AnimationEvent<>(entityIn, limbSwing, lastLimbDistance, yaw,
//                !(lastLimbDistance > -0.15F && lastLimbDistance < 0.15F), Collections.singletonList(entityModelData));
//
//        ((IAnimatableModel<SubmarineEntity>) modelProvider).setCustomAnimations(entityIn, this.getInstanceId(entityIn), predicate);
//        tickDelta.popPose();
//        super.render(entityIn, entity, yaw, tickDelta, matrices, vertexConsumers);
//    }
//
//    public static int getPackedOverlay(Entity livingEntityIn, float uIn) {
//        return OverlayTexture.pack(OverlayTexture.u(uIn), false);
//    }
//
//    @Override
//    public void setCurrentRTB(MultiBufferSource bufferSource) {
//        this.rtb = bufferSource;
//    }
//
//    @Override
//    public MultiBufferSource getCurrentRTB() {
//        return this.rtb;
//    }
//
//    @Override
//    public GeoModelProvider<SubmarineEntity> getGeoModelProvider() {
//        return this.modelProvider;
//    }
//
//    @Override
//    public @NotNull ResourceLocation getTextureLocation(@NotNull SubmarineEntity instance) {
//        return this.modelProvider.getTextureResource(instance);
//    }
}