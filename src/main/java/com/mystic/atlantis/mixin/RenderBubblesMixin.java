package com.mystic.atlantis.mixin;

import com.mystic.atlantis.dimension.DimensionAtlantis;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class RenderBubblesMixin extends DrawableHelper {


    @Shadow
    @Final
    private MinecraftClient client;
    @Shadow
    private int scaledWidth;
    @Shadow
    private int scaledHeight;
    private LivingEntity getCameraPlayer;

    @Shadow
    protected abstract PlayerEntity getCameraPlayer();

    @Shadow
    protected abstract int getHeartCount(LivingEntity entity);

    @Shadow
    protected abstract int getHeartRows(int heartCount);

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiler/Profiler;swap(Ljava/lang/String;)V", ordinal = 2), method = "renderStatusBars", cancellable = true)
    public void RenderBubbles(MatrixStack matrices, CallbackInfo ci) {
        ci.cancel();
        int ah;
        int ai;
        int ad;
        int ae;
        int o = this.scaledHeight - 39;
        int t = o - 10 - 10;
        int al;
        Entity playerEntity = this.getCameraPlayer();
        LivingEntity livingEntity = this.getCameraPlayer;
        int aa = this.getHeartCount(livingEntity);
        int n = this.scaledWidth / 2 + 91;

        this.client.getProfiler().swap("air");
        ah = playerEntity.getMaxAir();
        ai = Math.min(playerEntity.getAir(), ah);
        if (playerEntity.world.getRegistryKey() == DimensionAtlantis.ATLANTIS_WORLD) {
            if (!playerEntity.isSubmergedIn(FluidTags.WATER) || ai < ah) {/*change*/
                ad = this.getHeartRows(aa) - 1;
                t -= ad * 10;
                ae = MathHelper.ceil((double) (ai - 2) * 10.0D / (double) ah);
                al = MathHelper.ceil((double) ai * 10.0D / (double) ah) - ae;

                for (int ar = 0; ar < ae + al; ++ar) {
                    if (ar < ae) {
                        this.drawTexture(matrices, n - ar * 8 - 9, t, 16, 18, 9, 9);
                    } else {
                        this.drawTexture(matrices, n - ar * 8 - 9, t, 25, 18, 9, 9);
                    }
                }
            }
        } else {
            if (playerEntity.isSubmergedIn(FluidTags.WATER) || ai < ah) {/*change*/
                ad = this.getHeartRows(aa) - 1;
                t -= ad * 10;
                ae = MathHelper.ceil((double) (ai - 2) * 10.0D / (double) ah);
                al = MathHelper.ceil((double) ai * 10.0D / (double) ah) - ae;

                for (int ar = 0; ar < ae + al; ++ar) {
                    if (ar < ae) {
                        this.drawTexture(matrices, n - ar * 8 - 9, t, 16, 18, 9, 9);
                    } else {
                        this.drawTexture(matrices, n - ar * 8 - 9, t, 25, 18, 9, 9);
                    }
                }
            }
        }
        this.client.getProfiler().pop();
    }
}
