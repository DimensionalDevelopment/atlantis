package com.mystic.atlantis.blocks.blockentities;

import com.mystic.atlantis.blocks.blockentities.registry.TileRegistry;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class DummyDataStorage extends BlockEntity {

    public DummyDataStorage(BlockPos pos, BlockState state) {
        super(TileRegistry.DUMMY_DATA_STORAGE.get(), pos, state);
    }

    public BlockPos destination;

    public void setDestination(BlockPos destination) {
        this.destination = destination;
    }

    @Override
    public void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);

        // Save the current value of the number to the tag
        if(this.destination != null) {
            BlockPos blockPos = this.destination;
            nbt.putInt("destination_x", blockPos.getX());
            nbt.putInt("destination_y", blockPos.getY());
            nbt.putInt("destination_z", blockPos.getZ());
        }
    }

    @Override
    public void load(CompoundTag nbt) {
        if(nbt.contains("destination_x")) {
            super.load(nbt);
            int destination_x = nbt.getInt("destination_x");
            int destination_y = nbt.getInt("destination_y");
            int destination_z = nbt.getInt("destination_z");
            destination = new BlockPos(destination_x, destination_y, destination_z);
        }
    }

    public BlockPos getDestination() {
        return destination;
    }
}
