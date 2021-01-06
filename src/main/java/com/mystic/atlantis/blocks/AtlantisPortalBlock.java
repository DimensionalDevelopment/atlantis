package com.mystic.atlantis.blocks;

import com.mystic.atlantis.dimension.DimensionAtlantis;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.DimensionRenderInfo;
import net.minecraft.command.impl.TeleportCommand;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.Dimension;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.ServerWorldInfo;
import net.minecraftforge.common.util.ITeleporter;

public class AtlantisPortalBlock extends Block{

    public AtlantisPortalBlock(Properties properties) {
        super(properties);
    }

    @SuppressWarnings({"ConstantConditions", "NullableProblems"})
    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (worldIn.getDimensionKey() != DimensionAtlantis.ATLANTIS_WORLD_KEY) {
            if (!worldIn.isRemote) {
                ServerWorld world = worldIn.getServer().getWorld(DimensionAtlantis.ATLANTIS_WORLD_KEY);
                if (world != null) {
                    ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
                    Vector3d vector3d = new Vector3d(player.getPosX(), player.getPosY(), player.getPosZ());
                    sendPlayerToDimension((ServerPlayerEntity) player, world, vector3d);
                    player.sendStatusMessage(new TranslationTextComponent("Welcome To Atlantis!!!"), true);
                    return ActionResultType.SUCCESS;
                } else {
                    player.sendStatusMessage(new TranslationTextComponent("NO PASSING THE GATES OF ATLANTIS!!!"), true);
                    return ActionResultType.FAIL;
                }
            }
        } else {
            if (!worldIn.isRemote) {
                ServerWorld world = worldIn.getServer().getWorld(getOverworldKey());
                if (world != null) {
                    sendPlayerToDimension((ServerPlayerEntity) player, world, new Vector3d(player.getPosX(), player.getPosY(), player.getPosZ()));
                    player.sendStatusMessage(new TranslationTextComponent("We Hope You Enjoyed Atlantis, Come Back Soon!"), true);
                    return ActionResultType.SUCCESS;
                } else {
                    player.sendStatusMessage(new TranslationTextComponent("NO PASSING THE GATES OF ATLANTIS!!!"), true);
                    return ActionResultType.FAIL;
                }
            }
        }
        return ActionResultType.PASS;
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

}
