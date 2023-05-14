package com.mystic.atlantis.items.tools;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class HammerItem extends PickaxeItem {

	public HammerItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
		super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
	}
	
	@Override
	public boolean mineBlock(ItemStack pStack, Level pLevel, BlockState pState, BlockPos pPos, LivingEntity pEntityLiving) {	
		if (!pLevel.isClientSide) {
			MutableBlockPos minePos = new MutableBlockPos(pPos.getX(), pPos.getY(), pPos.getZ());
			ObjectArrayList<BlockPos> blocksToBreak = new ObjectArrayList<BlockPos>();
			Direction targetDir = pEntityLiving.getDirection();
			int radius = 1;
			int depth = 1;
			
			for (int y = -radius; y < radius; y++) {
				for (int x = -radius; x < radius; x++) {
					for (int z = -radius; z < radius; z++) {
						
					}
				}
			}
		}
		
		return super.mineBlock(pStack, pLevel, pState, pPos, pEntityLiving);
	}

}
