package com.mystic.atlantis.init;

import com.mystic.atlantis.blocks.AncientWood;
import com.mystic.atlantis.blocks.AtlantisPortalBlock;
import com.mystic.atlantis.blocks.ColoredShellBlocks;
import com.mystic.atlantis.blocks.plants.Algae;
import com.mystic.atlantis.blocks.plants.UnderwaterFlower;
import com.mystic.atlantis.dimension.DimensionAtlantis;
import com.mystic.atlantis.util.Reference;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

public class BlockInit {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Reference.MODID);

    public static final RegistryObject<AncientWood> ANCIENT_ACACIA_WOOD_MOSS = register("ancient_acacia_wood_moss", () -> new AncientWood(AbstractBlock.Properties.create(Material.WOOD)));
    public static final RegistryObject<AncientWood> ANCIENT_OAK_WOOD_MOSS = register("ancient_oak_wood_moss", () -> new AncientWood(AbstractBlock.Properties.create(Material.WOOD)));
    public static final RegistryObject<AncientWood> ANCIENT_JUNGLE_WOOD_MOSS = register("ancient_jungle_wood_moss", () -> new AncientWood(AbstractBlock.Properties.create(Material.WOOD)));
    public static final RegistryObject<AncientWood> ANCIENT_SPRUCE_WOOD_MOSS = register("ancient_spruce_wood_moss", () -> new AncientWood(AbstractBlock.Properties.create(Material.WOOD)));
    public static final RegistryObject<AncientWood> ANCIENT_BIRCH_WOOD_MOSS = register("ancient_birch_wood_moss", () -> new AncientWood(AbstractBlock.Properties.create(Material.WOOD)));
    public static final RegistryObject<AncientWood> ANCIENT_DARK_OAK_WOOD_MOSS = register("ancient_dark_oak_wood_moss", () -> new AncientWood(AbstractBlock.Properties.create(Material.WOOD)));
    public static final RegistryObject<ColoredShellBlocks>  BLACK_COLORED_SHELL_BLOCK = register("black_colored_shell_block", () -> new ColoredShellBlocks(AbstractBlock.Properties.create(Material.ROCK)));
    public static final RegistryObject<ColoredShellBlocks>  BLUE_COLORED_SHELL_BLOCK = register("blue_colored_shell_block", () -> new ColoredShellBlocks(AbstractBlock.Properties.create(Material.ROCK)));
    public static final RegistryObject<ColoredShellBlocks>  BROWN_COLORED_SHELL_BLOCK = register("brown_colored_shell_block", () -> new ColoredShellBlocks(AbstractBlock.Properties.create(Material.ROCK)));
    public static final RegistryObject<ColoredShellBlocks>  CYAN_COLORED_SHELL_BLOCK = register("cyan_colored_shell_block", () -> new ColoredShellBlocks(AbstractBlock.Properties.create(Material.ROCK)));
    public static final RegistryObject<ColoredShellBlocks>  GRAY_COLORED_SHELL_BLOCK = register("gray_colored_shell_block", () -> new ColoredShellBlocks(AbstractBlock.Properties.create(Material.ROCK)));
    public static final RegistryObject<ColoredShellBlocks>  GREEN_COLORED_SHELL_BLOCK = register("green_colored_shell_block", () -> new ColoredShellBlocks(AbstractBlock.Properties.create(Material.ROCK)));
    public static final RegistryObject<ColoredShellBlocks>  LIGHT_BLUE_COLORED_SHELL_BLOCK = register("light_blue_colored_shell_block", () -> new ColoredShellBlocks(AbstractBlock.Properties.create(Material.ROCK)));
    public static final RegistryObject<ColoredShellBlocks>  LIGHT_GRAY_COLORED_SHELL_BLOCK = register("light_gray_colored_shell_block", () -> new ColoredShellBlocks(AbstractBlock.Properties.create(Material.ROCK)));
    public static final RegistryObject<ColoredShellBlocks>  LIME_COLORED_SHELL_BLOCK = register("lime_colored_shell_block", () -> new ColoredShellBlocks(AbstractBlock.Properties.create(Material.ROCK)));
    public static final RegistryObject<ColoredShellBlocks>  MAGENTA_COLORED_SHELL_BLOCK = register("magenta_colored_shell_block", () -> new ColoredShellBlocks(AbstractBlock.Properties.create(Material.ROCK)));
    public static final RegistryObject<ColoredShellBlocks>  ORANGE_COLORED_SHELL_BLOCK = register("orange_colored_shell_block", () -> new ColoredShellBlocks(AbstractBlock.Properties.create(Material.ROCK)));
    public static final RegistryObject<ColoredShellBlocks>  PINK_COLORED_SHELL_BLOCK = register("pink_colored_shell_block", () -> new ColoredShellBlocks(AbstractBlock.Properties.create(Material.ROCK)));
    public static final RegistryObject<ColoredShellBlocks>  PURPLE_COLORED_SHELL_BLOCK = register("purple_colored_shell_block", () -> new ColoredShellBlocks(AbstractBlock.Properties.create(Material.ROCK)));
    public static final RegistryObject<ColoredShellBlocks>  RED_COLORED_SHELL_BLOCK = register("red_colored_shell_block", () -> new ColoredShellBlocks(AbstractBlock.Properties.create(Material.ROCK)));
    public static final RegistryObject<ColoredShellBlocks>  WHITE_COLORED_SHELL_BLOCK = register("white_colored_shell_block", () -> new ColoredShellBlocks(AbstractBlock.Properties.create(Material.ROCK)));
    public static final RegistryObject<ColoredShellBlocks>  YELLOW_COLORED_SHELL_BLOCK = register("yellow_colored_shell_block", () -> new ColoredShellBlocks(AbstractBlock.Properties.create(Material.ROCK)));
    public static final RegistryObject<AtlantisPortalBlock>  ATLANTIS_PORTAL = register("atlantis_portal", () -> new AtlantisPortalBlock(AbstractBlock.Properties.create(Material.PORTAL), DimensionAtlantis.ATLANTIS_WORLD_KEY));
    public static final RegistryObject<UnderwaterFlower> UNDERWATER_FLOWER = register("underwater_flower", () -> new UnderwaterFlower(AbstractBlock.Properties.create(Material.PLANTS)));
    public static final RegistryObject<Algae> ALGAE = register("algae", () -> new Algae(AbstractBlock.Properties.create(Material.PLANTS)));

    private static <T extends Block> RegistryObject<T> baseRegister(String name, Supplier<? extends T> block, Function<RegistryObject<T>, Supplier<? extends Item>> item) {
        RegistryObject<T> register = BLOCKS.register(name, block);
        ItemInit.ITEMS.register(name, item.apply(register));
        return register;
    }

    private static <B extends Block> RegistryObject<B> register(String name, Supplier<? extends Block> block) {
        return (RegistryObject<B>)baseRegister(name, block, BlockInit::registerBlockItem);
    }

    private static <T extends Block> Supplier<BlockItem> registerBlockItem(final RegistryObject<T> block) {
        return () -> new BlockItem(Objects.requireNonNull(block.get()), new Item.Properties().group(ItemInit.CREATIVE_TAB_ATLANTIS));
    }
}
