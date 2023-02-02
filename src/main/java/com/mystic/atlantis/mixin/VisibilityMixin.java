package com.mystic.atlantis.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mystic.atlantis.config.AtlantisConfig;
import com.mystic.atlantis.dimension.DimensionAtlantis;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.world.level.material.FogType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = FogRenderer.class)
public class VisibilityMixin {
    @Inject(method = "setupFog", at = @At(value = "TAIL"), require = 0)
    private static void waterVisibility(Camera camera, FogRenderer.FogMode fogMode, float waterSeeThroughFactor, boolean shouldOverrideWaterFogDensity, float shouldCompletelySeeThroughWater, CallbackInfo ci) {
        FogType cameraSubmersionType = camera.getFluidInCamera();
        AtlantisConfig config = AtlantisConfig.INSTANCE;

        if(DimensionAtlantis.isAtlantisDimension(Minecraft.getInstance().level)) {
            if (cameraSubmersionType == FogType.WATER) {
                float endVal = config.waterVisibility.get().floatValue();
                endVal = endVal > 200 ? 200 : (endVal < 1 ? 1 : endVal);
                RenderSystem.setShaderFogEnd(endVal);
            }
        }
    }
}