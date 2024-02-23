package com.mystic.atlantis.entities.renders;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mystic.atlantis.entities.ShrimpEntity;
import com.mystic.atlantis.entities.models.ShrimpEntityModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.item.DyeColor;
import mod.azure.azurelib.common.api.client.renderer.GeoEntityRenderer;

public class ShrimpEntityRenderer extends GeoEntityRenderer<ShrimpEntity> {

    public ShrimpEntityRenderer(EntityRendererProvider.Context ctx, ShrimpEntityModel modelProvider) {
        super(ctx, modelProvider);
    }

    @Override
    public void render(ShrimpEntity mobEntity, float entityYaw, float partialTick, PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int packedLight) {
        this.animatable = mobEntity;
        defaultRender(matrixStack, mobEntity, vertexConsumerProvider, null, null, entityYaw, partialTick, packedLight);
        renderStuff(mobEntity, entityYaw, partialTick, matrixStack, vertexConsumerProvider, packedLight);
    }

    private void renderStuff(ShrimpEntity entity, float entityYaw, float partialTicks, PoseStack stack, MultiBufferSource bufferIn, int packedLightIn) {
        stack.pushPose();
        Minecraft.getInstance().getTextureManager().bindForSetup(getTextureLocation(entity));

        //Shrimp Rainbow color START
        int n = entity.tickCount / 25 + entity.getId();
        int o = DyeColor.values().length;
        int p = n % o;
        int q = (n + 1) % o;
        float r = ((float) (entity.tickCount % 25) + partialTicks) / 25.0F;
        float[] fs = Sheep.getColorArray(DyeColor.byId(p));
        float[] gs = Sheep.getColorArray(DyeColor.byId(q));
        float rColor = fs[0] * (1.0F - r) + gs[0] * r;
        float gColor = fs[1] * (1.0F - r) + gs[1] * r;
        float bColor = fs[2] * (1.0F - r) + gs[2] * r;
        //Shrimp Rainbow color END

        stack.scale(0.5f, 0.5f, 0.5f);
        renderFinal(stack, entity, this.getGeoModel().getBakedModel(this.getGeoModel().getModelResource(entity)) ,bufferIn, bufferIn.getBuffer(RenderType.cutout()), partialTicks, packedLightIn, getPackedOverlay(entity, 0), rColor, gColor,  bColor, 255.0f);
        stack.popPose();
    }
}
