package com.mystic.atlantis.blocks.base;

import com.mystic.atlantis.init.FluidInit;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import org.jetbrains.annotations.NotNull;

import static net.minecraft.world.level.block.LiquidBlock.LEVEL;

public class CoconutSlice extends Block {
    public static final Property<Boolean> HAS_MILK = BooleanProperty.create("has_milk");

    public CoconutSlice(BlockBehaviour.Properties pProperties) {
        super(pProperties
                .sound(SoundType.CHERRY_WOOD)
                .requiresCorrectToolForDrops()
                .strength(3.0F, 6.0F));
        this.defaultBlockState().setValue(HAS_MILK, Boolean.FALSE);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(HAS_MILK);
    }
}
