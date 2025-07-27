package com.mystic.atlantis.init;

import com.mystic.atlantis.blocks.BlockType;
import com.mystic.atlantis.blocks.ancient_cuprum.*;
import com.mystic.atlantis.blocks.base.*;
import com.mystic.atlantis.blocks.blockentities.plants.*;
import com.mystic.atlantis.blocks.plants.*;
import com.mystic.atlantis.blocks.pots.*;
import com.mystic.atlantis.blocks.aquatic_power.*;
import com.mystic.atlantis.blocks.shells.ColoredShellBlock;
import com.mystic.atlantis.blocks.shells.CrackedShellBlock;
import com.mystic.atlantis.blocks.shells.NautilusShellBlock;
import com.mystic.atlantis.blocks.shells.OysterShellBlock;
import com.mystic.atlantis.util.Reference;
import net.minecraft.Util;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class BlockInit {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Reference.MODID);

    public static final Map<LinguisticGlyph, Map<DyeColor, DeferredBlock<Block>>> DYED_LINGUISTICS = new HashMap<>();
    public static final Map<LinguisticGlyph, DeferredBlock<Block>> NON_LINGUISTICS = new HashMap<>();
    public static final Map<DyeColor, DeferredBlock<Block>> COLORED_SHELL_BLOCKS = new HashMap<>();
    public static final Map<DyeColor, DeferredBlock<Block>> CRACKED_SHELL_BLOCKS = new HashMap<>();
    public static final Map<DyeColor, DeferredBlock<Block>> CRACKED_MOSSY_SHELL_BLOCKS = new HashMap<>();
    public static final Map<DyeColor, DeferredBlock<Block>> MOSSY_SHELL_BLOCKS = new HashMap<>();
    public static final Map<DyeColor, BlockType> SEA_GLASS_PATTERNS = new HashMap<>();
    public static final Map<DyeColor, BlockType> SEA_GLASS_LIST = new HashMap<>();
    public static final DeferredBlock<Block> WATERFALL_BLOCK = registerBlock("waterfall_block", () -> new WaterfallBlock(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> WAVE_BLOCK = registerBlock("wave_block", () -> new WaveBlock(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> CRYSTAL_TRANSFERENCE_BLOCK = registerBlock("crystal_transference_block", () -> new CrystalTransferenceBlock(BlockBehaviour.Properties.of()));

    //Portal
    public static final DeferredBlock<Block> ATLANTEAN_PORTAL_FRAME = registerBlock("atlantean_portal_frame", AtlanteanPortalFrame::new);
    public static final DeferredBlock<AtlanteanPortalBlock> ATLANTEAN_PORTAL = registerOnlyBlock("atlantean_portal", () -> new AtlanteanPortalBlock(BlockBehaviour.Properties.of()));

    //Fluid Blocks
    public static final DeferredBlock<LiquidBlock> JETSTREAM_WATER = BLOCKS.register("jetstream_water",
            () -> new LiquidBlock(FluidInit.JETSTREAM_WATER.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER)));
    public static final DeferredBlock<LiquidBlock> SALTY_SEAWATER = BLOCKS.register("salty_seawater",
            () -> new LiquidBlock(FluidInit.SALTY_SEAWATER.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER)));

    //Wood Types
    public static BlockSetType NYMPH_BLOCK_TYPE = BlockSetType.register(new BlockSetType("nymph"));
    public static BlockSetType PALM_BLOCK_TYPE = BlockSetType.register(new BlockSetType("palm"));
    public static final WoodType NYMPH_WOOD_TYPE = WoodType.register(new WoodType("nymph", NYMPH_BLOCK_TYPE));
    public static final WoodType PALM_WOOD_TYPE = WoodType.register(new WoodType("palm", PALM_BLOCK_TYPE));

    //Pots
    public static final DeferredBlock<Block> TUBEN_POT = registerBlock("tuben_pot", () -> new TubenPotBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DECORATED_POT).sound(SoundType.DECORATED_POT)));
    public static final DeferredBlock<Block> BELEN_POT = registerBlock("belen_pot", () -> new BelenPotBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DECORATED_POT).sound(SoundType.DECORATED_POT)));
    public static final DeferredBlock<Block> TOPER_POT = registerBlock("toper_pot", () -> new ToperPotBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DECORATED_POT).sound(SoundType.DECORATED_POT)));
    public static final DeferredBlock<Block> SNOWN_POT = registerBlock("snown_pot", () -> new SnownPotBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DECORATED_POT).sound(SoundType.DECORATED_POT)));
    public static final DeferredBlock<Block> HORPEN_POT = registerBlock("horpen_pot", () -> new HorpenPotBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DECORATED_POT).sound(SoundType.DECORATED_POT)));
    public static final DeferredBlock<Block> CELEN_POT = registerBlock("celen_pot", () -> new CelenPotBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DECORATED_POT).sound(SoundType.DECORATED_POT)));
    public static final DeferredBlock<Block> OBEMO_POT = registerBlock("obemo_pot", () -> new ObemoPotBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DECORATED_POT).sound(SoundType.DECORATED_POT)));

    //Coconut stuff
    public static final DeferredBlock<CoconutSlice> COCONUT_SLICE = registerOnlyBlock("coconut_slice", () -> new CoconutSlice(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Coconut> COCONUT = registerBlock("coconut", () -> new EquipableCoconut(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).strength(1.0F).sound(SoundType.WOOD)));
    public static final DeferredBlock<EquipableCarvedCoconut> CARVED_COCONUT = registerBlock("carved_coconut", () -> new EquipableCarvedCoconut(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).strength(1.0F).sound(SoundType.WOOD)));
    public static final DeferredBlock<CarvedCoconut> SATIRE_LANTERN = registerBlock("satire_lantern", () -> new CarvedCoconut(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).strength(1.0F).sound(SoundType.WOOD).lightLevel((p_50870_) -> 15)));

    //Palm Variants
    public static final DeferredBlock<PalmLogBlock> PALM_LOG = registerBlock("palm_log", () -> new PalmLogBlock(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<StrippedPalmLog> STRIPPED_PALM_LOG = registerBlock("stripped_palm_log", () -> new StrippedPalmLog(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<StandingSignBlock> PALM_SIGN = registerOnlyBlock("palm_sign", () -> new StandingSignBlock(PALM_WOOD_TYPE, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_SIGN)));
    public static final DeferredBlock<WallSignBlock> PALM_WALL_SIGN = registerOnlyBlock("palm_wall_sign", () -> new WallSignBlock(PALM_WOOD_TYPE, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_SIGN)));

    public static final BlockType PALM_PLANKS = registerBlockType("palm", PalmWoodBlock::new,
            BlockBehaviour.Properties.of().sound(SoundType.WOOD)
                    .requiresCorrectToolForDrops()
                    .strength(2.0F), true, PALM_BLOCK_TYPE, PALM_WOOD_TYPE,
            30, true
    );

    //Nymph Variants
    public static final DeferredBlock<NymphLogBlock> NYMPH_LOG = registerBlock("nymph_log", () -> new NymphLogBlock(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<StrippedNymphLog> STRIPPED_NYMPH_LOG = registerBlock("stripped_nymph_log", () -> new StrippedNymphLog(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<StandingSignBlock> NYMPH_SIGN = registerOnlyBlock("nymph_sign", () -> new StandingSignBlock(NYMPH_WOOD_TYPE, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SIGN)));
    public static final DeferredBlock<WallSignBlock> NYMPH_WALL_SIGN = registerOnlyBlock("nymph_wall_sign", () -> new WallSignBlock(NYMPH_WOOD_TYPE, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_SIGN)));

    public static final BlockType NYMPH_PLANKS = registerBlockType("nymph", NymphWoodBlock::new,
            BlockBehaviour.Properties.of().sound(SoundType.WOOD)
                    .requiresCorrectToolForDrops()
                    .strength(2.0F), true, NYMPH_BLOCK_TYPE, NYMPH_WOOD_TYPE,
            30, true
    );

    //Geckolib blocktypes
    public static final DeferredBlock<SeashroomBlock> SEASHROOM = registerBlock("seashroom", SeashroomBlock::new);
    public static final DeferredBlock<TuberUpBlock> TUBER_UP = registerBlock("tuber_up", TuberUpBlock::new);
    public static final DeferredBlock<BlueLilyBlock> BLUE_LILY = registerBlock("blue_lily", BlueLilyBlock::new);
    public static final DeferredBlock<BurntDeepBlock> BURNT_DEEP = registerBlock("burnt_deep", BurntDeepBlock::new);
    public static final DeferredBlock<AnemoneBlock> ANEMONE = registerBlock("anemone", AnemoneBlock::new);

    //Ancient Woods
    public static final BlockType ANCIENT_DARK_OAK = registerBlockType("ancient_dark_oak", AncientWoodBlock::new,
            BlockBehaviour.Properties.of(), true, BlockSetType.DARK_OAK, WoodType.DARK_OAK,
            30, true
    );

    public static final BlockType ANCIENT_OAK = registerBlockType("ancient_oak", AncientWoodBlock::new,
            BlockBehaviour.Properties.of(), true, BlockSetType.OAK, WoodType.OAK,
            30, true
    );

    public static final BlockType ANCIENT_JUNGLE = registerBlockType("ancient_jungle", AncientWoodBlock::new,
            BlockBehaviour.Properties.of(), true, BlockSetType.JUNGLE, WoodType.JUNGLE,
            30, true
    );

    public static final BlockType ANCIENT_ACACIA = registerBlockType("ancient_acacia", AncientWoodBlock::new,
            BlockBehaviour.Properties.of(), true, BlockSetType.ACACIA, WoodType.ACACIA,
            30, true
    );

    public static final BlockType ANCIENT_BIRCH = registerBlockType("ancient_birch", AncientWoodBlock::new,
            BlockBehaviour.Properties.of(), true, BlockSetType.BIRCH, WoodType.BIRCH,
            30, true
    );

    public static final BlockType ANCIENT_SPRUCE = registerBlockType("ancient_spruce", AncientWoodBlock::new,
            BlockBehaviour.Properties.of(), true, BlockSetType.SPRUCE, WoodType.SPRUCE,
            30, true
    );

    public static final BlockType ANCIENT_BAMBOO = registerBlockType("ancient_bamboo", AncientWoodBlock::new,
            BlockBehaviour.Properties.of(), true, BlockSetType.BAMBOO, WoodType.BAMBOO,
            30, true
    );

    public static final BlockType ANCIENT_MANGROVE = registerBlockType("ancient_mangrove", AncientWoodBlock::new,
            BlockBehaviour.Properties.of(), true, BlockSetType.MANGROVE, WoodType.MANGROVE,
            30, true
    );

    public static final BlockType ANCIENT_CHERRY = registerBlockType("ancient_cherry", AncientWoodBlock::new,
            BlockBehaviour.Properties.of(), true, BlockSetType.CHERRY, WoodType.CHERRY,
            30, true
    );

    public static final BlockType ANCIENT_CRIMSON = registerBlockType("ancient_crimson", AncientWoodBlock::new,
            BlockBehaviour.Properties.of(), true, BlockSetType.CRIMSON, WoodType.CRIMSON,
            30, true
    );

    public static final BlockType ANCIENT_WARPED = registerBlockType("ancient_warped", AncientWoodBlock::new,
            BlockBehaviour.Properties.of(), true, BlockSetType.WARPED, WoodType.WARPED,
            30, true
    );

    //Shells
    public static final DeferredBlock<OysterShellBlock> OYSTER_SHELL_BLOCK = registerBlock("oyster_shell_block", () -> new OysterShellBlock(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<NautilusShellBlock> NAUTILUS_SHELL_BLOCK = registerBlock("nautilus_shell_block", () -> new NautilusShellBlock(BlockBehaviour.Properties.of().strength(2.0F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.BONE_BLOCK)));
    public static final DeferredBlock<OysterShellBlock> CRACKED_OYSTER_SHELL = registerBlock("cracked_oyster_shell", () -> new OysterShellBlock(BlockBehaviour.Properties.of().strength(1.5F, 5.0F)));
    public static final DeferredBlock<NautilusShellBlock> CRACKED_NAUTILUS_SHELL = registerBlock("cracked_nautilus_shell", () -> new NautilusShellBlock(BlockBehaviour.Properties.of().strength(1.5F, 5.0F).requiresCorrectToolForDrops().sound(SoundType.BONE_BLOCK)));
    public static final DeferredBlock<OysterShellBlock> CRACKED_MOSSY_OYSTER_SHELL = registerBlock("cracked_mossy_oyster_shell", () -> new OysterShellBlock(BlockBehaviour.Properties.of().strength(1.5F, 5.0F)));
    public static final DeferredBlock<NautilusShellBlock> CRACKED_MOSSY_NAUTILUS_SHELL = registerBlock("cracked_mossy_nautilus_shell", () -> new NautilusShellBlock(BlockBehaviour.Properties.of().strength(1.5F, 5.0F).requiresCorrectToolForDrops().sound(SoundType.BONE_BLOCK)));
    public static final DeferredBlock<OysterShellBlock> MOSSY_OYSTER_SHELL = registerBlock("mossy_oyster_shell", () -> new OysterShellBlock(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<NautilusShellBlock> MOSSY_NAUTILUS_SHELL = registerBlock("mossy_nautilus_shell", () -> new NautilusShellBlock(BlockBehaviour.Properties.of().strength(2.0F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.BONE_BLOCK)));

    //Regular blocks
    public static final DeferredBlock<SodiumBombBlock> SODIUM_BOMB = registerBlock("sodium_bomb", () -> new SodiumBombBlock(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<SeasaltChunkBlock> SEASALT_CHUNK = registerBlock("seasalt_chunk", () -> new SeasaltChunkBlock(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> SUNKEN_GRAVEL = registerBlock("sunken_gravel", () -> new SunkenGravelBlock(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<CrackedGlowstoneBlock> CRACKED_GLOWSTONE = registerBlock("cracked_glowstone", () -> new CrackedGlowstoneBlock(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<DeadGlowstoneBlock> DEAD_GLOWSTONE = registerBlock("dead_glowstone", () -> new DeadGlowstoneBlock(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<AlgaeDetritusStoneBlock> ALGAE_DETRITUS_STONE = registerBlock("algae_detritus_stone", () -> new AlgaeDetritusStoneBlock(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<DetritusSandstoneBlock> DETRITUS_SANDSTONE = registerBlock("detritus_sandstone", () -> new DetritusSandstoneBlock(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<LuminescentPrismarine> LUMINESCENT_PRISMARINE = registerBlock("luminescent_prismarine", () -> new LuminescentPrismarine(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<BubbleMagmaBlock> BUBBLE_MAGMA = registerBlock("bubble_magma", () -> new BubbleMagmaBlock(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<PalmLeavesBlock> PALM_LEAVES = registerBlock("palm_leaves", () -> new PalmLeavesBlock(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<NymphLeavesBlock> NYMPH_LEAVES = registerBlock("nymph_leaves", () -> new NymphLeavesBlock(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> AQUAMARINE_ORE = registerBlock("aquamarine_ore", () -> new DropExperienceBlock(ConstantInt.of(1), BlockBehaviour.Properties.of()
            .sound(SoundType.STONE)
            .requiresCorrectToolForDrops()
            .strength(3.0F, 15.0F)
            .lightLevel((state) -> 2)));

    public static final DeferredBlock<Block> DEEPSLATE_AQUAMARINE_ORE = registerBlock("deepslate_aquamarine_ore", () -> new DropExperienceBlock(ConstantInt.of(1), BlockBehaviour.Properties.of()
            .sound(SoundType.STONE)
            .requiresCorrectToolForDrops()
            .strength(5.0F, 15.0F)
            .lightLevel((state) -> 4)));

    public static final DeferredBlock<SeabedBlock> SEABED = registerBlock("seabed", () -> new SeabedBlock(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<OceanLanternBlock> OCEAN_LANTERN = registerBlock("ocean_lantern", () -> new OceanLanternBlock(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<SurgeLanternBlock> SURGE_LANTERN = registerBlock("surge_lantern", () -> new SurgeLanternBlock(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<AtlanteanCoreBlock> ATLANTEAN_CORE = registerBlock("atlantean_core", () -> new AtlanteanCoreBlock(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<AquamarineBlock> BLOCK_OF_AQUAMARINE = registerBlock("aquamarine_block", () -> new AquamarineBlock(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<AquamarineBlock> CHISELED_GOLDEN_BLOCK = registerBlock("chiseled_golden_block", () -> new AquamarineBlock(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<AquamarineBlock> CHISELED_GOLDEN_AQUAMARINE = registerBlock("chiseled_golden_aquamarine", () -> new AquamarineBlock(BlockBehaviour.Properties.of()));
    ;
    public static final DeferredBlock<PearlBlock> BLACK_PEARL_BLOCK = registerBlock("black_pearl_block", () -> new PearlBlock(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<PearlBlock> BLUE_PEARL_BLOCK = registerBlock("blue_pearl_block", () -> new PearlBlock(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<PearlBlock> BROWN_PEARL_BLOCK = registerBlock("brown_pearl_block", () -> new PearlBlock(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<PearlBlock> CYAN_PEARL_BLOCK = registerBlock("cyan_pearl_block", () -> new PearlBlock(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<PearlBlock> GRAY_PEARL_BLOCK = registerBlock("gray_pearl_block", () -> new PearlBlock(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<PearlBlock> GREEN_PEARL_BLOCK = registerBlock("green_pearl_block", () -> new PearlBlock(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<PearlBlock> LIGHT_BLUE_PEARL_BLOCK = registerBlock("light_blue_pearl_block", () -> new PearlBlock(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<PearlBlock> LIGHT_GRAY_PEARL_BLOCK = registerBlock("light_gray_pearl_block", () -> new PearlBlock(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<PearlBlock> LIME_PEARL_BLOCK = registerBlock("lime_pearl_block", () -> new PearlBlock(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<PearlBlock> MAGENTA_PEARL_BLOCK = registerBlock("magenta_pearl_block", () -> new PearlBlock(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<PearlBlock> ORANGE_PEARL_BLOCK = registerBlock("orange_pearl_block", () -> new PearlBlock(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<PearlBlock> PINK_PEARL_BLOCK = registerBlock("pink_pearl_block", () -> new PearlBlock(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<PearlBlock> PURPLE_PEARL_BLOCK = registerBlock("purple_pearl_block", () -> new PearlBlock(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<PearlBlock> RED_PEARL_BLOCK = registerBlock("red_pearl_block", () -> new PearlBlock(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<PearlBlock> WHITE_PEARL_BLOCK = registerBlock("white_pearl_block", () -> new PearlBlock(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<PearlBlock> YELLOW_PEARL_BLOCK = registerBlock("yellow_pearl_block", () -> new PearlBlock(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Seabloom> SEABLOOM = registerBlock("seabloom", () -> new Seabloom(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Seabloom> RED_SEABLOOM = registerBlock("red_seabloom", () -> new Seabloom(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<PurpleSeashroom> PURPLE_SEASHROOM = registerBlock("purple_seashroom", () -> new PurpleSeashroom(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<YellowSeashroom> YELLOW_SEASHROOM = registerBlock("yellow_seashroom", () -> new YellowSeashroom(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Seabloom> YELLOW_SEABLOOM = registerBlock("yellow_seabloom", () -> new Seabloom(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<AlgaePlantBlock> ALGAE = registerBlock("algae", () -> new AlgaePlantBlock(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<AquaticPowerStoneBlock> AQUATIC_POWER_STONE = registerBlock("aquatic_power_stone", () -> new AquaticPowerStoneBlock(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<AquaticPowerLampBlock> AQUATIC_POWER_LAMP = registerBlock("aquatic_power_lamp", () -> new AquaticPowerLampBlock(BlockBehaviour.Properties.of().strength(0.3F)));
    public static final DeferredBlock<AquaticPowerTorchBlock> AQUATIC_POWER_TORCH = registerOnlyBlock("aquatic_power_torch", () -> new AquaticPowerTorchBlock(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<WallAquaticPowerTorchBlock> WALL_AQUATIC_POWER_TORCH = registerOnlyBlock("aquatic_power_wall_torch", () -> new WallAquaticPowerTorchBlock(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<AquaticPowerDustBlock> AQUATIC_POWER_DUST_WIRE = registerOnlyBlock("aquatic_power_dust", () -> new AquaticPowerDustBlock(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<AquaticPowerRepeaterBlock> AQUATIC_POWER_REPEATER = registerBlock("aquatic_power_repeater", () -> new AquaticPowerRepeaterBlock(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<AquaticPowerTripwireHook> AQUATIC_POWER_TRIPWIRE_HOOK = registerBlock("aquatic_power_tripwire_hook", () -> new AquaticPowerTripwireHook(BlockBehaviour.Properties.of().noCollission()));
    public static final DeferredBlock<AquaticPowerTripwireBlock> AQUATIC_POWER_TRIPWIRE = registerOnlyBlock("aquatic_power_tripwire", () -> new AquaticPowerTripwireBlock(AQUATIC_POWER_TRIPWIRE_HOOK.get(), BlockBehaviour.Properties.of().noCollission()));
    public static final DeferredBlock<AquaticPowerLeverBlock> AQUATIC_POWER_LEVER = registerBlock("aquatic_power_lever", () -> new AquaticPowerLeverBlock(BlockBehaviour.Properties.of().noCollission().strength(0.5F).sound(SoundType.WOOD)));
    public static final DeferredBlock<AquaticPowerComparatorBlock> AQUATIC_POWER_COMPARATOR = registerBlock("aquatic_power_comparator", () -> new AquaticPowerComparatorBlock(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<HardenedCalciteBlock> HARDENED_CALCITE_BLOCK = registerBlock("hardened_calcite_block", () -> new HardenedCalciteBlock(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<PushBubbleColumnBlock> PUSH_BUBBLE_COLUMN = registerOnlyBlock("push_bubble_column", () -> new PushBubbleColumnBlock(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<AlgaeBlock> ALGAE_BLOCK = registerBlock("algae_block", () -> new AlgaeBlock(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<ChiseledAquamarineBlock> CHISELED_AQUAMARINE_BLOCK = registerBlock("chiseled_aquamarine_block", () -> new ChiseledAquamarineBlock(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> RAW_ANCIENT_CUPRUM_BLOCK = registerBlock("raw_ancient_cuprum_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLUE).requiresCorrectToolForDrops().strength(3.0F, 6.0F).sound(SoundType.METAL)));
    public static final DeferredBlock<Block> ANCIENT_CUPRUM_ORE = registerBlock("ancient_cuprum_ore", () -> new DropExperienceBlock(ConstantInt.of(5), BlockBehaviour.Properties.of().sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 15.0F).lightLevel((state) -> 1)));
    public static final DeferredBlock<Block> DEEPSLATE_ANCIENT_CUPRUM_ORE = registerBlock("deepslate_ancient_cuprum_ore", () -> new DropExperienceBlock(ConstantInt.of(5), BlockBehaviour.Properties.of().sound(SoundType.STONE).requiresCorrectToolForDrops().strength(4.0F, 15.0F).lightLevel((state) -> 3)));

    public static final DeferredBlock<Block> ORICHALCUM_BLOCK = registerBlock("orichalcum_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops().strength(5.0f, 6.0f).sound(SoundType.METAL)));

    public static final DeferredBlock<LinguisticBlock> LINGUISTIC_TABLE = registerLinguisticBlock("linguistic_table", () -> new LinguisticBlock(BlockBehaviour.Properties.of().strength(2.5F).sound(SoundType.WOOD)));

    public static final DeferredBlock<WritingBlock> WRITING_TABLE = registerLinguisticBlock("writing_table", () -> new WritingBlock(BlockBehaviour.Properties.of().strength(2.5F).sound(SoundType.WOOD)));
    public static final DeferredBlock<NymphSaplingBlock> NYMPH_SAPLING = registerBlock("nymph_sapling", () ->
            new NymphSaplingBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING)));

    public static final DeferredBlock<PalmSaplingBlock> PALM_SAPLING = registerBlock("palm_sapling", () ->
            new PalmSaplingBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING)));

    public static final DeferredBlock<FireMelonSpikedFruitBlock> FIRE_MELON_FRUIT_SPIKED = registerOnlyBlock("fire_melon_fruit_spiked", () -> new FireMelonSpikedFruitBlock(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<FireMelonFruitBlock> FIRE_MELON_FRUIT = registerOnlyBlock("fire_melon_fruit", () -> new FireMelonFruitBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops()));

    public static final DeferredBlock<FireMelonBody> FIRE_MELON_STEM = registerOnlyBlock("fire_melon_stem", () -> new FireMelonBody(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<FireMelonHead> FIRE_MELON_TOP = registerOnlyBlock("fire_melon_top", () -> new FireMelonHead(BlockBehaviour.Properties.of()));

    public static final BlockType MAGENTA_SEA_GLASS = registerSeaGlass("magenta");
    public static final BlockType LIGHT_GRAY_SEA_GLASS = registerSeaGlass("light_gray");
    public static final BlockType PINK_SEA_GLASS = registerSeaGlass("pink");
    public static final BlockType YELLOW_SEA_GLASS = registerSeaGlass("yellow");
    public static final BlockType PURPLE_SEA_GLASS = registerSeaGlass("purple");
    public static final BlockType SEA_GLASS = registerSeaGlass("");
    public static final BlockType LIGHT_BLUE_SEA_GLASS = registerSeaGlass("light_blue");
    public static final BlockType RED_SEA_GLASS = registerSeaGlass("red");
    public static final BlockType MONOCHROMATIC_SEA_GLASS = registerSeaGlass("monochromatic");
    public static final BlockType GREEN_SEA_GLASS = registerSeaGlass("green");
    public static final BlockType WHITE_SEA_GLASS = registerSeaGlass("white");
    public static final BlockType CYAN_SEA_GLASS = registerSeaGlass("cyan");
    public static final BlockType MULTICOLOR_SEA_GLASS = registerSeaGlass("multicolor");
    public static final BlockType ORANGE_SEA_GLASS = registerSeaGlass("orange");
    public static final BlockType BLACK_SEA_GLASS = registerSeaGlass("black");
    public static final BlockType GRAY_SEA_GLASS = registerSeaGlass("gray");
    public static final BlockType BLUE_SEA_GLASS = registerSeaGlass("blue");
    public static final BlockType BROWN_SEA_GLASS = registerSeaGlass("brown");
    public static final BlockType LIME_SEA_GLASS = registerSeaGlass("lime");

    public static final BlockType MAGENTA_PATTERNED_SEA_GLASS = registerSeaGlassPatterned("magenta");
    public static final BlockType LIGHT_GRAY_PATTERNED_SEA_GLASS = registerSeaGlassPatterned("light_gray");
    public static final BlockType PINK_PATTERNED_SEA_GLASS = registerSeaGlassPatterned("pink");
    public static final BlockType YELLOW_PATTERNED_SEA_GLASS = registerSeaGlassPatterned("yellow");
    public static final BlockType PURPLE_PATTERNED_SEA_GLASS = registerSeaGlassPatterned("purple");
    public static final BlockType LIGHT_BLUE_PATTERNED_SEA_GLASS = registerSeaGlassPatterned("light_blue");
    public static final BlockType RED_PATTERNED_SEA_GLASS = registerSeaGlassPatterned("red");
    public static final BlockType GREEN_PATTERNED_SEA_GLASS = registerSeaGlassPatterned("green");
    public static final BlockType WHITE_PATTERNED_SEA_GLASS = registerSeaGlassPatterned("white");
    public static final BlockType CYAN_PATTERNED_SEA_GLASS = registerSeaGlassPatterned("cyan");
    public static final BlockType ORANGE_PATTERNED_SEA_GLASS = registerSeaGlassPatterned("orange");
    public static final BlockType BLACK_PATTERNED_SEA_GLASS = registerSeaGlassPatterned("black");
    public static final BlockType GRAY_PATTERNED_SEA_GLASS = registerSeaGlassPatterned("gray");
    public static final BlockType BLUE_PATTERNED_SEA_GLASS = registerSeaGlassPatterned("blue");
    public static final BlockType BROWN_PATTERNED_SEA_GLASS = registerSeaGlassPatterned("brown");
    public static final BlockType LIME_PATTERNED_SEA_GLASS = registerSeaGlassPatterned("lime");

    public static final DeferredBlock<RotatedPillarBlock> COQUINA = registerMainTabBlock("coquina", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of() .sound(SoundType.BONE_BLOCK)
            .requiresCorrectToolForDrops()
            .strength(3.0F, 7.0F)
    .mapColor(MapColor.TERRACOTTA_ORANGE)), DeferredBlock -> () -> new BlockItem(DeferredBlock.get(), new Item.Properties()));

    private static <B extends Block> DeferredBlock<B> registerBlock(String name, Supplier<B> block) {
        return registerMainTabBlock(name, block, b -> () -> new BlockItem(b.get(), new Item.Properties()));
    }

    public static <B extends Block> DeferredBlock<B> registerLinguisticBlock(String name, Supplier<B> block) {
        return registerGylphTabBlock(name, block, b -> () -> new BlockItem(b.get(), new Item.Properties()));
    }

    public static <B extends Block> DeferredBlock<B> registerOnlyBlock(String name, Supplier<B> block) {
        return BLOCKS.register(name, block);
    }

    private static <T extends Block> BlockType registerBlockType(String name, Function<BlockBehaviour.Properties, Block> block, BlockBehaviour.Properties properties, boolean genDoors, BlockSetType blockSetType, @Nullable WoodType woodType, int pTicksToStayPressed, boolean pArrowsCanPress) {
        var blockBase = registerMainTabBlock(name + (woodType != null ? "_planks" : ""), () -> block.apply(properties), tDeferredBlock -> () -> new BlockItem(tDeferredBlock.get(), new Item.Properties()));
        var blockSlab = registerMainTabBlock(name + "_slab", blockBase, block1 -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(block1)), block2 -> new BlockItem(block2, new Item.Properties()));
        var blockWall = woodType == null ? registerMainTabBlock(name + "_wall", blockBase, block1 -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(block1)), block2 -> new BlockItem(block2, new Item.Properties())) : null;
        var blockFence = woodType != null ? registerMainTabBlock(name + "_fence", blockBase, block1 -> new FenceBlock(BlockBehaviour.Properties.ofFullCopy(block1)), block2 -> new BlockItem(block2, new Item.Properties())) : null;
        var blockGateBlock = woodType != null ? registerMainTabBlock(name + "_fence_gate", blockBase, block1 -> new FenceGateBlock(woodType, BlockBehaviour.Properties.ofFullCopy(block1)), block2 -> new BlockItem(block2, new Item.Properties())) : null;
        var blockStairs = registerMainTabBlock(name + "_stairs", blockBase, block1 -> new StairBlock(block1.defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(block1)), block2 -> new BlockItem(block2, new Item.Properties()));
        var blockDoor = genDoors ? registerMainTabBlock(name + "_door", blockBase, block1 -> new DoorBlock(blockSetType, BlockBehaviour.Properties.ofFullCopy(block1)), block2 -> new BlockItem(block2, new Item.Properties())) : null;
        var blockTrapDoor = genDoors ? registerMainTabBlock(name + "_trapdoor", blockBase, block1 -> new TrapDoorBlock(blockSetType, BlockBehaviour.Properties.ofFullCopy(block1)), block2 -> new BlockItem(block2, new Item.Properties())) : null;
        var blockButton = registerMainTabBlock(name + "_button", blockBase, block1 -> new ButtonBlock(blockSetType, pTicksToStayPressed, BlockBehaviour.Properties.ofFullCopy(block1)), block2 -> new BlockItem(block2, new Item.Properties()));
        var pressurePlate = registerMainTabBlock(name + "_pressure_plate", blockBase, block1 -> new PressurePlateBlock(blockSetType, BlockBehaviour.Properties.ofFullCopy(block1)), block2 -> new BlockItem(block2, new Item.Properties()));
        return BlockType.of(blockBase, blockSlab, blockWall, blockFence, blockGateBlock, blockStairs, blockDoor, blockTrapDoor, blockButton, pressurePlate);
    }

    private static BlockType registerSeaGlass(String name) {
        var blockName = "";
        if (name.isEmpty()) {
            blockName = "sea_glass";
        } else {
            blockName = name + "_sea_glass";
        }

        BlockType blockType = registerBlockType(blockName, HalfTransparentBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).sound(SoundType.GLASS), false, BlockSetType.IRON, null, 40, true);

        if(!name.equals("monochromatic") && !name.equals("multicolor") && !name.isEmpty()) {
            addToSeaGlassList(name, blockType);
        }

        return blockType;
    }

    private static BlockType registerSeaGlassPatterned(String name) {
        var blockName = name + "_patterned_sea_glass";
        BlockType blockType = registerBlockType(blockName, HalfTransparentBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).sound(SoundType.GLASS), false, BlockSetType.IRON, null, 40, true);
        addToSeaGlassPatternList(name, blockType);
        return blockType;
    }

    private static void addToSeaGlassPatternList(String name, BlockType blockType) {
        SEA_GLASS_PATTERNS.put(DyeColor.valueOf(name.toUpperCase(Locale.ROOT)), blockType);
    }

    private static void addToSeaGlassList(String name, BlockType blockType) {
        SEA_GLASS_LIST.put(DyeColor.valueOf(name.toUpperCase(Locale.ROOT)), blockType);
    }

    private static <B extends Block, I extends BlockItem> DeferredBlock<B> registerMainTabBlock(String name, Supplier<B> block, Function<DeferredBlock<B>, Supplier<I>> item) {
        var reg = BLOCKS.register(name, block);
        AtlantisGroupInit.addToMainTab(ItemInit.ITEMS.register(name, () -> item.apply(reg).get()));
        return reg;
    }

    private static <B extends Block, C extends Block, I extends BlockItem> DeferredBlock<C> registerMainTabBlock(String name, Supplier<B> block, Function<B, C> blockFunction, Function<C, I> item) {
        var reg = BLOCKS.register(name, () -> blockFunction.apply(block.get()));
        AtlantisGroupInit.addToMainTab(ItemInit
                .ITEMS.register(name, () ->
                        item.apply(reg.get())));
        return reg;
    }

    private static <B extends Block, I extends BlockItem> DeferredBlock<B> registerGylphTabBlock(String name, Supplier<B> block, Function<DeferredBlock<B>, Supplier<I>> item) {
        var reg = BLOCKS.register(name, block);
        AtlantisGroupInit.addToGylphTab(ItemInit.ITEMS.register(name, () -> item.apply(reg).get()));
        return reg;
    }

    public static DeferredBlock<Block> getLinguisticBlock(LinguisticGlyph symbol, DyeColor color) {
        if (color != null) {
            if (symbol != null && symbol != LinguisticGlyph.BLANK) {
                return DYED_LINGUISTICS.get(symbol).get(color);
            } else {
                return DYED_LINGUISTICS.get(LinguisticGlyph.BLANK).get(color);
            }
        } else {
            if (symbol != null && symbol != LinguisticGlyph.BLANK) {
                return NON_LINGUISTICS.get(symbol);
            } else {
                return NON_LINGUISTICS.get(LinguisticGlyph.BLANK);
            }
        }
    }


    public static final BlockBehaviour.Properties ANCIENT_CUPRUM_PROPERTIES = BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GRAY).requiresCorrectToolForDrops().strength(3.0F, 6.0F).sound(SoundType.METAL);

    public static Map<WeatheringCuprum.WeatherState, TrailsGroup> ANCIENT_CUPRUM = Util.make(new HashMap<>(), map -> Arrays.stream(WeatheringCuprum.WeatherState.values()).forEach(state -> map.put(state, registerTrialsGroup("ancient_cuprum", state, ANCIENT_CUPRUM_PROPERTIES))));

    private static TrailsGroup registerTrialsGroup(String name, WeatheringCuprum.WeatherState state, BlockBehaviour.Properties properties) {
        var prefix = state == WeatheringCuprum.WeatherState.UNAFFECTED ? "" : state.getSerializedName() + "_";

        DeferredBlock<WeatheringCuprumFullBlock> block = registerBlock(prefix + name + "_block", () -> new WeatheringCuprumFullBlock(state, properties));
        DeferredBlock<WeatheringCuprumFullBlock> cut = registerBlock(prefix + "cut_" + name, () -> new WeatheringCuprumFullBlock(state, properties));
        DeferredBlock<WeatheringCuprumFullBlock> chiseled = registerBlock(prefix + "chiseled_" + name, () -> new WeatheringCuprumFullBlock(state, properties));
        DeferredBlock<WeatheringCuprumStairsBlock> cut_stairs = registerBlock(prefix + "cut_" + name + "_stairs", () -> new WeatheringCuprumStairsBlock(state, cut.get().defaultBlockState(), properties));
        DeferredBlock<WeatheringCuprumSlabBlock> cut_slab = registerBlock(prefix + "cut_" + name + "_slab", () -> new WeatheringCuprumSlabBlock(state, properties));
        DeferredBlock<WeatheringCuprumDoorBlock> door = registerBlock(prefix + name + "_door", () -> new WeatheringCuprumDoorBlock(BlockSetType.IRON, state, BlockBehaviour.Properties.ofFullCopy(block.get()).noOcclusion().pushReaction(PushReaction.DESTROY)));
        DeferredBlock<WeatheringCuprumTrapdoorBlock> trapdoor = registerBlock(prefix + name + "_trapdoor", () -> new WeatheringCuprumTrapdoorBlock(state, BlockBehaviour.Properties.ofFullCopy(block.get()).noOcclusion().isValidSpawn((pState, pLevel, pPos, pValue) -> false)));
        DeferredBlock<WeatheringCuprumGrateBlock> grate = registerBlock(prefix + name + "_grate", () -> new WeatheringCuprumGrateBlock(state, BlockBehaviour.Properties.ofFullCopy(block.get()).sound(SoundType.METAL).requiresCorrectToolForDrops().noOcclusion().isValidSpawn((pState, pLevel, pPos, pValue) -> false).isRedstoneConductor((pState, pLevel, pPos) -> false).isSuffocating((pState, pLevel, pPos) -> false).isViewBlocking((pState, pLevel, pPos) -> false)));
        DeferredBlock<WeatheringCuprumBulbBlock> bulb = registerBlock(prefix + name + "_bulb", () -> new WeatheringCuprumBulbBlock(state, BlockBehaviour.Properties.ofFullCopy(block.get()).sound(SoundType.GLASS).isRedstoneConductor((pState, pLevel, pPos) -> false).lightLevel(value -> value.getValue(WeatheringCuprumBulbBlock.LIT) ? state.lightLevel() : 0)));

        DeferredBlock<Block> waxed_block = registerBlock("waxed_" + prefix + name + "_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(block.get())));
        DeferredBlock<Block> waxed_cut = registerBlock("waxed_" + prefix + "cut_" + name, () -> new Block(BlockBehaviour.Properties.ofFullCopy(cut.get())));
        DeferredBlock<Block> waxed_chiseled = registerBlock("waxed_" + prefix + "chiseled_" + name, () -> new Block(BlockBehaviour.Properties.ofFullCopy(chiseled.get())));
        DeferredBlock<StairBlock> waxed_cut_stairs = registerBlock("waxed_" + prefix + "cut_" + name + "_stairs", () -> new StairBlock(cut.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(cut_stairs.get())));
        DeferredBlock<SlabBlock> waxed_cut_slab = registerBlock("waxed_" + prefix + "cut_" + name + "_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(cut_slab.get())));
        DeferredBlock<DoorBlock> waxed_door = registerBlock("waxed_" + prefix + name + "_door", () -> new DoorBlock(BlockSetType.IRON, BlockBehaviour.Properties.ofFullCopy(door.get()).noOcclusion().pushReaction(PushReaction.DESTROY)));
        DeferredBlock<TrapDoorBlock> waxed_trapdoor = registerBlock("waxed_" + prefix + name + "_trapdoor", () -> new TrapDoorBlock(BlockSetType.IRON, BlockBehaviour.Properties.ofFullCopy(trapdoor.get()).noOcclusion().isValidSpawn((pState, pLevel, pPos, pValue) -> false)));
        DeferredBlock<Block> waxed_grate = registerBlock("waxed_" + prefix + name + "_grate", () -> new Block(BlockBehaviour.Properties.ofFullCopy(grate.get()).sound(SoundType.METAL).requiresCorrectToolForDrops().noOcclusion().isValidSpawn((pState, pLevel, pPos, pValue) -> false).isRedstoneConductor((pState, pLevel, pPos) -> false).isSuffocating((pState, pLevel, pPos) -> false).isViewBlocking((pState, pLevel, pPos) -> false)));
        DeferredBlock<CuprumBulbBlock> waxed_bulb = registerBlock("waxed_" + prefix + name + "_bulb", () -> new CuprumBulbBlock(BlockBehaviour.Properties.ofFullCopy(bulb.get()).sound(SoundType.GLASS).isRedstoneConductor((pState, pLevel, pPos) -> false).lightLevel(value -> value.getValue(CuprumBulbBlock.LIT) ? state.lightLevel() : 0)));

        return new TrailsGroup(block, cut, chiseled, cut_stairs, cut_slab, door, trapdoor, grate, bulb, waxed_block, waxed_cut, waxed_chiseled, waxed_cut_stairs, waxed_cut_slab, waxed_door, waxed_trapdoor, waxed_grate, waxed_bulb);
    }

    public static void init(IEventBus bus) {
        BLOCKS.register(bus);
    }

    static {
        BiFunction<LinguisticGlyph, DyeColor, Supplier<Block>> blockSupplier = (glyph, dyeColor) -> () -> new GlyphBlock(glyph, dyeColor, BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(1.5f, 6.0f
        ));

        for (LinguisticGlyph symbol : LinguisticGlyph.values()) {
            String name = "linguistic_glyph" + symbol.toString();

            for (DyeColor color : DyeColor.values()) {
                DYED_LINGUISTICS.computeIfAbsent(symbol, c -> new HashMap<>()).put(color, registerLinguisticBlock(color.getSerializedName() + "_" + name, blockSupplier.apply(symbol, color)));
            }

            NON_LINGUISTICS.put(symbol, registerLinguisticBlock(name, blockSupplier.apply(symbol, null)));
        }
    }

    static {
        Function<DyeColor, Supplier<Block>> blockSupplier = (dyeColor) -> () -> new ColoredShellBlock(BlockBehaviour.Properties.of());
        for (DyeColor color : DyeColor.values()) {
            COLORED_SHELL_BLOCKS.put(color, registerBlock(color.getSerializedName() + "_shell_block", blockSupplier.apply(color)));
        }
    }

    static {
        Function<DyeColor, Supplier<Block>> blockSupplier = (dyeColor) -> () -> new CrackedShellBlock(BlockBehaviour.Properties.of());
        for (DyeColor color : DyeColor.values()) {
            CRACKED_SHELL_BLOCKS.put(color, registerBlock("cracked_" + color.getSerializedName() + "_shell", blockSupplier.apply(color)));
        }
    }

    static {
        Function<DyeColor, Supplier<Block>> blockSupplier = (dyeColor) -> () -> new CrackedShellBlock(BlockBehaviour.Properties.of());
        for (DyeColor color : DyeColor.values()) {
            CRACKED_MOSSY_SHELL_BLOCKS.put(color, registerBlock("cracked_mossy_" +color.getSerializedName() + "_shell", blockSupplier.apply(color)));
        }
    }

    static {
        Function<DyeColor, Supplier<Block>> blockSupplier = (dyeColor) -> () -> new CrackedShellBlock(BlockBehaviour.Properties.of());
        for (DyeColor color : DyeColor.values()) {
            MOSSY_SHELL_BLOCKS.put(color, registerBlock("mossy_" +   color.getSerializedName() + "_shell", blockSupplier.apply(color)));
        }
    }
}
