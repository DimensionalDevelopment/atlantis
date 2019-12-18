package com.mystic.dimensionatlantis.init;

import java.util.ArrayList;
import java.util.List;
import com.mystic.dimensionatlantis.blocks.AquamarineOre;
import com.mystic.dimensionatlantis.blocks.BlockAquamarine;
import com.mystic.dimensionatlantis.blocks.BlockBase;
import com.mystic.dimensionatlantis.blocks.ColoredShellBlock;
import com.mystic.dimensionatlantis.blocks.OceanLantern;
import com.mystic.dimensionatlantis.blocks.OysterShellBlock;
import com.mystic.dimensionatlantis.blocks.PearlBlock;
import com.mystic.dimensionatlantis.blocks.portal.AtlanteanCore;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class ModBlocks 
{
	public static final List<Block> BLOCKS = new ArrayList<Block>();
	
	public static final Block ANCIENT_SPRUCE_WOOD_MOSS = new BlockBase("ancient_spruce_wood_moss", Material.WOOD);
	public static final Block ANCIENT_ACACIA_WOOD_MOSS = new BlockBase("ancient_acacia_wood_moss", Material.WOOD);
	public static final Block ANCIENT_BIRCH_WOOD_MOSS = new BlockBase("ancient_birch_wood_moss", Material.WOOD);
	public static final Block ANCIENT_DARK_OAK_WOOD_MOSS = new BlockBase("ancient_dark_oak_wood_moss", Material.WOOD);
	public static final Block ANCIENT_JUNGLE_WOOD_MOSS = new BlockBase("ancient_jungle_wood_moss", Material.WOOD);
	public static final Block ANCIENT_OAK_WOOD_MOSS = new BlockBase("ancient_oak_wood_moss", Material.WOOD);
	public static final Block AQUAMARINE_ORE = new AquamarineOre("aquamarine_ore", Material.ROCK);
	public static final Block ATLANTEAN_CORE = new AtlanteanCore("atlantean_core", Material.GLASS);
	public static final Block OCEAN_LANTERN = new OceanLantern("ocean_lantern", Material.IRON);
	public static final Block BLOCK_OF_AQUAMARINE = new BlockAquamarine("block_of_aquamarine", Material.IRON);
	public static final Block CHISELED_GOLDEN_BLOCK = new BlockAquamarine("chiseled_golden_block", Material.IRON);
	public static final Block CHISELED_GOLDEN_AQUAMARINE = new BlockAquamarine("chiseled_golden_aquamarine", Material.IRON);
	public static final Block BLACK_COLORED_SHELL_BLOCK = new ColoredShellBlock("black_colored_shell_block", Material.ROCK);
	public static final Block BLUE_COLORED_SHELL_BLOCK = new ColoredShellBlock("blue_colored_shell_block", Material.ROCK);
	public static final Block BROWN_COLORED_SHELL_BLOCK = new ColoredShellBlock("brown_colored_shell_block", Material.ROCK);
	public static final Block CYAN_COLORED_SHELL_BLOCK = new ColoredShellBlock("cyan_colored_shell_block", Material.ROCK);
	public static final Block GRAY_COLORED_SHELL_BLOCK = new ColoredShellBlock("gray_colored_shell_block", Material.ROCK);
	public static final Block GREEN_COLORED_SHELL_BLOCK = new ColoredShellBlock("green_colored_shell_block", Material.ROCK);
	public static final Block LIGHT_BLUE_COLORED_SHELL_BLOCK = new ColoredShellBlock("light_blue_colored_shell_block", Material.ROCK);
	public static final Block LIGHT_GRAY_COLORED_SHELL_BLOCK = new ColoredShellBlock("light_gray_colored_shell_block", Material.ROCK);
	public static final Block LIME_COLORED_SHELL_BLOCK = new ColoredShellBlock("Lime_colored_shell_block", Material.ROCK);
	public static final Block MAGENTA_COLORED_SHELL_BLOCK = new ColoredShellBlock("magenta_colored_shell_block", Material.ROCK);
	public static final Block ORANGE_COLORED_SHELL_BLOCK = new ColoredShellBlock("orange_colored_shell_block", Material.ROCK);
	public static final Block PINK_COLORED_SHELL_BLOCK = new ColoredShellBlock("pink_colored_shell_block", Material.ROCK);
	public static final Block PURPLE_COLORED_SHELL_BLOCK = new ColoredShellBlock("purple_colored_shell_block", Material.ROCK);
	public static final Block RED_COLORED_SHELL_BLOCK = new ColoredShellBlock("red_colored_shell_block", Material.ROCK);
	public static final Block WHITE_COLORED_SHELL_BLOCK = new ColoredShellBlock("white_colored_shell_block", Material.ROCK);
	public static final Block YELLOW_COLORED_SHELL_BLOCK = new ColoredShellBlock("yellow_colored_shell_block", Material.ROCK);
	public static final Block OYSTER_SHELL_BLOCK = new OysterShellBlock("oyster_shell_block", Material.ROCK);
	public static final Block WHITE_PEARL_BLOCK = new PearlBlock("white_pearl_block", Material.ROCK);
}
