package com.mystic.atlantis.data;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mystic.atlantis.init.ItemInit;
import com.mystic.atlantis.util.Reference;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
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
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class AtlantisModifier {

    private static final DeferredRegister<Codec<? extends IGlobalLootModifier>> GLM = DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, Reference.MODID);

    private static final RegistryObject<Codec<SeaGrassModifier>> SEEDS_DROP = GLM.register("seeds_drop", SeaGrassModifier.CODEC);

    public static void init(IEventBus bus) { GLM.register(bus); }

    public static class EventHandlers {
        @SubscribeEvent
        public static void runData(GatherDataEvent event) {
            event.getGenerator().addProvider(event.includeServer(), new DataProvider(event.getGenerator(), Reference.MODID));
        }
    }

    private static class DataProvider extends GlobalLootModifierProvider {
        public DataProvider(DataGenerator gen, String modid) {
            super(gen, modid);
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
