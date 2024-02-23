package com.mystic.atlantis.blocks.blockentities.plants;

import com.mojang.serialization.MapCodec;
import com.mystic.atlantis.init.TileEntityInit;
import net.minecraft.world.level.block.BushBlock;

import java.util.function.Function;

public class EnenmomyBlock extends GeneralPlantBlock<EnenmomyTileEntity> {
	
    public EnenmomyBlock() {
        super(TileEntityInit.ENENMOMY_TILE);
    }

    public EnenmomyBlock(Properties properties) {
        super(TileEntityInit.ENENMOMY_TILE);
    }

    @Override
    public MapCodec<? extends BushBlock> codec() {
        return simpleCodec((Function<Properties, ? extends BushBlock>) EnenmomyBlock::new);
    }
}
