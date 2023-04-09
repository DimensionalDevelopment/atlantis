package com.mystic.atlantis.blocks.blockentities;

import com.mystic.atlantis.init.TileEntityInit;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class DummyDataStorage extends BlockEntity {
	public BlockPos destinationPos;

    public DummyDataStorage(BlockPos targetPos, BlockState targetState) {
        super(TileEntityInit.DUMMY_DATA_STORAGE.get(), targetPos, targetState);
    }

    public void setDestination(BlockPos destinationPos) {
        this.destinationPos = destinationPos;
    }

    @Override
    public void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);

        // Save the current value of the number to the tag
        if(this.destinationPos != null) {
            BlockPos destination = this.destinationPos;
            nbt.putInt("destination_x", destination.getX());
            nbt.putInt("destination_y", destination.getY());
            nbt.putInt("destination_z", destination.getZ());
        }
    }

    @Override
    public void load(CompoundTag nbt) {
        if(nbt.contains("destination_x")) {
            super.load(nbt);
            int destination_x = nbt.getInt("destination_x");
            int destination_y = nbt.getInt("destination_y");
            int destination_z = nbt.getInt("destination_z");
            destinationPos = new BlockPos(destination_x, destination_y, destination_z);
        }
    }

    public BlockPos getDestination() {
        return destinationPos;
    }
}
