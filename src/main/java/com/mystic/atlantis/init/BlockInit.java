package com.mystic.atlantis.init;

import com.mystic.atlantis.blocks.*;
import com.mystic.atlantis.blocks.blockentities.plants.*;
import com.mystic.atlantis.blocks.plants.*;
import com.mystic.atlantis.blocks.power.*;
import com.mystic.atlantis.blocks.slabs.AncientWoodSlabs;
import com.mystic.atlantis.configfeature.trees.AtlanteanTreeSaplingGenerator;
import com.mystic.atlantis.util.Reference;
import net.fabricmc.fabric.api.object.builder.v1.block.AbstractBlock.Settings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;

import java.util.Objects;
import java.util.function.Function;

public class BlockInit {
    private static DeferredRegister<Block> BLOCKS = DeferredRegister.create(Block.class, Reference.MODID);
    
    public static void init(IEventBus bus) {
        BLOCKS.register(bus);
    }

    //Geckolib blocktypes
    public static final UnderwaterShroomBlock UNDERWATER_SHROOM_BLOCK = new UnderwaterShroomBlock();
    public static final TuberUpBlock TUBER_UP_BLOCK = (TuberUpBlock) register("tuber_up", new TuberUpBlock());
    public static final BlueLilyBlock BLUE_LILY_BLOCK = (BlueLilyBlock) register("blue_lily", new BlueLilyBlock());
    public static final BurntDeepBlock BURNT_DEEP_BLOCK = (BurntDeepBlock) register("burnt_deep", new BurntDeepBlock());
    public static final EnenmomyBlock ENENMOMY_BLOCK = (EnenmomyBlock) register("enenmomy", new EnenmomyBlock());

    //Trapdoors
    public static final AncientWoodTrapdoor ANCIENT_DARK_OAK_WOOD_MOSS_TRAPDOOR = (AncientWoodTrapdoor) register("ancient_dark_oak_wood_moss_trapdoor", new AncientWoodTrapdoor(AbstractBlock.Settings.of(Material.WOOD)));
    public static final AncientWoodTrapdoor ANCIENT_BIRCH_WOOD_MOSS_TRAPDOOR = (AncientWoodTrapdoor) register("ancient_birch_wood_moss_trapdoor", new AncientWoodTrapdoor(AbstractBlock.Settings.of(Material.WOOD)));
    public static final AncientWoodTrapdoor ANCIENT_SPRUCE_WOOD_MOSS_TRAPDOOR = (AncientWoodTrapdoor) register("ancient_spruce_wood_moss_trapdoor", new AncientWoodTrapdoor(AbstractBlock.Settings.of(Material.WOOD)));
    public static final AncientWoodTrapdoor ANCIENT_JUNGLE_WOOD_MOSS_TRAPDOOR = (AncientWoodTrapdoor) register("ancient_jungle_wood_moss_trapdoor", new AncientWoodTrapdoor(AbstractBlock.Settings.of(Material.WOOD)));
    public static final AncientWoodTrapdoor ANCIENT_OAK_WOOD_MOSS_TRAPDOOR = (AncientWoodTrapdoor) register("ancient_oak_wood_moss_trapdoor", new AncientWoodTrapdoor(AbstractBlock.Settings.of(Material.WOOD)));
    public static final AncientWoodTrapdoor ANCIENT_ACACIA_WOOD_MOSS_TRAPDOOR = (AncientWoodTrapdoor) register("ancient_acacia_wood_moss_trapdoor", new AncientWoodTrapdoor(AbstractBlock.Settings.of(Material.WOOD)));

