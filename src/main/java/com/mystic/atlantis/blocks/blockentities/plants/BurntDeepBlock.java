package com.mystic.atlantis.blocks.blockentities.plants;

import com.mojang.serialization.MapCodec;
import com.mystic.atlantis.init.TileEntityInit;
import net.minecraft.world.level.block.BushBlock;

public class BurntDeepBlock extends GeneralPlantBlock<BurntDeepTileEntity> {
	public BurntDeepBlock() {
		super(TileEntityInit.BURNT_DEEP_TILE);
	}

	public BurntDeepBlock(Properties properties) {
		super(TileEntityInit.BURNT_DEEP_TILE);
	}

	@Override
    public MapCodec<? extends BushBlock> codec() {
		return simpleCodec(BurntDeepBlock::new);
	}
}
