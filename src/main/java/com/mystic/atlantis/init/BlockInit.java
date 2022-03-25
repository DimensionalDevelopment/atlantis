package com.mystic.atlantis.init;

import com.mystic.atlantis.blocks.*;
import com.mystic.atlantis.blocks.AtlanteanWoodTrapdoor;
import com.mystic.atlantis.blocks.blockentities.plants.*;
import com.mystic.atlantis.blocks.plants.*;
import com.mystic.atlantis.blocks.power.*;
import com.mystic.atlantis.blocks.signs.AtlanteanSignBlock;
import com.mystic.atlantis.blocks.signs.AtlanteanWallSign;
import com.mystic.atlantis.blocks.slabs.AncientWoodSlabs;
import com.mystic.atlantis.blocks.slabs.AtlanteanWoodSlabs;
import com.mystic.atlantis.configfeature.trees.AtlanteanTreeSaplingGenerator;
import com.mystic.atlantis.itemgroup.AtlantisGroup;
import com.mystic.atlantis.util.Reference;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Function;
import java.util.function.Supplier;

public class BlockInit {
    private static DeferredRegister<Block> BLOCKS = DeferredRegister.create(Block.class, Reference.MODID);

    public static void init(IEventBus bus) {
        BLOCKS.register(bus);
    }

    //Atlantean Wood Type
    public static final WoodType ATLANTEAN = WoodType.register(WoodType.create("atlantean"));

    //Atlantean Wood Variants
    public static final RegistryObject<Block> ATLANTEAN_BUTTON = registerBlock("atlantean_button", () -> new AtlanteanButton(BlockBehaviour.Properties.of(Material.WOOD)));
    public static final RegistryObject<Block> ATLANTEAN_DOOR = registerBlock("atlantean_door", () -> new AtlanteanWoodDoors(BlockBehaviour.Properties.of(Material.WOOD)));
    public static final RegistryObject<Block> ATLANTEAN_FENCE = registerBlock("atlantean_fence", () -> new AtlanteanWoodFence(BlockBehaviour.Properties.of(Material.WOOD)));
    public static final RegistryObject<Block> ATLANTEAN_FENCE_GATE = registerBlock("atlantean_fence_gate", () -> new AtlanteanFenceGate(BlockBehaviour.Properties.of(Material.WOOD)));
    public static final RegistryObject<Block> ATLANTEAN_PLANKS = registerBlock("atlantean_planks", () -> new AtlanteanWood(BlockBehaviour.Properties.of(Material.WOOD)));
    public static final RegistryObject<Block> ATLANTEAN_PRESSURE_PLATE = registerBlock("atlantean_pressure_plate", () -> new AtlanteanPressurePlate(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.of(Material.WOOD)));
    public static final RegistryObject<Block> ATLANTEAN_SIGNS = registerOnlyBlock("atlantean_sign", () -> new AtlanteanSignBlock(BlockBehaviour.Properties.of(Material.WOOD), ATLANTEAN));
    public static final RegistryObject<Block> ATLANTEAN_SLAB = registerBlock("atlantean_slab", () -> new AtlanteanWoodSlabs(BlockBehaviour.Properties.of(Material.WOOD)));
    public static final RegistryObject<Block> ATLANTEAN_STAIRS = registerBlock("atlantean_stairs", () -> new AtlanteanWoodStairs(BlockInit.ATLANTEAN_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.of(Material.WOOD)));
    public static final RegistryObject<Block> ATLANTEAN_TRAPDOOR = registerBlock("atlantean_trapdoor", () -> new AtlanteanWoodTrapdoor(BlockBehaviour.Properties.of(Material.WOOD)));
    public static final RegistryObject<Block> ATLANTEAN_WALL_SIGN = registerOnlyBlock("atlantean_wall_sign", () -> new AtlanteanWallSign(BlockBehaviour.Properties.of(Material.WOOD), ATLANTEAN));