    //Stairs
    public static final AncientWoodStairs ANCIENT_DARK_OAK_WOOD_MOSS_STAIRS = (AncientWoodStairs) register("ancient_dark_oak_wood_moss_stairs", new AncientWoodStairs(Blocks.DARK_OAK_STAIRS.getDefaultState(), AbstractBlock.Settings.of(Material.WOOD)));
    public static final AncientWoodStairs ANCIENT_BIRCH_WOOD_MOSS_STAIRS = (AncientWoodStairs) register("ancient_birch_wood_moss_stairs", new AncientWoodStairs(Blocks.BIRCH_STAIRS.getDefaultState(),AbstractBlock.Settings.of(Material.WOOD)));
    public static final AncientWoodStairs ANCIENT_SPRUCE_WOOD_MOSS_STAIRS = (AncientWoodStairs) register("ancient_spruce_wood_moss_stairs", new AncientWoodStairs(Blocks.SPRUCE_STAIRS.getDefaultState(),AbstractBlock.Settings.of(Material.WOOD)));
    public static final AncientWoodStairs ANCIENT_JUNGLE_WOOD_MOSS_STAIRS = (AncientWoodStairs) register("ancient_jungle_wood_moss_stairs", new AncientWoodStairs(Blocks.JUNGLE_STAIRS.getDefaultState(),AbstractBlock.Settings.of(Material.WOOD)));
    public static final AncientWoodStairs ANCIENT_OAK_WOOD_MOSS_STAIRS = (AncientWoodStairs) register("ancient_oak_wood_moss_stairs", new AncientWoodStairs(Blocks.OAK_STAIRS.getDefaultState(),AbstractBlock.Settings.of(Material.WOOD)));
    public static final AncientWoodStairs ANCIENT_ACACIA_WOOD_MOSS_STAIRS = (AncientWoodStairs) register("ancient_acacia_wood_moss_stairs", new AncientWoodStairs(Blocks.ACACIA_STAIRS.getDefaultState(),AbstractBlock.Settings.of(Material.WOOD)));

    //Fences
    public static final AncientWoodFence ANCIENT_DARK_OAK_WOOD_MOSS_FENCE = (AncientWoodFence) register("ancient_dark_oak_wood_moss_fence", new AncientWoodFence(AbstractBlock.Settings.of(Material.WOOD)));
    public static final AncientWoodFence ANCIENT_BIRCH_WOOD_MOSS_FENCE = (AncientWoodFence) register("ancient_birch_wood_moss_fence", new AncientWoodFence(AbstractBlock.Settings.of(Material.WOOD)));
    public static final AncientWoodFence ANCIENT_SPRUCE_WOOD_MOSS_FENCE = (AncientWoodFence) register("ancient_spruce_wood_moss_fence", new AncientWoodFence(AbstractBlock.Settings.of(Material.WOOD)));
    public static final AncientWoodFence ANCIENT_JUNGLE_WOOD_MOSS_FENCE = (AncientWoodFence) register("ancient_jungle_wood_moss_fence", new AncientWoodFence(AbstractBlock.Settings.of(Material.WOOD)));
    public static final AncientWoodFence ANCIENT_OAK_WOOD_MOSS_FENCE = (AncientWoodFence) register("ancient_oak_wood_moss_fence", new AncientWoodFence(AbstractBlock.Settings.of(Material.WOOD)));
    public static final AncientWoodFence ANCIENT_ACACIA_WOOD_MOSS_FENCE = (AncientWoodFence) register("ancient_acacia_wood_moss_fence", new AncientWoodFence(AbstractBlock.Settings.of(Material.WOOD)));

