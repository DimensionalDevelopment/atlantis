package com.mystic.atlantis.recipes;

import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mystic.atlantis.init.BlockInit;
import com.mystic.atlantis.init.RecipesInit;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SingleItemRecipe;
import net.minecraft.world.level.Level;

public class WritingRecipe extends SingleItemRecipe {
    public WritingRecipe(String string, Ingredient arg2, ItemStack arg3) {
        super(RecipesInit.Types.WRITING, RecipesInit.Serializers.WRITING_SERIALIZER.get(), string, arg2, arg3);
    }

    @Override
    public boolean matches(Container container, Level level) {
        return this.ingredient.test(container.getItem(0));
    }

    @Override
    public ItemStack getToastSymbol() {
        return new ItemStack(BlockInit.WRITING_BLOCK.get());
    }

    public static class Serializer implements RecipeSerializer<WritingRecipe> {
        public WritingRecipe fromNetwork(FriendlyByteBuf buffer) {
            String s = buffer.readUtf();
            Ingredient ingredient = Ingredient.fromNetwork(buffer);
            ItemStack itemstack = buffer.readItem();
            return new WritingRecipe(s, ingredient, itemstack);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, WritingRecipe recipe) {
            buffer.writeUtf(recipe.group);
            recipe.ingredient.toNetwork(buffer);
            buffer.writeItem(recipe.result);
        }

        final SingleItemRecipe.Factory<WritingRecipe> factory;
        private final Codec<WritingRecipe> codec;

        public Serializer(SingleItemRecipe.Factory<WritingRecipe> pFactory) {
            this.factory = pFactory;
            this.codec = RecordCodecBuilder.create(
                    p_311738_ -> p_311738_.group(
                                    ExtraCodecs.strictOptionalField(Codec.STRING, "group", "").forGetter(p_300947_ -> p_300947_.group),
                                    Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(p_301068_ -> p_301068_.ingredient),
                                    ItemStack.RESULT_CODEC.forGetter(p_302316_ -> p_302316_.result)
                            )
                            .apply(p_311738_, pFactory::create)
            );
        }

        @Override
        public Codec<WritingRecipe> codec() {
            return this.codec;
        }
    }
}

