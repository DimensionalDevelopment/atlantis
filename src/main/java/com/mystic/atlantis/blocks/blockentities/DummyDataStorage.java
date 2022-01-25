package com.mystic.atlantis.blocks.blockentities;

import com.mystic.atlantis.Atlantis;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

public class DummyDataStorage extends BlockEntity {

    public DummyDataStorage(BlockPos pos, BlockState state) {
        super(Atlantis.DUMMY_DATA_STORAGE, pos, state);
    }

    public BlockPos destination;

    public void setDestination(BlockPos destination) {
        this.destination = destination;
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);

        // Save the current value of the number to the tag
        if(this.destination != null) {
            BlockPos blockPos = this.destination;
            nbt.putInt("destination_x", blockPos.getX());
            nbt.putInt("destination_y", blockPos.getY());
            nbt.putInt("destination_z", blockPos.getZ());
        }
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        if(nbt.contains("destination_x")) {
            super.readNbt(nbt);
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
