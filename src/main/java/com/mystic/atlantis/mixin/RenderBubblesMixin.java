package com.mystic.atlantis.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mystic.atlantis.dimension.AtlantisDimensions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@OnlyIn(Dist.CLIENT)
@Mixin(Gui.class)
public abstract class RenderBubblesMixin {
    @Shadow protected static final ResourceLocation AIR_SPRITE = ResourceLocation.parse("hud/air");
    @Shadow protected static final ResourceLocation AIR_BURSTING_SPRITE = ResourceLocation.parse("hud/air_bursting");
    @Shadow public int rightHeight;

    @Shadow
    @Final
    protected Minecraft minecraft;

    @Shadow
    protected abstract Player getCameraPlayer();

    @Inject(at = @At(value = "HEAD"), method = "renderAirLevel", cancellable = true)
    public void RenderBubbles(GuiGraphics p_283143_, CallbackInfo ci) {
        ci.cancel();
        Player player = this.getCameraPlayer();
        if (player != null) {
            int i1 = p_283143_.guiWidth() / 2 + 91;

            this.minecraft.getProfiler().push("air");
            int i3 = player.getMaxAirSupply();
            int j3 = Math.min(player.getAirSupply(), i3);
            if (player.level().dimension() == AtlantisDimensions.ATLANTIS_WORLD) {
                if (!player.isEyeInFluid(FluidTags.WATER) || j3 < i3) {
                    int j2 = p_283143_.guiHeight() - rightHeight;
                    int l3 = Mth.ceil((double)(j3 - 2) * 10.0 / (double)i3);
                    int i4 = Mth.ceil((double)j3 * 10.0 / (double)i3) - l3;
                    RenderSystem.enableBlend();

                    for (int j4 = 0; j4 < l3 + i4; j4++) {
                        if (j4 < l3) {
                            p_283143_.blitSprite(AIR_SPRITE, i1 - j4 * 8 - 9, j2, 9, 9);
                        } else {
                            p_283143_.blitSprite(AIR_BURSTING_SPRITE, i1 - j4 * 8 - 9, j2, 9, 9);
                        }
                    }

                    RenderSystem.disableBlend();
                    rightHeight += 10;
                }
            } else {
                if (player.isEyeInFluid(FluidTags.WATER) || j3 < i3) {
                    int j2 = p_283143_.guiHeight() - rightHeight;
                    int l3 = Mth.ceil((double)(j3 - 2) * 10.0 / (double)i3);
                    int i4 = Mth.ceil((double)j3 * 10.0 / (double)i3) - l3;
                    RenderSystem.enableBlend();

                    for (int j4 = 0; j4 < l3 + i4; j4++) {
                        if (j4 < l3) {
                            p_283143_.blitSprite(AIR_SPRITE, i1 - j4 * 8 - 9, j2, 9, 9);
                        } else {
                            p_283143_.blitSprite(AIR_BURSTING_SPRITE, i1 - j4 * 8 - 9, j2, 9, 9);
                        }
                    }

                    RenderSystem.disableBlend();
                    rightHeight += 10;
                }
            }

            this.minecraft.getProfiler().pop();
        }
    }
}