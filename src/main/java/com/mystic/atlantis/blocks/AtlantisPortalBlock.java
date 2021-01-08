package com.mystic.atlantis.blocks;

import com.mystic.atlantis.dimension.DimensionAtlantis;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionOptions;

public class AtlantisPortalBlock extends Block{

    public AtlantisPortalBlock(Settings properties) {
        super(properties);
    }

    @SuppressWarnings({"ConstantConditions", "NullableProblems"})
    @Override
    public ActionResult onUse(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockHitResult hit) {
        if (worldIn.getRegistryKey() != DimensionAtlantis.ATLANTIS_WORLD_KEY) {
            if (!worldIn.isClient) {
                ServerWorld world = worldIn.getServer().getWorld(DimensionAtlantis.ATLANTIS_WORLD_KEY);
                if (world != null) {
                    ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
                    Vec3d vector3d = new Vec3d(player.getX(), player.getY(), player.getZ());
                    sendPlayerToDimension((ServerPlayerEntity) player, world, vector3d);
                    player.sendMessage(new TranslatableText("Welcome To Atlantis!!!"), true);
                    return ActionResult.SUCCESS;
                } else {
                    player.sendMessage(new TranslatableText("NO PASSING THE GATES OF ATLANTIS!!!"), true);
                    return ActionResult.FAIL;
                }
            }
        } else {
            if (!worldIn.isClient) {
                ServerWorld world = worldIn.getServer().getWorld(getOverworldKey());
                if (world != null) {
                    sendPlayerToDimension((ServerPlayerEntity) player, world, new Vec3d(player.getX(), player.getY(), player.getZ()));
                    player.sendMessage(new TranslatableText("We Hope You Enjoyed Atlantis, Come Back Soon!"), true);
                    return ActionResult.SUCCESS;
                } else {
                    player.sendMessage(new TranslatableText("NO PASSING THE GATES OF ATLANTIS!!!"), true);
                    return ActionResult.FAIL;
                }
            }
        }
        return ActionResult.PASS;
    }

    public static RegistryKey<World> getOverworldKey(){
        Identifier OVERWORLD_ID = DimensionOptions.OVERWORLD.getValue();
        return RegistryKey.of(Registry.DIMENSION, OVERWORLD_ID);
    }

    public static void sendPlayerToDimension(ServerPlayerEntity serverPlayer, ServerWorld targetWorld, Vec3d targetVec) {
        // ensure destination chunk is loaded before we put the player in it
        targetWorld.getChunk(new BlockPos(targetVec));
        serverPlayer.teleport(targetWorld, targetVec.getX(), targetVec.getY(), targetVec.getZ(), serverPlayer.yaw, serverPlayer.pitch);
    }

}