    //Doors
    public static final AncientWoodDoors ANCIENT_DARK_OAK_WOOD_MOSS_DOOR = (AncientWoodDoors) register("ancient_dark_oak_wood_moss_door", new AncientWoodDoors(AbstractBlock.Settings.of(Material.WOOD)));
    public static final AncientWoodDoors ANCIENT_BIRCH_WOOD_MOSS_DOOR = (AncientWoodDoors) register("ancient_birch_wood_moss_door", new AncientWoodDoors(AbstractBlock.Settings.of(Material.WOOD)));
    public static final AncientWoodDoors ANCIENT_SPRUCE_WOOD_MOSS_DOOR = (AncientWoodDoors) register("ancient_spruce_wood_moss_door", new AncientWoodDoors(AbstractBlock.Settings.of(Material.WOOD)));
    public static final AncientWoodDoors ANCIENT_JUNGLE_WOOD_MOSS_DOOR = (AncientWoodDoors) register("ancient_jungle_wood_moss_door", new AncientWoodDoors(AbstractBlock.Settings.of(Material.WOOD)));
    public static final AncientWoodDoors ANCIENT_OAK_WOOD_MOSS_DOOR = (AncientWoodDoors) register("ancient_oak_wood_moss_door", new AncientWoodDoors(AbstractBlock.Settings.of(Material.WOOD)));
    public static final AncientWoodDoors ANCIENT_ACACIA_WOOD_MOSS_DOOR = (AncientWoodDoors) register("ancient_acacia_wood_moss_door", new AncientWoodDoors(AbstractBlock.Settings.of(Material.WOOD)));

