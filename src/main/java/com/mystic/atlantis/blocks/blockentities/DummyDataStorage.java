package com.mystic.atlantis.blocks.blockentities;

import com.mystic.atlantis.Main;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.BlockPos;

public class DummyDataStorage extends BlockEntity {

    public DummyDataStorage() {
        super(Main.DUMMY_DATA_STORAGE);
    }

    public BlockPos destination;

    public void setDestination(BlockPos destination) {
        this.destination = destination;
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        tag = super.toTag(tag);

        // Save the current value of the number to the tag
        if(this.destination != null) {
            BlockPos blockPos = this.destination;
            tag.putInt("destination_x", blockPos.getX());
            tag.putInt("destination_y", blockPos.getY());
            tag.putInt("destination_z", blockPos.getZ());
        }
        return tag;
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        if(tag.contains("destination_x")) {
            super.fromTag(state, tag);
            int destination_x = tag.getInt("destination_x");
            int destination_y = tag.getInt("destination_y");
            int destination_z = tag.getInt("destination_z");
            destination = new BlockPos(destination_x, destination_y, destination_z);
        }
    }

    public BlockPos getDestination() {
        return destination;
    }
}
