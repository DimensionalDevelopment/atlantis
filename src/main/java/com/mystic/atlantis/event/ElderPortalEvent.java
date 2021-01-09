package com.mystic.atlantis.event;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.ElderGuardianEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

import com.mystic.atlantis.init.BlockInit;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerChunkEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

public class ElderPortalEvent {
    public static List<Location> deadElderGuardians = new ArrayList<>();

    static {
        ServerLifecycleEvents.SERVER_STOPPING.register(minecraftServer -> deadElderGuardians.clear());
        ServerChunkEvents.CHUNK_UNLOAD.register((serverWorld, worldChunk) -> {
            Box box = new Box(worldChunk.getPos().getStartX(), 0, worldChunk.getPos().getStartZ(), worldChunk.getPos().getEndX(), 256, worldChunk.getPos().getEndZ());

            deadElderGuardians.removeIf(location -> serverWorld.getRegistryKey().equals(location.key) && box.contains(location.pos));
        });
    }

    public static void checkEntity(LivingEntity livingEntity) {
        if (livingEntity instanceof ElderGuardianEntity) {
            Box box = livingEntity.getVisibilityBoundingBox().stretch(100, 100, 100);

            List<Location> locations = deadElderGuardians.stream()
                    .filter(a -> livingEntity.world.getRegistryKey().equals(a.key))
                    .filter(a -> box.contains(a.pos))
                    .collect(Collectors.toList());

            if(locations.size() >= 2) {
                deadElderGuardians.remove(locations.remove(0));
                deadElderGuardians.remove(locations.remove(0));

                World world = livingEntity.world;
                BlockPos pos = livingEntity.getBlockPos();
                world.getBlockState(pos).getBlock();
                world.setBlockState(pos, BlockInit.ATLANTIS_PORTAL.getDefaultState());
            } else {
                deadElderGuardians.add(new Location(livingEntity.world.getRegistryKey(), livingEntity.getPos()));
            }
        }
    }

    static class Location {
        public final RegistryKey<World> key;
        public final Vec3d pos;

        public Location(RegistryKey<World> key, Vec3d pos) {

            this.key = key;
            this.pos = pos;
        }
    }
}
