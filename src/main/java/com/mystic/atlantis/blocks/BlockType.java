package com.mystic.atlantis.blocks;

import com.google.common.base.Suppliers;
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
        MAP.computeIfAbsent(blockType, blockType1 -> Suppliers.memoize(() -> BlockType.family(blockType1)));
        return blockType;
    }

    public static BlockFamily family(BlockType type) {
        var family = new BlockFamily.Builder(type.block.get());
        if(type.slab != null) family.slab(type.slab.get());
        if(type.door != null) family.door(type.door.get());
        if(type.trapDoor != null) family.trapdoor(type.trapDoor.get());
        if(type.pressurePlate != null) family.pressurePlate(type.pressurePlate.get());
        if(type.button != null) family.button(type.button.get());
        if(type.wall != null) family.wall(type.wall.get());
        if(type.fence != null) family.fence(type.fence.get());
        if(type.fenceGate != null) family.fence(type.fenceGate.get());

        return family.getFamily();
    }
}
