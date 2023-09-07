package com.mystic.atlantis.blocks.base;

import org.jetbrains.annotations.Nullable;

import com.mystic.atlantis.Atlantis;
import com.mystic.atlantis.blocks.blockentities.DummyDataStorage;
import com.mystic.atlantis.dimension.DimensionAtlantis;
import com.mystic.atlantis.init.BlockInit;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class AtlantisPortalBlock extends Block implements EntityBlock {

	public AtlantisPortalBlock(Properties settings) {
		super(settings);
	}

	@Override
	public InteractionResult use(BlockState targetState, Level level, BlockPos targetPos, Player player, InteractionHand handIn, BlockHitResult result) {
		if (!DimensionAtlantis.isAtlantisDimension(level)) {
			if(!level.isClientSide) {
				// From Overworld to Atlantis
				ServerLevel atlantis = player.getServer().getLevel(DimensionAtlantis.ATLANTIS_WORLD);
				ServerLevel overWorld = player.getServer().getLevel(Atlantis.getOverworldKey());
				atlantis.getBlockState(targetPos);
				BlockEntity targetTileEntity = level.getBlockEntity(targetPos);
				DummyDataStorage dataStorage = (DummyDataStorage) targetTileEntity;
				BlockPos atlantisPos;
				MutableBlockPos mutableBlockPos = new MutableBlockPos(0, 0, 0);

				if (dataStorage.getDestination() != null) {
					Vec3 targetVecPos = new Vec3(dataStorage.getDestination().getX(), dataStorage.getDestination().getY(), dataStorage.getDestination().getZ());
					sendPlayerToDimension((ServerPlayer) player, atlantis, targetVecPos);
					player.displayClientMessage(Component.translatable("Welcome To Atlantis!!!"), true);
					return InteractionResult.SUCCESS;
				}

				for (int y = 0; y < 255; y++) {
					for (int x = (int) player.getX() - 6; x < (int) player.getX() + 6; x++) {
						for (int z = (int) player.getZ() - 6; z < (int) player.getZ() + 6; z++) {
							mutableBlockPos.set(x, y, z);
							if (atlantis.getBlockState(mutableBlockPos).getBlock() == this.asBlock() && isPortalAt(atlantis, mutableBlockPos)) {
								atlantisPos = mutableBlockPos.offset(0, 1, 0);
								dataStorage.setDestination(atlantisPos);
								Vec3 targetAtlantisPos = new Vec3(atlantisPos.getX(), atlantisPos.getY(), atlantisPos.getZ());
								sendPlayerToDimension((ServerPlayer) player, atlantis, targetAtlantisPos);
								player.displayClientMessage(Component.translatable("Welcome To Atlantis!!!"), true);
								return InteractionResult.SUCCESS;
							} else {
								atlantis.setBlock(targetPos, this.asBlock().defaultBlockState(), 2);
								if (atlantis.getBlockState(mutableBlockPos).getBlock() == this.asBlock() && isPortalAt(atlantis, mutableBlockPos)) {
									atlantisPos = mutableBlockPos.offset(0, 1, 0);
									dataStorage.setDestination(atlantisPos);
									Vec3 targetAtlantisPos = new Vec3(atlantisPos.getX(), atlantisPos.getY(), atlantisPos.getZ());
									sendPlayerToDimension((ServerPlayer) player, atlantis, targetAtlantisPos);
									player.displayClientMessage(Component.translatable("Welcome To Atlantis!!!"), true);
									return InteractionResult.SUCCESS;
								}
							}
						}
					}
				}
				level.setBlock(targetPos, this.asBlock().defaultBlockState(), 2);
			} else {
				player.displayClientMessage(Component.translatable("NO PASSING THE GATES OF ATLANTIS!!!"), true);
				return InteractionResult.FAIL;
			}
		} else {
			if (!level.isClientSide) {
				ServerLevel overworld = level.getServer().getLevel(Atlantis.getOverworldKey());
				if (level != null) {
					overworld.getBlockState(targetPos);
					BlockEntity entity = level.getBlockEntity(targetPos);
					DummyDataStorage dataStorage = (DummyDataStorage) entity;
					BlockPos overWorldPos;
					BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos(0, 0, 0);

					if (dataStorage.getDestination() != null) {
						Vec3 vector3d = new Vec3(dataStorage.getDestination().getX(), dataStorage.getDestination().getY(), dataStorage.getDestination().getZ());
						sendPlayerToDimension((ServerPlayer) player, overworld, vector3d);
						player.displayClientMessage(Component.translatable("We Hope You Enjoyed Atlantis, Come Back Soon!"), true);
						return InteractionResult.SUCCESS;
					}

					for (int y = 0; y < 255; y++) {
						for (int x = (int) player.getX() - 6; x < (int) player.getX() + 6; x++) {
							for (int z = (int) (player.getZ() - 6); z < (int) player.getZ() + 6; z++) {
								mutableBlockPos.set(x, y, z);
								if (overworld.getBlockState(mutableBlockPos).getBlock() == this.asBlock() && isPortalAt(overworld, mutableBlockPos)) {
									overWorldPos = mutableBlockPos.offset(0, 1, 0);
									dataStorage.setDestination(overWorldPos);
									sendPlayerToDimension((ServerPlayer) player, overworld, new Vec3(overWorldPos.getX(), overWorldPos.getY(), overWorldPos.getZ()));
									player.displayClientMessage(Component.translatable("We Hope You Enjoyed Atlantis, Come Back Soon!"), true);
									return InteractionResult.SUCCESS;
								} else {
									overworld.setBlock(targetPos, this.asBlock().defaultBlockState(), 2);
									if (overworld.getBlockState(mutableBlockPos).getBlock() == this.asBlock() && isPortalAt(overworld, mutableBlockPos)) {
										overWorldPos = mutableBlockPos.offset(0, 1, 0);
										dataStorage.setDestination(overWorldPos);
										sendPlayerToDimension((ServerPlayer) player, overworld, new Vec3(overWorldPos.getX(), overWorldPos.getY(), overWorldPos.getZ()));
										player.displayClientMessage(Component.translatable("We Hope You Enjoyed Atlantis, Come Back Soon!"), true);
										return InteractionResult.SUCCESS;
									}
								}
							}
						}
					}
					level.setBlock(targetPos, this.asBlock().defaultBlockState(), 2);
				}
				//TODO This is dead code either way
				player.displayClientMessage(Component.translatable("NO PASSING THE GATES OF ATLANTIS!!!"), true);
				return InteractionResult.FAIL;
			}
		}
		player.displayClientMessage(Component.translatable("NO PASSING THE GATES OF ATLANTIS!!!"), true);
		return InteractionResult.PASS;
	}

	private boolean isPortalAt(ServerLevel level, BlockPos targetPos) {
		return level.getBlockState(targetPos).is(getPortal());
	}

	public HolderSet<Block> getPortal(){
		Holder<Block> airHolderSet = Holder.direct(BlockInit.ATLANTIS_PORTAL.get());
		return HolderSet.direct(airHolderSet);
	}

	public static void sendPlayerToDimension(ServerPlayer player, ServerLevel targetWorld, Vec3 targetVec) {
		// ensure destination chunk is loaded before we put the player in it
		targetWorld.getChunk(new BlockPos(targetVec));
		player.teleportTo(targetWorld, targetVec.x(), targetVec.y(), targetVec.z(), player.getYRot(), player.getXRot());
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos targetPos, BlockState targetState) {
		return new DummyDataStorage(targetPos, targetState);
	}
}