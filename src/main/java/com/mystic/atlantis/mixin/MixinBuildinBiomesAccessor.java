package com.mystic.atlantis.mixin;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BuiltinBiomes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BuiltinBiomes.class)
public interface MixinBuildinBiomesAccessor {
    @Accessor("BY_RAW_ID")
    static Int2ObjectMap<RegistryKey<Biome>> getIdMap() {
        throw new AssertionError();
    }
}
