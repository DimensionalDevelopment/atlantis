package com.mystic.atlantis.mixin;

import com.mystic.atlantis.config.AtlantisConfig;
import com.mystic.atlantis.dimension.DimensionAtlantis;
import com.mystic.atlantis.lighting.AtlantisChunkSkylightProvider;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LightChunkGetter;
import net.minecraft.world.level.lighting.LevelLightEngine;
import net.minecraft.world.level.lighting.LightEngine;
import net.minecraft.world.level.lighting.LightEventListener;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelLightEngine.class)
abstract public class LightingProviderMixin implements LightEventListener {

	@Shadow @Final @Mutable private LightEngine<?, ?> skyEngine;

	@Inject(at = @At("TAIL"), method = "<init>")
	public void init(LightChunkGetter chunkProvider, boolean hasBlockLight, boolean hasSkyLight, CallbackInfo ci) {
		if(chunkProvider.getLevel() instanceof Level level){
			if (DimensionAtlantis.isAtlantisDimension(level) && AtlantisConfig.INSTANCE.shouldHavePerBiomeLighting.get()) {
				skyEngine = hasSkyLight ? new AtlantisChunkSkylightProvider(chunkProvider) : null;
			}
		}
	}
}