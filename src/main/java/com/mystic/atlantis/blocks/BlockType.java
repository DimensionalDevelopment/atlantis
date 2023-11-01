package com.mystic.atlantis.blocks;

import com.google.common.collect.Maps;
import net.minecraft.data.BlockFamily;
import net.minecraft.world.level.block.*;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Stream;

public record BlockType(RegistryObject<Block> block, RegistryObject<SlabBlock> slab, RegistryObject<WallBlock> wall, RegistryObject<FenceBlock> fence, RegistryObject<FenceGateBlock> fenceGate, RegistryObject<DoorBlock> door, RegistryObject<TrapDoorBlock> trapDoor, RegistryObject<ButtonBlock> button, RegistryObject<PressurePlateBlock> pressurePlate) {
    private static final Map<BlockType, Supplier<BlockFamily>> MAP = Maps.newHashMap();

    public static @NotNull Stream<BlockFamily> getAllFamilies() {return MAP.values().stream().map(Supplier::get);}

    public static BlockType of(RegistryObject<Block> blockBase, RegistryObject<SlabBlock> blockSlab, RegistryObject<WallBlock> blockWall, RegistryObject<FenceBlock> blockFence, RegistryObject<FenceGateBlock> blockGateBlock, RegistryObject<DoorBlock> blockDoor, RegistryObject<TrapDoorBlock> blockTrapDoor, RegistryObject<ButtonBlock> blockButton, RegistryObject<PressurePlateBlock> pressurePlate) {
        var blockType = new BlockType(blockBase, blockSlab, blockWall, blockFence, blockGateBlock, blockDoor, blockTrapDoor, blockButton, pressurePlate);
        MAP.computeIfAbsent(blockType, (blockType1) -> () -> BlockType.family(blockType1));
        return blockType;
    }

    public static BlockFamily family(BlockType type) {
        var family = new BlockFamily.Builder(type.block.get()).slab(type.slab.get()).door(type.door.get()).trapdoor(type.trapDoor.get()).pressurePlate(type.pressurePlate.get()).pressurePlate(type.button.get());
        if(type.wall != null) family.wall(type.wall.get());
        if(type.fence != null) family.fence(type.fence.get());
        if(type.fenceGate != null) family.fence(type.fenceGate.get());

        return family.getFamily();
    }
}
