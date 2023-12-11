package com.mystic.atlantis.recipes;

import com.google.gson.JsonObject;
import com.mystic.atlantis.init.BlockInit;
import com.mystic.atlantis.init.RecipesInit;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SingleItemRecipe;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class WritingRecipe extends SingleItemRecipe {
    public WritingRecipe(ResourceLocation arg, String string, Ingredient arg2, ItemStack arg3) {
        super(RecipesInit.Types.WRITING.get(), RecipesInit.Serializers.WRITING_SERIALIZER.get(), arg, string, arg2, arg3);
    }

    @Override
    public boolean matches(Container container, Level level) {
        return this.ingredient.test(container.getItem(0));
    }

    @Override
    public ItemStack getToastSymbol() {
        return new ItemStack(BlockInit.WRITING_BLOCK.get());
    }

    public static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<com.mystic.atlantis.recipes.WritingRecipe> {

        public Serializer() {
        }

        public WritingRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            String s = GsonHelper.getAsString(json, "group", "");
            Ingredient ingredient;
            if (GsonHelper.isArrayNode(json, "ingredient")) {
                ingredient = Ingredient.fromJson(GsonHelper.getAsJsonArray(json, "ingredient"));
            } else {
                ingredient = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "ingredient"));
            }

            String s1 = GsonHelper.getAsString(json, "result");
            int i = GsonHelper.getAsInt(json, "count");
            ItemStack itemstack = new ItemStack(Registry.ITEM.get(new ResourceLocation(s1)), i);
            return new WritingRecipe(recipeId, s, ingredient, itemstack);
        }

        public WritingRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            String s = buffer.readUtf();
            Ingredient ingredient = Ingredient.fromNetwork(buffer);
            ItemStack itemstack = buffer.readItem();
            return new WritingRecipe(recipeId, s, ingredient, itemstack);
        }

        public void toNetwork(FriendlyByteBuf buffer, WritingRecipe recipe) {
            buffer.writeUtf(recipe.group);
            recipe.ingredient.toNetwork(buffer);
            buffer.writeItem(recipe.result);
        }
    }
}

