package com.mystic.atlantis.blocks.blockentities.plants;

import com.mojang.serialization.MapCodec;
import com.mystic.atlantis.init.TileEntityInit;
import net.minecraft.world.level.block.BushBlock;

public class BlueLilyBlock extends GeneralPlantBlock<BlueLilyTileEntity> {

	public BlueLilyBlock() {
		super(TileEntityInit.BLUE_LILY_TILE);
	}

	public BlueLilyBlock(Properties properties) {
		super(TileEntityInit.BLUE_LILY_TILE);
	}

	@Override
    public MapCodec<? extends BushBlock> codec() {
		return simpleCodec(BlueLilyBlock::new);
	}
}
