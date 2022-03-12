package com.mystic.atlantis.mixin;

import java.util.Map;
import net.minecraft.world.level.levelgen.StructureSettings;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.StructureFeatureConfiguration;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(StructureSettings.class)
public interface StructuresConfigAccessor {
    @Mutable @Accessor("structures")
    void setStructures(Map<StructureFeature<?>, StructureFeatureConfiguration> structuresSpacingMap);
}