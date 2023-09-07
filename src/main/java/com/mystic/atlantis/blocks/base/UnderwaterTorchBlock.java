package com.mystic.atlantis.blocks.base;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.TorchBlock;
import net.minecraft.world.level.block.state.BlockState;

public class UnderwaterTorchBlock extends TorchBlock {

	public UnderwaterTorchBlock(Properties pProperties, ParticleOptions pFlameParticle) {
		super(pProperties, pFlameParticle);
	}
	
	@Override
	public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
		return super.canSurvive(pState, pLevel, pPos);
	}

}
