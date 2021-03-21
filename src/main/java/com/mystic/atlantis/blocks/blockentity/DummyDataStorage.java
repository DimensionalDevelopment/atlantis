package com.mystic.atlantis.blocks.blockentity;

import com.mystic.atlantis.init.TileEntityInit;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;

public class DummyDataStorage extends TileEntity {

    public DummyDataStorage(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    public DummyDataStorage() {
        this(TileEntityInit.DUMMY_DATA_STORAGE_TILE_ENTITY_TYPE.get());
    }

    public BlockPos destination;

    public void setDestination(BlockPos destination) {
        this.destination = destination;
    }

    @Override
    public CompoundNBT write(CompoundNBT nbt) {
        nbt = super.write(nbt);

        // Save the current value of the number to the nbt
        if(this.destination != null) {
            BlockPos blockPos = this.destination;
            nbt.putInt("destination_x", blockPos.getX());
            nbt.putInt("destination_y", blockPos.getY());
            nbt.putInt("destination_z", blockPos.getZ());
        }
        return nbt;
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        if(nbt.contains("destination_x")) {
            super.read(state, nbt);
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
