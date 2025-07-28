package com.mystic.atlantis.recipes;

import com.mystic.atlantis.init.BlockInit;
import com.mystic.atlantis.init.RecipesInit;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.SingleItemRecipe;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class WritingRecipe extends SingleItemRecipe {
    public WritingRecipe(String group , Ingredient ingredient, ItemStack result) {
        super(RecipesInit.Types.WRITING, RecipesInit.Serializers.WRITING_SERIALIZER.get(), group, ingredient, result);
    }

    @Override
    public boolean matches(SingleRecipeInput input, @NotNull Level level) {
        return this.ingredient.test(input.item());
    }

    @Override
    public ItemStack getToastSymbol() {
        return new ItemStack(BlockInit.WRITING_TABLE.get());
    }


    public static class WritingSerializer extends SingleItemRecipe.Serializer<WritingRecipe> {
        public WritingSerializer() {
            super(WritingRecipe::new);
        }
    }
}