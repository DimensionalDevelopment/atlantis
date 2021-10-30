package com.mystic.atlantis.entities.renders;

import java.awt.Color;
import java.util.Collections;

import com.mystic.atlantis.entities.SubmarineEntity;
import com.mystic.atlantis.entities.models.SubmarineEntityModel;
import software.bernie.geckolib3.core.IAnimatableModel;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.GeoModelProvider;
import software.bernie.geckolib3.model.provider.data.EntityModelData;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;

public class SubmarineEntityRenderer extends EntityRenderer<SubmarineEntity> implements IGeoRenderer<SubmarineEntity> {
    private final AnimatedGeoModel<SubmarineEntity> modelProvider;

    public SubmarineEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        this.modelProvider = new SubmarineEntityModel();
    }

    @Override
    public void render(SubmarineEntity entityIn, float entity, float yaw, MatrixStack tickDelta,
                       VertexConsumerProvider matrices, int vertexConsumers) {
        GeoModel model = modelProvider.getModel(modelProvider.getModelLocation(entityIn));
        tickDelta.push();
        tickDelta.multiply(Vec3f.POSITIVE_Y
                .getDegreesQuaternion(-MathHelper.lerp(yaw, entityIn.prevYaw, entityIn.getYaw())));
//		tickDelta.multiply(Vec3f.POSITIVE_X
//				.getDegreesQuaternion(MathHelper.lerp(yaw, entityIn.prevPitch, entityIn.getPitch())));
//		tickDelta.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(45.0F));
        tickDelta.translate(0, -0.5, 0);
        tickDelta.scale(3,3,3);

        MinecraftClient.getInstance().getTextureManager().bindTexture(getTexture(entityIn));
        Color renderColor = getRenderColor(entityIn, yaw, tickDelta, matrices, null, vertexConsumers);
        RenderLayer renderType = getRenderType(entityIn, yaw, tickDelta, matrices, null, vertexConsumers,
                getTexture(entityIn));
        render(model, entityIn, yaw, renderType, tickDelta, matrices, null, vertexConsumers,
                getPackedOverlay(entityIn, 0), (float) renderColor.getRed() / 255f,
                (float) renderColor.getGreen() / 255f, (float) renderColor.getBlue() / 255f,
                (float) renderColor.getAlpha() / 255);

        float lastLimbDistance = 0.0F;
        float limbSwing = 0.0F;
        EntityModelData entityModelData = new EntityModelData();
        AnimationEvent<SubmarineEntity> predicate = new AnimationEvent<>(entityIn, limbSwing, lastLimbDistance, yaw,
                !(lastLimbDistance > -0.15F && lastLimbDistance < 0.15F), Collections.singletonList(entityModelData));

        ((IAnimatableModel<SubmarineEntity>) modelProvider).setLivingAnimations(entityIn, this.getUniqueID(entityIn), predicate);
        tickDelta.pop();
        super.render(entityIn, entity, yaw, tickDelta, matrices, vertexConsumers);
    }

    public static int getPackedOverlay(Entity livingEntityIn, float uIn) {
        return OverlayTexture.getUv(OverlayTexture.getU(uIn), false);
    }

    @Override
    public GeoModelProvider<SubmarineEntity> getGeoModelProvider() {
        return this.modelProvider;
    }

    @Override
    public Identifier getTextureLocation(SubmarineEntity instance) {
        return this.modelProvider.getTextureLocation(instance);
    }

    @Override
    public Identifier getTexture(SubmarineEntity entity) {
        return getTextureLocation(entity);
    }

}