package com.mystic.atlantis.datagen;

import com.mystic.atlantis.blocks.BlockType;
import com.mystic.atlantis.blocks.ancient_cuprum.TrailsGroup;
import com.mystic.atlantis.init.BlockInit;
import com.mystic.atlantis.init.ItemInit;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.neoforged.neoforge.registries.DeferredBlock;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

import static com.mystic.atlantis.init.BlockInit.*;

public class AtlantisLootTableProvider extends LootTableProvider {

    public AtlantisLootTableProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookup) {
        super(output, Set.of(), List.of(), lookup);
    }

    @Override
    public @NotNull List<SubProviderEntry> getTables() {
        return List.of(new SubProviderEntry(this::blocks, LootContextParamSets.BLOCK));
    }

    private LootTableSubProvider blocks(HolderLookup.Provider provider) {
        var silkTouch = provider.asGetterLookup()
                .lookupOrThrow(Registries.ENCHANTMENT)
                .getOrThrow(net.minecraft.world.item.enchantment.Enchantments.SILK_TOUCH);

        var silkTouchPredicate = ItemEnchantmentsPredicate.enchantments(List.of(new EnchantmentPredicate(silkTouch, MinMaxBounds.Ints.ANY)));

        return (BiConsumer<ResourceKey<LootTable>, LootTable.Builder> consumer) -> {

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

            for (var color : DyeColor.values()) {
                var blockType = BlockInit.SEA_GLASS_PATTERNS.get(color);
                dropSelfIfSilkTouched(blockType.block().get(), consumer, silkTouchPredicate);
                dropSelfIfSilkTouched(blockType.slab().get(), consumer, silkTouchPredicate);
                dropSelfIfSilkTouched(blockType.stairs().get(), consumer, silkTouchPredicate);
                dropSelfIfSilkTouched(blockType.pressurePlate().get(), consumer, silkTouchPredicate);
                dropSelfIfSilkTouched(blockType.button().get(), consumer, silkTouchPredicate);
                dropSelfIfSilkTouched(blockType.wall().get(), consumer, silkTouchPredicate);
            }

            for (Map<DyeColor, DeferredBlock<Block>> map : DYED_LINGUISTICS.values()) {
                map.forEach(((dyeColor, block) -> dropSelf(block.get(), consumer)));
            }

            for (DeferredBlock<Block> block : NON_LINGUISTICS.values()) {
                dropSelf(block.get(), consumer);
            }

            for (DyeColor color : DyeColor.values()) {
                 dropSelfIfSilkTouchedOrItem(MOSSY_SHELL_BLOCKS.get(color).get(), ItemInit.BROKEN_SHELLS.get(), consumer, silkTouchPredicate);
                 dropSelfIfSilkTouchedOrItem(CRACKED_MOSSY_SHELL_BLOCKS.get(color).get(), ItemInit.BROKEN_SHELLS.get(), consumer, silkTouchPredicate);
                 dropSelfIfSilkTouchedOrItem(CRACKED_SHELL_BLOCKS.get(color).get(), ItemInit.BROKEN_SHELLS.get(), consumer, silkTouchPredicate);
                 dropSelfIfSilkTouchedOrItem(COLORED_SHELL_BLOCKS.get(color).get(), ItemInit.BROKEN_SHELLS.get(), consumer, silkTouchPredicate);
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
        };
    }

    // --- helpers (fixed signatures) ---

    private static void dropSelfIfSilkTouched(Block block, BiConsumer<ResourceKey<LootTable>, LootTable.Builder> builder, ItemEnchantmentsPredicate.Enchantments silkTouch) {
        builder.accept(block.getLootTable(), LootTable.lootTable().withPool(
                LootPool.lootPool().add(LootItem.lootTableItem(block).when(
                        MatchTool.toolMatches(ItemPredicate.Builder.item().withSubPredicate(ItemSubPredicates.ENCHANTMENTS, silkTouch))
                ))
        ));
    }

    private static void dropSelfIfSilkTouchedOrItem(Block block, Item item, BiConsumer<ResourceKey<LootTable>, LootTable.Builder> builder, ItemEnchantmentsPredicate.Enchantments silkTouch) {
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
        builder.accept(block.getLootTable(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .add(LootItem.lootTableItem(block).when(ExplosionCondition.survivesExplosion()))
                        .add(LootItem.lootTableItem(block))));
    }

    private static void dropItemFromBlock(Block block, Item item, BiConsumer<ResourceKey<LootTable>, LootTable.Builder> builder) {
        builder.accept(block.getLootTable(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .add(LootItem.lootTableItem(item).when(ExplosionCondition.survivesExplosion()))
                        .add(LootItem.lootTableItem(item))));
    }

    private static void dropAlternativeBlock(Block block, Block block2, BiConsumer<ResourceKey<LootTable>, LootTable.Builder> builder) {
        builder.accept(block.getLootTable(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .add(LootItem.lootTableItem(block2).when(ExplosionCondition.survivesExplosion()))
                        .add(LootItem.lootTableItem(block2))));
    }
}