    //Regular blocks
    public static final AtlanteanLeaves ATLANTEAN_LEAVES = (AtlanteanLeaves) register("atlantean_leaf_block", new AtlanteanLeaves(AbstractBlock.Settings.of(Material.LEAVES)));
    public static final AtlanteanLogs ATLANTEAN_LOGS = (AtlanteanLogs) register("atlantean_log", new AtlanteanLogs(AbstractBlock.Settings.of(Material.WOOD)));
    public static final AncientWood ANCIENT_ACACIA_WOOD_MOSS = (AncientWood) register("ancient_acacia_wood_moss", new AncientWood(AbstractBlock.Settings.of(Material.WOOD)));
    public static final AncientWood ANCIENT_OAK_WOOD_MOSS = (AncientWood) register("ancient_oak_wood_moss", new AncientWood(AbstractBlock.Settings.of(Material.WOOD)));
    public static final AncientWood ANCIENT_JUNGLE_WOOD_MOSS = (AncientWood) register("ancient_jungle_wood_moss", new AncientWood(AbstractBlock.Settings.of(Material.WOOD)));
    public static final AncientWood ANCIENT_SPRUCE_WOOD_MOSS = (AncientWood) register("ancient_spruce_wood_moss", new AncientWood(AbstractBlock.Settings.of(Material.WOOD)));
    public static final AncientWood ANCIENT_BIRCH_WOOD_MOSS = (AncientWood) register("ancient_birch_wood_moss", new AncientWood(AbstractBlock.Settings.of(Material.WOOD)));
    public static final AncientWood ANCIENT_DARK_OAK_WOOD_MOSS = (AncientWood) register("ancient_dark_oak_wood_moss", new AncientWood(AbstractBlock.Settings.of(Material.WOOD)));
    public static final AquamarineOre AQUAMARINE_ORE = (AquamarineOre) register("aquamarine_ore", new AquamarineOre(AbstractBlock.Settings.of(Material.STONE)));
    public static final OceanLantern OCEAN_LANTERN = (OceanLantern) register("ocean_lantern", new OceanLantern(AbstractBlock.Settings.of(Material.METAL)));
    public static final AtlantianSeaLantern ATLANTIAN_SEA_LANTERN = (AtlantianSeaLantern) register("atlantean_sea_lantern", new AtlantianSeaLantern(AbstractBlock.Settings.of(Material.GLASS)));
    public static final AtlanteanCore ATLANTEAN_CORE = (AtlanteanCore) register("atlantean_core", new AtlanteanCore(AbstractBlock.Settings.of(Material.GLASS)));
    public static final BlockAquamarine BLOCK_OF_AQUAMARINE = (BlockAquamarine) register("block_of_aquamarine", new BlockAquamarine(AbstractBlock.Settings.of(Material.METAL)));
    public static final BlockAquamarine CHISELED_GOLDEN_BLOCK = (BlockAquamarine) register("chiseled_golden_block", new BlockAquamarine(AbstractBlock.Settings.of(Material.METAL)));
    public static final BlockAquamarine CHISELED_GOLDEN_AQUAMARINE = (BlockAquamarine) register("chiseled_golden_aquamarine", new BlockAquamarine(AbstractBlock.Settings.of(Material.METAL)));;
    public static final ColoredShellBlocks BLACK_COLORED_SHELL_BLOCK = (ColoredShellBlocks) register("black_colored_shell_block", new ColoredShellBlocks(AbstractBlock.Settings.of(Material.STONE)));
    public static final ColoredShellBlocks BLUE_COLORED_SHELL_BLOCK = (ColoredShellBlocks) register("blue_colored_shell_block", new ColoredShellBlocks(AbstractBlock.Settings.of(Material.STONE)));
    public static final ColoredShellBlocks BROWN_COLORED_SHELL_BLOCK = (ColoredShellBlocks) register("brown_colored_shell_block", new ColoredShellBlocks(AbstractBlock.Settings.of(Material.STONE)));
    public static final ColoredShellBlocks CYAN_COLORED_SHELL_BLOCK = (ColoredShellBlocks) register("cyan_colored_shell_block", new ColoredShellBlocks(AbstractBlock.Settings.of(Material.STONE)));
    public static final ColoredShellBlocks GRAY_COLORED_SHELL_BLOCK = (ColoredShellBlocks) register("gray_colored_shell_block", new ColoredShellBlocks(AbstractBlock.Settings.of(Material.STONE)));
    public static final ColoredShellBlocks GREEN_COLORED_SHELL_BLOCK = (ColoredShellBlocks) register("green_colored_shell_block", new ColoredShellBlocks(AbstractBlock.Settings.of(Material.STONE)));
    public static final ColoredShellBlocks LIGHT_BLUE_COLORED_SHELL_BLOCK = (ColoredShellBlocks) register("light_blue_colored_shell_block", new ColoredShellBlocks(AbstractBlock.Settings.of(Material.STONE)));
    public static final ColoredShellBlocks LIGHT_GRAY_COLORED_SHELL_BLOCK = (ColoredShellBlocks) register("light_gray_colored_shell_block", new ColoredShellBlocks(AbstractBlock.Settings.of(Material.STONE)));
    public static final ColoredShellBlocks LIME_COLORED_SHELL_BLOCK = (ColoredShellBlocks) register("lime_colored_shell_block", new ColoredShellBlocks(AbstractBlock.Settings.of(Material.STONE)));
    public static final ColoredShellBlocks MAGENTA_COLORED_SHELL_BLOCK = (ColoredShellBlocks) register("magenta_colored_shell_block", new ColoredShellBlocks(AbstractBlock.Settings.of(Material.STONE)));
    public static final ColoredShellBlocks ORANGE_COLORED_SHELL_BLOCK = (ColoredShellBlocks) register("orange_colored_shell_block", new ColoredShellBlocks(AbstractBlock.Settings.of(Material.STONE)));
    public static final ColoredShellBlocks PINK_COLORED_SHELL_BLOCK = (ColoredShellBlocks) register("pink_colored_shell_block", new ColoredShellBlocks(AbstractBlock.Settings.of(Material.STONE)));
    public static final ColoredShellBlocks PURPLE_COLORED_SHELL_BLOCK = (ColoredShellBlocks) register("purple_colored_shell_block", new ColoredShellBlocks(AbstractBlock.Settings.of(Material.STONE)));
    public static final ColoredShellBlocks RED_COLORED_SHELL_BLOCK = (ColoredShellBlocks) register("red_colored_shell_block", new ColoredShellBlocks(AbstractBlock.Settings.of(Material.STONE)));
    public static final ColoredShellBlocks WHITE_COLORED_SHELL_BLOCK = (ColoredShellBlocks) register("white_colored_shell_block", new ColoredShellBlocks(AbstractBlock.Settings.of(Material.STONE)));
    public static final ColoredShellBlocks YELLOW_COLORED_SHELL_BLOCK = (ColoredShellBlocks) register("yellow_colored_shell_block", new ColoredShellBlocks(AbstractBlock.Settings.of(Material.STONE)));
    public static final PearlBlocks BLACK_PEARL_BLOCK = (PearlBlocks) register("black_pearl_block", new PearlBlocks(AbstractBlock.Settings.of(Material.STONE)));
    public static final PearlBlocks BLUE_PEARL_BLOCK = (PearlBlocks) register("blue_pearl_block", new PearlBlocks(AbstractBlock.Settings.of(Material.STONE)));
    public static final PearlBlocks BROWN_PEARL_BLOCK = (PearlBlocks) register("brown_pearl_block", new PearlBlocks(AbstractBlock.Settings.of(Material.STONE)));
    public static final PearlBlocks CYAN_PEARL_BLOCK = (PearlBlocks) register("cyan_pearl_block", new PearlBlocks(AbstractBlock.Settings.of(Material.STONE)));
    public static final PearlBlocks GRAY_PEARL_BLOCK = (PearlBlocks) register("gray_pearl_block", new PearlBlocks(AbstractBlock.Settings.of(Material.STONE)));
    public static final PearlBlocks GREEN_PEARL_BLOCK = (PearlBlocks) register("green_pearl_block", new PearlBlocks(AbstractBlock.Settings.of(Material.STONE)));
    public static final PearlBlocks LIGHT_BLUE_PEARL_BLOCK = (PearlBlocks) register("light_blue_pearl_block", new PearlBlocks(AbstractBlock.Settings.of(Material.STONE)));
    public static final PearlBlocks LIGHT_GRAY_PEARL_BLOCK = (PearlBlocks) register("light_gray_pearl_block", new PearlBlocks(AbstractBlock.Settings.of(Material.STONE)));
    public static final PearlBlocks LIME_PEARL_BLOCK = (PearlBlocks) register("lime_pearl_block", new PearlBlocks(AbstractBlock.Settings.of(Material.STONE)));
    public static final PearlBlocks MAGENTA_PEARL_BLOCK = (PearlBlocks) register("magenta_pearl_block", new PearlBlocks(AbstractBlock.Settings.of(Material.STONE)));
    public static final PearlBlocks ORANGE_PEARL_BLOCK = (PearlBlocks) register("orange_pearl_block", new PearlBlocks(AbstractBlock.Settings.of(Material.STONE)));
    public static final PearlBlocks PINK_PEARL_BLOCK = (PearlBlocks) register("pink_pearl_block", new PearlBlocks(AbstractBlock.Settings.of(Material.STONE)));
    public static final PearlBlocks PURPLE_PEARL_BLOCK = (PearlBlocks) register("purple_pearl_block", new PearlBlocks(AbstractBlock.Settings.of(Material.STONE)));
    public static final PearlBlocks RED_PEARL_BLOCK = (PearlBlocks) register("red_pearl_block", new PearlBlocks(AbstractBlock.Settings.of(Material.STONE)));
    public static final PearlBlocks WHITE_PEARL_BLOCK = (PearlBlocks) register("white_pearl_block", new PearlBlocks(AbstractBlock.Settings.of(Material.STONE)));
    public static final PearlBlocks YELLOW_PEARL_BLOCK = (PearlBlocks) register("yellow_pearl_block", new PearlBlocks(AbstractBlock.Settings.of(Material.STONE)));
    public static final OysterShellBlock OYSTER_SHELL_BLOCK = (OysterShellBlock) register("oyster_shell_block", new OysterShellBlock(AbstractBlock.Settings.of(Material.STONE)));
    public static final AtlantisPortalBlock ATLANTIS_PORTAL = (AtlantisPortalBlock) register("atlantis_portal", new AtlantisPortalBlock(AbstractBlock.Settings.of(Material.PORTAL)));
    public static final UnderwaterFlower UNDERWATER_FLOWER = (UnderwaterFlower) register("underwater_flower", new UnderwaterFlower(AbstractBlock.Settings.of(Material.PLANT)));
    public static final UnderwaterFlower RED_UNDERWATER_FLOWER = (UnderwaterFlower) register("red_underwater_flower", new UnderwaterFlower(AbstractBlock.Settings.of(Material.PLANT)));
    public static final PurpleGlowingMushroom PURPLE_GLOWING_MUSHROOM = (PurpleGlowingMushroom) register("purple_glowing_mushroom", new PurpleGlowingMushroom(AbstractBlock.Settings.of(Material.PLANT)));
    public static final YellowGlowingMushroom YELLOW_GLOWING_MUSHROOM = (YellowGlowingMushroom) register("yellow_glowing_mushroom", new YellowGlowingMushroom(AbstractBlock.Settings.of(Material.PLANT)));
    public static final UnderwaterFlower YELLOW_UNDERWATER_FLOWER = (UnderwaterFlower) register("yellow_underwater_flower", new UnderwaterFlower(AbstractBlock.Settings.of(Material.PLANT)));
    public static final Algae ALGAE = (Algae) register("algae", new Algae(AbstractBlock.Settings.of(Material.PLANT)));
    public static final AtlantisClearPortalBlock ATLANTIS_CLEAR_PORTAL = (AtlantisClearPortalBlock) register("atlantis_clear_portal", new AtlantisClearPortalBlock(AbstractBlock.Settings.of(Material.PORTAL)));
    public static final AtlanteanPowerStone ATLANTEAN_POWER_STONE = (AtlanteanPowerStone) register("atlantean_power_stone", new AtlanteanPowerStone(AbstractBlock.Settings.of(Material.STONE)));
    public static final AtlanteanPowerLamp ATLANTEAN_POWER_LAMP = (AtlanteanPowerLamp) register("atlantean_power_lamp", new AtlanteanPowerLamp(AbstractBlock.Settings.of(Material.REDSTONE_LAMP).strength(0.3F)));
    public static final AtlanteanPowerTorch ATLANTEAN_POWER_TORCH = (AtlanteanPowerTorch) blockOnlyRegistry("atlantean_power_torch", new AtlanteanPowerTorch(AbstractBlock.Settings.of(Material.DECORATION)));
    public static final WallAtlanteanPowerTorch WALL_ATLANTEAN_POWER_TORCH = (WallAtlanteanPowerTorch) blockOnlyRegistry("atlantean_power_wall_torch", new WallAtlanteanPowerTorch(AbstractBlock.Settings.of(Material.DECORATION)));
    public static final AtlanteanPowerDust ATLANTEAN_POWER_DUST_WIRE = (AtlanteanPowerDust) blockOnlyRegistry("atlantean_power_dust", new AtlanteanPowerDust(AbstractBlock.Settings.of(Material.DECORATION)));
    public static final AtlanteanPowerRepeater ATLANTEAN_POWER_REPEATER = (AtlanteanPowerRepeater) register("atlantean_power_repeater", new AtlanteanPowerRepeater(AbstractBlock.Settings.of(Material.DECORATION)));
    public static final AtlanteanTripwireHook ATLANTEAN_TRIPWIRE_HOOK = (AtlanteanTripwireHook) register("atlantean_tripwire_hook", new AtlanteanTripwireHook(AbstractBlock.Settings.of(Material.DECORATION).noCollision()));
    public static final AtlanteanTripwire ATLANTEAN_TRIPWIRE = (AtlanteanTripwire) blockOnlyRegistry("atlantean_tripwire", new AtlanteanTripwire(ATLANTEAN_TRIPWIRE_HOOK, AbstractBlock.Settings.of(Material.DECORATION).noCollision()));
    public static final AtlanteanPowerLever ATLANTEAN_POWER_LEVER = (AtlanteanPowerLever) register("atlantean_power_lever", new AtlanteanPowerLever(AbstractBlock.Settings.of(Material.DECORATION).noCollision().strength(0.5F).sounds(BlockSoundGroup.WOOD)));
    public static final AtlanteanPowerComparator ATLANTEAN_POWER_COMPARATOR = (AtlanteanPowerComparator) register("atlantean_power_comparator", new AtlanteanPowerComparator(AbstractBlock.Settings.of(Material.DECORATION)));
    public static final CalciteBlock CALCITE_BLOCK = (CalciteBlock) register("calcite_block", new CalciteBlock(AbstractBlock.Settings.of(Material.STONE)));
    public static final PushBubbleColumn PUSH_BUBBLE_COLUMN = (PushBubbleColumn) blockOnlyRegistry("push_bubble_column", new PushBubbleColumn(AbstractBlock.Settings.of(Material.BUBBLE_COLUMN)));
    public static final AncientWoodSlabs ANCIENT_ACACIA_WOOD_MOSS_SLAB = (AncientWoodSlabs) register("ancient_acacia_wood_moss_slab", new AncientWoodSlabs(AbstractBlock.Settings.of(Material.WOOD)));
    public static final AncientWoodSlabs ANCIENT_OAK_WOOD_MOSS_SLAB = (AncientWoodSlabs) register("ancient_oak_wood_moss_slab", new AncientWoodSlabs(AbstractBlock.Settings.of(Material.WOOD)));
    public static final AncientWoodSlabs ANCIENT_JUNGLE_WOOD_MOSS_SLAB = (AncientWoodSlabs) register("ancient_jungle_wood_moss_slab", new AncientWoodSlabs(AbstractBlock.Settings.of(Material.WOOD)));
    public static final AncientWoodSlabs ANCIENT_SPRUCE_WOOD_MOSS_SLAB = (AncientWoodSlabs) register("ancient_spruce_wood_moss_slab", new AncientWoodSlabs(AbstractBlock.Settings.of(Material.WOOD)));
    public static final AncientWoodSlabs ANCIENT_BIRCH_WOOD_MOSS_SLAB = (AncientWoodSlabs) register("ancient_birch_wood_moss_slab", new AncientWoodSlabs(AbstractBlock.Settings.of(Material.WOOD)));
    public static final AncientWoodSlabs ANCIENT_DARK_OAK_WOOD_MOSS_SLAB = (AncientWoodSlabs) register("ancient_dark_oak_wood_moss_slab", new AncientWoodSlabs(AbstractBlock.Settings.of(Material.WOOD)));
    public static final AlgaeBlock ALGAE_BLOCK = (AlgaeBlock) register("algae_block", new AlgaeBlock(AbstractBlock.Settings.of(Material.PLANT)));
    public static final ChiseledAquamarine CHISELED_AQUAMARINE = (ChiseledAquamarine) register("chiseled_aquamarine", new ChiseledAquamarine(AbstractBlock.Settings.of(Material.STONE)));

    public static final Block ATLANTEAN_SAPLING = register("atlantean_sapling",
            new AtlanteanSapling(new AtlanteanTreeSaplingGenerator(),
                    AbstractBlock.Settings.copy(Blocks.OAK_SAPLING)));

    private static Block baseRegister(String name, Block block, Function<Block, Item> item) {
        BLOCKS.register(name, () -> block);
        register(name, item.apply(block));
        return block;
    }

    private static Block register(String name, Block block) {
        return baseRegister(name, block, BlockInit::registerBlockItem);
    }

    private static Block blockOnlyRegistry(String name, Block block) {
        BLOCKS.register(name, () -> block);
        return block;
    }

    private static BlockItem registerBlockItem(Block block) {
        return new BlockItem(Objects.requireNonNull(block), new Item.Settings());
    }

    public static Item register(String name, Item item) {
        return ItemInit.register(name, item);
    }
}
