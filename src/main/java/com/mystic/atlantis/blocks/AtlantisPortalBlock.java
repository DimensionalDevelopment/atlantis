package com.mystic.atlantis.blocks;

import org.jetbrains.annotations.Nullable;

import com.mystic.atlantis.Atlantis;
import com.mystic.atlantis.blocks.blockentities.DummyDataStorage;
import com.mystic.atlantis.dimension.DimensionAtlantis;
import com.mystic.atlantis.init.BlockInit;

import net.minecraft.core.BlockPos;
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

    public AtlantisPortalBlock(Properties properties) {
        super(properties);
    }

    @SuppressWarnings({"ConstantConditions", "NullableProblems"})
    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if (!DimensionAtlantis.isAtlantisDimension(worldIn)) {
            if(!worldIn.isClientSide) {
                // From Overworld to Atlantis
                ServerLevel atlantis = player.getServer().getLevel(DimensionAtlantis.ATLANTIS_WORLD);
                ServerLevel overWorld = player.getServer().getLevel(Atlantis.getOverworldKey());
                atlantis.getBlockState(pos);
                BlockEntity entity = worldIn.getBlockEntity(pos);
                DummyDataStorage dataStorage = (DummyDataStorage) entity;
                BlockPos atlantisPos;
                BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos(0, 0, 0);

                if (dataStorage.getDestination() != null) {
                    Vec3 vector3d = new Vec3(dataStorage.getDestination().getX(), dataStorage.getDestination().getY(), dataStorage.getDestination().getZ());
                    sendPlayerToDimension((ServerPlayer) player, atlantis, vector3d);
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
                                Vec3 vector3d = new Vec3(atlantisPos.getX(), atlantisPos.getY(), atlantisPos.getZ());
                                sendPlayerToDimension((ServerPlayer) player, atlantis, vector3d);
                                player.displayClientMessage(Component.translatable("Welcome To Atlantis!!!"), true);
                                return InteractionResult.SUCCESS;
                            } else {
                                atlantis.setBlock(pos, this.asBlock().defaultBlockState(), 2);
                                if (atlantis.getBlockState(mutableBlockPos).getBlock() == this.asBlock() && isPortalAt(atlantis, mutableBlockPos)) {
                                    atlantisPos = mutableBlockPos.offset(0, 1, 0);
                                    dataStorage.setDestination(atlantisPos);
                                    Vec3 vector3d = new Vec3(atlantisPos.getX(), atlantisPos.getY(), atlantisPos.getZ());
                                    sendPlayerToDimension((ServerPlayer) player, atlantis, vector3d);
                                    player.displayClientMessage(Component.translatable("Welcome To Atlantis!!!"), true);
                                    return InteractionResult.SUCCESS;
                                }
                            }
                        }
                    }
                }
                worldIn.setBlock(pos, this.asBlock().defaultBlockState(), 2);
            } else {
                player.displayClientMessage(Component.translatable("NO PASSING THE GATES OF ATLANTIS!!!"), true);
                return InteractionResult.FAIL;
            }
        } else {
            if (!worldIn.isClientSide) {
                ServerLevel atlantis =  worldIn.getServer().getLevel(DimensionAtlantis.ATLANTIS_WORLD);
                ServerLevel overWorld = worldIn.getServer().getLevel(Atlantis.getOverworldKey());
                if (worldIn != null) {
                    overWorld.getBlockState(pos);
                    BlockEntity entity = worldIn.getBlockEntity(pos);
                    DummyDataStorage dataStorage = (DummyDataStorage) entity;
                    BlockPos overWorldPos;
                    BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos(0, 0, 0);

                    if (dataStorage.getDestination() != null) {
                        Vec3 vector3d = new Vec3(dataStorage.getDestination().getX(), dataStorage.getDestination().getY(), dataStorage.getDestination().getZ());
                        sendPlayerToDimension((ServerPlayer) player, overWorld, vector3d);
                        player.displayClientMessage(Component.translatable("We Hope You Enjoyed Atlantis, Come Back Soon!"), true);
                        return InteractionResult.SUCCESS;
                    }

                    for (int y = 0; y < 255; y++) {
                        for (int x = (int) player.getX() - 6; x < (int) player.getX() + 6; x++) {
                            for (int z = (int) (player.getZ() - 6); z < (int) player.getZ() + 6; z++) {
                                mutableBlockPos.set(x, y, z);
                                if (overWorld.getBlockState(mutableBlockPos).getBlock() == this.asBlock() && isPortalAt(overWorld, mutableBlockPos)) {
                                    overWorldPos = mutableBlockPos.offset(0, 1, 0);
                                    dataStorage.setDestination(overWorldPos);
                                    sendPlayerToDimension((ServerPlayer) player, overWorld, new Vec3(overWorldPos.getX(), overWorldPos.getY(), overWorldPos.getZ()));
                                    player.displayClientMessage(Component.translatable("We Hope You Enjoyed Atlantis, Come Back Soon!"), true);
                                    return InteractionResult.SUCCESS;
                                } else {
                                    overWorld.setBlock(pos, this.asBlock().defaultBlockState(), 2);
                                    if (overWorld.getBlockState(mutableBlockPos).getBlock() == this.asBlock() && isPortalAt(overWorld, mutableBlockPos)) {
                                        overWorldPos = mutableBlockPos.offset(0, 1, 0);
                                        dataStorage.setDestination(overWorldPos);
                                        sendPlayerToDimension((ServerPlayer) player, overWorld, new Vec3(overWorldPos.getX(), overWorldPos.getY(), overWorldPos.getZ()));
                                        player.displayClientMessage(Component.translatable("We Hope You Enjoyed Atlantis, Come Back Soon!"), true);
                                        return InteractionResult.SUCCESS;
                                    }
                                }
                            }
                        }
                    }
                    worldIn.setBlock(pos, this.asBlock().defaultBlockState(), 2);
                } else {
                    player.displayClientMessage(Component.translatable("NO PASSING THE GATES OF ATLANTIS!!!"), true);
                    return InteractionResult.FAIL;
                }
            }
        }
        player.displayClientMessage(Component.translatable("NO PASSING THE GATES OF ATLANTIS!!!"), true);
        return InteractionResult.PASS;
    }

    private boolean isPortalAt(ServerLevel world, BlockPos pos) {
        return world.getBlockState(pos).is(getPortal());
    }

    public HolderSet<Block> getPortal(){
        Holder<Block> airHolderSet = Holder.direct(BlockInit.ATLANTIS_PORTAL.get());
        return HolderSet.direct(airHolderSet);
    }

    public static void sendPlayerToDimension(ServerPlayer serverPlayer, ServerLevel targetWorld, Vec3 targetVec) {
        // ensure destination chunk is loaded before we put the player in it
        targetWorld.getChunk(new BlockPos(targetVec));
        serverPlayer.teleportTo(targetWorld, targetVec.x(), targetVec.y(), targetVec.z(), serverPlayer.getYRot(), serverPlayer.getXRot());
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new DummyDataStorage(pos, state);
    }
}