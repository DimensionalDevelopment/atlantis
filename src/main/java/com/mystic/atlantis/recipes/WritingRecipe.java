package com.mystic.atlantis.recipes;

import com.google.gson.JsonObject;
import com.mystic.atlantis.init.BlockInit;
import com.mystic.atlantis.init.RecipesInit;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SingleItemRecipe;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;

public class WritingRecipe extends SingleItemRecipe {
    public WritingRecipe(String string, Ingredient arg2, ItemStack arg3) {
        super(RecipesInit.Types.WRITING, RecipesInit.Serializers.WRITING_SERIALIZER.get(), string, arg2, arg3);
    }


    @Override
    public boolean matches(SingleRecipeInput singleRecipeInput, Level level) {
        return this.ingredient.test(singleRecipeInput.getItem(0));
    }

    @Override
    public ItemStack getToastSymbol() {
        return new ItemStack(BlockInit.WRITING_TABLE.get());
    }

    public static class Serializer implements RecipeSerializer<WritingRecipe>
    {
        public WritingRecipe fromJson(JsonObject json) {
            String s = GsonHelper.getAsString(json, "group", "");
            Ingredient ingredient;
            if (GsonHelper.isArrayNode(json, "ingredient")) {
                ingredient = Ingredient.fromJson(GsonHelper.getAsJsonArray(json, "ingredient"));
            } else {
                ingredient = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "ingredient"));
            }

            String s1 = GsonHelper.getAsString(json, "result");
            int i = GsonHelper.getAsInt(json, "count");
            ItemStack itemstack = new ItemStack(BuiltInRegistries.ITEM.get(ResourceLocation.parse(s1)), i);
            return new WritingRecipe(s, ingredient, itemstack);
        }

        public WritingRecipe fromNetwork(FriendlyByteBuf buffer) {
            String s = buffer.readUtf();
            Ingredient ingredient = Ingredient.fromNetwork(buffer);
            ItemStack itemstack = buffer.readItem();
            return new WritingRecipe(s, ingredient, itemstack);
        }

        public void toNetwork(FriendlyByteBuf buffer, WritingRecipe recipe) {
            buffer.writeUtf(recipe.group);
            recipe.ingredient.toNetwork(buffer);
            buffer.writeItem(recipe.result);
        }
    }
}

