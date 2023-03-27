package com.mystic.atlantis.mixin;

import java.util.Objects;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.mystic.atlantis.config.AtlantisConfig;
import com.mystic.atlantis.dimension.DimensionAtlantis;

import net.minecraft.tags.FluidTags;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

@Mixin(Player.class)
public abstract class ChangeBreakSpeedMixin extends LivingEntity {
    @Shadow
    @Final
    private Inventory inventory;

    protected ChangeBreakSpeedMixin(EntityType<? extends LivingEntity> entityType, Level world) {
        super(entityType, world);
    }

    //copied from base with changed lines marked with /*change*/

    /**
     * @author j, Mysticpasta1
     * @reason for breaking speed faster in water in the custom dimension!
     */
    @Inject(method = "getDestroySpeed", at = @At(value = "HEAD"), cancellable = true)
    public void getBlockBreakingSpeed(BlockState block, CallbackInfoReturnable<Float> cir) {
        if (AtlantisConfig.INSTANCE.turnOnDimensionalHaste.get()) {
            cir.cancel();
            float f = this.inventory.getDestroySpeed(block);
            if (f > 1.0F) {
                int i = EnchantmentHelper.getBlockEfficiency(this);
                ItemStack itemStack = this.getMainHandItem();
                if (i > 0 && !itemStack.isEmpty()) {
                    f += (float) (i * i + 1);
                }
            }

            if (MobEffectUtil.hasDigSpeed(this)) {
                f *= 1.0F + (float) (MobEffectUtil.getDigSpeedAmplification(this) + 1) * 0.2F;
            }

            if (this.hasEffect(MobEffects.DIG_SLOWDOWN)) {
                float k = switch (Objects.requireNonNull(this.getEffect(MobEffects.DIG_SLOWDOWN)).getAmplifier()) {
                    case 0 -> 0.3F;
                    case 1 -> 0.09F;
                    case 2 -> 0.0027F;
                    default -> 8.1E-4F;
                };

                f *= k;
            }

            if (level.dimension() == DimensionAtlantis.ATLANTIS_WORLD) {
                if (!this.isEyeInFluid(FluidTags.WATER) && !EnchantmentHelper.hasAquaAffinity(this)) {
                    f /= 5.0F;
                }
            }

            if (level.dimension() == DimensionAtlantis.ATLANTIS_WORLD) {

                if (!this.onGround && !this.isEyeInFluid(FluidTags.WATER)) {
                    f /= 5.0F;
                }
            }

            cir.setReturnValue(f);
        }
    }
}