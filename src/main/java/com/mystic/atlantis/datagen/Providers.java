package com.mystic.atlantis.datagen;

import com.mystic.atlantis.Atlantis;
import com.mystic.atlantis.JukeboxSongsInit;
import com.mystic.atlantis.TagsInit;
import com.mystic.atlantis.blocks.BlockType;
import com.mystic.atlantis.blocks.ancient_cuprum.TrailsGroup;
import com.mystic.atlantis.blocks.ancient_cuprum.WeatheringCuprum;
import com.mystic.atlantis.dimension.AtlantisDimensions;
import com.mystic.atlantis.init.*;
import com.mystic.atlantis.items.armor.AtlantisArmorSet;
import com.mystic.atlantis.recipes.WritingRecipe;
import com.mystic.atlantis.util.Reference;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.data.recipes.*;
import net.minecraft.data.tags.FluidTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.OptionalLong;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;

import static com.mystic.atlantis.init.BlockInit.*;

public class Providers {
    public static void init(IEventBus bus) {
        bus.addListener(Providers::dataGather);
    }

    public static void dataGather(GatherDataEvent event) {
        var output = event.getGenerator().getPackOutput();

        var registryProvider = new DatapackBuiltinEntriesProvider(output, event.getLookupProvider(), new RegistrySetBuilder()
                .add(Registries.ENCHANTMENT, EnchantmentInit::new)
                .add(Registries.CONFIGURED_FEATURE, ConfiguredFeaturesInit::new)
                .add(Registries.PLACED_FEATURE, PlacedFeatureInit::new)
                .add(Registries.DIMENSION_TYPE, context -> context.register(AtlantisDimensions.ATLANTIS_DIMENSION_TYPE_KEY, new DimensionType(
                        OptionalLong.empty(),
                        true, false, false, false, 1, true, true, -64, 512, 512, BlockTags.INFINIBURN_OVERWORLD, AtlantisDimensions.ATLANTIS_DIMENSION_EFFECT, 0, new DimensionType.MonsterSettings(false, false, UniformInt.of(0, 7), 0)
                )))
                .add(Registries.BIOME, BiomeInit::new)
                .add(Registries.LEVEL_STEM, AtlantisDimensions::new)
                .add(Registries.PROCESSOR_LIST, ProcessorListInit::new)
                .add(Registries.TEMPLATE_POOL, TemplatePoolInit::new)
                .add(Registries.NOISE_SETTINGS, NoiseSettingsInit::new)
                .add(Registries.STRUCTURE, StructureInit::new)
                .add(Registries.PAINTING_VARIANT, PaintingVariantsInit::init)
                .add(Registries.JUKEBOX_SONG, JukeboxSongsInit::new),
                Set.of(Reference.MODID));

        event.getGenerator().addProvider(event.includeServer(), new AtlantisModifierInit.DataProvider(event.getGenerator(), event.getLookupProvider(), Reference.MODID));

        event.getGenerator().addProvider(true, registryProvider);
        event.getGenerator().addProvider(true, new AtlantisBlockModelProvider(output, event.getExistingFileHelper()));
        event.getGenerator().addProvider(true, new AtlantisMainProvider(output, event.getExistingFileHelper(), AtlantisBlockStateProvider::new));
        event.getGenerator().addProvider(true, new AtlantisItemModelProvider(output, event.getExistingFileHelper()));
        event.getGenerator().addProvider(true, new AtlantisEnglishLanguageProvider(output));
        event.getGenerator().addProvider(true, new MyRecipeProvider(output, event));

        LootTableProvider.SubProviderEntry lootTableProvider = new LootTableProvider.SubProviderEntry(new Function<HolderLookup.Provider, LootTableSubProvider>() {
            @Override
            public LootTableSubProvider apply(HolderLookup.Provider provider) {
                var silkTouch = provider.asGetterLookup().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.SILK_TOUCH);

                var silkTouchPredicate = ItemEnchantmentsPredicate.enchantments(List.of(new EnchantmentPredicate(silkTouch, MinMaxBounds.Ints.ANY)));

                return new LootTableSubProvider() {
                    @Override
                    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> consumer)  {

                            for (TrailsGroup group : BlockInit.ANCIENT_CUPRUM.values()) {
                                dropSelf(group.block().get(), consumer);
                                dropSelf(group.bulb().get(), consumer);
                                dropSelf(group.grate().get(), consumer);
                                dropSelf(group.cut().get(), consumer);
                                dropSelf(group.cut_slab().get(), consumer);
                                dropSelf(group.cut_stairs().get(), consumer);
                                dropSelf(group.chiseled().get(), consumer);
                                dropSelf(group.door().get(), consumer);
                                dropSelf(group.trapdoor().get(), consumer);
                                dropSelf(group.waxed_block().get(), consumer);
                                dropSelf(group.waxed_bulb().get(), consumer);
                                dropSelf(group.waxed_grate().get(), consumer);
                                dropSelf(group.waxed_cut().get(), consumer);
                                dropSelf(group.waxed_cut_slab().get(), consumer);
                                dropSelf(group.waxed_cut_stairs().get(), consumer);
                                dropSelf(group.waxed_chiseled().get(), consumer);
                                dropSelf(group.waxed_door().get(), consumer);
                                dropSelf(group.waxed_trapdoor().get(), consumer);
                            }



                            for (DeferredBlock<Block> block : COLORED_SHELL_BLOCKS.values()) {
                                dropSelfIfSilkTouchedOrItem(block.get(), ItemInit.BROKEN_SHELLS.get(), consumer, silkTouchPredicate);
                            }

                            for (DeferredBlock<Block> block : MOSSY_SHELL_BLOCKS.values()) {
                                dropSelfIfSilkTouchedOrItem(block.get(), ItemInit.BROKEN_SHELLS.get(), consumer, silkTouchPredicate);
                            }

                            for (DeferredBlock<Block> block : CRACKED_MOSSY_SHELL_BLOCKS.values()) {
                                dropSelfIfSilkTouchedOrItem(block.get(), ItemInit.BROKEN_SHELLS.get(), consumer, silkTouchPredicate);
                            }

                            for (DeferredBlock<Block> block : CRACKED_SHELL_BLOCKS.values()) {
                                dropSelfIfSilkTouchedOrItem(block.get(), ItemInit.BROKEN_SHELLS.get(), consumer, silkTouchPredicate);
                            }

                            dropSelf(ANCIENT_ACACIA.block().get(), consumer);
                            dropSelf(ANCIENT_ACACIA.door().get(), consumer);
                            dropSelf(ANCIENT_ACACIA.fence().get(), consumer);
                            dropSelf(ANCIENT_ACACIA.fenceGate().get(), consumer);
                            dropSelf(ANCIENT_ACACIA.slab().get(), consumer);
                            dropSelf(ANCIENT_ACACIA.stairs().get(), consumer);
                            dropSelf(ANCIENT_ACACIA.trapDoor().get(), consumer);
                            dropSelf(ANCIENT_ACACIA.pressurePlate().get(), consumer);
                            dropSelf(ANCIENT_ACACIA.button().get(), consumer);

                            dropSelf(ANCIENT_BIRCH.block().get(), consumer);
                            dropSelf(ANCIENT_BIRCH.door().get(), consumer);
                            dropSelf(ANCIENT_BIRCH.fence().get(), consumer);
                            dropSelf(ANCIENT_BIRCH.fenceGate().get(), consumer);
                            dropSelf(ANCIENT_BIRCH.slab().get(), consumer);
                            dropSelf(ANCIENT_BIRCH.stairs().get(), consumer);
                            dropSelf(ANCIENT_BIRCH.trapDoor().get(), consumer);
                            dropSelf(ANCIENT_BIRCH.pressurePlate().get(), consumer);
                            dropSelf(ANCIENT_BIRCH.button().get(), consumer);

                            dropSelf(ANCIENT_OAK.block().get(), consumer);
                            dropSelf(ANCIENT_OAK.door().get(), consumer);
                            dropSelf(ANCIENT_OAK.fence().get(), consumer);
                            dropSelf(ANCIENT_OAK.fenceGate().get(), consumer);
                            dropSelf(ANCIENT_OAK.slab().get(), consumer);
                            dropSelf(ANCIENT_OAK.stairs().get(), consumer);
                            dropSelf(ANCIENT_OAK.trapDoor().get(), consumer);
                            dropSelf(ANCIENT_OAK.pressurePlate().get(), consumer);
                            dropSelf(ANCIENT_OAK.button().get(), consumer);

                            dropSelf(ANCIENT_DARK_OAK.block().get(), consumer);
                            dropSelf(ANCIENT_DARK_OAK.door().get(), consumer);
                            dropSelf(ANCIENT_DARK_OAK.fence().get(), consumer);
                            dropSelf(ANCIENT_DARK_OAK.fenceGate().get(), consumer);
                            dropSelf(ANCIENT_DARK_OAK.slab().get(), consumer);
                            dropSelf(ANCIENT_DARK_OAK.stairs().get(), consumer);
                            dropSelf(ANCIENT_DARK_OAK.trapDoor().get(), consumer);
                            dropSelf(ANCIENT_DARK_OAK.pressurePlate().get(), consumer);
                            dropSelf(ANCIENT_DARK_OAK.button().get(), consumer);

                            dropSelf(ANCIENT_JUNGLE.block().get(), consumer);
                            dropSelf(ANCIENT_JUNGLE.door().get(), consumer);
                            dropSelf(ANCIENT_JUNGLE.fence().get(), consumer);
                            dropSelf(ANCIENT_JUNGLE.fenceGate().get(), consumer);
                            dropSelf(ANCIENT_JUNGLE.slab().get(), consumer);
                            dropSelf(ANCIENT_JUNGLE.stairs().get(), consumer);
                            dropSelf(ANCIENT_JUNGLE.trapDoor().get(), consumer);
                            dropSelf(ANCIENT_JUNGLE.pressurePlate().get(), consumer);
                            dropSelf(ANCIENT_JUNGLE.button().get(), consumer);

                            dropSelf(ANCIENT_SPRUCE.block().get(), consumer);
                            dropSelf(ANCIENT_SPRUCE.door().get(), consumer);
                            dropSelf(ANCIENT_SPRUCE.fence().get(), consumer);
                            dropSelf(ANCIENT_SPRUCE.fenceGate().get(), consumer);
                            dropSelf(ANCIENT_SPRUCE.slab().get(), consumer);
                            dropSelf(ANCIENT_SPRUCE.stairs().get(), consumer);
                            dropSelf(ANCIENT_SPRUCE.trapDoor().get(), consumer);
                            dropSelf(ANCIENT_SPRUCE.pressurePlate().get(), consumer);
                            dropSelf(ANCIENT_SPRUCE.button().get(), consumer);

                            dropSelf(ANCIENT_CHERRY.block().get(), consumer);
                            dropSelf(ANCIENT_CHERRY.door().get(), consumer);
                            dropSelf(ANCIENT_CHERRY.fence().get(), consumer);
                            dropSelf(ANCIENT_CHERRY.fenceGate().get(), consumer);
                            dropSelf(ANCIENT_CHERRY.slab().get(), consumer);
                            dropSelf(ANCIENT_CHERRY.stairs().get(), consumer);
                            dropSelf(ANCIENT_CHERRY.trapDoor().get(), consumer);
                            dropSelf(ANCIENT_CHERRY.pressurePlate().get(), consumer);
                            dropSelf(ANCIENT_CHERRY.button().get(), consumer);

                            dropSelf(ANCIENT_BAMBOO.block().get(), consumer);
                            dropSelf(ANCIENT_BAMBOO.door().get(), consumer);
                            dropSelf(ANCIENT_BAMBOO.fence().get(), consumer);
                            dropSelf(ANCIENT_BAMBOO.fenceGate().get(), consumer);
                            dropSelf(ANCIENT_BAMBOO.slab().get(), consumer);
                            dropSelf(ANCIENT_BAMBOO.stairs().get(), consumer);
                            dropSelf(ANCIENT_BAMBOO.trapDoor().get(), consumer);
                            dropSelf(ANCIENT_BAMBOO.pressurePlate().get(), consumer);
                            dropSelf(ANCIENT_BAMBOO.button().get(), consumer);

                            dropSelf(ANCIENT_MANGROVE.block().get(), consumer);
                            dropSelf(ANCIENT_MANGROVE.door().get(), consumer);
                            dropSelf(ANCIENT_MANGROVE.fence().get(), consumer);
                            dropSelf(ANCIENT_MANGROVE.fenceGate().get(), consumer);
                            dropSelf(ANCIENT_MANGROVE.slab().get(), consumer);
                            dropSelf(ANCIENT_MANGROVE.stairs().get(), consumer);
                            dropSelf(ANCIENT_MANGROVE.trapDoor().get(), consumer);
                            dropSelf(ANCIENT_MANGROVE.pressurePlate().get(), consumer);
                            dropSelf(ANCIENT_MANGROVE.button().get(), consumer);

                            dropSelf(ANCIENT_CRIMSON.block().get(), consumer);
                            dropSelf(ANCIENT_CRIMSON.door().get(), consumer);
                            dropSelf(ANCIENT_CRIMSON.fence().get(), consumer);
                            dropSelf(ANCIENT_CRIMSON.fenceGate().get(), consumer);
                            dropSelf(ANCIENT_CRIMSON.slab().get(), consumer);
                            dropSelf(ANCIENT_CRIMSON.stairs().get(), consumer);
                            dropSelf(ANCIENT_CRIMSON.trapDoor().get(), consumer);
                            dropSelf(ANCIENT_CRIMSON.pressurePlate().get(), consumer);
                            dropSelf(ANCIENT_CRIMSON.button().get(), consumer);

                            dropSelf(ANCIENT_WARPED.block().get(), consumer);
                            dropSelf(ANCIENT_WARPED.door().get(), consumer);
                            dropSelf(ANCIENT_WARPED.fence().get(), consumer);
                            dropSelf(ANCIENT_WARPED.fenceGate().get(), consumer);
                            dropSelf(ANCIENT_WARPED.slab().get(), consumer);
                            dropSelf(ANCIENT_WARPED.stairs().get(), consumer);
                            dropSelf(ANCIENT_WARPED.trapDoor().get(), consumer);
                            dropSelf(ANCIENT_WARPED.pressurePlate().get(), consumer);
                            dropSelf(ANCIENT_WARPED.button().get(), consumer);

                            dropSelf(NYMPH_LOG.get(), consumer);
                            dropSelf(STRIPPED_NYMPH_LOG.get(), consumer);
                            dropSelf(PALM_LOG.get(), consumer);
                            dropSelf(STRIPPED_PALM_LOG.get(), consumer);

                            dropSelf(PALM_PLANKS.block().get(), consumer);
                            dropSelf(PALM_PLANKS.door().get(), consumer);
                            dropSelf(PALM_PLANKS.fence().get(), consumer);
                            dropSelf(PALM_PLANKS.fenceGate().get(), consumer);
                            dropSelf(PALM_PLANKS.slab().get(), consumer);
                            dropSelf(PALM_PLANKS.stairs().get(), consumer);
                            dropSelf(PALM_PLANKS.trapDoor().get(), consumer);
                            dropSelf(PALM_PLANKS.pressurePlate().get(), consumer);
                            dropSelf(PALM_PLANKS.button().get(), consumer);

                            dropSelf(NYMPH_PLANKS.block().get(), consumer);
                            dropSelf(NYMPH_PLANKS.door().get(), consumer);
                            dropSelf(NYMPH_PLANKS.fence().get(), consumer);
                            dropSelf(NYMPH_PLANKS.fenceGate().get(), consumer);
                            dropSelf(NYMPH_PLANKS.slab().get(), consumer);
                            dropSelf(NYMPH_PLANKS.stairs().get(), consumer);
                            dropSelf(NYMPH_PLANKS.trapDoor().get(), consumer);
                            dropSelf(NYMPH_PLANKS.pressurePlate().get(), consumer);
                            dropSelf(NYMPH_PLANKS.button().get(), consumer);

                            for (DyeColor color : DyeColor.values()) {
                                var blockType = BlockInit.SEA_GLASS_PATTERNS.get(color);
                                dropSelfIfSilkTouched(blockType.block().get(), consumer, silkTouchPredicate);
                                dropSelfIfSilkTouched(blockType.slab().get(), consumer, silkTouchPredicate);
                                dropSelfIfSilkTouched(blockType.stairs().get(), consumer, silkTouchPredicate);
                                dropSelfIfSilkTouched(blockType.pressurePlate().get(), consumer, silkTouchPredicate);
                                dropSelfIfSilkTouched(blockType.button().get(), consumer, silkTouchPredicate);
                                dropSelfIfSilkTouched(blockType.wall().get(), consumer, silkTouchPredicate);
                            }

                            for (Map<DyeColor, DeferredBlock<Block>> map : DYED_LINGUISTICS.values()) {
                                map.forEach(((dyeColor, block) ->
                                        dropSelf(block.get(), consumer)
                                ));
                            }

                            for (DeferredBlock<Block> block : NON_LINGUISTICS.values()) {
                                dropSelf(block.get(), consumer);
                            }

                            dropSelf(RAW_ANCIENT_CUPRUM_BLOCK.get(), consumer);
                            dropSelf(ALGAE.get(), consumer);
                            dropSelf(ALGAE_BLOCK.get(), consumer);
                            dropSelf(ALGAE_DETRITUS_STONE.get(), consumer);
                            dropSelf(ANEMONE.get(), consumer);
                            dropSelfIfSilkTouchedOrItem(FIRE_MELON_TOP.get(), ItemInit.FIRE_MELON_SPIKE.get(), consumer, silkTouchPredicate);
                            dropLeaves(NYMPH_LEAVES.get(), NYMPH_SAPLING.get(), consumer, silkTouchPredicate);
                            dropSelf(NYMPH_SAPLING.get(), consumer);
                            dropLeaves(PALM_LEAVES.get(), PALM_SAPLING.get(), consumer, silkTouchPredicate);
                            dropSelf(PALM_SAPLING.get(), consumer);
                            dropSelf(ATLANTEAN_PORTAL_FRAME.get(), consumer);
                            dropSelf(AQUATIC_POWER_COMPARATOR.get(), consumer);
                            dropSelfIfSilkTouchedOrItem(SURGE_LANTERN.get(), Items.PRISMARINE_CRYSTALS, consumer, silkTouchPredicate);
                            dropSelf(SUNKEN_GRAVEL.get(), consumer);
                            dropSelf(AQUATIC_POWER_TRIPWIRE_HOOK.get(), consumer);
                            dropItemFromBlock(AQUATIC_POWER_TRIPWIRE.get(), ItemInit.AQUAIEL_STRING.get(), consumer);
                            dropSelf(AQUATIC_POWER_TORCH.get(), consumer);
                            dropSelf(AQUATIC_POWER_STONE.get(), consumer);
                            dropSelf(AQUATIC_POWER_REPEATER.get(), consumer);
                            dropSelf(AQUATIC_POWER_DUST_WIRE.get(), consumer);
                            dropSelf(AQUATIC_POWER_LEVER.get(), consumer);
                            dropSelf(AQUATIC_POWER_LAMP.get(), consumer);
                            dropSelf(NYMPH_SIGN.get(), consumer);
                            dropAlternativeBlock(NYMPH_WALL_SIGN.get(), NYMPH_SIGN.get(), consumer);
                            dropAlternativeBlock(PALM_WALL_SIGN.get(), PALM_SIGN.get(), consumer);
                            dropSelf(PALM_SIGN.get(), consumer);
                            dropSelf(BLOCK_OF_AQUAMARINE.get(), consumer);
                            dropSelf(BLUE_LILY.get(), consumer);
                            dropSelf(PINK_PEARL_BLOCK.get(), consumer);
                            dropSelf(BLACK_PEARL_BLOCK.get(), consumer);
                            dropSelf(PURPLE_PEARL_BLOCK.get(), consumer);
                            dropSelf(BLUE_PEARL_BLOCK.get(), consumer);
                            dropSelf(BROWN_PEARL_BLOCK.get(), consumer);
                            dropSelf(YELLOW_PEARL_BLOCK.get(), consumer);
                            dropSelf(RED_PEARL_BLOCK.get(), consumer);
                            dropSelf(WHITE_PEARL_BLOCK.get(), consumer);
                            dropSelf(LIGHT_BLUE_PEARL_BLOCK.get(), consumer);
                            dropSelf(LIGHT_GRAY_PEARL_BLOCK.get(), consumer);
                            dropSelf(CYAN_PEARL_BLOCK.get(), consumer);
                            dropSelf(GREEN_PEARL_BLOCK.get(), consumer);
                            dropSelf(ORANGE_PEARL_BLOCK.get(), consumer);
                            dropSelf(GRAY_PEARL_BLOCK.get(), consumer);
                            dropSelf(LIME_PEARL_BLOCK.get(), consumer);
                            dropSelf(MAGENTA_PEARL_BLOCK.get(), consumer);
                            dropSelf(BUBBLE_MAGMA.get(), consumer);
                            dropSelf(BURNT_DEEP.get(), consumer);
                            dropSelf(HARDENED_CALCITE_BLOCK.get(), consumer);
                            dropSelf(CARVED_COCONUT.get(), consumer);
                            dropSelf(CHISELED_AQUAMARINE_BLOCK.get(), consumer);
                            dropSelf(CHISELED_GOLDEN_BLOCK.get(), consumer);
                            dropSelf(CHISELED_GOLDEN_AQUAMARINE.get(), consumer);
                            dropSelf(COCONUT.get(), consumer);
                            dropSelf(COCONUT_SLICE.get(), consumer);
                            dropSelf(COQUINA.get(), consumer);
                            dropSelf(CRACKED_GLOWSTONE.get(), consumer);
                            dropSelf(DEAD_GLOWSTONE.get(), consumer);
                            dropSelf(CRYSTAL_TRANSFERENCE_BLOCK.get(), consumer);
                            dropSelf(WATERFALL_BLOCK.get(), consumer);
                            dropSelf(WAVE_BLOCK.get(), consumer);
                            dropSelfIfSilkTouchedOrItem(DEEPSLATE_AQUAMARINE_ORE.get(), ItemInit.AQUAMARINE_GEM.get(), consumer, silkTouchPredicate);
                            dropSelfIfSilkTouchedOrItem(DEEPSLATE_ANCIENT_CUPRUM_ORE.get(), ItemInit.RAW_ANCIENT_CUPRUM.get(), consumer, silkTouchPredicate);
                            dropSelf(DETRITUS_SANDSTONE.get(), consumer);
                            dropSelfIfSilkTouchedOrItem(AQUAMARINE_ORE.get(), ItemInit.AQUAMARINE_GEM.get(), consumer, silkTouchPredicate);
                            dropSelfIfSilkTouchedOrItem(ANCIENT_CUPRUM_ORE.get(), ItemInit.RAW_ANCIENT_CUPRUM.get(), consumer, silkTouchPredicate);
                            dropSelf(LINGUISTIC_TABLE.get(), consumer);
                            dropSelf(WRITING_TABLE.get(), consumer);
                            dropSelfIfSilkTouchedOrItem(NAUTILUS_SHELL_BLOCK.get(), ItemInit.BROKEN_SHELLS.get(), consumer, silkTouchPredicate);
                            dropSelfIfSilkTouchedOrItem(CRACKED_NAUTILUS_SHELL.get(), ItemInit.BROKEN_SHELLS.get(), consumer, silkTouchPredicate);
                            dropSelfIfSilkTouchedOrItem(CRACKED_MOSSY_NAUTILUS_SHELL.get(), ItemInit.BROKEN_SHELLS.get(), consumer, silkTouchPredicate);
                            dropSelfIfSilkTouchedOrItem(MOSSY_NAUTILUS_SHELL.get(), ItemInit.BROKEN_SHELLS.get(), consumer, silkTouchPredicate);
                            dropSelfIfSilkTouchedOrItem(OYSTER_SHELL_BLOCK.get(), ItemInit.BROKEN_SHELLS.get(), consumer, silkTouchPredicate);
                            dropSelfIfSilkTouchedOrItem(CRACKED_OYSTER_SHELL.get(), ItemInit.BROKEN_SHELLS.get(), consumer, silkTouchPredicate);
                            dropSelfIfSilkTouchedOrItem(CRACKED_MOSSY_OYSTER_SHELL.get(), ItemInit.BROKEN_SHELLS.get(), consumer, silkTouchPredicate);
                            dropSelfIfSilkTouchedOrItem(MOSSY_OYSTER_SHELL.get(), ItemInit.BROKEN_SHELLS.get(), consumer, silkTouchPredicate);
                            dropSelfIfSilkTouchedOrItem(OCEAN_LANTERN.get(), Items.PRISMARINE_CRYSTALS, consumer, silkTouchPredicate);
                            dropSelfIfSilkTouchedOrItem(TUBEN_POT.get(), Items.CLAY_BALL, consumer, silkTouchPredicate);
                            dropSelfIfSilkTouchedOrItem(BELEN_POT.get(), Items.CLAY_BALL, consumer, silkTouchPredicate);
                            dropSelfIfSilkTouchedOrItem(TOPER_POT.get(), Items.CLAY_BALL, consumer, silkTouchPredicate);
                            dropSelfIfSilkTouchedOrItem(SNOWN_POT.get(), Items.CLAY_BALL, consumer, silkTouchPredicate);
                            dropSelfIfSilkTouchedOrItem(HORPEN_POT.get(), Items.CLAY_BALL, consumer, silkTouchPredicate);
                            dropSelfIfSilkTouchedOrItem(CELEN_POT.get(), Items.CLAY_BALL, consumer, silkTouchPredicate);
                            dropSelfIfSilkTouchedOrItem(OBEMO_POT.get(), Items.CLAY_BALL, consumer, silkTouchPredicate);
                            dropSelf(PURPLE_SEASHROOM.get(), consumer);
                            dropSelf(YELLOW_SEASHROOM.get(), consumer);
                            dropSelf(SEABLOOM.get(), consumer);
                            dropSelf(RED_SEABLOOM.get(), consumer);
                            dropSelf(YELLOW_SEABLOOM.get(), consumer);
                            dropSelf(SEASALT_CHUNK.get(), consumer);
                            dropSelf(SEABED.get(), consumer);
                            dropSelf(SATIRE_LANTERN.get(), consumer);
                            dropSelf(SODIUM_BOMB.get(), consumer);
                            dropSelf(TUBER_UP.get(), consumer);
                        }

                };
            }
        }, LootContextParamSets.BLOCK);


        event.getGenerator().addProvider(true, new LootTableProvider(output, Set.of(), List.of(), event.getLookupProvider()) {
            @Override
            public @NotNull List<SubProviderEntry> getTables() {
                return List.of(lootTableProvider);
            }
        });

        BlockTagsProvider blockTagsProvider = new BlockTagsProvider(output, event.getLookupProvider(), "atlantis", event.getExistingFileHelper()) {
            @Override
            protected void addTags(HolderLookup.@NotNull Provider pProvider) {
                tag(BlockTags.ANIMALS_SPAWNABLE_ON).add(BlockInit.SEABED.get());
                for (TrailsGroup group : BlockInit.ANCIENT_CUPRUM.values()) {
                    tag(BlockTags.MINEABLE_WITH_PICKAXE).add(group.block().get());
                    tag(BlockTags.MINEABLE_WITH_PICKAXE).add(group.bulb().get());
                    tag(BlockTags.MINEABLE_WITH_PICKAXE).add(group.grate().get());
                    tag(BlockTags.MINEABLE_WITH_PICKAXE).add(group.cut().get());
                    tag(BlockTags.MINEABLE_WITH_PICKAXE).add(group.cut_slab().get());
                    tag(BlockTags.MINEABLE_WITH_PICKAXE).add(group.cut_stairs().get());
                    tag(BlockTags.MINEABLE_WITH_PICKAXE).add(group.chiseled().get());
                    tag(BlockTags.MINEABLE_WITH_PICKAXE).add(group.door().get());
                    tag(BlockTags.MINEABLE_WITH_PICKAXE).add(group.trapdoor().get());
                    tag(BlockTags.MINEABLE_WITH_PICKAXE).add(group.waxed_block().get());
                    tag(BlockTags.MINEABLE_WITH_PICKAXE).add(group.waxed_bulb().get());
                    tag(BlockTags.MINEABLE_WITH_PICKAXE).add(group.waxed_grate().get());
                    tag(BlockTags.MINEABLE_WITH_PICKAXE).add(group.waxed_cut().get());
                    tag(BlockTags.MINEABLE_WITH_PICKAXE).add(group.waxed_cut_slab().get());
                    tag(BlockTags.MINEABLE_WITH_PICKAXE).add(group.waxed_cut_stairs().get());
                    tag(BlockTags.MINEABLE_WITH_PICKAXE).add(group.waxed_chiseled().get());
                    tag(BlockTags.MINEABLE_WITH_PICKAXE).add(group.waxed_door().get());
                    tag(BlockTags.MINEABLE_WITH_PICKAXE).add(group.waxed_trapdoor().get());
                    tag(BlockTags.NEEDS_IRON_TOOL).add(group.block().get());
                    tag(BlockTags.NEEDS_IRON_TOOL).add(group.bulb().get());
                    tag(BlockTags.NEEDS_IRON_TOOL).add(group.grate().get());
                    tag(BlockTags.NEEDS_IRON_TOOL).add(group.cut().get());
                    tag(BlockTags.NEEDS_IRON_TOOL).add(group.cut_slab().get());
                    tag(BlockTags.NEEDS_IRON_TOOL).add(group.cut_stairs().get());
                    tag(BlockTags.NEEDS_IRON_TOOL).add(group.chiseled().get());
                    tag(BlockTags.NEEDS_IRON_TOOL).add(group.door().get());
                    tag(BlockTags.NEEDS_IRON_TOOL).add(group.trapdoor().get());
                    tag(BlockTags.NEEDS_IRON_TOOL).add(group.waxed_block().get());
                    tag(BlockTags.NEEDS_IRON_TOOL).add(group.waxed_bulb().get());
                    tag(BlockTags.NEEDS_IRON_TOOL).add(group.waxed_grate().get());
                    tag(BlockTags.NEEDS_IRON_TOOL).add(group.waxed_cut().get());
                    tag(BlockTags.NEEDS_IRON_TOOL).add(group.waxed_cut_slab().get());
                    tag(BlockTags.NEEDS_IRON_TOOL).add(group.waxed_cut_stairs().get());
                    tag(BlockTags.NEEDS_IRON_TOOL).add(group.waxed_chiseled().get());
                    tag(BlockTags.NEEDS_IRON_TOOL).add(group.waxed_door().get());
                    tag(BlockTags.NEEDS_IRON_TOOL).add(group.waxed_trapdoor().get());
                }
                for (DyeColor color : DyeColor.values()) {
                    tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BlockInit.SEA_GLASS_PATTERNS.get(color).block().get());
                    tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BlockInit.SEA_GLASS_PATTERNS.get(color).slab().get());
                    tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BlockInit.SEA_GLASS_PATTERNS.get(color).stairs().get());
                    tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BlockInit.SEA_GLASS_PATTERNS.get(color).pressurePlate().get());
                    tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BlockInit.SEA_GLASS_PATTERNS.get(color).button().get());
                    tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BlockInit.SEA_GLASS_PATTERNS.get(color).wall().get());
                }
                for (DyeColor color : DyeColor.values()) {
                    tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BlockInit.SEA_GLASS_LIST.get(color).block().get());
                    tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BlockInit.SEA_GLASS_LIST.get(color).slab().get());
                    tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BlockInit.SEA_GLASS_LIST.get(color).stairs().get());
                    tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BlockInit.SEA_GLASS_LIST.get(color).pressurePlate().get());
                    tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BlockInit.SEA_GLASS_LIST.get(color).button().get());
                    tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BlockInit.SEA_GLASS_LIST.get(color).wall().get());
                }
                tag(BlockTags.MINEABLE_WITH_PICKAXE).add(
                        RAW_ANCIENT_CUPRUM_BLOCK.get(),
                        ANCIENT_CUPRUM_ORE.get(),
                        DEEPSLATE_ANCIENT_CUPRUM_ORE.get(),
                        ORICHALCUM_BLOCK.get(),
                        WATERFALL_BLOCK.get(),
                        WAVE_BLOCK.get(),
                        CRYSTAL_TRANSFERENCE_BLOCK.get(),
                        ATLANTEAN_PORTAL_FRAME.get(),
                        TUBEN_POT.get(),
                        BELEN_POT.get(),
                        TOPER_POT.get(),
                        SNOWN_POT.get(),
                        HORPEN_POT.get(),
                        CELEN_POT.get(),
                        OBEMO_POT.get(),
                        OYSTER_SHELL_BLOCK.get(),
                        NAUTILUS_SHELL_BLOCK.get(),
                        CRACKED_OYSTER_SHELL.get(),
                        CRACKED_NAUTILUS_SHELL.get(),
                        CRACKED_MOSSY_OYSTER_SHELL.get(),
                        CRACKED_MOSSY_NAUTILUS_SHELL.get(),
                        MOSSY_OYSTER_SHELL.get(),
                        MOSSY_NAUTILUS_SHELL.get(),
                        SEASALT_CHUNK.get(),
                        CRACKED_GLOWSTONE.get(),
                        DEAD_GLOWSTONE.get(),
                        ALGAE_DETRITUS_STONE.get(),
                        DETRITUS_SANDSTONE.get(),
                        LUMINESCENT_PRISMARINE.get(),
                        BUBBLE_MAGMA.get(),
                        AQUAMARINE_ORE.get(),
                        DEEPSLATE_AQUAMARINE_ORE.get(),
                        OCEAN_LANTERN.get(),
                        SURGE_LANTERN.get(),
                        ATLANTEAN_CORE.get(),
                        BLOCK_OF_AQUAMARINE.get(),
                        CHISELED_GOLDEN_BLOCK.get(),
                        CHISELED_GOLDEN_AQUAMARINE.get(),
                        BLACK_PEARL_BLOCK.get(),
                        BLUE_PEARL_BLOCK.get(),
                        BROWN_PEARL_BLOCK.get(),
                        CYAN_PEARL_BLOCK.get(),
                        GRAY_PEARL_BLOCK.get(),
                        GREEN_PEARL_BLOCK.get(),
                        LIGHT_BLUE_PEARL_BLOCK.get(),
                        LIGHT_GRAY_PEARL_BLOCK.get(),
                        LIME_PEARL_BLOCK.get(),
                        MAGENTA_PEARL_BLOCK.get(),
                        ORANGE_PEARL_BLOCK.get(),
                        PINK_PEARL_BLOCK.get(),
                        PURPLE_PEARL_BLOCK.get(),
                        RED_PEARL_BLOCK.get(),
                        WHITE_PEARL_BLOCK.get(),
                        YELLOW_PEARL_BLOCK.get()
                );
                tag(BlockTags.MINEABLE_WITH_SHOVEL).add(
                        SUNKEN_GRAVEL.get(),
                        SEABED.get()
                );
                tag(BlockTags.NEEDS_IRON_TOOL).add(
                        RAW_ANCIENT_CUPRUM_BLOCK.get(),
                        ANCIENT_CUPRUM_ORE.get(),
                        DEEPSLATE_ANCIENT_CUPRUM_ORE.get(),
                        ORICHALCUM_BLOCK.get(),
                        ATLANTEAN_PORTAL_FRAME.get(),
                        AQUAMARINE_ORE.get(),
                        DEEPSLATE_AQUAMARINE_ORE.get()
                );
                tag(BlockTags.MINEABLE_WITH_AXE).add(
                        NYMPH_PLANKS.block().get(),
                        NYMPH_PLANKS.slab().get(),
                        NYMPH_PLANKS.fence().get(),
                        NYMPH_PLANKS.fenceGate().get(),
                        NYMPH_PLANKS.stairs().get(),
                        NYMPH_PLANKS.door().get(),
                        NYMPH_PLANKS.trapDoor().get(),
                        NYMPH_PLANKS.button().get(),
                        NYMPH_PLANKS.pressurePlate().get(),

                        PALM_PLANKS.block().get(),
                        PALM_PLANKS.slab().get(),
                        PALM_PLANKS.fence().get(),
                        PALM_PLANKS.fenceGate().get(),
                        PALM_PLANKS.stairs().get(),
                        PALM_PLANKS.door().get(),
                        PALM_PLANKS.trapDoor().get(),

                        ANCIENT_CHERRY.button().get(),
                        ANCIENT_CHERRY.pressurePlate().get(),
                        ANCIENT_CHERRY.block().get(),
                        ANCIENT_CHERRY.slab().get(),
                        ANCIENT_CHERRY.fence().get(),
                        ANCIENT_CHERRY.fenceGate().get(),
                        ANCIENT_CHERRY.stairs().get(),
                        ANCIENT_CHERRY.door().get(),
                        ANCIENT_CHERRY.trapDoor().get(),
                        ANCIENT_CHERRY.button().get(),
                        ANCIENT_CHERRY.pressurePlate().get(),

                        ANCIENT_OAK.button().get(),
                        ANCIENT_OAK.pressurePlate().get(),
                        ANCIENT_OAK.block().get(),
                        ANCIENT_OAK.slab().get(),
                        ANCIENT_OAK.fence().get(),
                        ANCIENT_OAK.fenceGate().get(),
                        ANCIENT_OAK.stairs().get(),
                        ANCIENT_OAK.door().get(),
                        ANCIENT_OAK.trapDoor().get(),
                        ANCIENT_OAK.button().get(),
                        ANCIENT_OAK.pressurePlate().get(),

                        ANCIENT_DARK_OAK.button().get(),
                        ANCIENT_DARK_OAK.pressurePlate().get(),
                        ANCIENT_DARK_OAK.block().get(),
                        ANCIENT_DARK_OAK.slab().get(),
                        ANCIENT_DARK_OAK.fence().get(),
                        ANCIENT_DARK_OAK.fenceGate().get(),
                        ANCIENT_DARK_OAK.stairs().get(),
                        ANCIENT_DARK_OAK.door().get(),
                        ANCIENT_DARK_OAK.trapDoor().get(),
                        ANCIENT_DARK_OAK.button().get(),
                        ANCIENT_DARK_OAK.pressurePlate().get(),

                        ANCIENT_SPRUCE.button().get(),
                        ANCIENT_SPRUCE.pressurePlate().get(),
                        ANCIENT_SPRUCE.block().get(),
                        ANCIENT_SPRUCE.slab().get(),
                        ANCIENT_SPRUCE.fence().get(),
                        ANCIENT_SPRUCE.fenceGate().get(),
                        ANCIENT_SPRUCE.stairs().get(),
                        ANCIENT_SPRUCE.door().get(),
                        ANCIENT_SPRUCE.trapDoor().get(),
                        ANCIENT_SPRUCE.button().get(),
                        ANCIENT_SPRUCE.pressurePlate().get(),

                        ANCIENT_ACACIA.button().get(),
                        ANCIENT_ACACIA.pressurePlate().get(),
                        ANCIENT_ACACIA.block().get(),
                        ANCIENT_ACACIA.slab().get(),
                        ANCIENT_ACACIA.fence().get(),
                        ANCIENT_ACACIA.fenceGate().get(),
                        ANCIENT_ACACIA.stairs().get(),
                        ANCIENT_ACACIA.door().get(),
                        ANCIENT_ACACIA.trapDoor().get(),
                        ANCIENT_ACACIA.button().get(),
                        ANCIENT_ACACIA.pressurePlate().get(),

                        ANCIENT_BIRCH.button().get(),
                        ANCIENT_BIRCH.pressurePlate().get(),
                        ANCIENT_BIRCH.block().get(),
                        ANCIENT_BIRCH.slab().get(),
                        ANCIENT_BIRCH.fence().get(),
                        ANCIENT_BIRCH.fenceGate().get(),
                        ANCIENT_BIRCH.stairs().get(),
                        ANCIENT_BIRCH.door().get(),
                        ANCIENT_BIRCH.trapDoor().get(),
                        ANCIENT_BIRCH.button().get(),
                        ANCIENT_BIRCH.pressurePlate().get(),

                        ANCIENT_JUNGLE.button().get(),
                        ANCIENT_JUNGLE.pressurePlate().get(),
                        ANCIENT_JUNGLE.block().get(),
                        ANCIENT_JUNGLE.slab().get(),
                        ANCIENT_JUNGLE.fence().get(),
                        ANCIENT_JUNGLE.fenceGate().get(),
                        ANCIENT_JUNGLE.stairs().get(),
                        ANCIENT_JUNGLE.door().get(),
                        ANCIENT_JUNGLE.trapDoor().get(),
                        ANCIENT_JUNGLE.button().get(),
                        ANCIENT_JUNGLE.pressurePlate().get(),

                        ANCIENT_BAMBOO.button().get(),
                        ANCIENT_BAMBOO.pressurePlate().get(),
                        ANCIENT_BAMBOO.block().get(),
                        ANCIENT_BAMBOO.slab().get(),
                        ANCIENT_BAMBOO.fence().get(),
                        ANCIENT_BAMBOO.fenceGate().get(),
                        ANCIENT_BAMBOO.stairs().get(),
                        ANCIENT_BAMBOO.door().get(),
                        ANCIENT_BAMBOO.trapDoor().get(),
                        ANCIENT_BAMBOO.button().get(),
                        ANCIENT_BAMBOO.pressurePlate().get(),

                        ANCIENT_MANGROVE.button().get(),
                        ANCIENT_MANGROVE.pressurePlate().get(),
                        ANCIENT_MANGROVE.block().get(),
                        ANCIENT_MANGROVE.slab().get(),
                        ANCIENT_MANGROVE.fence().get(),
                        ANCIENT_MANGROVE.fenceGate().get(),
                        ANCIENT_MANGROVE.stairs().get(),
                        ANCIENT_MANGROVE.door().get(),
                        ANCIENT_MANGROVE.trapDoor().get(),
                        ANCIENT_MANGROVE.button().get(),
                        ANCIENT_MANGROVE.pressurePlate().get(),

                        ANCIENT_CRIMSON.button().get(),
                        ANCIENT_CRIMSON.pressurePlate().get(),
                        ANCIENT_CRIMSON.block().get(),
                        ANCIENT_CRIMSON.slab().get(),
                        ANCIENT_CRIMSON.fence().get(),
                        ANCIENT_CRIMSON.fenceGate().get(),
                        ANCIENT_CRIMSON.stairs().get(),
                        ANCIENT_CRIMSON.door().get(),
                        ANCIENT_CRIMSON.trapDoor().get(),
                        ANCIENT_CRIMSON.button().get(),
                        ANCIENT_CRIMSON.pressurePlate().get(),

                        ANCIENT_WARPED.button().get(),
                        ANCIENT_WARPED.pressurePlate().get(),
                        ANCIENT_WARPED.block().get(),
                        ANCIENT_WARPED.slab().get(),
                        ANCIENT_WARPED.fence().get(),
                        ANCIENT_WARPED.fenceGate().get(),
                        ANCIENT_WARPED.stairs().get(),
                        ANCIENT_WARPED.door().get(),
                        ANCIENT_WARPED.trapDoor().get(),
                        ANCIENT_WARPED.button().get(),
                        ANCIENT_WARPED.pressurePlate().get(),

                        PALM_SIGN.get(),
                        PALM_WALL_SIGN.get(),
                        STRIPPED_PALM_LOG.get(),
                        STRIPPED_NYMPH_LOG.get(),
                        COCONUT_SLICE.get(),
                        SATIRE_LANTERN.get(),
                        CARVED_COCONUT.get(),
                        COCONUT.get(),
                        PALM_LOG.get(),
                        NYMPH_SIGN.get(),
                        NYMPH_WALL_SIGN.get(),
                        NYMPH_LOG.get(),
                        LINGUISTIC_TABLE.get(),
                        WRITING_TABLE.get()
                );
                tag(BlockTags.WOODEN_STAIRS).add(
                        ANCIENT_BIRCH.stairs().get(),
                        ANCIENT_ACACIA.stairs().get(),
                        ANCIENT_JUNGLE.stairs().get(),
                        ANCIENT_OAK.stairs().get(),
                        ANCIENT_DARK_OAK.stairs().get(),
                        ANCIENT_SPRUCE.stairs().get(),
                        ANCIENT_MANGROVE.stairs().get(),
                        ANCIENT_BAMBOO.stairs().get(),
                        ANCIENT_CHERRY.stairs().get(),
                        ANCIENT_CRIMSON.stairs().get(),
                        ANCIENT_WARPED.stairs().get(),
                        NYMPH_PLANKS.stairs().get(),
                        PALM_PLANKS.stairs().get()
                );
                tag(BlockTags.WOODEN_SLABS).add(
                        ANCIENT_BIRCH.slab().get(),
                        ANCIENT_ACACIA.slab().get(),
                        ANCIENT_JUNGLE.slab().get(),
                        ANCIENT_OAK.slab().get(),
                        ANCIENT_DARK_OAK.slab().get(),
                        ANCIENT_SPRUCE.slab().get(),
                        ANCIENT_MANGROVE.slab().get(),
                        ANCIENT_BAMBOO.slab().get(),
                        ANCIENT_CHERRY.slab().get(),
                        ANCIENT_CRIMSON.slab().get(),
                        ANCIENT_WARPED.slab().get(),
                        NYMPH_PLANKS.slab().get(),
                        PALM_PLANKS.slab().get()
                );
                tag(BlockTags.WOODEN_TRAPDOORS).add(
                        ANCIENT_BIRCH.trapDoor().get(),
                        ANCIENT_ACACIA.trapDoor().get(),
                        ANCIENT_JUNGLE.trapDoor().get(),
                        ANCIENT_OAK.trapDoor().get(),
                        ANCIENT_DARK_OAK.trapDoor().get(),
                        ANCIENT_SPRUCE.trapDoor().get(),
                        ANCIENT_MANGROVE.trapDoor().get(),
                        ANCIENT_BAMBOO.trapDoor().get(),
                        ANCIENT_CHERRY.trapDoor().get(),
                        ANCIENT_CRIMSON.trapDoor().get(),
                        ANCIENT_WARPED.trapDoor().get(),
                        NYMPH_PLANKS.trapDoor().get(),
                        PALM_PLANKS.trapDoor().get()
                );
                tag(BlockTags.SAPLINGS).add(
                        NYMPH_SAPLING.get(),
                        PALM_SAPLING.get()
                );
                tag(BlockTags.WOODEN_PRESSURE_PLATES).add(
                        ANCIENT_BIRCH.pressurePlate().get(),
                        ANCIENT_ACACIA.pressurePlate().get(),
                        ANCIENT_JUNGLE.pressurePlate().get(),
                        ANCIENT_OAK.pressurePlate().get(),
                        ANCIENT_DARK_OAK.pressurePlate().get(),
                        ANCIENT_SPRUCE.pressurePlate().get(),
                        ANCIENT_MANGROVE.pressurePlate().get(),
                        ANCIENT_BAMBOO.pressurePlate().get(),
                        ANCIENT_CHERRY.pressurePlate().get(),
                        ANCIENT_CRIMSON.pressurePlate().get(),
                        ANCIENT_WARPED.pressurePlate().get(),
                        NYMPH_PLANKS.pressurePlate().get(),
                        PALM_PLANKS.pressurePlate().get()
                );
                tag(BlockTags.WOODEN_DOORS).add(
                        ANCIENT_BIRCH.door().get(),
                        ANCIENT_ACACIA.door().get(),
                        ANCIENT_JUNGLE.door().get(),
                        ANCIENT_OAK.door().get(),
                        ANCIENT_DARK_OAK.door().get(),
                        ANCIENT_SPRUCE.door().get(),
                        ANCIENT_MANGROVE.door().get(),
                        ANCIENT_BAMBOO.door().get(),
                        ANCIENT_CHERRY.door().get(),
                        ANCIENT_CRIMSON.door().get(),
                        ANCIENT_WARPED.door().get(),
                        NYMPH_PLANKS.door().get(),
                        PALM_PLANKS.door().get()
                );
                tag(BlockTags.MINEABLE_WITH_HOE).add(
                        NYMPH_LEAVES.get(),
                        PALM_LEAVES.get(),
                        SEABLOOM.get(),
                        RED_SEABLOOM.get(),
                        PURPLE_SEASHROOM.get(),
                        YELLOW_SEASHROOM.get(),
                        YELLOW_SEABLOOM.get(),
                        ALGAE.get(),
                        FIRE_MELON_FRUIT_SPIKED.get(),
                        FIRE_MELON_FRUIT.get(),
                        FIRE_MELON_STEM.get(),
                        FIRE_MELON_TOP.get(),
                        NYMPH_SAPLING.get(),
                        SEASHROOM.get(),
                        TUBER_UP.get(),
                        BLUE_LILY.get(),
                        BURNT_DEEP.get(),
                        ANEMONE.get(),
                        ALGAE_BLOCK.get()
                );
                tag(BlockTags.BUTTONS).add(
                        ANCIENT_BIRCH.button().get(),
                        ANCIENT_ACACIA.button().get(),
                        ANCIENT_JUNGLE.button().get(),
                        ANCIENT_OAK.button().get(),
                        ANCIENT_DARK_OAK.button().get(),
                        ANCIENT_SPRUCE.button().get(),
                        ANCIENT_MANGROVE.button().get(),
                        ANCIENT_BAMBOO.button().get(),
                        ANCIENT_CHERRY.button().get(),
                        ANCIENT_CRIMSON.button().get(),
                        ANCIENT_WARPED.button().get(),
                        NYMPH_PLANKS.button().get(),
                        PALM_PLANKS.button().get()
                );
                tag(BlockTags.WOODEN_BUTTONS).add(
                        ANCIENT_BIRCH.button().get(),
                        ANCIENT_ACACIA.button().get(),
                        ANCIENT_JUNGLE.button().get(),
                        ANCIENT_OAK.button().get(),
                        ANCIENT_DARK_OAK.button().get(),
                        ANCIENT_SPRUCE.button().get(),
                        ANCIENT_MANGROVE.button().get(),
                        ANCIENT_BAMBOO.button().get(),
                        ANCIENT_CHERRY.button().get(),
                        ANCIENT_CRIMSON.button().get(),
                        ANCIENT_WARPED.button().get(),
                        NYMPH_PLANKS.button().get(),
                        PALM_PLANKS.button().get()
                );
                tag(BlockTags.CLIMBABLE).add(BlockInit.ALGAE.get());
                tag(BlockTags.PLANKS).add(
                        ANCIENT_BIRCH.block().get(),
                        ANCIENT_ACACIA.block().get(),
                        ANCIENT_JUNGLE.block().get(),
                        ANCIENT_OAK.block().get(),
                        ANCIENT_DARK_OAK.block().get(),
                        ANCIENT_SPRUCE.block().get(),
                        ANCIENT_MANGROVE.block().get(),
                        ANCIENT_BAMBOO.block().get(),
                        ANCIENT_CHERRY.block().get(),
                        ANCIENT_CRIMSON.block().get(),
                        ANCIENT_WARPED.block().get(),
                        NYMPH_PLANKS.block().get(),
                        PALM_PLANKS.block().get()
                );
                tag(BlockTags.LEAVES).add(
                        PALM_LEAVES.get(),
                        NYMPH_LEAVES.get()
                );
                tag(BlockTags.WALL_SIGNS).add(
                        NYMPH_WALL_SIGN.get(),
                        PALM_WALL_SIGN.get()
                );
                tag(BlockTags.LOGS).add(
                        NYMPH_LOG.get(),
                        PALM_LOG.get(),
                        STRIPPED_PALM_LOG.get(),
                        STRIPPED_NYMPH_LOG.get()
                );
                tag(BlockTags.SIGNS).add(
                        NYMPH_SIGN.get(),
                        PALM_SIGN.get()
                );
                tag(BlockTags.WOODEN_FENCES).add(
                        ANCIENT_BIRCH.fence().get(),
                        ANCIENT_ACACIA.fence().get(),
                        ANCIENT_JUNGLE.fence().get(),
                        ANCIENT_OAK.fence().get(),
                        ANCIENT_DARK_OAK.fence().get(),
                        ANCIENT_SPRUCE.fence().get(),
                        ANCIENT_MANGROVE.fence().get(),
                        ANCIENT_BAMBOO.fence().get(),
                        ANCIENT_CHERRY.fence().get(),
                        ANCIENT_CRIMSON.fence().get(),
                        ANCIENT_WARPED.fence().get(),
                        NYMPH_PLANKS.fence().get(),
                        PALM_PLANKS.fence().get()
                );
            }
        };

        FluidTagsProvider fluidTagsProvider = new FluidTagsProvider(output, event.getLookupProvider(), Reference.MODID, event.getExistingFileHelper()) {
            @Override
            protected void addTags(HolderLookup.@NotNull Provider provider) {
                tag(FluidTags.WATER).add(
                        FluidInit.JETSTREAM_WATER.get(),
                        FluidInit.SALTY_SEAWATER.get(),
                        FluidInit.FLOWING_SALTY_SEAWATER.get(),
                        FluidInit.FLOWING_JETSTREAM_WATER.get()
                );
            }
        };

        event.getGenerator().addProvider(true, blockTagsProvider);
        event.getGenerator().addProvider(true, fluidTagsProvider);

        event.getGenerator().addProvider(true, new ItemTagsProvider(output, event.getLookupProvider(), blockTagsProvider.contentsGetter(), "atlantis", event.getExistingFileHelper()) {
            @Override
            protected void addTags(HolderLookup.@NotNull Provider pProvider) {
                TagAppender<Item> tag = tag(TagsInit.Item.CAN_ITEM_SINK);
                tag(ItemTags.FENCES).add(
                        ANCIENT_BIRCH.fence().get().asItem(),
                        ANCIENT_ACACIA.fence().get().asItem(),
                        ANCIENT_JUNGLE.fence().get().asItem(),
                        ANCIENT_OAK.fence().get().asItem(),
                        ANCIENT_DARK_OAK.fence().get().asItem(),
                        ANCIENT_SPRUCE.fence().get().asItem(),
                        ANCIENT_MANGROVE.fence().get().asItem(),
                        ANCIENT_BAMBOO.fence().get().asItem(),
                        ANCIENT_CHERRY.fence().get().asItem(),
                        ANCIENT_CRIMSON.fence().get().asItem(),
                        ANCIENT_WARPED.fence().get().asItem(),
                        NYMPH_PLANKS.fence().get().asItem(),
                        PALM_PLANKS.fence().get().asItem()
                );
                tag(ItemTags.BUTTONS).add(
                        ANCIENT_BIRCH.button().get().asItem(),
                        ANCIENT_ACACIA.button().get().asItem(),
                        ANCIENT_JUNGLE.button().get().asItem(),
                        ANCIENT_OAK.button().get().asItem(),
                        ANCIENT_DARK_OAK.button().get().asItem(),
                        ANCIENT_SPRUCE.button().get().asItem(),
                        ANCIENT_MANGROVE.button().get().asItem(),
                        ANCIENT_BAMBOO.button().get().asItem(),
                        ANCIENT_CHERRY.button().get().asItem(),
                        ANCIENT_CRIMSON.button().get().asItem(),
                        ANCIENT_WARPED.button().get().asItem(),
                        NYMPH_PLANKS.button().get().asItem(),
                        PALM_PLANKS.button().get().asItem()
                );
                tag(ItemTags.FENCE_GATES).add(
                        ANCIENT_BIRCH.fenceGate().get().asItem(),
                        ANCIENT_ACACIA.fenceGate().get().asItem(),
                        ANCIENT_JUNGLE.fenceGate().get().asItem(),
                        ANCIENT_OAK.fenceGate().get().asItem(),
                        ANCIENT_DARK_OAK.fenceGate().get().asItem(),
                        ANCIENT_SPRUCE.fenceGate().get().asItem(),
                        ANCIENT_MANGROVE.fenceGate().get().asItem(),
                        ANCIENT_BAMBOO.fenceGate().get().asItem(),
                        ANCIENT_CHERRY.fenceGate().get().asItem(),
                        ANCIENT_CRIMSON.fenceGate().get().asItem(),
                        ANCIENT_WARPED.fenceGate().get().asItem(),
                        NYMPH_PLANKS.fenceGate().get().asItem(),
                        PALM_PLANKS.fenceGate().get().asItem()
                );
                tag(ItemTags.DOORS).add(
                        ANCIENT_BIRCH.door().get().asItem(),
                        ANCIENT_ACACIA.door().get().asItem(),
                        ANCIENT_JUNGLE.door().get().asItem(),
                        ANCIENT_OAK.door().get().asItem(),
                        ANCIENT_DARK_OAK.door().get().asItem(),
                        ANCIENT_SPRUCE.door().get().asItem(),
                        ANCIENT_MANGROVE.door().get().asItem(),
                        ANCIENT_BAMBOO.door().get().asItem(),
                        ANCIENT_CHERRY.door().get().asItem(),
                        ANCIENT_CRIMSON.door().get().asItem(),
                        ANCIENT_WARPED.door().get().asItem(),
                        NYMPH_PLANKS.door().get().asItem(),
                        PALM_PLANKS.door().get().asItem()
                );
                tag(ItemTags.PLANKS).add(
                        ANCIENT_BIRCH.block().get().asItem(),
                        ANCIENT_ACACIA.block().get().asItem(),
                        ANCIENT_JUNGLE.block().get().asItem(),
                        ANCIENT_OAK.block().get().asItem(),
                        ANCIENT_DARK_OAK.block().get().asItem(),
                        ANCIENT_SPRUCE.block().get().asItem(),
                        ANCIENT_MANGROVE.block().get().asItem(),
                        ANCIENT_BAMBOO.block().get().asItem(),
                        ANCIENT_CHERRY.block().get().asItem(),
                        ANCIENT_CRIMSON.block().get().asItem(),
                        ANCIENT_WARPED.block().get().asItem(),
                        NYMPH_PLANKS.block().get().asItem(),
                        PALM_PLANKS.block().get().asItem()
                );
                tag(ItemTags.SIGNS).add(
                        ItemInit.NYMPH_SIGN.get(),
                        ItemInit.PALM_SIGN.get()
                );
                tag(ItemTags.TRAPDOORS).add(
                        ANCIENT_BIRCH.trapDoor().get().asItem(),
                        ANCIENT_ACACIA.trapDoor().get().asItem(),
                        ANCIENT_JUNGLE.trapDoor().get().asItem(),
                        ANCIENT_OAK.trapDoor().get().asItem(),
                        ANCIENT_DARK_OAK.trapDoor().get().asItem(),
                        ANCIENT_SPRUCE.trapDoor().get().asItem(),
                        ANCIENT_MANGROVE.trapDoor().get().asItem(),
                        ANCIENT_BAMBOO.trapDoor().get().asItem(),
                        ANCIENT_CHERRY.trapDoor().get().asItem(),
                        ANCIENT_CRIMSON.trapDoor().get().asItem(),
                        ANCIENT_WARPED.trapDoor().get().asItem(),
                        NYMPH_PLANKS.trapDoor().get().asItem(),
                        PALM_PLANKS.trapDoor().get().asItem()
                );
                for (BlockType blockType : SEA_GLASS_LIST.values()) {
                    tag(ItemTags.WALLS).add(blockType.wall().get().asItem());
                }
                for (BlockType blockType : SEA_GLASS_PATTERNS.values()) {
                    tag(ItemTags.WALLS).add(blockType.wall().get().asItem());
                }
                tag(ItemTags.BOATS).add(
                        ItemInit.NYMPH_BOAT.get(),
                        ItemInit.PALM_BOAT.get(),
                        ItemInit.SUBMARINE.get()
                );
                tag(ItemTags.SHOVELS).add(
                        ItemInit.ORICHALCUM_SHOVEL.get(),
                        ItemInit.ORICHALCUM_SHOVEL.get()
                );
                tag(ItemTags.SWORDS).add(
                        ItemInit.ORICHALCUM_SWORD.get(),
                        ItemInit.ORICHALCUM_SWORD.get()
                );
                tag(ItemTags.HOES).add(
                        ItemInit.AQUAMARINE_HOE.get(),
                        ItemInit.ORICHALCUM_HOE.get()
                );
                tag(ItemTags.PICKAXES).add(
                        ItemInit.AQUAMARINE_PICKAXE.get(),
                        ItemInit.ORICHALCUM_PICKAXE.get()
                );
                tag(ItemTags.AXES).add(
                        ItemInit.AQUAMARINE_AXE.get(),
                        ItemInit.ORICHALCUM_AXE.get()
                );
                tag(ItemTags.WOODEN_FENCES).add(
                        ANCIENT_BIRCH.fence().get().asItem(),
                        ANCIENT_ACACIA.fence().get().asItem(),
                        ANCIENT_JUNGLE.fence().get().asItem(),
                        ANCIENT_OAK.fence().get().asItem(),
                        ANCIENT_DARK_OAK.fence().get().asItem(),
                        ANCIENT_SPRUCE.fence().get().asItem(),
                        ANCIENT_MANGROVE.fence().get().asItem(),
                        ANCIENT_BAMBOO.fence().get().asItem(),
                        ANCIENT_CHERRY.fence().get().asItem(),
                        ANCIENT_CRIMSON.fence().get().asItem(),
                        ANCIENT_WARPED.fence().get().asItem(),
                        NYMPH_PLANKS.fence().get().asItem(),
                        PALM_PLANKS.fence().get().asItem()
                );
                tag(Tags.Items.INGOTS).add(
                        ItemInit.ANCIENT_CUPRUM_INGOT.get(),
                        ItemInit.ORICHALCUM_INGOT.get()
                );
                tag(Tags.Items.GEMS).add(
                        ItemInit.AQUAMARINE_GEM.get()
                );
                tag(ItemTags.WOODEN_DOORS).add(
                        ANCIENT_BIRCH.door().get().asItem(),
                        ANCIENT_ACACIA.door().get().asItem(),
                        ANCIENT_JUNGLE.door().get().asItem(),
                        ANCIENT_OAK.door().get().asItem(),
                        ANCIENT_DARK_OAK.door().get().asItem(),
                        ANCIENT_SPRUCE.door().get().asItem(),
                        ANCIENT_MANGROVE.door().get().asItem(),
                        ANCIENT_BAMBOO.door().get().asItem(),
                        ANCIENT_CHERRY.door().get().asItem(),
                        ANCIENT_CRIMSON.door().get().asItem(),
                        ANCIENT_WARPED.door().get().asItem(),
                        NYMPH_PLANKS.door().get().asItem(),
                        PALM_PLANKS.door().get().asItem()
                );
                tag(ItemTags.WOODEN_SLABS).add(
                        ANCIENT_BIRCH.slab().get().asItem(),
                        ANCIENT_ACACIA.slab().get().asItem(),
                        ANCIENT_JUNGLE.slab().get().asItem(),
                        ANCIENT_OAK.slab().get().asItem(),
                        ANCIENT_DARK_OAK.slab().get().asItem(),
                        ANCIENT_SPRUCE.slab().get().asItem(),
                        ANCIENT_MANGROVE.slab().get().asItem(),
                        ANCIENT_BAMBOO.slab().get().asItem(),
                        ANCIENT_CHERRY.slab().get().asItem(),
                        ANCIENT_CRIMSON.slab().get().asItem(),
                        ANCIENT_WARPED.slab().get().asItem(),
                        NYMPH_PLANKS.slab().get().asItem(),
                        PALM_PLANKS.slab().get().asItem()
                );
                tag(ItemTags.WOODEN_STAIRS).add(
                        ANCIENT_BIRCH.stairs().get().asItem(),
                        ANCIENT_ACACIA.stairs().get().asItem(),
                        ANCIENT_JUNGLE.stairs().get().asItem(),
                        ANCIENT_OAK.stairs().get().asItem(),
                        ANCIENT_DARK_OAK.stairs().get().asItem(),
                        ANCIENT_SPRUCE.stairs().get().asItem(),
                        ANCIENT_MANGROVE.stairs().get().asItem(),
                        ANCIENT_BAMBOO.stairs().get().asItem(),
                        ANCIENT_CHERRY.stairs().get().asItem(),
                        ANCIENT_CRIMSON.stairs().get().asItem(),
                        ANCIENT_WARPED.stairs().get().asItem(),
                        NYMPH_PLANKS.stairs().get().asItem(),
                        PALM_PLANKS.stairs().get().asItem()
                );
                tag(ItemTags.WOODEN_PRESSURE_PLATES).add(
                        ANCIENT_BIRCH.pressurePlate().get().asItem(),
                        ANCIENT_ACACIA.pressurePlate().get().asItem(),
                        ANCIENT_JUNGLE.pressurePlate().get().asItem(),
                        ANCIENT_OAK.pressurePlate().get().asItem(),
                        ANCIENT_DARK_OAK.pressurePlate().get().asItem(),
                        ANCIENT_SPRUCE.pressurePlate().get().asItem(),
                        ANCIENT_MANGROVE.pressurePlate().get().asItem(),
                        ANCIENT_BAMBOO.pressurePlate().get().asItem(),
                        ANCIENT_CHERRY.pressurePlate().get().asItem(),
                        ANCIENT_CRIMSON.pressurePlate().get().asItem(),
                        ANCIENT_WARPED.pressurePlate().get().asItem(),
                        NYMPH_PLANKS.pressurePlate().get().asItem(),
                        PALM_PLANKS.pressurePlate().get().asItem()
                );
                tag(ItemTags.WOODEN_TRAPDOORS).add(
                        ANCIENT_BIRCH.trapDoor().get().asItem(),
                        ANCIENT_ACACIA.trapDoor().get().asItem(),
                        ANCIENT_JUNGLE.trapDoor().get().asItem(),
                        ANCIENT_OAK.trapDoor().get().asItem(),
                        ANCIENT_DARK_OAK.trapDoor().get().asItem(),
                        ANCIENT_SPRUCE.trapDoor().get().asItem(),
                        ANCIENT_MANGROVE.trapDoor().get().asItem(),
                        ANCIENT_BAMBOO.trapDoor().get().asItem(),
                        ANCIENT_CHERRY.trapDoor().get().asItem(),
                        ANCIENT_CRIMSON.trapDoor().get().asItem(),
                        ANCIENT_WARPED.trapDoor().get().asItem(),
                        NYMPH_PLANKS.trapDoor().get().asItem(),
                        PALM_PLANKS.trapDoor().get().asItem()
                );
                tag(ItemTags.WOODEN_BUTTONS).add(
                        ANCIENT_BIRCH.fence().get().asItem(),
                        ANCIENT_ACACIA.fence().get().asItem(),
                        ANCIENT_JUNGLE.fence().get().asItem(),
                        ANCIENT_OAK.fence().get().asItem(),
                        ANCIENT_DARK_OAK.fence().get().asItem(),
                        ANCIENT_SPRUCE.fence().get().asItem(),
                        ANCIENT_MANGROVE.fence().get().asItem(),
                        ANCIENT_BAMBOO.fence().get().asItem(),
                        ANCIENT_CHERRY.fence().get().asItem(),
                        ANCIENT_CRIMSON.fence().get().asItem(),
                        ANCIENT_WARPED.fence().get().asItem(),
                        NYMPH_PLANKS.fence().get().asItem(),
                        PALM_PLANKS.button().get().asItem()
                );
                tag(ItemTags.NON_FLAMMABLE_WOOD).add(
                        BlockInit.ANCIENT_CRIMSON.button().get().asItem(),
                        BlockInit.ANCIENT_CRIMSON.pressurePlate().get().asItem(),
                        BlockInit.ANCIENT_CRIMSON.block().get().asItem(),
                        BlockInit.ANCIENT_CRIMSON.slab().get().asItem(),
                        BlockInit.ANCIENT_CRIMSON.fence().get().asItem(),
                        BlockInit.ANCIENT_CRIMSON.fenceGate().get().asItem(),
                        BlockInit.ANCIENT_CRIMSON.stairs().get().asItem(),
                        BlockInit.ANCIENT_CRIMSON.door().get().asItem(),
                        BlockInit.ANCIENT_CRIMSON.trapDoor().get().asItem(),
                        BlockInit.ANCIENT_CRIMSON.button().get().asItem(),
                        BlockInit.ANCIENT_CRIMSON.pressurePlate().get().asItem(),
                        BlockInit.ANCIENT_WARPED.button().get().asItem(),
                        BlockInit.ANCIENT_WARPED.pressurePlate().get().asItem(),
                        BlockInit.ANCIENT_WARPED.block().get().asItem(),
                        BlockInit.ANCIENT_WARPED.slab().get().asItem(),
                        BlockInit.ANCIENT_WARPED.fence().get().asItem(),
                        BlockInit.ANCIENT_WARPED.fenceGate().get().asItem(),
                        BlockInit.ANCIENT_WARPED.stairs().get().asItem(),
                        BlockInit.ANCIENT_WARPED.door().get().asItem(),
                        BlockInit.ANCIENT_WARPED.trapDoor().get().asItem(),
                        BlockInit.ANCIENT_WARPED.button().get().asItem(),
                        BlockInit.ANCIENT_WARPED.pressurePlate().get().asItem()
                );
                tag(ItemTags.LEAVES).add(
                        NYMPH_LEAVES.get().asItem(),
                        PALM_LEAVES.get().asItem()
                );
                tag(ItemTags.LOGS_THAT_BURN).add(
                        BlockInit.NYMPH_LOG.get().asItem(),
                        BlockInit.STRIPPED_NYMPH_LOG.get().asItem(),
                        BlockInit.PALM_LOG.get().asItem(),
                        BlockInit.STRIPPED_PALM_LOG.get().asItem()
                );
                tag(ItemTags.LOGS).add(
                        BlockInit.NYMPH_LOG.get().asItem(),
                        BlockInit.STRIPPED_NYMPH_LOG.get().asItem(),
                        BlockInit.PALM_LOG.get().asItem(),
                        BlockInit.STRIPPED_PALM_LOG.get().asItem()
                );
                tag(ItemTags.CREEPER_DROP_MUSIC_DISCS).add(ItemInit.PANBEE.get(), ItemInit.COLUMN_CAVITATION.get());
                TagsInit.Item.getItemsThatCanSink().stream().map(ItemLike::asItem).map(Item::builtInRegistryHolder).map(Holder.Reference::key).forEach(tag::add);
                var trimmables = tag(ItemTags.TRIMMABLE_ARMOR);
                addArmorSet(trimmables, ItemInit.AQUAMARINE);
                addArmorSet(trimmables, ItemInit.BROWN_WROUGHT);
                addArmorSet(trimmables, ItemInit.ORICHALCUM);
            }

            private void addArmorSet(IntrinsicTagAppender<Item> tag, AtlantisArmorSet set) {
                tag.add(set.helmet().get(), set.chestplate().get(), set.leggings().get(), set.boots().get());
            }
        });
    }

    private static void dropSelfIfSilkTouched(Block block, BiConsumer<ResourceKey<LootTable>, LootTable.Builder> builder, ItemEnchantmentsPredicate.Enchantments.Enchantments silkTouch) {
        builder.accept(block.getLootTable(), LootTable.lootTable().withPool(LootPool.lootPool().add(LootItem.lootTableItem(block).when(MatchTool.toolMatches(
                ItemPredicate.Builder.item().withSubPredicate(ItemSubPredicates.ENCHANTMENTS, silkTouch)
        )))));
    }

    private static void dropSelfIfSilkTouchedOrItem(Block block, Item item, BiConsumer<ResourceKey<LootTable>, LootTable.Builder> builder, ItemEnchantmentsPredicate.Enchantments.Enchantments silkTouch) {
        builder.accept(block.getLootTable(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .add(LootItem.lootTableItem(block).when(MatchTool.toolMatches(
                                ItemPredicate.Builder.item().withSubPredicate(ItemSubPredicates.ENCHANTMENTS, silkTouch))))
                        .add(LootItem.lootTableItem(item).when(ExplosionCondition.survivesExplosion()))));
    }

    private static void dropLeaves(Block block, Block block2, BiConsumer<ResourceKey<LootTable>, LootTable.Builder> builder, ItemEnchantmentsPredicate.Enchantments silkTouchPredicate) {
        builder.accept(block.getLootTable(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .add(LootItem.lootTableItem(block).when(MatchTool.toolMatches(
                                ItemPredicate.Builder.item().withSubPredicate(ItemSubPredicates.ENCHANTMENTS, silkTouchPredicate))))
                        .add(LootItem.lootTableItem(block2).when(ExplosionCondition.survivesExplosion()))
                        .add(LootItem.lootTableItem(Items.STICK).when(ExplosionCondition.survivesExplosion()))));
    }

    private static void dropSelf(Block block, BiConsumer<ResourceKey<LootTable>, LootTable.Builder> builder) {
        builder.accept(block.getLootTable(), LootTable.lootTable().withPool(LootPool.lootPool().add(LootItem.lootTableItem(block).when(ExplosionCondition.survivesExplosion())).add(LootItem.lootTableItem(block))));
    }

    private static void dropItemFromBlock(Block block, Item item, BiConsumer<ResourceKey<LootTable>, LootTable.Builder> builder) {
        builder.accept(block.getLootTable(), LootTable.lootTable().withPool(LootPool.lootPool().add(LootItem.lootTableItem(item).when(ExplosionCondition.survivesExplosion())).add(LootItem.lootTableItem(item))));
    }

    private static void dropAlternativeBlock(Block block, Block block2, BiConsumer<ResourceKey<LootTable>, LootTable.Builder> builder) {
        builder.accept(block.getLootTable(), LootTable.lootTable().withPool(LootPool.lootPool().add(LootItem.lootTableItem(block2).when(ExplosionCondition.survivesExplosion())).add(LootItem.lootTableItem(block2))));
    }

    private static class MyRecipeProvider extends RecipeProvider {
        public MyRecipeProvider(PackOutput output, GatherDataEvent event) {
            super(output, event.getLookupProvider());
        }

        @Override
        protected void buildRecipes(@NotNull RecipeOutput recipeOutput) {
            var list = ItemInit.getScrolls();
            var ingredient = Ingredient.of(list.toArray(Item[]::new));

            for (var result : list) {
                glyphScroll(recipeOutput, result, ingredient);
            }

            for (WeatheringCuprum.WeatherState state : WeatheringCuprum.WeatherState.values()) {
                var group = BlockInit.ANCIENT_CUPRUM.get(state);
                registerGroup(group, recipeOutput);

                if (WeatheringCuprum.WeatherState.UNAFFECTED == group.block().get().getAge()) {
                    SimpleCookingRecipeBuilder.smelting(Ingredient.of(BlockInit.RAW_ANCIENT_CUPRUM_BLOCK.get()), RecipeCategory.BUILDING_BLOCKS, group.block().get(), 0.7F, 200)
                            .unlockedBy(getHasName(group.block().get()), has(group.block().get()))
                            .save(recipeOutput, Atlantis.id(group.block().get().getDescriptionId().replace("block.atlantis.", "") + "_smelting"));

                    SimpleCookingRecipeBuilder.blasting(Ingredient.of(BlockInit.RAW_ANCIENT_CUPRUM_BLOCK.get()), RecipeCategory.BUILDING_BLOCKS, group.block().get(), 0.7F, 100)
                            .unlockedBy(getHasName(group.block().get()), has(group.block().get()))
                            .save(recipeOutput, Atlantis.id(group.block().get().getDescriptionId().replace("block.atlantis.", "") + "_blasting"));
                }
            }

            registerWood(ANCIENT_BAMBOO, recipeOutput);
            registerWood(ANCIENT_ACACIA, recipeOutput);
            registerWood(ANCIENT_SPRUCE, recipeOutput);
            registerWood(ANCIENT_BIRCH, recipeOutput);
            registerWood(ANCIENT_CRIMSON, recipeOutput);
            registerWood(ANCIENT_WARPED, recipeOutput);
            registerWood(ANCIENT_JUNGLE, recipeOutput);
            registerWood(ANCIENT_OAK, recipeOutput);
            registerWood(ANCIENT_DARK_OAK, recipeOutput);
            registerWood(ANCIENT_MANGROVE, recipeOutput);
            registerWood(ANCIENT_CHERRY, recipeOutput);
            registerWood(NYMPH_PLANKS, recipeOutput);
            registerWood(PALM_PLANKS, recipeOutput);

            registerSeaGlass(recipeOutput);

            SimpleCookingRecipeBuilder.smelting(Ingredient.of(ItemInit.RAW_ANCIENT_CUPRUM.get()), RecipeCategory.BUILDING_BLOCKS, ItemInit.ANCIENT_CUPRUM_INGOT.get(), 0.7F, 200)
                    .unlockedBy(getHasName(ItemInit.RAW_ANCIENT_CUPRUM.get()), has(ItemInit.RAW_ANCIENT_CUPRUM.get()))
                    .save(recipeOutput, Atlantis.id(ItemInit.ANCIENT_CUPRUM_INGOT.get().getDescriptionId().replace("item.atlantis.", "") + "_smelting"));

            SimpleCookingRecipeBuilder.blasting(Ingredient.of(ItemInit.RAW_ANCIENT_CUPRUM.get()), RecipeCategory.BUILDING_BLOCKS, ItemInit.ANCIENT_CUPRUM_INGOT.get(), 0.7F, 100)
                    .unlockedBy(getHasName(ItemInit.RAW_ANCIENT_CUPRUM.get()), has(ItemInit.RAW_ANCIENT_CUPRUM.get()))
                    .save(recipeOutput, Atlantis.id(ItemInit.ANCIENT_CUPRUM_INGOT.get().getDescriptionId().replace("item.atlantis.", "") + "_blasting"));

            ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockInit.RAW_ANCIENT_CUPRUM_BLOCK.get(), 1)
                    .pattern("###")
                    .pattern("###")
                    .pattern("###")
                    .define('#', ItemInit.RAW_ANCIENT_CUPRUM.get())
                    .unlockedBy(getHasName(ItemInit.RAW_ANCIENT_CUPRUM.get()), has(ItemInit.RAW_ANCIENT_CUPRUM.get()))
                    .save(recipeOutput, Atlantis.id(BlockInit.RAW_ANCIENT_CUPRUM_BLOCK.get().getDescriptionId().replace("block.atlantis.", "") + "_recipe"));
        }

        private static void registerWood(BlockType blockType, RecipeOutput recipeOutput) {
            ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, blockType.button().get())
                    .requires(blockType.block().get())
                    .unlockedBy(getHasName(blockType.block().get()), has(blockType.block().get()))
                    .save(recipeOutput, Atlantis.id(blockType.button().get().getDescriptionId().replace("block.atlantis.", "") + "_recipe"));
            ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, blockType.door().get(), 6)
                    .pattern("## ")
                    .pattern("## ")
                    .pattern("## ")
                    .define('#', blockType.block().get())
                    .unlockedBy(getHasName(blockType.block().get()), has(blockType.block().get()))
                    .save(recipeOutput, Atlantis.id(blockType.door().get().getDescriptionId().replace("block.atlantis.", "") + "_recipe"));
            ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, blockType.trapDoor().get(), 6)
                    .pattern("###")
                    .pattern("###")
                    .define('#', blockType.block().get())
                    .unlockedBy(getHasName(blockType.block().get()), has(blockType.block().get()))
                    .save(recipeOutput, Atlantis.id(blockType.trapDoor().get().getDescriptionId().replace("block.atlantis.", "") + "_recipe"));
            ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, blockType.slab().get(), 6)
                    .pattern("###")
                    .define('#', blockType.block().get())
                    .unlockedBy(getHasName(blockType.block().get()), has(blockType.block().get()))
                    .save(recipeOutput, Atlantis.id(blockType.slab().get().getDescriptionId().replace("block.atlantis.", "") + "_recipe"));
            ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, blockType.stairs().get(), 4)
                    .pattern("#  ")
                    .pattern("## ")
                    .pattern("###")
                    .define('#', blockType.block().get())
                    .unlockedBy(getHasName(blockType.block().get()), has(blockType.block().get()))
                    .save(recipeOutput, Atlantis.id(blockType.stairs().get().getDescriptionId().replace("block.atlantis.", "") + "_recipe"));
            ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, blockType.fence().get(), 3)
                    .pattern("#W#")
                    .pattern("#W#")
                    .define('W', Items.STICK)
                    .define('#', blockType.block().get())
                    .unlockedBy(getHasName(blockType.block().get()), has(blockType.block().get()))
                    .save(recipeOutput, Atlantis.id(blockType.fence().get().getDescriptionId().replace("block.atlantis.", "") + "_recipe"));
            ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, blockType.fenceGate().get(), 1)
                    .pattern("#W#")
                    .pattern("#W#")
                    .define('W', Items.STICK)
                    .define('#', blockType.block().get())
                    .unlockedBy(getHasName(blockType.block().get()), has(blockType.block().get()))
                    .save(recipeOutput, Atlantis.id(blockType.fenceGate().get().getDescriptionId().replace("block.atlantis.", "") + "_recipe"));
            ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, blockType.pressurePlate().get(), 1)
                    .pattern("## ")
                    .define('#', blockType.block().get())
                    .unlockedBy(getHasName(blockType.block().get()), has(blockType.block().get()))
                    .save(recipeOutput, Atlantis.id(blockType.pressurePlate().get().getDescriptionId().replace("block.atlantis.", "") + "_recipe"));
        }

        private static void registerSeaGlass(RecipeOutput recipeOutput) {
            for (DyeColor color : DyeColor.values()) {
                var blockType = BlockInit.SEA_GLASS_PATTERNS.get(color);
                var blockType2 = SEA_GLASS_LIST.get(color);
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, blockType.block().get(), 4)
                        .pattern("## ")
                        .pattern("## ")
                        .pattern("   ")
                        .define('#', blockType2.block().get())
                        .unlockedBy(getHasName(blockType2.block().get().asItem()), has(blockType2.block().get().asItem()))
                        .save(recipeOutput, Atlantis.id(blockType.block().get().getDescriptionId().replace("block.atlantis.", "") + "_recipe"));
                ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, blockType.button().get())
                        .requires(blockType.block().get())
                        .unlockedBy(getHasName(blockType.block().get()), has(blockType.block().get()))
                        .save(recipeOutput, Atlantis.id(blockType.button().get().getDescriptionId().replace("block.atlantis.", "") + "_recipe"));
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, blockType.slab().get(), 6)
                        .pattern("###")
                        .define('#', blockType.block().get())
                        .unlockedBy(getHasName(blockType.block().get()), has(blockType.block().get()))
                        .save(recipeOutput, Atlantis.id(blockType.slab().get().getDescriptionId().replace("block.atlantis.", "") + "_recipe"));
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, blockType.stairs().get(), 4)
                        .pattern("#  ")
                        .pattern("## ")
                        .pattern("###")
                        .define('#', blockType.block().get())
                        .unlockedBy(getHasName(blockType.block().get()), has(blockType.block().get()))
                        .save(recipeOutput, Atlantis.id(blockType.stairs().get().getDescriptionId().replace("block.atlantis.", "") + "_recipe"));
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, blockType.wall().get(), 6)
                        .pattern("###")
                        .pattern("###")
                        .define('#', blockType.block().get())
                        .unlockedBy(getHasName(blockType.block().get()), has(blockType.block().get()))
                        .save(recipeOutput, Atlantis.id(blockType.wall().get().getDescriptionId().replace("block.atlantis.", "") + "_recipe"));
                ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, blockType.pressurePlate().get(), 1)
                        .pattern("## ")
                        .define('#', blockType.block().get())
                        .unlockedBy(getHasName(blockType.block().get()), has(blockType.block().get()))
                        .save(recipeOutput, Atlantis.id(blockType.pressurePlate().get().getDescriptionId().replace("block.atlantis.", "") + "_recipe"));
            }
        }

        private static void registerGroup(TrailsGroup group, RecipeOutput recipeOutput) {
            ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, group.bulb().get(), 1)
                    .pattern(" # ")
                    .pattern("#X#")
                    .pattern(" R ")
                    .define('#', group.block().get())
                    .define('X', Items.BLAZE_ROD)
                    .define('R', Items.REDSTONE)
                    .unlockedBy(getHasName(ItemInit.ANCIENT_CUPRUM_INGOT.get()), has(ItemInit.ANCIENT_CUPRUM_INGOT.get()))
                    .save(recipeOutput, Atlantis.id(group.bulb().get().getDescriptionId().replace("block.atlantis.", "") + "_recipe"));

            ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, group.block().get(), 1)
                    .pattern("###")
                    .pattern("###")
                    .pattern("###")
                    .define('#', group.block().get())
                    .unlockedBy(getHasName(ItemInit.ANCIENT_CUPRUM_INGOT.get()), has(ItemInit.ANCIENT_CUPRUM_INGOT.get()))
                    .save(recipeOutput, Atlantis.id(group.block().get().getDescriptionId().replace("block.atlantis.", "") + "_recipe"));

            ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, group.cut().get(), 4)
                    .pattern("##")
                    .pattern("##")
                    .define('#', group.block().get())
                    .unlockedBy(getHasName(ItemInit.ANCIENT_CUPRUM_INGOT.get()), has(ItemInit.ANCIENT_CUPRUM_INGOT.get()))
                    .save(recipeOutput, Atlantis.id(group.cut().get().getDescriptionId().replace("block.atlantis.", "") + "_recipe"));

            ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, group.cut_slab().get(), 6)
                    .pattern("###")
                    .define('#', group.cut().get())
                    .unlockedBy(getHasName(ItemInit.ANCIENT_CUPRUM_INGOT.get()), has(ItemInit.ANCIENT_CUPRUM_INGOT.get()))
                    .save(recipeOutput, Atlantis.id(group.cut_slab().get().getDescriptionId().replace("block.atlantis.", "") + "_recipe"));

            ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, group.cut_stairs().get(), 4)
                    .pattern("#  ")
                    .pattern("## ")
                    .pattern("###")
                    .define('#', group.cut().get())
                    .unlockedBy(getHasName(ItemInit.ANCIENT_CUPRUM_INGOT.get()), has(ItemInit.ANCIENT_CUPRUM_INGOT.get()))
                    .save(recipeOutput, Atlantis.id(group.cut_stairs().get().getDescriptionId().replace("block.atlantis.", "") + "_recipe"));

            ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, group.chiseled().get(), 1)
                    .pattern(" # ")
                    .pattern(" # ")
                    .pattern("   ")
                    .define('#', group.cut_slab().get())
                    .unlockedBy(getHasName(ItemInit.ANCIENT_CUPRUM_INGOT.get()), has(ItemInit.ANCIENT_CUPRUM_INGOT.get()))
                    .save(recipeOutput, Atlantis.id(group.chiseled().get().getDescriptionId().replace("block.atlantis.", "") + "_recipe"));

            ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, group.door().get(), 3)
                    .pattern("##")
                    .pattern("##")
                    .pattern("##")
                    .define('#', ItemInit.ANCIENT_CUPRUM_INGOT.get())
                    .unlockedBy(getHasName(ItemInit.ANCIENT_CUPRUM_INGOT.get()), has(ItemInit.ANCIENT_CUPRUM_INGOT.get()))
                    .save(recipeOutput, Atlantis.id(group.door().get().getDescriptionId().replace("block.atlantis.", "") + "_recipe"));

            ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, group.trapdoor().get(), 2)
                    .pattern("###")
                    .pattern("###")
                    .define('#', ItemInit.ANCIENT_CUPRUM_INGOT.get())
                    .unlockedBy(getHasName(ItemInit.ANCIENT_CUPRUM_INGOT.get()), has(ItemInit.ANCIENT_CUPRUM_INGOT.get()))
                    .save(recipeOutput, Atlantis.id(group.trapdoor().get().getDescriptionId().replace("block.atlantis.", "") + "_recipe"));

            ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, group.grate().get(), 3)
                    .pattern(" # ")
                    .pattern("# #")
                    .pattern(" # ")
                    .define('#', group.block().get())
                    .unlockedBy(getHasName(ItemInit.ANCIENT_CUPRUM_INGOT.get()), has(ItemInit.ANCIENT_CUPRUM_INGOT.get()))
                    .save(recipeOutput, Atlantis.id(group.grate().get().getDescriptionId().replace("block.atlantis.", "") + "_recipe"));

            ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, group.waxed_block().get(), 1)
                    .requires(group.block().get())
                    .requires(Items.HONEYCOMB)
                    .unlockedBy(getHasName(group.block().get()), has(group.block().get()))
                    .save(recipeOutput, Atlantis.id(group.waxed_block().get().getDescriptionId().replace("block.atlantis.", "") + "_recipe"));

            ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, group.waxed_cut().get(), 1)
                    .requires(group.cut().get())
                    .requires(Items.HONEYCOMB)
                    .unlockedBy(getHasName(group.cut().get()), has(group.cut().get()))
                    .save(recipeOutput, Atlantis.id(group.waxed_cut().get().getDescriptionId().replace("block.atlantis.", "") + "_recipe"));

            ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, group.waxed_chiseled().get(), 1)
                    .requires(group.chiseled().get())
                    .requires(Items.HONEYCOMB)
                    .unlockedBy(getHasName(group.chiseled().get()), has(group.chiseled().get()))
                    .save(recipeOutput, Atlantis.id(group.waxed_chiseled().get().getDescriptionId().replace("block.atlantis.", "") + "_recipe"));

            ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, group.waxed_cut_slab().get(), 1)
                    .requires(group.cut_slab().get())
                    .requires(Items.HONEYCOMB)
                    .unlockedBy(getHasName(group.cut_slab().get()), has(group.cut_slab().get()))
                    .save(recipeOutput, Atlantis.id(group.waxed_cut_slab().get().getDescriptionId().replace("block.atlantis.", "") + "_recipe"));

            ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, group.waxed_cut_stairs().get(), 1)
                    .requires(group.cut_stairs().get())
                    .requires(Items.HONEYCOMB)
                    .unlockedBy(getHasName(group.cut_stairs().get()), has(group.cut_stairs().get()))
                    .save(recipeOutput, Atlantis.id(group.waxed_cut_stairs().get().getDescriptionId().replace("block.atlantis.", "") + "_recipe"));

            ShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, group.waxed_door().get(), 1)
                    .requires(group.door().get())
                    .requires(Items.HONEYCOMB)
                    .unlockedBy(getHasName(group.door().get()), has(group.door().get()))
                    .save(recipeOutput, Atlantis.id(group.waxed_door().get().getDescriptionId().replace("block.atlantis.", "") + "_recipe"));

            ShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, group.waxed_trapdoor().get(), 1)
                    .requires(group.trapdoor().get())
                    .requires(Items.HONEYCOMB)
                    .unlockedBy(getHasName(group.trapdoor().get()), has(group.trapdoor().get()))
                    .save(recipeOutput, Atlantis.id(group.waxed_trapdoor().get().getDescriptionId().replace("block.atlantis.", "") + "_recipe"));

            ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, group.waxed_grate().get(), 1)
                    .requires(group.grate().get())
                    .requires(Items.HONEYCOMB)
                    .unlockedBy(getHasName(group.grate().get()), has(group.grate().get()))
                    .save(recipeOutput, Atlantis.id(group.waxed_grate().get().getDescriptionId().replace("block.atlantis.", "") + "_recipe"));

            ShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, group.waxed_bulb().get(), 1)
                    .requires(group.bulb().get())
                    .requires(Items.HONEYCOMB)
                    .unlockedBy(getHasName(group.bulb().get()), has(group.bulb().get()))
                    .save(recipeOutput, Atlantis.id(group.waxed_bulb().get().getDescriptionId().replace("block.atlantis.", "") + "_recipe"));
        }

        private static void glyphScroll(RecipeOutput recipeOutput, ItemLike result, Ingredient material) {
            writing(material, RecipeCategory.MISC, result, 1)
                    .unlockedBy(getHasName(ItemInit.LINGUISTIC_GLYPH_SCROLL.get()), has(ItemInit.LINGUISTIC_GLYPH_SCROLL.get()))
                    .save(recipeOutput, getConversionRecipeName(result, ItemInit.LINGUISTIC_GLYPH_SCROLL.get()) + "_writing");
        }

        public static SingleItemRecipeBuilder writing(Ingredient ingredient, RecipeCategory category, ItemLike result, int count) {
            return new SingleItemRecipeBuilder(category, WritingRecipe::new, ingredient, result, count);
        }
    }
}
