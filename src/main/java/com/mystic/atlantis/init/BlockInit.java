package com.mystic.atlantis.init;

import com.mystic.atlantis.blocks.*;
import com.mystic.atlantis.blocks.plants.Algae;
import com.mystic.atlantis.blocks.plants.UnderwaterFlower;
import com.mystic.atlantis.blocks.power.*;
import com.mystic.atlantis.blocks.slabs.AncientWoodSlabs;
import com.mystic.atlantis.util.Reference;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.Objects;
import java.util.function.Function;

public class BlockInit {

    public static void init() {}

    public static final AncientWood ANCIENT_ACACIA_WOOD_MOSS = (AncientWood) register("ancient_acacia_wood_moss", new AncientWood(FabricBlockSettings.of(Material.WOOD)));
    public static final AncientWood ANCIENT_OAK_WOOD_MOSS = (AncientWood) register("ancient_oak_wood_moss", new AncientWood(FabricBlockSettings.of(Material.WOOD)));
    public static final AncientWood ANCIENT_JUNGLE_WOOD_MOSS = (AncientWood) register("ancient_jungle_wood_moss", new AncientWood(FabricBlockSettings.of(Material.WOOD)));
    public static final AncientWood ANCIENT_SPRUCE_WOOD_MOSS = (AncientWood) register("ancient_spruce_wood_moss", new AncientWood(FabricBlockSettings.of(Material.WOOD)));
    public static final AncientWood ANCIENT_BIRCH_WOOD_MOSS = (AncientWood) register("ancient_birch_wood_moss", new AncientWood(FabricBlockSettings.of(Material.WOOD)));
    public static final AncientWood ANCIENT_DARK_OAK_WOOD_MOSS = (AncientWood) register("ancient_dark_oak_wood_moss", new AncientWood(FabricBlockSettings.of(Material.WOOD)));
    public static final AquamarineOre AQUAMARINE_ORE = (AquamarineOre) register("aquamarine_ore", new AquamarineOre(FabricBlockSettings.of(Material.STONE)));
    public static final OceanLantern OCEAN_LANTERN = (OceanLantern) register("ocean_lantern", new OceanLantern(FabricBlockSettings.of(Material.METAL)));
    public static final AtlanteanCore ATLANTEAN_CORE = (AtlanteanCore) register("atlantean_core", new AtlanteanCore(FabricBlockSettings.of(Material.GLASS)));
    public static final BlockAquamarine BLOCK_OF_AQUAMARINE = (BlockAquamarine) register("block_of_aquamarine", new BlockAquamarine(FabricBlockSettings.of(Material.METAL)));
    public static final BlockAquamarine CHISELED_GOLDEN_BLOCK = (BlockAquamarine) register("chiseled_golden_block", new BlockAquamarine(FabricBlockSettings.of(Material.METAL)));
    public static final BlockAquamarine CHISELED_GOLDEN_AQUAMARINE = (BlockAquamarine) register("chiseled_golden_aquamarine", new BlockAquamarine(FabricBlockSettings.of(Material.METAL)));;
    public static final ColoredShellBlocks BLACK_COLORED_SHELL_BLOCK = (ColoredShellBlocks) register("black_colored_shell_block", new ColoredShellBlocks(FabricBlockSettings.of(Material.STONE)));
    public static final ColoredShellBlocks BLUE_COLORED_SHELL_BLOCK = (ColoredShellBlocks) register("blue_colored_shell_block", new ColoredShellBlocks(FabricBlockSettings.of(Material.STONE)));
    public static final ColoredShellBlocks BROWN_COLORED_SHELL_BLOCK = (ColoredShellBlocks) register("brown_colored_shell_block", new ColoredShellBlocks(FabricBlockSettings.of(Material.STONE)));
    public static final ColoredShellBlocks CYAN_COLORED_SHELL_BLOCK = (ColoredShellBlocks) register("cyan_colored_shell_block", new ColoredShellBlocks(FabricBlockSettings.of(Material.STONE)));
    public static final ColoredShellBlocks GRAY_COLORED_SHELL_BLOCK = (ColoredShellBlocks) register("gray_colored_shell_block", new ColoredShellBlocks(FabricBlockSettings.of(Material.STONE)));
    public static final ColoredShellBlocks GREEN_COLORED_SHELL_BLOCK = (ColoredShellBlocks) register("green_colored_shell_block", new ColoredShellBlocks(FabricBlockSettings.of(Material.STONE)));
    public static final ColoredShellBlocks LIGHT_BLUE_COLORED_SHELL_BLOCK = (ColoredShellBlocks) register("light_blue_colored_shell_block", new ColoredShellBlocks(FabricBlockSettings.of(Material.STONE)));
    public static final ColoredShellBlocks LIGHT_GRAY_COLORED_SHELL_BLOCK = (ColoredShellBlocks) register("light_gray_colored_shell_block", new ColoredShellBlocks(FabricBlockSettings.of(Material.STONE)));
    public static final ColoredShellBlocks LIME_COLORED_SHELL_BLOCK = (ColoredShellBlocks) register("lime_colored_shell_block", new ColoredShellBlocks(FabricBlockSettings.of(Material.STONE)));
    public static final ColoredShellBlocks MAGENTA_COLORED_SHELL_BLOCK = (ColoredShellBlocks) register("magenta_colored_shell_block", new ColoredShellBlocks(FabricBlockSettings.of(Material.STONE)));
    public static final ColoredShellBlocks ORANGE_COLORED_SHELL_BLOCK = (ColoredShellBlocks) register("orange_colored_shell_block", new ColoredShellBlocks(FabricBlockSettings.of(Material.STONE)));
    public static final ColoredShellBlocks PINK_COLORED_SHELL_BLOCK = (ColoredShellBlocks) register("pink_colored_shell_block", new ColoredShellBlocks(FabricBlockSettings.of(Material.STONE)));
    public static final ColoredShellBlocks PURPLE_COLORED_SHELL_BLOCK = (ColoredShellBlocks) register("purple_colored_shell_block", new ColoredShellBlocks(FabricBlockSettings.of(Material.STONE)));
    public static final ColoredShellBlocks RED_COLORED_SHELL_BLOCK = (ColoredShellBlocks) register("red_colored_shell_block", new ColoredShellBlocks(FabricBlockSettings.of(Material.STONE)));
    public static final ColoredShellBlocks WHITE_COLORED_SHELL_BLOCK = (ColoredShellBlocks) register("white_colored_shell_block", new ColoredShellBlocks(FabricBlockSettings.of(Material.STONE)));
    public static final ColoredShellBlocks YELLOW_COLORED_SHELL_BLOCK = (ColoredShellBlocks) register("yellow_colored_shell_block", new ColoredShellBlocks(FabricBlockSettings.of(Material.STONE)));
    public static final PearlBlocks BLACK_PEARL_BLOCK = (PearlBlocks) register("black_pearl_block", new PearlBlocks(FabricBlockSettings.of(Material.STONE)));
    public static final PearlBlocks BLUE_PEARL_BLOCK = (PearlBlocks) register("blue_pearl_block", new PearlBlocks(FabricBlockSettings.of(Material.STONE)));
    public static final PearlBlocks BROWN_PEARL_BLOCK = (PearlBlocks) register("brown_pearl_block", new PearlBlocks(FabricBlockSettings.of(Material.STONE)));
    public static final PearlBlocks CYAN_PEARL_BLOCK = (PearlBlocks) register("cyan_pearl_block", new PearlBlocks(FabricBlockSettings.of(Material.STONE)));
    public static final PearlBlocks GRAY_PEARL_BLOCK = (PearlBlocks) register("gray_pearl_block", new PearlBlocks(FabricBlockSettings.of(Material.STONE)));
    public static final PearlBlocks GREEN_PEARL_BLOCK = (PearlBlocks) register("green_pearl_block", new PearlBlocks(FabricBlockSettings.of(Material.STONE)));
    public static final PearlBlocks LIGHT_BLUE_PEARL_BLOCK = (PearlBlocks) register("light_blue_pearl_block", new PearlBlocks(FabricBlockSettings.of(Material.STONE)));
    public static final PearlBlocks LIGHT_GRAY_PEARL_BLOCK = (PearlBlocks) register("light_gray_pearl_block", new PearlBlocks(FabricBlockSettings.of(Material.STONE)));
    public static final PearlBlocks LIME_PEARL_BLOCK = (PearlBlocks) register("lime_pearl_block", new PearlBlocks(FabricBlockSettings.of(Material.STONE)));
    public static final PearlBlocks MAGENTA_PEARL_BLOCK = (PearlBlocks) register("magenta_pearl_block", new PearlBlocks(FabricBlockSettings.of(Material.STONE)));
    public static final PearlBlocks ORANGE_PEARL_BLOCK = (PearlBlocks) register("orange_pearl_block", new PearlBlocks(FabricBlockSettings.of(Material.STONE)));
    public static final PearlBlocks PINK_PEARL_BLOCK = (PearlBlocks) register("pink_pearl_block", new PearlBlocks(FabricBlockSettings.of(Material.STONE)));
    public static final PearlBlocks PURPLE_PEARL_BLOCK = (PearlBlocks) register("purple_pearl_block", new PearlBlocks(FabricBlockSettings.of(Material.STONE)));
    public static final PearlBlocks RED_PEARL_BLOCK = (PearlBlocks) register("red_pearl_block", new PearlBlocks(FabricBlockSettings.of(Material.STONE)));
    public static final PearlBlocks WHITE_PEARL_BLOCK = (PearlBlocks) register("white_pearl_block", new PearlBlocks(FabricBlockSettings.of(Material.STONE)));
    public static final PearlBlocks YELLOW_PEARL_BLOCK = (PearlBlocks) register("yellow_pearl_block", new PearlBlocks(FabricBlockSettings.of(Material.STONE)));
    public static final OysterShellBlock OYSTER_SHELL_BLOCK = (OysterShellBlock) register("oyster_shell_block", new OysterShellBlock(FabricBlockSettings.of(Material.STONE)));
    public static final AtlantisPortalBlock ATLANTIS_PORTAL = (AtlantisPortalBlock) register("atlantis_portal", new AtlantisPortalBlock(FabricBlockSettings.of(Material.PORTAL)));
    public static final UnderwaterFlower UNDERWATER_FLOWER = (UnderwaterFlower) register("underwater_flower", new UnderwaterFlower(FabricBlockSettings.of(Material.PLANT)));
    public static final UnderwaterFlower RED_UNDERWATER_FLOWER = (UnderwaterFlower) register("red_underwater_flower", new UnderwaterFlower(FabricBlockSettings.of(Material.PLANT)));
    public static final UnderwaterFlower YELLOW_UNDERWATER_FLOWER = (UnderwaterFlower) register("yellow_underwater_flower", new UnderwaterFlower(FabricBlockSettings.of(Material.PLANT)));
    public static final Algae ALGAE = (Algae) register("algae", new Algae(FabricBlockSettings.of(Material.PLANT)));
    public static final AtlantisClearPortalBlock ATLANTIS_CLEAR_PORTAL = (AtlantisClearPortalBlock) register("atlantis_clear_portal", new AtlantisClearPortalBlock(FabricBlockSettings.of(Material.PORTAL)));
    public static final AtlanteanPowerStone ATLANTEAN_POWER_STONE = (AtlanteanPowerStone) register("atlantean_power_stone", new AtlanteanPowerStone(FabricBlockSettings.of(Material.STONE)));
    public static final AtlanteanPowerLamp ATLANTEAN_POWER_LAMP = (AtlanteanPowerLamp) register("atlantean_power_lamp", new AtlanteanPowerLamp(FabricBlockSettings.of(Material.REDSTONE_LAMP).strength(0.3F)));
    public static final AtlanteanPowerTorch ATLANTEAN_POWER_TORCH = (AtlanteanPowerTorch) blockOnlyRegistry("atlantean_power_torch", new AtlanteanPowerTorch(FabricBlockSettings.of(Material.DECORATION)));
    public static final WallAtlanteanPowerTorch WALL_ATLANTEAN_POWER_TORCH = (WallAtlanteanPowerTorch) blockOnlyRegistry("atlantean_power_wall_torch", new WallAtlanteanPowerTorch(FabricBlockSettings.of(Material.DECORATION)));
    public static final AtlanteanPowerDust ATLANTEAN_POWER_DUST_WIRE = (AtlanteanPowerDust) blockOnlyRegistry("atlantean_power_dust", new AtlanteanPowerDust(FabricBlockSettings.of(Material.DECORATION)));
    public static final AtlanteanPowerRepeater ATLANTEAN_POWER_REPEATER = (AtlanteanPowerRepeater) register("atlantean_power_repeater", new AtlanteanPowerRepeater(FabricBlockSettings.of(Material.DECORATION)));
    public static final AtlanteanTripwireHook ATLANTEAN_TRIPWIRE_HOOK = (AtlanteanTripwireHook) register("atlantean_tripwire_hook", new AtlanteanTripwireHook(FabricBlockSettings.of(Material.DECORATION).noCollision()));
    public static final AtlanteanTripwire ATLANTEAN_TRIPWIRE = (AtlanteanTripwire) blockOnlyRegistry("atlantean_tripwire", new AtlanteanTripwire(ATLANTEAN_TRIPWIRE_HOOK, AbstractBlock.Settings.of(Material.DECORATION).noCollision()));
    public static final AtlanteanPowerLever ATLANTEAN_POWER_LEVER = (AtlanteanPowerLever) register("atlantean_power_lever", new AtlanteanPowerLever(FabricBlockSettings.of(Material.DECORATION).noCollision().strength(0.5F).sounds(BlockSoundGroup.WOOD)));
    public static final AtlanteanPowerComparator ATLANTEAN_POWER_COMPARATOR = (AtlanteanPowerComparator) register("atlantean_power_comparator", new AtlanteanPowerComparator(FabricBlockSettings.of(Material.DECORATION)));
    public static final CalciteBlock CALCITE_BLOCK = (CalciteBlock) register("calcite_block", new CalciteBlock(FabricBlockSettings.of(Material.STONE)));
    public static final PushBubbleColumn PUSH_BUBBLE_COLUMN = (PushBubbleColumn) blockOnlyRegistry("push_bubble_column", new PushBubbleColumn(FabricBlockSettings.of(Material.BUBBLE_COLUMN)));
    public static final AncientWoodSlabs ANCIENT_ACACIA_WOOD_MOSS_SLAB = (AncientWoodSlabs) register("ancient_acacia_wood_moss_slab", new AncientWoodSlabs(FabricBlockSettings.of(Material.WOOD)));
    public static final AncientWoodSlabs ANCIENT_OAK_WOOD_MOSS_SLAB = (AncientWoodSlabs) register("ancient_oak_wood_moss_slab", new AncientWoodSlabs(FabricBlockSettings.of(Material.WOOD)));
    public static final AncientWoodSlabs ANCIENT_JUNGLE_WOOD_MOSS_SLAB = (AncientWoodSlabs) register("ancient_jungle_wood_moss_slab", new AncientWoodSlabs(FabricBlockSettings.of(Material.WOOD)));
    public static final AncientWoodSlabs ANCIENT_SPRUCE_WOOD_MOSS_SLAB = (AncientWoodSlabs) register("ancient_spruce_wood_moss_slab", new AncientWoodSlabs(FabricBlockSettings.of(Material.WOOD)));
    public static final AncientWoodSlabs ANCIENT_BIRCH_WOOD_MOSS_SLAB = (AncientWoodSlabs) register("ancient_birch_wood_moss_slab", new AncientWoodSlabs(FabricBlockSettings.of(Material.WOOD)));
    public static final AncientWoodSlabs ANCIENT_DARK_OAK_WOOD_MOSS_SLAB = (AncientWoodSlabs) register("ancient_dark_oak_wood_moss_slab", new AncientWoodSlabs(FabricBlockSettings.of(Material.WOOD)));

    private static Block baseRegister(String name, Block block, Function<Block, Item> item) {
        Registry.register(Registry.BLOCK, new Identifier(Reference.MODID, name), block);
        register(name, item.apply(block));
        return block;
    }

    private static Block register(String name, Block block) {
        return baseRegister(name, block, BlockInit::registerBlockItem);
    }

    private static Block blockOnlyRegistry(String name, Block block) {
        return Registry.register(Registry.BLOCK, new Identifier(Reference.MODID, name), block);
    }

    private static BlockItem registerBlockItem(Block block) {
        return new BlockItem(Objects.requireNonNull(block), new Item.Settings());
    }

    public static Item register(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(Reference.MODID, name), item);
    }
}