    //Geckolib blocktypes
    public static final RegistryObject<Block> UNDERWATER_SHROOM_BLOCK = registerBlock("underwater_shroom", UnderwaterShroomBlock::new);
    public static final RegistryObject<Block> TUBER_UP_BLOCK = registerBlock("tuber_up", TuberUpBlock::new);
    public static final RegistryObject<Block> BLUE_LILY_BLOCK = registerBlock("blue_lily", BlueLilyBlock::new);
    public static final RegistryObject<Block> BURNT_DEEP_BLOCK = registerBlock("burnt_deep", BurntDeepBlock::new);
    public static final RegistryObject<Block> ENENMOMY_BLOCK = registerBlock("enenmomy", EnenmomyBlock::new);

    //Trapdoors
    public static final RegistryObject<Block> ANCIENT_DARK_OAK_WOOD_MOSS_TRAPDOOR = registerBlock("ancient_dark_oak_wood_moss_trapdoor",()-> new AncientWoodTrapdoor(BlockBehaviour.Properties.of(Material.WOOD)));
    public static final RegistryObject<Block> ANCIENT_BIRCH_WOOD_MOSS_TRAPDOOR = registerBlock("ancient_birch_wood_moss_trapdoor",()-> new AncientWoodTrapdoor(BlockBehaviour.Properties.of(Material.WOOD)));
    public static final RegistryObject<Block> ANCIENT_SPRUCE_WOOD_MOSS_TRAPDOOR = registerBlock("ancient_spruce_wood_moss_trapdoor",()-> new AncientWoodTrapdoor(BlockBehaviour.Properties.of(Material.WOOD)));
    public static final RegistryObject<Block> ANCIENT_JUNGLE_WOOD_MOSS_TRAPDOOR = registerBlock("ancient_jungle_wood_moss_trapdoor",()-> new AncientWoodTrapdoor(BlockBehaviour.Properties.of(Material.WOOD)));
    public static final RegistryObject<Block> ANCIENT_OAK_WOOD_MOSS_TRAPDOOR = registerBlock("ancient_oak_wood_moss_trapdoor",()-> new AncientWoodTrapdoor(BlockBehaviour.Properties.of(Material.WOOD)));
    public static final RegistryObject<Block> ANCIENT_ACACIA_WOOD_MOSS_TRAPDOOR = registerBlock("ancient_acacia_wood_moss_trapdoor",()-> new AncientWoodTrapdoor(BlockBehaviour.Properties.of(Material.WOOD)));

    //Stairs
    public static final RegistryObject<Block> ANCIENT_DARK_OAK_WOOD_MOSS_STAIRS = registerBlock("ancient_dark_oak_wood_moss_stairs",()-> new AncientWoodStairs(Blocks.DARK_OAK_STAIRS.defaultBlockState(), BlockBehaviour.Properties.of(Material.WOOD)));
    public static final RegistryObject<Block> ANCIENT_BIRCH_WOOD_MOSS_STAIRS = registerBlock("ancient_birch_wood_moss_stairs",()-> new AncientWoodStairs(Blocks.BIRCH_STAIRS.defaultBlockState(), BlockBehaviour.Properties.of(Material.WOOD)));
    public static final RegistryObject<Block> ANCIENT_SPRUCE_WOOD_MOSS_STAIRS = registerBlock("ancient_spruce_wood_moss_stairs",()-> new AncientWoodStairs(Blocks.SPRUCE_STAIRS.defaultBlockState(), BlockBehaviour.Properties.of(Material.WOOD)));
    public static final RegistryObject<Block> ANCIENT_JUNGLE_WOOD_MOSS_STAIRS = registerBlock("ancient_jungle_wood_moss_stairs",()-> new AncientWoodStairs(Blocks.JUNGLE_STAIRS.defaultBlockState(), BlockBehaviour.Properties.of(Material.WOOD)));
    public static final RegistryObject<Block> ANCIENT_OAK_WOOD_MOSS_STAIRS = registerBlock("ancient_oak_wood_moss_stairs",()-> new AncientWoodStairs(Blocks.OAK_STAIRS.defaultBlockState(), BlockBehaviour.Properties.of(Material.WOOD)));
    public static final RegistryObject<Block> ANCIENT_ACACIA_WOOD_MOSS_STAIRS = registerBlock("ancient_acacia_wood_moss_stairs",()-> new AncientWoodStairs(Blocks.ACACIA_STAIRS.defaultBlockState(), BlockBehaviour.Properties.of(Material.WOOD)));

