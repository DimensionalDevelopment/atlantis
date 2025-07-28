package com.mystic.atlantis.mixin;

import com.mystic.atlantis.config.AtlantisConfig;
import com.mystic.atlantis.dimension.AtlantisDimensions;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(Player.class)
public abstract class ChangeBreakSpeedMixin extends LivingEntity {
    @Shadow
    @Final
    private Inventory inventory;

    @Shadow public abstract ItemStack getItemBySlot(EquipmentSlot slot1);

    protected ChangeBreakSpeedMixin(EntityType<? extends LivingEntity> entityType, Level world) {
        super(entityType, world);
    }

    /**
     * @author j, Mysticpasta1
     * @reason for breaking speed faster in water in the custom dimension!
     */
    @Inject(method = "getDestroySpeed", at = @At(value = "HEAD"), cancellable = true)
    public void getBlockBreakingSpeed(BlockState block, CallbackInfoReturnable<Float> cir) {
        if (AtlantisConfig.CONFIG.turnOnDimensionalHaste.get()) {
            cir.cancel();
            float f = this.inventory.getDestroySpeed(block);
            if (f > 1.0F) {
                float i = block.getBlock().getSpeedFactor();
                ItemStack itemStack = this.getMainHandItem();
                if (i > 0 && !itemStack.isEmpty()) {
                    f += i * i + 1;
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

            if (level().dimension() == AtlantisDimensions.ATLANTIS_WORLD) {

                if (!this.onGround() && !this.isEyeInFluid(FluidTags.WATER)) {
                    f /= 5.0F;
                }
            }

            cir.setReturnValue(f);
        }
    }
}