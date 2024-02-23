package com.mystic.atlantis.datagen;

import com.mystic.atlantis.blocks.base.*;
import com.mystic.atlantis.blocks.plants.AtlanteanSaplingBlock;
import com.mystic.atlantis.blocks.shells.ColoredShellBlock;
import com.mystic.atlantis.blocks.shells.OysterShellBlock;
import com.mystic.atlantis.init.AtlantisEntityInit;
import com.mystic.atlantis.init.BlockInit;
import com.mystic.atlantis.init.ItemInit;
import com.mystic.atlantis.util.Reference;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.packs.VanillaBlockLoot;
import net.minecraft.data.loot.packs.VanillaEntityLoot;
import net.minecraft.data.loot.packs.VanillaLootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
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
import net.minecraft.world.level.storage.loot.functions.LootingEnchantFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithLootingCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class AtlantisLootTableProvider extends LootTableProvider {

	public AtlantisLootTableProvider(DataGenerator pGenerator) {
		super(pGenerator.getPackOutput(), Set.of(), VanillaLootTableProvider.create(pGenerator.getPackOutput()).getTables());
	}

//	@Override
//	public String getName() {
//		return Reference.NAME + ": ";
//	}

	@Override
	protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationtracker) {
//		super.validate(map, validationtracker);
	}

	@Override
	public List<SubProviderEntry> getTables() {
		return List.of(new SubProviderEntry(AtlantisBlockLootTableProvider::new, LootContextParamSets.BLOCK));
	}

	public static class AtlantisBlockLootTableProvider extends VanillaBlockLoot {
		private static final float[] NORMAL_LEAVES_SAPLING_CHANCES = new float[]{0.05F, 0.0625F, 0.083333336F, 0.1F};

		@Override
		protected void generate() {
			for (DeferredHolder<Block, ? extends Block> blockGenEntry : BlockInit.BLOCKS.getEntries()) {
				Block blockEntry = blockGenEntry.get();

				// Checks (UNTESTED)
				//TODO Dimension GlobalLootModifier <- gtg atm, should I just commit this in the meantime?
				if (blockEntry instanceof PearlBlock || blockEntry instanceof ColoredShellBlock || blockEntry instanceof OysterShellBlock) dropWhenSilkTouch(blockEntry);
				else if (blockEntry instanceof SeaSaltChunkBlock) add(blockEntry, createOreDrop(blockEntry, ItemInit.SEA_SALT.get()));
				else if (blockEntry instanceof AtlanteanPrismarineBlock) add(blockEntry, createOreDrop(blockEntry, Items.PRISMARINE));
				else if (blockEntry instanceof DropExperienceBlock) add(blockEntry, createOreDrop(blockEntry, ItemInit.AQUAMARINE_GEM.get())); //TODO Unhard-code
				else if (blockEntry instanceof SunkenGravelBlock) add(blockEntry, createGravelDrop(blockEntry, Items.GLOWSTONE));
				else if (blockEntry instanceof AtlanteanSaplingBlock) add(blockEntry, createLeavesDrops(blockEntry, BlockInit.ATLANTEAN_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
//				else if (blockEntry instanceof AtlanteanFireMelonFruitBlock) add(blockEntry, createToolOnlyDrop(blockEntry, BlockTags.TOOLS_HOES)); //TODO FIX
//				else if (blockEntry instanceof AtlanteanFireMelonSpikedFruitBlock) add(blockEntry, createToolOnlyDrop(blockEntry, Tags.Items.TOOLS_HOES, Tags.Items.SHEARS, ItemInit.ATLANTEAN_FIRE_MELON_SPIKE.get()));
				else if (blockEntry instanceof AtlantianSeaLanternBlock || blockEntry instanceof OceanLanternBlock) add(blockEntry, createSeaLanternDrop(blockEntry));
				else dropSelf(blockEntry);
			}
		}

		@Override
		protected Iterable<Block> getKnownBlocks() {
			return BuiltInRegistries.BLOCK.stream()
					.filter(block -> Objects.requireNonNull(BuiltInRegistries.BLOCK.getKey(block)).getNamespace().equals(Reference.MODID))
					.collect(Collectors.toList());
		}

		private LootTable.Builder createGravelDrop(Block gravelBlock) {
			return createSilkTouchDispatchTable(gravelBlock, applyExplosionCondition(gravelBlock, LootItem.lootTableItem(Items.FLINT).when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.1F, 0.14285715F, 0.25F, 1.0F)).otherwise(LootItem.lootTableItem(gravelBlock))));
		}

		private LootTable.Builder createGravelDrop(Block gravelBlock, Item altItem) {
			return createSilkTouchDispatchTable(gravelBlock, applyExplosionCondition(gravelBlock, LootItem.lootTableItem(altItem).when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.1F, 0.14285715F, 0.25F, 1.0F)).otherwise(LootItem.lootTableItem(gravelBlock))));
		}
		
		private LootTable.Builder createSeaLanternDrop(Block seaLanternBlock) {
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

	public static class AtlantisEntityLootTableProvider extends VanillaEntityLoot {
		@Override
		public void generate() {
			for (DeferredHolder<EntityType<?>, ? extends EntityType<?>> entityGetEntry : AtlantisEntityInit.ENTITIES.getEntries()) {

				EntityType<?> entityTypeEntry = entityGetEntry.get();

				// Checks (UNTESTED)
				if (entityTypeEntry.equals(AtlantisEntityInit.CRAB.get())) createSingleLootDrop(ItemInit.CRAB_LEGS.get());
				else if (entityTypeEntry.equals(AtlantisEntityInit.JELLYFISH.get())) createSingleLootDrop(ItemInit.ATLANTEAN_STRING.get());
				else if (entityTypeEntry.equals(AtlantisEntityInit.SEAHORSE.get())) createSingleLootDrop(Items.SEAGRASS);
				else if (entityTypeEntry.equals(AtlantisEntityInit.SHRIMP.get()) || entityTypeEntry.equals(AtlantisEntityInit.STARFISH.get())) createSingleLootDrop(ItemInit.SHRIMP.get());
				else if (entityTypeEntry.equals(AtlantisEntityInit.SEAHORSE.get())) createUnderwaterUndeadLootDrop(ItemInit.SHRIMP.get());
				else if (entityTypeEntry.equals(AtlantisEntityInit.SUBMARINE.get())) createSingleLootDrop(ItemInit.SUBMARINE.get());
			}
		}

		private static void createSingleLootDrop(Item item) {
			LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(item).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))));
		}

		private static void createUnderwaterUndeadLootDrop(Item item) {
			LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(Items.ROTTEN_FLESH).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F))).apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(item)).when(LootItemKilledByPlayerCondition.killedByPlayer()).when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.11F, 0.02F)));
		}
	}
}
