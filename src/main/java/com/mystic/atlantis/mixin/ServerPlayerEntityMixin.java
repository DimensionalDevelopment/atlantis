package com.mystic.atlantis.mixin;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;

import com.mystic.atlantis.event.DimensionEffectTimed;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin {
	@Shadow public abstract ServerWorld getServerWorld();

	@Inject(method = "tick", at = @At(value = "TAIL"))
	public void playerTick(CallbackInfo ci) {
		DimensionEffectTimed.playerTick(((ServerPlayerEntity) (Object) this));
	}
}
