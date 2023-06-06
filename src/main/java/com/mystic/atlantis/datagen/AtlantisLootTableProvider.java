package com.mystic.atlantis.datagen;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.mojang.datafixers.util.Pair;
import com.mystic.atlantis.blocks.AtlanteanFireMelonFruitBlock;
import com.mystic.atlantis.blocks.AtlanteanFireMelonSpikedFruitBlock;
import com.mystic.atlantis.blocks.AtlanteanPrismarineBlock;
import com.mystic.atlantis.blocks.AtlantianSeaLanternBlock;
import com.mystic.atlantis.blocks.OceanLanternBlock;
import com.mystic.atlantis.blocks.PearlBlock;
import com.mystic.atlantis.blocks.SeaSaltChunkBlock;
import com.mystic.atlantis.blocks.SunkenGravelBlock;
import com.mystic.atlantis.blocks.plants.AtlanteanSaplingBlock;
import com.mystic.atlantis.blocks.shells.ColoredShellBlock;
import com.mystic.atlantis.blocks.shells.OysterShellBlock;
import com.mystic.atlantis.init.BlockInit;
import com.mystic.atlantis.init.ItemInit;
import com.mystic.atlantis.util.Reference;

import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.storage.loot.IntRange;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.LimitCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AtlantisLootTableProvider extends LootTableProvider {

	public AtlantisLootTableProvider(DataGenerator pGenerator) {
		super(pGenerator);
	}

	@Override
	public String getName() {
		return Reference.NAME + ": ";
	}

	@Override
	protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationtracker) {
//		super.validate(map, validationtracker);
	}

	@Override
	protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> getTables() {
		return List.of(Pair.of(AtlantisBlockLootTableProvider::new, LootContextParamSets.BLOCK));
	}

	public static class AtlantisBlockLootTableProvider extends BlockLoot {
		private static final float[] NORMAL_LEAVES_SAPLING_CHANCES = new float[]{0.05F, 0.0625F, 0.083333336F, 0.1F};

		@Override
		protected void addTables() {
			for (RegistryObject<Block> blockGenEntry : BlockInit.BLOCKS.getEntries()) {
				Block blockEntry = blockGenEntry.get();

				// Checks (UNTESTED)
				//TODO Dimension GlobalLootModifier <- gtg atm, should I just commit this in the meantime?
				if (blockEntry instanceof PearlBlock || blockEntry instanceof ColoredShellBlock || blockEntry instanceof OysterShellBlock) dropWhenSilkTouch(blockEntry);
				else if (blockEntry instanceof SeaSaltChunkBlock) add(blockEntry, createOreDrop(blockEntry, ItemInit.SEA_SALT.get()));
				else if (blockEntry instanceof AtlanteanPrismarineBlock) add(blockEntry, createOreDrop(blockEntry, Items.PRISMARINE));
				else if (blockEntry instanceof DropExperienceBlock) add(blockEntry, createOreDrop(blockEntry, ItemInit.AQUAMARINE_GEM.get())); //TODO Unhard-code
				else if (blockEntry instanceof SunkenGravelBlock) add(blockEntry, createGravelDrop(blockEntry, Items.GLOWSTONE));
				else if (blockEntry instanceof AtlanteanSaplingBlock) add(blockEntry, createLeavesDrops(blockEntry, BlockInit.ATLANTEAN_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
				else if (blockEntry instanceof AtlanteanFireMelonFruitBlock) add(blockEntry, createToolOnlyDrop(blockEntry, Tags.Items.TOOLS_HOES));
				else if (blockEntry instanceof AtlanteanFireMelonSpikedFruitBlock) add(blockEntry, createToolOnlyDrop(blockEntry, Tags.Items.TOOLS_HOES, Tags.Items.SHEARS, ItemInit.ATLANTEAN_FIRE_MELON_SPIKE.get()));
				else if (blockEntry instanceof AtlantianSeaLanternBlock || blockEntry instanceof OceanLanternBlock) add(blockEntry, createSeaLanternDrop(blockEntry));
				else dropSelf(blockEntry);
			}
		}

		@Override
		protected Iterable<Block> getKnownBlocks() {
			return ForgeRegistries.BLOCKS.getValues().stream()
					.filter(block -> Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block)).getNamespace().equals(Reference.MODID))
					.collect(Collectors.toList());
		}

		private static LootTable.Builder createGravelDrop(Block gravelBlock) {
			return createSilkTouchDispatchTable(gravelBlock, applyExplosionCondition(gravelBlock, LootItem.lootTableItem(Items.FLINT).when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.1F, 0.14285715F, 0.25F, 1.0F)).otherwise(LootItem.lootTableItem(gravelBlock))));
		}

		private static LootTable.Builder createGravelDrop(Block gravelBlock, Item altItem) {
			return createSilkTouchDispatchTable(gravelBlock, applyExplosionCondition(gravelBlock, LootItem.lootTableItem(altItem).when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.1F, 0.14285715F, 0.25F, 1.0F)).otherwise(LootItem.lootTableItem(gravelBlock))));
		}
		
		private static LootTable.Builder createSeaLanternDrop(Block seaLanternBlock) {
			return createSilkTouchDispatchTable(seaLanternBlock, applyExplosionDecay(seaLanternBlock, LootItem.lootTableItem(Items.PRISMARINE_CRYSTALS).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 3.0F))).apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE)).apply(LimitCount.limitCount(IntRange.range(1, 5)))));
		}
		
		private static LootTable.Builder createToolOnlyDrop(Block block, TagKey<Item> toolTag) {
			return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(block).when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(toolTag)))));
		}
		
		private static LootTable.Builder createToolOnlyDrop(Block block, TagKey<Item> toolTag, Item altDrop) {
			return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(block).when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(toolTag))).otherwise(LootItem.lootTableItem(altDrop))));
		}
		
		private static LootTable.Builder createToolOnlyDrop(Block block, TagKey<Item> toolTag, TagKey<Item> altToolTag, Item altDrop) {
			return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(block).when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(toolTag))).otherwise(LootItem.lootTableItem(altDrop).when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(altToolTag))))));
		}

	}
}
