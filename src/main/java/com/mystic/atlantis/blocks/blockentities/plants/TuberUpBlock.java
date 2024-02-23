package com.mystic.atlantis.blocks.blockentities.plants;

import com.mojang.serialization.MapCodec;
import com.mystic.atlantis.init.TileEntityInit;
import net.minecraft.world.level.block.BushBlock;

public class TuberUpBlock extends GeneralPlantBlock<TuberUpTileEntity> {
	
    public TuberUpBlock() {
		super(TileEntityInit.TUBER_UP_TILE);
	}

	public TuberUpBlock(Properties properties) {
		super(TileEntityInit.TUBER_UP_TILE);
	}

	@Override
    public MapCodec<? extends BushBlock> codec() {
		return simpleCodec(TuberUpBlock::new);
	}
}
