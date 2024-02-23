package com.mystic.atlantis.init;

import com.mystic.atlantis.recipes.WritingRecipe;
import com.mystic.atlantis.util.Reference;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class RecipesInit {

    public static class Types {
        public static final DeferredRegister<RecipeType<?>> RECIPE_WRITING = DeferredRegister.create(Registries.RECIPE_TYPE, "atlantis");

        public static final RecipeType<WritingRecipe> WRITING = RecipeType.simple(new ResourceLocation(Reference.MODID, "writing"));
    }

    public static class Serializers {
        public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, "atlantis");

        public static final Supplier<RecipeSerializer<WritingRecipe>> WRITING_SERIALIZER =
                RECIPE_SERIALIZERS.register("writing", () ->
                        new WritingRecipe.Serializer((pGroup, pIngredient, pResult) -> new WritingRecipe("writing", pIngredient, pResult)));
    }
    
    public static void init(IEventBus bus) {
        Serializers.RECIPE_SERIALIZERS.register(bus);
        Types.RECIPE_WRITING.register(bus);
    }
}
