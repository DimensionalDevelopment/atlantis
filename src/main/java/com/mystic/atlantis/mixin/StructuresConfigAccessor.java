package com.mystic.atlantis.mixin;

import java.util.Map;

import net.minecraft.world.gen.chunk.StructureConfig;
import net.minecraft.world.gen.chunk.StructuresConfig;
import net.minecraft.world.gen.feature.StructureFeature;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(StructuresConfig.class)
public interface StructuresConfigAccessor {

    @Mutable @Accessor("structures")
    void setStructures(Map<StructureFeature<?>, StructureConfig> structuresSpacingMap);
}