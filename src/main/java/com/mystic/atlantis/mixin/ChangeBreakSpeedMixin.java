package com.mystic.atlantis.mixin;

import com.mystic.atlantis.dimension.DimensionAtlantis;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectUtil;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.FluidTags;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(PlayerEntity.class)
public abstract class ChangeBreakSpeedMixin extends LivingEntity {
    @Shadow
    @Final
    private PlayerInventory inventory;

    protected ChangeBreakSpeedMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    //copied from base with changed lines marked with /*change*/

    /**
     * @author j, Mysticpasta1
     * @reason for breaking speed faster in water in the custom dimension!
     */
    @Inject(method = "getBlockBreakingSpeed", at = @At(value = "HEAD"), cancellable = true)
    public void getBlockBreakingSpeed(BlockState block, CallbackInfoReturnable<Float> cir) {
        cir.cancel();
        float f = this.inventory.getBlockBreakingSpeed(block);
        if (f > 1.0F) {
            int i = EnchantmentHelper.getEfficiency(this);
            ItemStack itemStack = this.getMainHandStack();
            if (i > 0 && !itemStack.isEmpty()) {
                f += (float) (i * i + 1);
            }
        }

        if (StatusEffectUtil.hasHaste(this)) {
            f *= 1.0F + (float) (StatusEffectUtil.getHasteAmplifier(this) + 1) * 0.2F;
        }

        if (this.hasStatusEffect(StatusEffects.MINING_FATIGUE)) {
            float k = switch (Objects.requireNonNull(this.getStatusEffect(StatusEffects.MINING_FATIGUE)).getAmplifier()) {
                case 0 -> 0.3F;
                case 1 -> 0.09F;
                case 2 -> 0.0027F;
                default -> 8.1E-4F;
            };

            f *= k;
        }

        if (world.getRegistryKey() == DimensionAtlantis.ATLANTIS_WORLD) {
            if (!this.isSubmergedIn(FluidTags.WATER) && !EnchantmentHelper.hasAquaAffinity(this)) {
                f /= 5.0F;
            }
        }

        if (world.getRegistryKey() == DimensionAtlantis.ATLANTIS_WORLD) {

            if (!this.onGround && !this.isSubmergedIn(FluidTags.WATER)) {
                f /= 5.0F;
            }
        }

        cir.setReturnValue(f);
    }
}