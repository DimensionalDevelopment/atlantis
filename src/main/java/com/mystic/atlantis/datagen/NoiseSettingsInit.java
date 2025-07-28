package com.mystic.atlantis.datagen;

import com.mystic.atlantis.dimension.AtlantisDimensions;
import com.mystic.atlantis.init.BlockInit;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

import java.util.List;

import static net.minecraft.world.level.levelgen.DensityFunctions.HolderHolder;
import static net.minecraft.world.level.levelgen.DensityFunctions.*;
import static net.minecraft.world.level.levelgen.SurfaceRules.RuleSource;
import static net.minecraft.world.level.levelgen.SurfaceRules.*;
import static net.minecraft.world.level.levelgen.VerticalAnchor.*;

public class NoiseSettingsInit {
    private static HolderGetter<NormalNoise.NoiseParameters> noiseRegistry = null;
    private static HolderGetter<DensityFunction> functionRegistry = null;

    public NoiseSettingsInit(BootstrapContext<NoiseGeneratorSettings> context) {
        noiseRegistry = context.lookup(Registries.NOISE);
        functionRegistry = context.lookup(Registries.DENSITY_FUNCTION);

        context.register(AtlantisDimensions.ATLANTIS_DIMENSION_NOISE_SETTING, new NoiseGeneratorSettings(
                new NoiseSettings(-64, 512, 1, 2),
                Blocks.STONE.defaultBlockState(),
                Blocks.WATER.defaultBlockState().setValue(LiquidBlock.LEVEL, 0),

                new NoiseRouter(
                        noise(parameters(Noises.AQUIFER_BARRIER), 1, 0.5),
                        noise(parameters(Noises.AQUIFER_FLUID_LEVEL_FLOODEDNESS), 1, 0.67),
                        noise(parameters(Noises.AQUIFER_FLUID_LEVEL_SPREAD), 1, 0.7142857142857143),
                        zero(),
                        shiftedNoise2d(parameters(Noises.TEMPERATURE)),
                        shiftedNoise2d(parameters(Noises.VEGETATION)),
                        function(NoiseRouterData.CONTINENTS),
                        function(NoiseRouterData.EROSION),
                        function(NoiseRouterData.DEPTH),
                        function(NoiseRouterData.RIDGES),
                        zero(),
                        mul(
                                constant(0.64),
                                interpolated(
                                        blendDensity(
                                                add(
                                                        constant(2.5),
                                                        mul(
                                                                yClampedGradient(70, 0, 1, 0),
                                                                add(
                                                                        constant(-2.77),
                                                                        add(
                                                                                constant(2),
                                                                                mul(
                                                                                        yClampedGradient(70, 0, 1, 0),
                                                                                        add(
                                                                                                constant(-2),
                                                                                                mul(
                                                                                                        yClampedGradient(350, 450, 1, 0),
                                                                                                        function(NoiseRouterData.BASE_3D_NOISE_OVERWORLD)
                                                                                                )
                                                                                        )
                                                                                )
                                                                        )
                                                                )
                                                        )
                                                )
                                        )
                                )
                        ).squeeze(),
                        zero(),
                        zero(),
                        zero()
                ),
                sequence(
                        conditionalVerticalBlockPlacement("minecraft:bedrock_floor", -55, -54, Blocks.BEDROCK),
                        sequence(
                                conditionalVerticalBlockPlacement("atlantis:deepslate_layer", 4, 5, Blocks.DEEPSLATE.defaultBlockState().setValue(RotatedPillarBlock.AXIS, Direction.Axis.X)),
                                conditionalVerticalBlockPlacement("atlantis:stone_layer", 33, 34, Blocks.STONE),
                                conditionalVerticalBlockPlacement("atlantis:detritus_sandstone_layer", 55, 56, BlockInit.DETRITUS_SANDSTONE.get()),
                                conditionalVerticalBlockPlacement("atlantis:sandstone_layer", 199, 200, Blocks.SANDSTONE),
                                conditionalVerticalBlockPlacement("atlantis:seabed_layer", 511, 512, BlockInit.SEABED.get())
                        )
                ),
                List.of(),
                512,
                false,
                true,
                true,
                false
        ));
    }

    private static RuleSource block(Block block) {
        return state(block.defaultBlockState());
    }

    private static DensityFunction noise(Holder<NormalNoise.NoiseParameters> parameter, double xz_scale, double y_scale) {
        return DensityFunctions.noise(parameter, xz_scale, y_scale);
    }

    private static RuleSource conditionalVerticalBlockPlacement(String name, int min, int max, BlockState state) {
        return ifTrue(verticalGradient(name, absolute(min), absolute(max)), state(state));
    }

    private static DensityFunction function(ResourceKey<DensityFunction> key) {
        return new HolderHolder(functionRegistry.getOrThrow(key));
    }

    private static Holder<NormalNoise.NoiseParameters> parameters(ResourceKey<NormalNoise.NoiseParameters> key) {
        return noiseRegistry.getOrThrow(key);
    }

    private static RuleSource conditionalVerticalBlockPlacement(String name, int min, int max, Block block) {
        return conditionalVerticalBlockPlacement(name, min, max, block.defaultBlockState());
    }

    private static DensityFunction shiftedNoise2d(Holder<NormalNoise.NoiseParameters> noise) {
        return DensityFunctions.shiftedNoise2d(
                function(NoiseRouterData.SHIFT_X),
                function(NoiseRouterData.SHIFT_Z),
                    0.25, noise);
    }
}
