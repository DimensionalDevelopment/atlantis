package com.mystic.atlantis.data;

import com.google.gson.JsonObject;
import com.mystic.atlantis.init.ItemInit;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;

import java.util.List;
import java.util.Random;

public class AtlantisModifer extends LootModifier {

    public AtlantisModifer(LootItemCondition[] conditionsIn) {
        super(conditionsIn);
    }

    @Override
    public List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {

        ItemStack ctxTool = context.getParamOrNull(LootContextParams.TOOL);
        Random random = context.getRandom();
        if(ctxTool != null){
            int silkTouch = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, ctxTool);
            if(silkTouch > 0 || ctxTool.getItem() instanceof ShearsItem){
                return generatedLoot;
            }
        }
        int bonusLevel = ctxTool != null ? EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_FORTUNE, ctxTool) : 0;
        int seedRarity = (int) (0.5f - (bonusLevel * 2));
        if (seedRarity < 1 || random.nextInt(seedRarity) == 0) {
            generatedLoot.add(new ItemStack(ItemInit.ATLANTEAN_FIRE_MELON_SEEDS.get()));
        }
        return generatedLoot;
    }

    public static class Serializer extends GlobalLootModifierSerializer<AtlantisModifer> {

        @Override
        public AtlantisModifer read(ResourceLocation name, JsonObject object, LootItemCondition[] conditionsIn) {
            return new AtlantisModifer(conditionsIn);
        }

        @Override
        public JsonObject write(AtlantisModifer instance) {
            return this.makeConditions(instance.conditions);
        }
    }
}
