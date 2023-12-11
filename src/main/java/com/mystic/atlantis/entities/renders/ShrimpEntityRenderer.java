package com.mystic.atlantis.entities.renders;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mystic.atlantis.entities.Jellyfish2Entity;
import com.mystic.atlantis.entities.ShrimpEntity;
import com.mystic.atlantis.entities.models.ShrimpEntityModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib3.core.IAnimatableModel;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;

import java.util.Collections;

import static com.mystic.atlantis.entities.renders.JellyfishEntityRenderer.method_23187;

public class ShrimpEntityRenderer extends GeoEntityRenderer<ShrimpEntity> {
    public ShrimpEntityRenderer(EntityRendererProvider.Context ctx, ShrimpEntityModel modelProvider) {
        super(ctx, modelProvider);
    }

    @Override
    public void render(ShrimpEntity mobEntity, float f, float g, PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i) {
        renderStuff(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);

        Entity entity = mobEntity.getLeashHolder();
        if (entity != null) {
            this.method_4073(mobEntity, g, matrixStack, vertexConsumerProvider, entity);
        }
    }

    private void renderStuff(ShrimpEntity entity, float entityYaw, float partialTicks, PoseStack stack,
                             MultiBufferSource bufferIn, int packedLightIn) {
        stack.pushPose();
        boolean shouldSit = entity.isPassenger() && (entity.getVehicle() != null);
        EntityModelData entityModelData = new EntityModelData();
        entityModelData.isSitting = shouldSit;
        entityModelData.isChild = entity.isBaby();

        float f = Mth.rotLerp(partialTicks, entity.yBodyRotO, entity.yBodyRot);
        float f1 = Mth.rotLerp(partialTicks, entity.yHeadRotO, entity.yHeadRot);
        float netHeadYaw = f1 - f;
        if (shouldSit && entity.getVehicle() instanceof LivingEntity livingentity) {
            f = Mth.rotLerp(partialTicks, livingentity.yBodyRotO, livingentity.yBodyRot);
            netHeadYaw = f1 - f;
            float f3 = Mth.wrapDegrees(netHeadYaw);
            if (f3 < -85.0F) {
                f3 = -85.0F;
            }

            if (f3 >= 85.0F) {
                f3 = 85.0F;
            }

            f = f1 - f3;
            if (f3 * f3 > 2500.0F) {
                f += f3 * 0.2F;
            }

            netHeadYaw = f1 - f;
        }

        float headPitch = Mth.lerp(partialTicks, entity.xRotO, entity.getXRot());
        if (entity.getPose() == Pose.SLEEPING) {
            Direction direction = entity.getBedOrientation();
            if (direction != null) {
                float f4 = entity.getEyeHeight(Pose.STANDING) - 0.1F;
                stack.translate((float) (-direction.getStepX()) * f4, 0.0D, (float) (-direction.getStepZ()) * f4);
            }
        }
        float f7 = this.handleRotationFloat(entity, partialTicks);
        this.applyRotations(entity, stack, f7, f, partialTicks);

        float lastLimbDistance = 0.0F;
        float limbSwing = 0.0F;
        if (!shouldSit && entity.isAlive()) {
            lastLimbDistance = Mth.lerp(partialTicks, entity.animationSpeedOld, entity.animationSpeed);
            limbSwing = entity.animationPosition - entity.animationSpeed * (1.0F - partialTicks);
            if (entity.isBaby()) {
                limbSwing *= 3.0F;
            }

            if (lastLimbDistance > 1.0F) {
                lastLimbDistance = 1.0F;
            }
        }
        entityModelData.headPitch = -headPitch;
        entityModelData.netHeadYaw = -netHeadYaw;

        AnimationEvent<ShrimpEntity> predicate = new AnimationEvent<>(entity, limbSwing, lastLimbDistance, partialTicks,
                !(lastLimbDistance > -0.15F && lastLimbDistance < 0.15F), Collections.singletonList(entityModelData));
        GeoModel model = getGeoModelProvider().getModel(getGeoModelProvider().getModelLocation(entity));
        if (getGeoModelProvider() instanceof IAnimatableModel) {
            ((IAnimatableModel<ShrimpEntity>) getGeoModelProvider()).setLivingAnimations(entity, this.getUniqueID(entity), predicate);
        }

        stack.translate(0, 0.01f, 0);
        Minecraft.getInstance().getTextureManager().bindForSetup(getTextureLocation(entity));

        //Shrimp Rainbow color START
        int n = entity.tickCount / 25 + entity.getId();
        int o = DyeColor.values().length;
        int p = n % o;
        int q = (n + 1) % o;
        float r = ((float)(entity.tickCount % 25) + partialTicks) / 25.0F;
        float[] fs = Sheep.getColorArray(DyeColor.byId(p));
        float[] gs = Sheep.getColorArray(DyeColor.byId(q));
        float rColor = fs[0] * (1.0F - r) + gs[0] * r;
        float gColor = fs[1] * (1.0F - r) + gs[1] * r;
        float bColor = fs[2] * (1.0F - r) + gs[2] * r;
        //Shrimp Rainbow color END

        stack.scale(0.5f, 0.5f, 0.5f);

        RenderType renderType = getRenderType(entity, partialTicks, stack, bufferIn, null, packedLightIn,
                getTextureLocation(entity));
        boolean invis = entity.isInvisibleTo(Minecraft.getInstance().player);
        render(model, entity, partialTicks, renderType, stack, bufferIn, null, packedLightIn,
                OverlayTexture.pack(0, 0), rColor, gColor,
                bColor, invis ? 0.0F : 1f);

        if (!entity.isSpectator()) {
            for (GeoLayerRenderer<ShrimpEntity> layerRenderer : this.layerRenderers) {
                layerRenderer.render(stack, bufferIn, packedLightIn, entity, limbSwing, lastLimbDistance, partialTicks,
                        f7, netHeadYaw, headPitch);
            }
        }
//        if (FabricLoader.getInstance().isModLoaded("patchouli")) {
//            PatchouliCompat.patchouliLoaded(stack);
//        }
        stack.popPose();

        if (this.shouldShowName(entity)) {
            this.renderNameTag(entity, entity.getDisplayName(), stack, bufferIn, packedLightIn);
        }
    }

