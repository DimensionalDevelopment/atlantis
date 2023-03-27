package com.mystic.atlantis.init;

import com.mystic.atlantis.recipes.WritingRecipe;
import com.mystic.atlantis.util.Reference;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RecipesInit {
    public static void init(IEventBus bus) {
        Serializers.RECIPE_SERIALIZERS.register(bus);
        Types.RECIPE_WRITING.register(bus);
    }

    public static class Types {
        public static DeferredRegister<RecipeType<?>> RECIPE_WRITING = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, "atlantis");

        public static RecipeType<WritingRecipe> WRITING = RecipeType.simple(new ResourceLocation(Reference.MODID, "writing"));
    }

    public static class Serializers {
        public static DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, "atlantis");

        public static RegistryObject<RecipeSerializer<?>> WRITING_SERIALIZER = RECIPE_SERIALIZERS.register("writing", WritingRecipe.Serializer::new);
    }
}
