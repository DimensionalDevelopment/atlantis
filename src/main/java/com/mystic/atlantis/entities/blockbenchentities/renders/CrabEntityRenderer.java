package com.mystic.atlantis.entities.blockbenchentities.renders;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mystic.atlantis.entities.blockbenchentities.CrabEntity;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class CrabEntityRenderer extends GeoEntityRenderer<CrabEntity> {

    public CrabEntityRenderer(EntityRendererProvider.Context renderManager, AnimatedGeoModel<CrabEntity> modelProvider) {
        super(renderManager, modelProvider);
    }

    @Override
    public void render(CrabEntity mobEntity, float f, float g, PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i) {
        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
        Entity entity = mobEntity.getLeashHolder();
        if (entity != null) {
            this.method_4073(mobEntity, g, matrixStack, vertexConsumerProvider, entity);
        }
    }

    private <E extends Entity> void method_4073(CrabEntity entity, float tickDelta, PoseStack matrices, MultiBufferSource provider, E holdingEntity) {
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

    private static void method_23187(VertexConsumer vertexConsumer, Matrix4f matrix4f, float f, float g, float h, int i, int j, int k, int l, float m, float n, float o, float p, int q, boolean bl) {
        float r = (float)q / 24.0F;
        int s = (int)Mth.lerp(r, (float)i, (float)j);
        int t = (int)Mth.lerp(r, (float)k, (float)l);
        int u = LightTexture.pack(s, t);
        float v = q % 2 == (bl ? 1 : 0) ? 0.7F : 1.0F;
        float w = 0.5F * v;
        float x = 0.4F * v;
        float y = 0.3F * v;
        float z = f * r;
        float aa = g > 0.0F ? g * r * r : g - g * (1.0F - r) * (1.0F - r);
        float ab = h * r;
        vertexConsumer.vertex(matrix4f, z - o, aa + n, ab + p).color(w, x, y, 1.0F).uv2(u).endVertex();
        vertexConsumer.vertex(matrix4f, z + o, aa + m - n, ab - p).color(w, x, y, 1.0F).uv2(u).endVertex();
    }
}
