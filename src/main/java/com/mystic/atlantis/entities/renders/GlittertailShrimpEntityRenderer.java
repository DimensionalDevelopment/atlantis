package com.mystic.atlantis.entities.renders;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mystic.atlantis.entities.GlittertailShrimpEntity;
import com.mystic.atlantis.entities.models.GlittertailShrimpEntityModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.item.DyeColor;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class GlittertailShrimpEntityRenderer extends GeoEntityRenderer<GlittertailShrimpEntity> {

    public GlittertailShrimpEntityRenderer(EntityRendererProvider.Context ctx, GlittertailShrimpEntityModel modelProvider) {
        super(ctx, modelProvider);
    }

    @Override
    public void render(GlittertailShrimpEntity mobEntity, float entityYaw, float partialTick, PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int packedLight) {
        this.animatable = mobEntity;
        defaultRender(matrixStack, mobEntity, vertexConsumerProvider, null, null, entityYaw, partialTick, packedLight);
        renderStuff(mobEntity, entityYaw, partialTick, matrixStack, vertexConsumerProvider, packedLight);
    }

    private void renderStuff(GlittertailShrimpEntity entity, float entityYaw, float partialTicks, PoseStack stack,
                             MultiBufferSource bufferIn, int packedLightIn) {
        stack.pushPose();
        Minecraft.getInstance().getTextureManager().bindForSetup(getTextureLocation(entity));

        //Shrimp Rainbow color START
        int n = entity.tickCount / 25 + entity.getId();
        int o = DyeColor.values().length;
        int p = n % o;
        int q = (n + 1) % o;
        float r = ((float) (entity.tickCount % 25) + partialTicks) / 25.0F;
        float fs = Sheep.getColor(DyeColor.byId(p));
        float gs = Sheep.getColor(DyeColor.byId(q));
        int color = (int) fs * (1 -  (int) r) + (int) gs * (int) r;
        //Shrimp Rainbow color END

        stack.scale(0.5f, 0.5f, 0.5f);
        renderFinal(stack, entity, this.getGeoModel().getBakedModel(this.getGeoModel().getModelResource(entity)) ,bufferIn, bufferIn.getBuffer(RenderType.cutout()), partialTicks, packedLightIn, getPackedOverlay(entity, 0, partialTicks), color);
        stack.popPose();
    }
}
