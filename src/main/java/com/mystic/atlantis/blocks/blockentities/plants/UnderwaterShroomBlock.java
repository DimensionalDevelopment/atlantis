package com.mystic.atlantis.blocks.blockentities.plants;

import com.mojang.serialization.MapCodec;
import com.mystic.atlantis.init.TileEntityInit;
import net.minecraft.world.level.block.BushBlock;

public class UnderwaterShroomBlock extends GeneralPlantBlock<UnderwaterShroomTileEntity> {
	
    public UnderwaterShroomBlock() {
        super(TileEntityInit.UNDERWATER_SHROOM_TILE);
    }

    public UnderwaterShroomBlock(Properties properties) {
        super(TileEntityInit.UNDERWATER_SHROOM_TILE);
    }

    @Override
    public MapCodec<? extends BushBlock> codec() {
        return simpleCodec(UnderwaterShroomBlock::new);
    }
}
