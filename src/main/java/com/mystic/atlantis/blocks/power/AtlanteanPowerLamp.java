package com.mystic.atlantis.blocks.power;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RedstoneLampBlock;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AtlanteanPowerLamp extends RedstoneLampBlock {

    public static boolean power;

    public static final BooleanProperty LIT = RedstoneLampBlock.LIT;

    public AtlanteanPowerLamp(Settings settings) {
        super(settings.sounds(BlockSoundGroup.GLASS).luminance(value -> power ? 15 : 0));
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        if(state.get(LIT)) {
            state.get(LIT);
        }
    }
}