    //Fences
    public static final RegistryObject<Block> ANCIENT_DARK_OAK_WOOD_MOSS_FENCE = registerBlock("ancient_dark_oak_wood_moss_fence",()-> new AncientWoodFence(BlockBehaviour.Properties.of(Material.WOOD)));
    public static final RegistryObject<Block> ANCIENT_BIRCH_WOOD_MOSS_FENCE = registerBlock("ancient_birch_wood_moss_fence",()-> new AncientWoodFence(BlockBehaviour.Properties.of(Material.WOOD)));
    public static final RegistryObject<Block> ANCIENT_SPRUCE_WOOD_MOSS_FENCE = registerBlock("ancient_spruce_wood_moss_fence",()-> new AncientWoodFence(BlockBehaviour.Properties.of(Material.WOOD)));
    public static final RegistryObject<Block> ANCIENT_JUNGLE_WOOD_MOSS_FENCE = registerBlock("ancient_jungle_wood_moss_fence",()-> new AncientWoodFence(BlockBehaviour.Properties.of(Material.WOOD)));
    public static final RegistryObject<Block> ANCIENT_OAK_WOOD_MOSS_FENCE = registerBlock("ancient_oak_wood_moss_fence",()-> new AncientWoodFence(BlockBehaviour.Properties.of(Material.WOOD)));
    public static final RegistryObject<Block> ANCIENT_ACACIA_WOOD_MOSS_FENCE = registerBlock("ancient_acacia_wood_moss_fence",()-> new AncientWoodFence(BlockBehaviour.Properties.of(Material.WOOD)));

    //Doors
    public static final RegistryObject<Block> ANCIENT_DARK_OAK_WOOD_MOSS_DOOR = registerBlock("ancient_dark_oak_wood_moss_door",()-> new AncientWoodDoors(BlockBehaviour.Properties.of(Material.WOOD)));
    public static final RegistryObject<Block> ANCIENT_BIRCH_WOOD_MOSS_DOOR = registerBlock("ancient_birch_wood_moss_door",()-> new AncientWoodDoors(BlockBehaviour.Properties.of(Material.WOOD)));
    public static final RegistryObject<Block> ANCIENT_SPRUCE_WOOD_MOSS_DOOR = registerBlock("ancient_spruce_wood_moss_door",()-> new AncientWoodDoors(BlockBehaviour.Properties.of(Material.WOOD)));
    public static final RegistryObject<Block> ANCIENT_JUNGLE_WOOD_MOSS_DOOR = registerBlock("ancient_jungle_wood_moss_door",()-> new AncientWoodDoors(BlockBehaviour.Properties.of(Material.WOOD)));
    public static final RegistryObject<Block> ANCIENT_OAK_WOOD_MOSS_DOOR = registerBlock("ancient_oak_wood_moss_door",()-> new AncientWoodDoors(BlockBehaviour.Properties.of(Material.WOOD)));
    public static final RegistryObject<Block> ANCIENT_ACACIA_WOOD_MOSS_DOOR = registerBlock("ancient_acacia_wood_moss_door",()-> new AncientWoodDoors(BlockBehaviour.Properties.of(Material.WOOD)));

