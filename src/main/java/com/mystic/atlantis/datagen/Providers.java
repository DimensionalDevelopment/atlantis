package com.mystic.atlantis.datagen;

import com.mystic.atlantis.JukeboxSongsInit;
import com.mystic.atlantis.dimension.AtlantisDimensions;
import com.mystic.atlantis.init.*;
import com.mystic.atlantis.util.Reference;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.dimension.DimensionType;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.bus.api.IEventBus;

import java.nio.file.Path;
import java.util.OptionalLong;
import java.util.Set;

public class Providers {
    public static void init(IEventBus bus) {
        bus.addListener(Providers::dataGather);
    }

    public static void dataGather(GatherDataEvent event) {
        PackOutput output = event.getGenerator().getPackOutput();

        var registryProvider = new DatapackBuiltinEntriesProvider(
                output, event.getLookupProvider(),
                new RegistrySetBuilder()
                        .add(Registries.ENCHANTMENT, EnchantmentInit::new)
                        .add(Registries.CONFIGURED_FEATURE, ConfiguredFeaturesInit::new)
                        .add(Registries.PLACED_FEATURE, PlacedFeatureInit::new)
                        .add(Registries.BIOME, BiomeInit::new)
                        .add(Registries.LEVEL_STEM, AtlantisDimensions::new)
                        .add(Registries.DIMENSION_TYPE, context -> context.register(AtlantisDimensions.ATLANTIS_DIMENSION_TYPE_KEY, new DimensionType(
                                OptionalLong.empty(),
                                true, false, false, false, 1, true, true, -64, 512, 512, BlockTags.INFINIBURN_OVERWORLD, AtlantisDimensions.ATLANTIS_DIMENSION_EFFECT, 0, new DimensionType.MonsterSettings(false, false, UniformInt.of(0, 7), 0)
                        )))
                        .add(Registries.PROCESSOR_LIST, ProcessorListInit::new)
                        .add(Registries.TEMPLATE_POOL, TemplatePoolInit::new)
                        .add(Registries.NOISE_SETTINGS, NoiseSettingsInit::new)
                        .add(Registries.STRUCTURE, StructureInit::new)
                        .add(Registries.PAINTING_VARIANT, PaintingVariantsInit::init)
                        .add(Registries.JUKEBOX_SONG, JukeboxSongsInit::new),
                Set.of(Reference.MODID)
        );

        event.getGenerator().addProvider(event.includeServer(),
                new AtlantisModifierInit.DataProvider(event.getGenerator(), event.getLookupProvider(), Reference.MODID));

        event.getGenerator().addProvider(true, registryProvider);
        event.getGenerator().addProvider(true, new AtlantisBlockModelProvider(output, event.getExistingFileHelper()));
        event.getGenerator().addProvider(true, new AtlantisMainProvider(output, event.getExistingFileHelper(), AtlantisBlockStateProvider::new));
        event.getGenerator().addProvider(true, new AtlantisItemModelProvider(output, event.getExistingFileHelper()));
        event.getGenerator().addProvider(true, new AtlantisEnglishLanguageProvider(output));

        // Loot
        event.getGenerator().addProvider(true, new AtlantisLootTableProvider(output, event.getLookupProvider()));

        // Tags
        var blockTags = new AtlantisBlockTagsProvider(output, event.getLookupProvider(), Reference.MODID);
        event.getGenerator().addProvider(true, blockTags);
        event.getGenerator().addProvider(true, new AtlantisItemTagsProvider(output, event.getLookupProvider(), blockTags, Reference.MODID));
        event.getGenerator().addProvider(true, new AtlantisFluidTagsProvider(output, event.getLookupProvider(), Reference.MODID, event.getExistingFileHelper()));
        event.getGenerator().addProvider(true, new AtlantisRecipeProvider(output, event.getLookupProvider()));
    }
}