    protected float handleRotationFloat(ShrimpEntity livingBase, float partialTicks) {
        return (float) livingBase.tickCount + partialTicks;
    }

    private <E extends Entity> void method_4073(ShrimpEntity entity, float tickDelta, PoseStack matrices, MultiBufferSource provider, E holdingEntity) {
        matrices.pushPose();
        Vec3 vec3d = holdingEntity.getViewVector(tickDelta);
        double d = (double)(Mth.lerp(tickDelta, entity.yBodyRot, entity.yBodyRotO) * 0.017453292F) + 1.5707963267948966D;
        Vec3 vec3d2 = entity.getLeashOffset();
        double e = Math.cos(d) * vec3d2.z + Math.sin(d) * vec3d2.x;
        double f = Math.sin(d) * vec3d2.z - Math.cos(d) * vec3d2.x;
        double g = Mth.lerp(tickDelta, entity.xo, entity.getX()) + e;
        double h = Mth.lerp(tickDelta, entity.yo, entity.getY()) + vec3d2.y;
        double i = Mth.lerp(tickDelta, entity.zo, entity.getZ()) + f;
        matrices.translate(e, vec3d2.y, f);
        float j = (float)(vec3d.x - g);
        float k = (float)(vec3d.y - h);
        float l = (float)(vec3d.z - i);
        float m = 0.025F;
        VertexConsumer vertexConsumer = provider.getBuffer(RenderType.leash());
        Matrix4f matrix4f = matrices.last().pose();
        float n = Mth.fastInvSqrt(j * j + l * l) * 0.025F / 2.0F;
        float o = l * n;
        float p = j * n;
        BlockPos blockPos = new BlockPos(entity.getEyePosition(tickDelta));
        BlockPos blockPos2 = new BlockPos(holdingEntity.getEyePosition(tickDelta));
        int q = this.getBlockLightLevel(entity, blockPos);
        int r = this.entityRenderDispatcher.getRenderer(holdingEntity).getBlockLightLevel(holdingEntity, blockPos2);
        int s = entity.level.getBrightness(LightLayer.SKY, blockPos);
        int t = entity.level.getBrightness(LightLayer.SKY, blockPos2);

        int v;
        for(v = 0; v <= 24; ++v) {
            method_23187(vertexConsumer, matrix4f, j, k, l, q, r, s, t, 0.025F, 0.025F, o, p, v, false);
        }

        for(v = 24; v >= 0; --v) {
            method_23187(vertexConsumer, matrix4f, j, k, l, q, r, s, t, 0.025F, 0.0F, o, p, v, true);
        }

        matrices.popPose();
    }
}
