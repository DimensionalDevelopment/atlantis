package com.mystic.atlantis.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;

import com.mystic.atlantis.event.ElderPortalEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
	@Shadow public abstract boolean isDead();

	@Inject(method = "applyDamage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/damage/DamageTracker;onDamage(Lnet/minecraft/entity/damage/DamageSource;FF)V"))
	public void onPostEntityDamage(DamageSource source, float amount, CallbackInfo ci) {
		if(isDead()) {
			ElderPortalEvent.checkEntity(((LivingEntity) (Object) this));
		}
	}
}
