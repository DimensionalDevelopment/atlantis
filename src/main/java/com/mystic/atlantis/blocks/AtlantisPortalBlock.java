package com.mystic.atlantis.blocks;

import com.mystic.atlantis.blocks.blockentity.DummyDataStorage;
import com.mystic.atlantis.dimension.DimensionAtlantis;
import com.mystic.atlantis.init.BlockInit;
import com.mystic.atlantis.init.TileEntityInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.DimensionRenderInfo;
import net.minecraft.command.impl.TeleportCommand;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tags.ITag;
import net.minecraft.tags.Tag;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.Dimension;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.ServerWorldInfo;
import net.minecraftforge.common.util.ITeleporter;

import java.util.ArrayList;
import java.util.List;

public class AtlantisPortalBlock extends Block{

    public AtlantisPortalBlock(Properties properties) {
        super(properties);
    }

    @SuppressWarnings({"ConstantConditions", "NullableProblems"})
    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (worldIn.getDimensionKey() != DimensionAtlantis.ATLANTIS_WORLD_KEY) {
            if (!worldIn.isRemote) {
                // From Overworld to Atlantis
                ServerWorld atlantis =  worldIn.getServer().getWorld(DimensionAtlantis.ATLANTIS_WORLD_KEY);
                ServerWorld overWorld = worldIn.getServer().getWorld(getOverworldKey());
                atlantis.getBlockState(pos);
                TileEntity entity = worldIn.getTileEntity(pos);
                DummyDataStorage dataStorage = (DummyDataStorage) entity;
                BlockPos atlantisPos;
                BlockPos.Mutable mutableBlockPos = new BlockPos.Mutable(0, 0, 0);

                if(dataStorage.getDestination() != null){
                    Vector3d vector3d = new Vector3d(dataStorage.getDestination().getX(), dataStorage.getDestination().getY(), dataStorage.getDestination().getZ());
                    sendPlayerToDimension((ServerPlayerEntity) player, atlantis, vector3d);
                    player.sendStatusMessage(new TranslationTextComponent("Welcome To Atlantis!!!"), true);
                    return ActionResultType.SUCCESS;
                }

                for (int y = 0; y < 255; y++) {
                    for (int x = (int) player.getPosX() - 6; x < (int) player.getPosX() + 6; x++) {
                        for (int z = (int) player.getPosZ() - 6; z < (int) player.getPosZ() + 6; z++) {
                            mutableBlockPos.setPos(x, y, z);
                            if (atlantis.getBlockState(mutableBlockPos).getBlock() == this && isPortalAt(atlantis, mutableBlockPos)) {
                                atlantisPos = mutableBlockPos.add(0, 1, 0);
                                dataStorage.setDestination(atlantisPos);
                                Vector3d vector3d = new Vector3d(atlantisPos.getX(), atlantisPos.getY(), atlantisPos.getZ());
                                sendPlayerToDimension((ServerPlayerEntity) player, atlantis, vector3d);
                                player.sendStatusMessage(new TranslationTextComponent("Welcome To Atlantis!!!"), true);
                                return ActionResultType.SUCCESS;
                            } else {
                                atlantis.setBlockState(pos, this.getDefaultState(), 2);
                                if(atlantis.getBlockState(mutableBlockPos).getBlock() == this && isPortalAt(atlantis, mutableBlockPos)) {
                                    atlantisPos = mutableBlockPos.add(0, 1, 0);
                                    dataStorage.setDestination(atlantisPos);
                                    Vector3d vector3d = new Vector3d(atlantisPos.getX(), atlantisPos.getY(), atlantisPos.getZ());
                                    sendPlayerToDimension((ServerPlayerEntity) player, atlantis, vector3d);
                                    player.sendStatusMessage(new TranslationTextComponent("Welcome To Atlantis!!!"), true);
                                    return ActionResultType.SUCCESS;
                                }
                            }
                        }
                    }
                }
                worldIn.setBlockState(pos, this.getDefaultState(), 2);
            } else {
                player.sendStatusMessage(new TranslationTextComponent("NO PASSING THE GATES OF ATLANTIS!!!"), true);
                return ActionResultType.FAIL;
            }
        } else {
            if (!worldIn.isRemote) {
                ServerWorld atlantis =  worldIn.getServer().getWorld(DimensionAtlantis.ATLANTIS_WORLD_KEY);
                ServerWorld overWorld = worldIn.getServer().getWorld(getOverworldKey());
                if (worldIn != null) {
                    overWorld.getBlockState(pos);
                    TileEntity entity = worldIn.getTileEntity(pos);
                    DummyDataStorage dataStorage = (DummyDataStorage) entity;
                    BlockPos overWorldPos;
                    BlockPos.Mutable mutableBlockPos = new BlockPos.Mutable(0, 0, 0);

                    if (dataStorage.getDestination() != null) {
                        Vector3d vector3d = new Vector3d(dataStorage.getDestination().getX(), dataStorage.getDestination().getY(), dataStorage.getDestination().getZ());
                        sendPlayerToDimension((ServerPlayerEntity) player, overWorld, vector3d);
                        player.sendStatusMessage(new TranslationTextComponent("We Hope You Enjoyed Atlantis, Come Back Soon!"), true);
                        return ActionResultType.SUCCESS;
                    }

                    for (int y = 0; y < 255; y++) {
                        for (int x = (int) player.getPosX() - 6; x < (int) player.getPosX() + 6; x++) {
                            for (int z = (int) (player.getPosZ() - 6); z < (int) player.getPosZ() + 6; z++) {
                                mutableBlockPos.setPos(x, y, z);
                                if (overWorld.getBlockState(mutableBlockPos).getBlock() == this && isPortalAt(overWorld, mutableBlockPos)) {
                                    overWorldPos = mutableBlockPos.add(0, 1, 0);
                                    dataStorage.setDestination(overWorldPos);
                                    sendPlayerToDimension((ServerPlayerEntity) player, overWorld, new Vector3d(overWorldPos.getX(), overWorldPos.getY(), overWorldPos.getZ()));
                                    player.sendStatusMessage(new TranslationTextComponent("We Hope You Enjoyed Atlantis, Come Back Soon!"), true);
                                    return ActionResultType.SUCCESS;
                                } else {
                                    overWorld.setBlockState(pos, this.getDefaultState(), 2);
                                    if(overWorld.getBlockState(mutableBlockPos).getBlock() == this && isPortalAt(overWorld, mutableBlockPos)) {
                                        overWorldPos = mutableBlockPos.add(0, 1, 0);
                                        dataStorage.setDestination(overWorldPos);
                                        sendPlayerToDimension((ServerPlayerEntity) player, overWorld, new Vector3d(overWorldPos.getX(), overWorldPos.getY(), overWorldPos.getZ()));
                                        player.sendStatusMessage(new TranslationTextComponent("We Hope You Enjoyed Atlantis, Come Back Soon!"), true);
                                        return ActionResultType.SUCCESS;
                                    }
                                }
                            }
                        }
                    }
                    worldIn.setBlockState(pos, this.getDefaultState(), 2);
                } else {
                    player.sendStatusMessage(new TranslationTextComponent("NO PASSING THE GATES OF ATLANTIS!!!"), true);
                    return ActionResultType.FAIL;
                }
            }
        }
        return ActionResultType.PASS;
    }

    private boolean isPortalAt(ServerWorld world, BlockPos pos) {
        return world.getBlockState(pos).isIn(getPortal());
    }

    public ITag<Block> getPortal(){
        ITag<Block> portal = new ITag<Block>() {
            @Override
            public boolean contains(Block element) {
                return true;
            }

            @Override
            public List<Block> getAllElements() {
                List<Block> portal2 = new ArrayList<Block>();
                portal2.add(BlockInit.ATLANTIS_PORTAL.get());
                return portal2;
            }
        };
        return portal;
    }

    public static RegistryKey<World> getOverworldKey(){
        ResourceLocation OVERWORLD_ID = Dimension.OVERWORLD.getLocation();
        return RegistryKey.getOrCreateKey(Registry.WORLD_KEY, OVERWORLD_ID);
    }

    public static void sendPlayerToDimension(ServerPlayerEntity serverPlayer, ServerWorld targetWorld, Vector3d targetVec) {
        // ensure destination chunk is loaded before we put the player in it
        targetWorld.getChunk(new BlockPos(targetVec));
        serverPlayer.teleport(targetWorld, targetVec.getX(), targetVec.getY(), targetVec.getZ(), serverPlayer.rotationYaw, serverPlayer.rotationPitch);
    }

    @Override
    public boolean hasTileEntity(BlockState state){
        return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world){
        return TileEntityInit.DUMMY_DATA_STORAGE_TILE_ENTITY_TYPE.get().create();
    }
}
