package com.mystic.atlantis.init;

import com.mystic.atlantis.blocks.*;
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
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
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
    public static final RegistryObject<AquamarineOre> AQUAMARINE_ORE = register("aquamarine_ore", () -> new AquamarineOre(AbstractBlock.Properties.create(Material.ROCK)));
    public static final RegistryObject<OceanLantern> OCEAN_LANTERN = register("ocean_lantern", () -> new OceanLantern(AbstractBlock.Properties.create(Material.IRON)));
    public static final RegistryObject<AtlanteanCore> ATLANTEAN_CORE = register("atlantean_core", () -> new BlockAquamarine(AbstractBlock.Properties.create(Material.GLASS)));
    public static final RegistryObject<BlockAquamarine> BLOCK_OF_AQUAMARINE = register("block_of_aquamarine", () -> new BlockAquamarine(AbstractBlock.Properties.create(Material.IRON)));
    public static final RegistryObject<BlockAquamarine> CHISELED_GOLDEN_BLOCK = register("chiseled_golden_block", () -> new BlockAquamarine(AbstractBlock.Properties.create(Material.IRON)));
    public static final RegistryObject<BlockAquamarine> CHISELED_GOLDEN_AQUAMARINE = register("chiseled_golden_aquamarine", () -> new BlockAquamarine(AbstractBlock.Properties.create(Material.IRON)));;
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
    public static final RegistryObject<PearlBlocks>  BLACK_PEARL_BLOCK = register("black_pearl_block", () -> new PearlBlocks(AbstractBlock.Properties.create(Material.ROCK)));
    public static final RegistryObject<PearlBlocks>  BLUE_PEARL_BLOCK = register("blue_pearl_block", () -> new PearlBlocks(AbstractBlock.Properties.create(Material.ROCK)));
    public static final RegistryObject<PearlBlocks>  BROWN_PEARL_BLOCK = register("brown_pearl_block", () -> new PearlBlocks(AbstractBlock.Properties.create(Material.ROCK)));
    public static final RegistryObject<PearlBlocks>  CYAN_PEARL_BLOCK = register("cyan_pearl_block", () -> new PearlBlocks(AbstractBlock.Properties.create(Material.ROCK)));
    public static final RegistryObject<PearlBlocks>  GRAY_PEARL_BLOCK = register("gray_pearl_block", () -> new PearlBlocks(AbstractBlock.Properties.create(Material.ROCK)));
    public static final RegistryObject<PearlBlocks>  GREEN_PEARL_BLOCK = register("green_pearl_block", () -> new PearlBlocks(AbstractBlock.Properties.create(Material.ROCK)));
    public static final RegistryObject<PearlBlocks>  LIGHT_BLUE_PEARL_BLOCK = register("light_blue_pearl_block", () -> new PearlBlocks(AbstractBlock.Properties.create(Material.ROCK)));
    public static final RegistryObject<PearlBlocks>  LIGHT_GRAY_PEARL_BLOCK = register("light_gray_pearl_block", () -> new PearlBlocks(AbstractBlock.Properties.create(Material.ROCK)));
    public static final RegistryObject<PearlBlocks>  LIME_PEARL_BLOCK = register("lime_pearl_block", () -> new PearlBlocks(AbstractBlock.Properties.create(Material.ROCK)));
    public static final RegistryObject<PearlBlocks>  MAGENTA_PEARL_BLOCK = register("magenta_pearl_block", () -> new PearlBlocks(AbstractBlock.Properties.create(Material.ROCK)));
    public static final RegistryObject<PearlBlocks>  ORANGE_PEARL_BLOCK = register("orange_pearl_block", () -> new PearlBlocks(AbstractBlock.Properties.create(Material.ROCK)));
    public static final RegistryObject<PearlBlocks>  PINK_PEARL_BLOCK = register("pink_pearl_block", () -> new PearlBlocks(AbstractBlock.Properties.create(Material.ROCK)));
    public static final RegistryObject<PearlBlocks>  PURPLE_PEARL_BLOCK = register("purple_pearl_block", () -> new PearlBlocks(AbstractBlock.Properties.create(Material.ROCK)));
    public static final RegistryObject<PearlBlocks>  RED_PEARL_BLOCK = register("red_pearl_block", () -> new PearlBlocks(AbstractBlock.Properties.create(Material.ROCK)));
    public static final RegistryObject<PearlBlocks>  WHITE_PEARL_BLOCK = register("white_pearl_block", () -> new PearlBlocks(AbstractBlock.Properties.create(Material.ROCK)));
    public static final RegistryObject<PearlBlocks>  YELLOW_PEARL_BLOCK = register("yellow_pearl_block", () -> new PearlBlocks(AbstractBlock.Properties.create(Material.ROCK)));
    public static final RegistryObject<OysterShellBlock> OYSTER_SHELL_BLOCK = register("oyster_shell_block", () -> new OysterShellBlock(AbstractBlock.Properties.create(Material.ROCK)));
    public static final RegistryObject<AtlantisPortalBlock>  ATLANTIS_PORTAL = register("atlantis_portal", () -> new AtlantisPortalBlock(AbstractBlock.Properties.create(Material.PORTAL)));
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