    //Regular blocks
    //TODO: see about public static final RegistryObject<Block> ATLANTEAN_PRISMARINE = registerBlock("atlantean_prismarine", () -> new AtlanteanPrismarine(BlockBehaviour.Properties.of(Material.STONE)));
    public static final RegistryObject<Block> BUBBLE_MAGMA = registerBlock("bubble_magma", () -> new BubbleMagma(BlockBehaviour.Properties.of(Material.STONE)));
    public static final RegistryObject<Block> ATLANTEAN_LEAVES = registerBlock("atlantean_leaf_block",()-> new AtlanteanLeaves(BlockBehaviour.Properties.of(Material.LEAVES)));
    public static final RegistryObject<Block> ATLANTEAN_LOGS = registerBlock("atlantean_log",()-> new AtlanteanLogs(BlockBehaviour.Properties.of(Material.WOOD)));
    public static final RegistryObject<Block> ANCIENT_ACACIA_WOOD_MOSS = registerBlock("ancient_acacia_wood_moss",()-> new AncientWood(BlockBehaviour.Properties.of(Material.WOOD)));
    public static final RegistryObject<Block> ANCIENT_OAK_WOOD_MOSS = registerBlock("ancient_oak_wood_moss",()-> new AncientWood(BlockBehaviour.Properties.of(Material.WOOD)));
    public static final RegistryObject<Block> ANCIENT_JUNGLE_WOOD_MOSS = registerBlock("ancient_jungle_wood_moss",()-> new AncientWood(BlockBehaviour.Properties.of(Material.WOOD)));
    public static final RegistryObject<Block> ANCIENT_SPRUCE_WOOD_MOSS = registerBlock("ancient_spruce_wood_moss",()-> new AncientWood(BlockBehaviour.Properties.of(Material.WOOD)));
    public static final RegistryObject<Block> ANCIENT_BIRCH_WOOD_MOSS = registerBlock("ancient_birch_wood_moss",()-> new AncientWood(BlockBehaviour.Properties.of(Material.WOOD)));
    public static final RegistryObject<Block> ANCIENT_DARK_OAK_WOOD_MOSS = registerBlock("ancient_dark_oak_wood_moss",()-> new AncientWood(BlockBehaviour.Properties.of(Material.WOOD)));
    public static final RegistryObject<Block> AQUAMARINE_ORE = registerBlock("aquamarine_ore",()-> new AquamarineOre(BlockBehaviour.Properties.of(Material.STONE)));
    public static final RegistryObject<Block> OCEAN_LANTERN = registerBlock("ocean_lantern",()-> new OceanLantern(BlockBehaviour.Properties.of(Material.METAL)));
    public static final RegistryObject<Block> ATLANTIAN_SEA_LANTERN = registerBlock("atlantean_sea_lantern",()-> new AtlantianSeaLantern(BlockBehaviour.Properties.of(Material.GLASS)));
    public static final RegistryObject<Block> ATLANTEAN_CORE = registerBlock("atlantean_core",()-> new AtlanteanCore(BlockBehaviour.Properties.of(Material.GLASS)));
    public static final RegistryObject<Block> BLOCK_OF_AQUAMARINE = registerBlock("block_of_aquamarine",()-> new BlockAquamarine(BlockBehaviour.Properties.of(Material.METAL)));
    public static final RegistryObject<Block> CHISELED_GOLDEN_BLOCK = registerBlock("chiseled_golden_block",()-> new BlockAquamarine(BlockBehaviour.Properties.of(Material.METAL)));
    public static final RegistryObject<Block> CHISELED_GOLDEN_AQUAMARINE = registerBlock("chiseled_golden_aquamarine",()-> new BlockAquamarine(BlockBehaviour.Properties.of(Material.METAL)));;
    public static final RegistryObject<Block> BLACK_COLORED_SHELL_BLOCK = registerBlock("black_colored_shell_block",()-> new ColoredShellBlocks(BlockBehaviour.Properties.of(Material.STONE)));
    public static final RegistryObject<Block> BLUE_COLORED_SHELL_BLOCK = registerBlock("blue_colored_shell_block",()-> new ColoredShellBlocks(BlockBehaviour.Properties.of(Material.STONE)));
    public static final RegistryObject<Block> BROWN_COLORED_SHELL_BLOCK = registerBlock("brown_colored_shell_block",()-> new ColoredShellBlocks(BlockBehaviour.Properties.of(Material.STONE)));
    public static final RegistryObject<Block> CYAN_COLORED_SHELL_BLOCK = registerBlock("cyan_colored_shell_block",()-> new ColoredShellBlocks(BlockBehaviour.Properties.of(Material.STONE)));
    public static final RegistryObject<Block> GRAY_COLORED_SHELL_BLOCK = registerBlock("gray_colored_shell_block",()-> new ColoredShellBlocks(BlockBehaviour.Properties.of(Material.STONE)));
    public static final RegistryObject<Block> GREEN_COLORED_SHELL_BLOCK = registerBlock("green_colored_shell_block",()-> new ColoredShellBlocks(BlockBehaviour.Properties.of(Material.STONE)));
    public static final RegistryObject<Block> LIGHT_BLUE_COLORED_SHELL_BLOCK = registerBlock("light_blue_colored_shell_block",()-> new ColoredShellBlocks(BlockBehaviour.Properties.of(Material.STONE)));
    public static final RegistryObject<Block> LIGHT_GRAY_COLORED_SHELL_BLOCK = registerBlock("light_gray_colored_shell_block",()-> new ColoredShellBlocks(BlockBehaviour.Properties.of(Material.STONE)));
    public static final RegistryObject<Block> LIME_COLORED_SHELL_BLOCK = registerBlock("lime_colored_shell_block",()-> new ColoredShellBlocks(BlockBehaviour.Properties.of(Material.STONE)));
    public static final RegistryObject<Block> MAGENTA_COLORED_SHELL_BLOCK = registerBlock("magenta_colored_shell_block",()-> new ColoredShellBlocks(BlockBehaviour.Properties.of(Material.STONE)));
    public static final RegistryObject<Block> ORANGE_COLORED_SHELL_BLOCK = registerBlock("orange_colored_shell_block",()-> new ColoredShellBlocks(BlockBehaviour.Properties.of(Material.STONE)));
    public static final RegistryObject<Block> PINK_COLORED_SHELL_BLOCK = registerBlock("pink_colored_shell_block",()-> new ColoredShellBlocks(BlockBehaviour.Properties.of(Material.STONE)));
    public static final RegistryObject<Block> PURPLE_COLORED_SHELL_BLOCK = registerBlock("purple_colored_shell_block",()-> new ColoredShellBlocks(BlockBehaviour.Properties.of(Material.STONE)));
    public static final RegistryObject<Block> RED_COLORED_SHELL_BLOCK = registerBlock("red_colored_shell_block",()-> new ColoredShellBlocks(BlockBehaviour.Properties.of(Material.STONE)));
    public static final RegistryObject<Block> WHITE_COLORED_SHELL_BLOCK = registerBlock("white_colored_shell_block",()-> new ColoredShellBlocks(BlockBehaviour.Properties.of(Material.STONE)));
    public static final RegistryObject<Block> YELLOW_COLORED_SHELL_BLOCK = registerBlock("yellow_colored_shell_block",()-> new ColoredShellBlocks(BlockBehaviour.Properties.of(Material.STONE)));
    public static final RegistryObject<Block> BLACK_PEARL_BLOCK = registerBlock("black_pearl_block",()-> new PearlBlocks(BlockBehaviour.Properties.of(Material.STONE)));
    public static final RegistryObject<Block> BLUE_PEARL_BLOCK = registerBlock("blue_pearl_block",()-> new PearlBlocks(BlockBehaviour.Properties.of(Material.STONE)));
    public static final RegistryObject<Block> BROWN_PEARL_BLOCK = registerBlock("brown_pearl_block",()-> new PearlBlocks(BlockBehaviour.Properties.of(Material.STONE)));
    public static final RegistryObject<Block> CYAN_PEARL_BLOCK = registerBlock("cyan_pearl_block",()-> new PearlBlocks(BlockBehaviour.Properties.of(Material.STONE)));
    public static final RegistryObject<Block> GRAY_PEARL_BLOCK = registerBlock("gray_pearl_block",()-> new PearlBlocks(BlockBehaviour.Properties.of(Material.STONE)));
    public static final RegistryObject<Block> GREEN_PEARL_BLOCK = registerBlock("green_pearl_block",()-> new PearlBlocks(BlockBehaviour.Properties.of(Material.STONE)));
    public static final RegistryObject<Block> LIGHT_BLUE_PEARL_BLOCK = registerBlock("light_blue_pearl_block",()-> new PearlBlocks(BlockBehaviour.Properties.of(Material.STONE)));
    public static final RegistryObject<Block> LIGHT_GRAY_PEARL_BLOCK = registerBlock("light_gray_pearl_block",()-> new PearlBlocks(BlockBehaviour.Properties.of(Material.STONE)));
    public static final RegistryObject<Block> LIME_PEARL_BLOCK = registerBlock("lime_pearl_block",()-> new PearlBlocks(BlockBehaviour.Properties.of(Material.STONE)));
    public static final RegistryObject<Block> MAGENTA_PEARL_BLOCK = registerBlock("magenta_pearl_block",()-> new PearlBlocks(BlockBehaviour.Properties.of(Material.STONE)));
    public static final RegistryObject<Block> ORANGE_PEARL_BLOCK = registerBlock("orange_pearl_block",()-> new PearlBlocks(BlockBehaviour.Properties.of(Material.STONE)));
    public static final RegistryObject<Block> PINK_PEARL_BLOCK = registerBlock("pink_pearl_block",()-> new PearlBlocks(BlockBehaviour.Properties.of(Material.STONE)));
    public static final RegistryObject<Block> PURPLE_PEARL_BLOCK = registerBlock("purple_pearl_block",()-> new PearlBlocks(BlockBehaviour.Properties.of(Material.STONE)));
    public static final RegistryObject<Block> RED_PEARL_BLOCK = registerBlock("red_pearl_block",()-> new PearlBlocks(BlockBehaviour.Properties.of(Material.STONE)));
    public static final RegistryObject<Block> WHITE_PEARL_BLOCK = registerBlock("white_pearl_block",()-> new PearlBlocks(BlockBehaviour.Properties.of(Material.STONE)));
    public static final RegistryObject<Block> YELLOW_PEARL_BLOCK = registerBlock("yellow_pearl_block",()-> new PearlBlocks(BlockBehaviour.Properties.of(Material.STONE)));
    public static final RegistryObject<Block> OYSTER_SHELL_BLOCK = registerBlock("oyster_shell_block",()-> new OysterShellBlock(BlockBehaviour.Properties.of(Material.STONE)));
    public static final RegistryObject<Block> ATLANTIS_PORTAL = registerBlock("atlantis_portal",()-> new AtlantisPortalBlock(BlockBehaviour.Properties.of(Material.PORTAL)));
    public static final RegistryObject<Block> UNDERWATER_FLOWER = registerBlock("underwater_flower",()-> new UnderwaterFlower(BlockBehaviour.Properties.of(Material.PLANT)));
    public static final RegistryObject<Block> RED_UNDERWATER_FLOWER = registerBlock("red_underwater_flower",()-> new UnderwaterFlower(BlockBehaviour.Properties.of(Material.PLANT)));
    public static final RegistryObject<Block> PURPLE_GLOWING_MUSHROOM = registerBlock("purple_glowing_mushroom",()-> new PurpleGlowingMushroom(BlockBehaviour.Properties.of(Material.PLANT)));
    public static final RegistryObject<Block> YELLOW_GLOWING_MUSHROOM = registerBlock("yellow_glowing_mushroom",()-> new YellowGlowingMushroom(BlockBehaviour.Properties.of(Material.PLANT)));
    public static final RegistryObject<Block> YELLOW_UNDERWATER_FLOWER = registerBlock("yellow_underwater_flower",()-> new UnderwaterFlower(BlockBehaviour.Properties.of(Material.PLANT)));
    public static final RegistryObject<Block> ALGAE = registerBlock("algae",()-> new Algae(BlockBehaviour.Properties.of(Material.PLANT)));
    public static final RegistryObject<Block> ATLANTIS_CLEAR_PORTAL = registerBlock("atlantis_clear_portal",()-> new AtlantisClearPortalBlock(BlockBehaviour.Properties.of(Material.PORTAL)));
    public static final RegistryObject<Block> ATLANTEAN_POWER_STONE = registerBlock("atlantean_power_stone",()-> new AtlanteanPowerStone(BlockBehaviour.Properties.of(Material.STONE)));
    public static final RegistryObject<Block> ATLANTEAN_POWER_LAMP = registerBlock("atlantean_power_lamp",()-> new AtlanteanPowerLamp(BlockBehaviour.Properties.of(Material.BUILDABLE_GLASS).strength(0.3F)));
    public static final RegistryObject<Block> ATLANTEAN_POWER_TORCH = registerOnlyBlock("atlantean_power_torch",()-> new AtlanteanPowerTorch(BlockBehaviour.Properties.of(Material.DECORATION)));
    public static final RegistryObject<Block> WALL_ATLANTEAN_POWER_TORCH = registerOnlyBlock("atlantean_power_wall_torch",()-> new WallAtlanteanPowerTorch(BlockBehaviour.Properties.of(Material.DECORATION)));
    public static final RegistryObject<Block> ATLANTEAN_POWER_DUST_WIRE = registerOnlyBlock("atlantean_power_dust",()-> new AtlanteanPowerDust(BlockBehaviour.Properties.of(Material.DECORATION)));
    public static final RegistryObject<Block> ATLANTEAN_POWER_REPEATER = registerBlock("atlantean_power_repeater",()-> new AtlanteanPowerRepeater(BlockBehaviour.Properties.of(Material.DECORATION)));
    public static final RegistryObject<Block> ATLANTEAN_TRIPWIRE_HOOK = registerBlock("atlantean_tripwire_hook",()-> new AtlanteanTripwireHook(BlockBehaviour.Properties.of(Material.DECORATION).noCollission()));
    public static final RegistryObject<Block> ATLANTEAN_TRIPWIRE = registerOnlyBlock("atlantean_tripwire",()-> new AtlanteanTripwire((AtlanteanTripwireHook)ATLANTEAN_TRIPWIRE_HOOK.get(), BlockBehaviour.Properties.of(Material.DECORATION).noCollission()));
    public static final RegistryObject<Block> ATLANTEAN_POWER_LEVER = registerBlock("atlantean_power_lever",()-> new AtlanteanPowerLever(BlockBehaviour.Properties.of(Material.DECORATION).noCollission().strength(0.5F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> ATLANTEAN_POWER_COMPARATOR = registerBlock("atlantean_power_comparator",()-> new AtlanteanPowerComparator(BlockBehaviour.Properties.of(Material.DECORATION)));
    public static final RegistryObject<Block> CALCITE_BLOCK = registerBlock("calcite_block",()-> new BubbleMagma(BlockBehaviour.Properties.of(Material.STONE)));
    public static final RegistryObject<Block> PUSH_BUBBLE_COLUMN = registerOnlyBlock("push_bubble_column",()-> new PushBubbleColumn(BlockBehaviour.Properties.of(Material.BUBBLE_COLUMN)));
    public static final RegistryObject<Block> ANCIENT_ACACIA_WOOD_MOSS_SLAB = registerBlock("ancient_acacia_wood_moss_slab",()-> new AncientWoodSlabs(BlockBehaviour.Properties.of(Material.WOOD)));
    public static final RegistryObject<Block> ANCIENT_OAK_WOOD_MOSS_SLAB = registerBlock("ancient_oak_wood_moss_slab",()-> new AncientWoodSlabs(BlockBehaviour.Properties.of(Material.WOOD)));
    public static final RegistryObject<Block> ANCIENT_JUNGLE_WOOD_MOSS_SLAB = registerBlock("ancient_jungle_wood_moss_slab",()-> new AncientWoodSlabs(BlockBehaviour.Properties.of(Material.WOOD)));
    public static final RegistryObject<Block> ANCIENT_SPRUCE_WOOD_MOSS_SLAB = registerBlock("ancient_spruce_wood_moss_slab",()-> new AncientWoodSlabs(BlockBehaviour.Properties.of(Material.WOOD)));
    public static final RegistryObject<Block> ANCIENT_BIRCH_WOOD_MOSS_SLAB = registerBlock("ancient_birch_wood_moss_slab",()-> new AncientWoodSlabs(BlockBehaviour.Properties.of(Material.WOOD)));
    public static final RegistryObject<Block> ANCIENT_DARK_OAK_WOOD_MOSS_SLAB = registerBlock("ancient_dark_oak_wood_moss_slab",()-> new AncientWoodSlabs(BlockBehaviour.Properties.of(Material.WOOD)));
    public static final RegistryObject<Block> ALGAE_BLOCK = registerBlock("algae_block",()-> new AlgaeBlock(BlockBehaviour.Properties.of(Material.PLANT)));
    public static final RegistryObject<Block> CHISELED_AQUAMARINE = registerBlock("chiseled_aquamarine",()-> new ChiseledAquamarine(BlockBehaviour.Properties.of(Material.STONE)));

    public static final RegistryObject<Block> ATLANTEAN_SAPLING = registerBlock("atlantean_sapling", ()->
            new AtlanteanSapling(new AtlanteanTreeSaplingGenerator(),
                    BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)));

    public static final RegistryObject<Block> ATLANTEAN_FIRE_MELON_FRUIT_SPIKED = registerOnlyBlock("atlantean_fire_melon_fruit_spiked", () -> new AtlanteanFireMelonFruitSpiked(BlockBehaviour.Properties.of(Material.PLANT)));
    public static final RegistryObject<Block> ATLANTEAN_FIRE_MELON_FRUIT = registerOnlyBlock("atlantean_fire_melon_fruit", () -> new AtlanteanFireMelonFruit(BlockBehaviour.Properties.of(Material.PLANT)));

    public static final RegistryObject<AtlanteanFireMelonBody> ATLANTEAN_FIRE_MELON_STEM = registerOnlyBlock("atlantean_fire_melon_stem", () -> new AtlanteanFireMelonBody(BlockBehaviour.Properties.of(Material.PLANT)));
    public static final RegistryObject<AtlanteanFireMelonHead> ATLANTEAN_FIRE_MELON_TOP = registerOnlyBlock("atlantean_fire_melon_top", () -> new AtlanteanFireMelonHead(BlockBehaviour.Properties.of(Material.PLANT)));

    private static RegistryObject<Block> registerBlock(String name, Supplier<Block> block) {
        return registerBlock(name, block, b -> () -> new BlockItem(b.get(),new Item.Properties().tab(AtlantisGroup.INSTANCE)));
    }
    public static <T extends Block> RegistryObject<T>registerOnlyBlock(String name, Supplier<T> block) {
        return BLOCKS.register(name, block);
    }
    public static RegistryObject<LiquidBlock> registerFluidBlock(String name, Supplier<LiquidBlock> block) {
        var reg = BLOCKS.register(name, block);
        return reg;
    }
    private static RegistryObject<Block> registerBlock(String name, Supplier<Block> block, Function<RegistryObject<Block>, Supplier<? extends BlockItem>> item) {
        var reg = BLOCKS.register(name, block);
        ItemInit.ITEMS.register(name, () -> item.apply(reg).get());
        return reg;
    }
//    private static Block baseregisterBlock(String name, Block block, Function<Block, Item> item) {
//        BLOCKS.registerBlock(name, () -> block);
//        registerBlock(name, item.apply(block));
//        return block;
//    }
//
//    private static Block registerBlock(String name, Block block) {
//        return baseregisterBlock(name, block, BlockInit::registerBlockItem);
//    }
//
//    public static Block blockOnlyRegistry(String name, Block block) {
//        BLOCKS.registerBlock(name, () -> block);
//        return block;
//    }
//
//    private static BlockItem registerBlockItem(Block block) {
//        return new BlockItem(Objects.requireNonNull(block),()-> new Item.Properties());
//    }
//
//    public static Item registerBlock(String name, Item item) {
//        return ItemInit.registerBlock(name, item);
//    }
}
