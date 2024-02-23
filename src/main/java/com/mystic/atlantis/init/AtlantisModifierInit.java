package com.mystic.atlantis.init;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mystic.atlantis.util.Reference;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class AtlantisModifierInit {
	public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> GLM = DeferredRegister.create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, Reference.MODID);

	public static final Supplier<Codec<SeaGrassModifier>> SEEDS_DROP = GLM.register("seeds_drop", SeaGrassModifier.CODEC);

	public static void init(IEventBus bus) {
		GLM.register(bus); 
	}

	public static class EventHandlers {
		@SubscribeEvent
		public static void runData(GatherDataEvent event) {
			event.getGenerator().addProvider(event.includeServer(), new DataProvider(event.getGenerator(), Reference.MODID));
		}
	}

	private static class DataProvider extends GlobalLootModifierProvider {
		public DataProvider(DataGenerator gen, String modid) {
			super(gen.getPackOutput(), modid);
		}

		@Override
		protected void start() {
			add("seeds_drop", new SeaGrassModifier(
					new LootItemCondition[]{
							LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.SEAGRASS).build()
					})
					);
		}
	}

	private static class SeaGrassModifier extends LootModifier {
		public static final Supplier<Codec<SeaGrassModifier>> CODEC = Suppliers.memoize(() -> RecordCodecBuilder.create(inst -> codecStart(inst)
				.apply(inst, SeaGrassModifier::new)
				));

		public SeaGrassModifier(LootItemCondition[] conditionsIn) {
			super(conditionsIn);
		}

		@Override
		public ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {

			ItemStack ctxTool = context.getParamOrNull(LootContextParams.TOOL);
			RandomSource random = context.getRandom();
			if (ctxTool != null) {
				int silkTouch = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, ctxTool);
				if (silkTouch > 0 || ctxTool.getItem() instanceof ShearsItem) {
					return generatedLoot;
				}
			}
			int bonusLevel = ctxTool != null ? EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_FORTUNE, ctxTool) : 0;
			int seedRarity = (int) (0.5f - (bonusLevel * 2));
			if (seedRarity < 1 || random.nextInt(seedRarity) == 0) {
				BlockState ctxBlockState = context.getParamOrNull(LootContextParams.BLOCK_STATE);
				if (ctxBlockState == Blocks.SEAGRASS.defaultBlockState() || ctxBlockState == Blocks.TALL_SEAGRASS.defaultBlockState()) {
					generatedLoot.add(new ItemStack(ItemInit.ATLANTEAN_FIRE_MELON_SEEDS.get()));
				}
			}
			return generatedLoot;
		}

		@Override
		public Codec<? extends IGlobalLootModifier> codec() {
			return CODEC.get();
		}
	}
}
