package com.mystic.atlantis.event;

import com.mystic.atlantis.dimension.DimensionAtlantis;
import dev.micalobia.breathinglib.BreathingLib;
import dev.micalobia.breathinglib.data.BreathingInfo;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.TypedActionResult;

import java.util.Optional;

public class BreathingLibIntegration {
	public static void register() {
		BreathingLib.register(BreathingLibIntegration::reverseBreathingInAtlantis);
	}

	static TypedActionResult<Optional<BreathingInfo>> reverseBreathingInAtlantis(LivingEntity entity) {
		// Don't touch breathing if we aren't in Atlantis
		if(entity.world.getRegistryKey() != DimensionAtlantis.ATLANTIS_WORLD)
			return TypedActionResult.pass(Optional.empty());
		ActionResult ret = BreathingLib.vanillaBreathing(entity);
		return switch(ret) {
			case SUCCESS -> TypedActionResult.fail(Optional.empty());
			case FAIL -> TypedActionResult.success(Optional.empty());
			case PASS -> TypedActionResult.pass(Optional.empty());
			case CONSUME, CONSUME_PARTIAL -> TypedActionResult.consume(Optional.empty());
		};
	}
}
