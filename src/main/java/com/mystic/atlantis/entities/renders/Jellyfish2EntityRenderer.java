package com.mystic.atlantis.entities.renders;

import java.util.Collections;

import com.mystic.atlantis.entities.Jellyfish2Entity;
import software.bernie.geckolib3.compat.PatchouliCompat;
import software.bernie.geckolib3.core.IAnimatableModel;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LightType;

import net.fabricmc.loader.api.FabricLoader;

public class Jellyfish2EntityRenderer extends GeoEntityRenderer<Jellyfish2Entity> {

    public Jellyfish2EntityRenderer(EntityRendererFactory.Context renderManager, AnimatedGeoModel<Jellyfish2Entity> modelProvider) {
        super(renderManager, modelProvider);
    }

    @Override
    public void render(Jellyfish2Entity mobEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        renderStuff(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);

        Entity entity = mobEntity.getHoldingEntity();
        if (entity != null) {
            this.method_4073(mobEntity, g, matrixStack, vertexConsumerProvider, entity);
        }
    }

    private void renderStuff(Jellyfish2Entity entity, float entityYaw, float partialTicks, MatrixStack stack,
                             VertexConsumerProvider bufferIn, int packedLightIn) {
        stack.push();
        boolean shouldSit = entity.hasVehicle() && (entity.getVehicle() != null);
        EntityModelData entityModelData = new EntityModelData();
        entityModelData.isSitting = shouldSit;
        entityModelData.isChild = entity.isBaby();

        float f = MathHelper.lerpAngleDegrees(partialTicks, entity.prevBodyYaw, entity.bodyYaw);
        float f1 = MathHelper.lerpAngleDegrees(partialTicks, entity.prevHeadYaw, entity.headYaw);
        float netHeadYaw = f1 - f;
        if (shouldSit && entity.getVehicle() instanceof LivingEntity livingentity) {
            f = MathHelper.lerpAngleDegrees(partialTicks, livingentity.prevBodyYaw, livingentity.bodyYaw);
            netHeadYaw = f1 - f;
            float f3 = MathHelper.wrapDegrees(netHeadYaw);
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

        float headPitch = MathHelper.lerp(partialTicks, entity.prevPitch, entity.getPitch());
        if (entity.getPose() == EntityPose.SLEEPING) {
            Direction direction = entity.getSleepingDirection();
            if (direction != null) {
                float f4 = entity.getEyeHeight(EntityPose.STANDING) - 0.1F;
                stack.translate((float) (-direction.getOffsetX()) * f4, 0.0D, (float) (-direction.getOffsetZ()) * f4);
            }
        }
        float f7 = this.handleRotationFloat(entity, partialTicks);
        this.applyRotations(entity, stack, f7, f, partialTicks);

        float lastLimbDistance = 0.0F;
        float limbSwing = 0.0F;
        if (!shouldSit && entity.isAlive()) {
            lastLimbDistance = MathHelper.lerp(partialTicks, entity.lastLimbDistance, entity.limbDistance);
            limbSwing = entity.limbAngle - entity.limbDistance * (1.0F - partialTicks);
            if (entity.isBaby()) {
                limbSwing *= 3.0F;
            }

            if (lastLimbDistance > 1.0F) {
                lastLimbDistance = 1.0F;
            }
        }
        entityModelData.headPitch = -headPitch;
        entityModelData.netHeadYaw = -netHeadYaw;

        AnimationEvent<Jellyfish2Entity> predicate = new AnimationEvent<Jellyfish2Entity>(entity, limbSwing, lastLimbDistance, partialTicks,
                !(lastLimbDistance > -0.15F && lastLimbDistance < 0.15F), Collections.singletonList(entityModelData));
        GeoModel model = getGeoModelProvider().getModel(getGeoModelProvider().getModelLocation(entity));
        if (getGeoModelProvider() instanceof IAnimatableModel) {
            ((IAnimatableModel<Jellyfish2Entity>) getGeoModelProvider()).setLivingAnimations(entity, this.getUniqueID(entity), predicate);
        }

        stack.translate(0, 0.01f, 0);
        MinecraftClient.getInstance().getTextureManager().bindTexture(getTexture(entity));
        int renderColor = entity.getColor();
        RenderLayer renderType = getRenderType(entity, partialTicks, stack, bufferIn, null, packedLightIn,
                getTexture(entity));
        boolean invis = entity.isInvisibleTo(MinecraftClient.getInstance().player);
        render(model, entity, partialTicks, renderType, stack, bufferIn, null, packedLightIn,
                getPackedOverlay(entity, 0), (float) ((renderColor >> 16) & 0xFF) / 255f, (float) ((renderColor >> 8) & 0xFF) / 255f,
                (float) ((renderColor) & 0xFF) / 255f, invis ? 0.0F : 125f / 255f);

        if (!entity.isSpectator()) {
            for (GeoLayerRenderer<Jellyfish2Entity> layerRenderer : this.layerRenderers) {
                layerRenderer.render(stack, bufferIn, packedLightIn, entity, limbSwing, lastLimbDistance, partialTicks,
                        f7, netHeadYaw, headPitch);
            }
        }
        if (FabricLoader.getInstance().isModLoaded("patchouli")) {
            PatchouliCompat.patchouliLoaded(stack);
        }
        stack.pop();

        if (this.hasLabel(entity)) {
            this.renderLabelIfPresent(entity, entity.getDisplayName(), stack, bufferIn, packedLightIn);
        }
    }

    private <E extends Entity> void method_4073(Jellyfish2Entity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider provider, E holdingEntity) {
        matrices.push();
        Vec3d vec3d = holdingEntity.getRotationVec(tickDelta);
        double d = (double)(MathHelper.lerp(tickDelta, entity.bodyYaw, entity.prevBodyYaw) * 0.017453292F) + 1.5707963267948966D;
        Vec3d vec3d2 = entity.getLeashOffset();
        double e = Math.cos(d) * vec3d2.z + Math.sin(d) * vec3d2.x;
        double f = Math.sin(d) * vec3d2.z - Math.cos(d) * vec3d2.x;
        double g = MathHelper.lerp(tickDelta, entity.prevX, entity.getX()) + e;
        double h = MathHelper.lerp(tickDelta, entity.prevY, entity.getY()) + vec3d2.y;
        double i = MathHelper.lerp(tickDelta, entity.prevZ, entity.getZ()) + f;
        matrices.translate(e, vec3d2.y, f);
        float j = (float)(vec3d.x - g);
        float k = (float)(vec3d.y - h);
        float l = (float)(vec3d.z - i);
        float m = 0.025F;
        VertexConsumer vertexConsumer = provider.getBuffer(RenderLayer.getLeash());
        Matrix4f matrix4f = matrices.peek().getPositionMatrix();
        float n = MathHelper.fastInverseSqrt(j * j + l * l) * 0.025F / 2.0F;
        float o = l * n;
        float p = j * n;
        BlockPos blockPos = new BlockPos(entity.getCameraPosVec(tickDelta));
        BlockPos blockPos2 = new BlockPos(holdingEntity.getCameraPosVec(tickDelta));
        int q = this.getBlockLight(entity, blockPos);
        int r = this.dispatcher.getRenderer(holdingEntity).getBlockLight(holdingEntity, blockPos2);
        int s = entity.world.getLightLevel(LightType.SKY, blockPos);
        int t = entity.world.getLightLevel(LightType.SKY, blockPos2);

        int v;
        for(v = 0; v <= 24; ++v) {
            method_23187(vertexConsumer, matrix4f, j, k, l, q, r, s, t, 0.025F, 0.025F, o, p, v, false);
        }

        for(v = 24; v >= 0; --v) {
            method_23187(vertexConsumer, matrix4f, j, k, l, q, r, s, t, 0.025F, 0.0F, o, p, v, true);
        }

        matrices.pop();
    }

    private static void method_23187(VertexConsumer vertexConsumer, Matrix4f matrix4f, float f, float g, float h, int i, int j, int k, int l, float m, float n, float o, float p, int q, boolean bl) {
        float r = (float)q / 24.0F;
        int s = (int)MathHelper.lerp(r, (float)i, (float)j);
        int t = (int)MathHelper.lerp(r, (float)k, (float)l);
        int u = LightmapTextureManager.pack(s, t);
        float v = q % 2 == (bl ? 1 : 0) ? 0.7F : 1.0F;
        float w = 0.5F * v;
        float x = 0.4F * v;
        float y = 0.3F * v;
        float z = f * r;
        float aa = g > 0.0F ? g * r * r : g - g * (1.0F - r) * (1.0F - r);
        float ab = h * r;
        vertexConsumer.vertex(matrix4f, z - o, aa + n, ab + p).color(w, x, y, 1.0F).light(u).next();
        vertexConsumer.vertex(matrix4f, z + o, aa + m - n, ab - p).color(w, x, y, 1.0F).light(u).next();
    }
}
