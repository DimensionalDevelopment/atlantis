package com.mystic.atlantis.configfeature;

import com.mojang.serialization.Codec;
import com.mystic.atlantis.init.BlockInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.CountConfig;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import java.util.Random;

public class ShellBlockFeature extends Feature<CountConfig> {

    private static final Block WATER = Blocks.WATER;

    public ShellBlockFeature(Codec<CountConfig> FeatureSpreadConfig) {
        super(FeatureSpreadConfig);
    }

    public boolean generate(StructureWorldAccess reader, ChunkGenerator generator, Random rand, BlockPos pos, CountConfig config) {
        int i = 0;
        int j = config.getCount().getValue(rand);

        for(int k = 0; k < j; ++k) {
            int l = rand.nextInt(8) - rand.nextInt(8);
            int i1 = rand.nextInt(8) - rand.nextInt(8);
            int j1 = reader.getTopY(Heightmap.Type.OCEAN_FLOOR, pos.getX() + l, pos.getZ() + i1);
            BlockPos blockpos = new BlockPos(pos.getX() + l, j1, pos.getZ() + i1);
            BlockState blockstate;
            switch(rand.nextInt(16)){
                case 0:
                    blockstate = BlockInit.BLACK_COLORED_SHELL_BLOCK.get().getDefaultState();
                break;
                case 1:
                    blockstate = BlockInit.BLUE_COLORED_SHELL_BLOCK.get().getDefaultState();
                break;
                case 2:
                    blockstate = BlockInit.LIGHT_BLUE_COLORED_SHELL_BLOCK.get().getDefaultState();
                break;
                case 3:
                    blockstate = BlockInit.LIGHT_GRAY_COLORED_SHELL_BLOCK.get().getDefaultState();
                    break;
                case 4:
                    blockstate = BlockInit.RED_COLORED_SHELL_BLOCK.get().getDefaultState();
                    break;
                case 5:
                    blockstate = BlockInit.YELLOW_COLORED_SHELL_BLOCK.get().getDefaultState();
                    break;
                case 6:
                    blockstate = BlockInit.ORANGE_COLORED_SHELL_BLOCK.get().getDefaultState();
                    break;
                case 7:
                    blockstate = BlockInit.PURPLE_COLORED_SHELL_BLOCK.get().getDefaultState();
                    break;
                case 8:
                    blockstate = BlockInit.MAGENTA_COLORED_SHELL_BLOCK.get().getDefaultState();
                    break;
                case 9:
                    blockstate = BlockInit.GREEN_COLORED_SHELL_BLOCK.get().getDefaultState();
                    break;
                case 10:
                    blockstate = BlockInit.LIME_COLORED_SHELL_BLOCK.get().getDefaultState();
                    break;
                case 11:
                    blockstate = BlockInit.BROWN_COLORED_SHELL_BLOCK.get().getDefaultState();
                    break;
                case 12:
                    blockstate = BlockInit.PINK_COLORED_SHELL_BLOCK.get().getDefaultState();
                    break;
                case 13:
                    blockstate = BlockInit.CYAN_COLORED_SHELL_BLOCK.get().getDefaultState();
                    break;
                case 14:
                    blockstate = BlockInit.WHITE_COLORED_SHELL_BLOCK.get().getDefaultState();
                    break;
                case 15:
                    blockstate = BlockInit.GRAY_COLORED_SHELL_BLOCK.get().getDefaultState();
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + rand.nextInt(16));
            }
            if (reader.getBlockState(blockpos).isOf(Blocks.WATER) && blockstate.canPlaceAt(reader, blockpos)) {
                reader.setBlockState(blockpos, blockstate, 2);
                ++i;
            }
        }

        return i > 0;
    }
}
