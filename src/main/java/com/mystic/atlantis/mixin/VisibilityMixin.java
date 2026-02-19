package com.mystic.atlantis.mixin;

import com.mystic.atlantis.init.ModDimensions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mystic.atlantis.config.AtlantisConfig;

import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.world.level.material.FogType;

@Mixin(value = FogRenderer.class)
public class VisibilityMixin {
    @Inject(method = "setupFog", at = @At(value = "TAIL"), require = 0)
    private static void waterVisibility(Camera camera, FogRenderer.FogMode fogMode, float waterSeeThroughFactor, boolean shouldOverrideWaterFogDensity, float shouldCompletelySeeThroughWater, CallbackInfo ci) {
        FogType cameraSubmersionType = camera.getFluidInCamera();
        AtlantisConfig config = AtlantisConfig.INSTANCE;

        if (Minecraft.getInstance().level != null
                && Minecraft.getInstance().level.dimension().equals(ModDimensions.ATLANTIS_WORLD)
                && cameraSubmersionType == FogType.WATER) {

            // Completely remove underwater fog
            RenderSystem.setShaderFogStart(0.0F);
            RenderSystem.setShaderFogEnd(1000000.0F); // very far away
        }
    }
}