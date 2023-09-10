package com.mystic.atlantis.blocks.blockentities.energy;

import com.mystic.atlantis.init.BlockInit;
import com.mystic.atlantis.init.TileEntityInit;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.mystic.atlantis.networking.AtlantisPacketHandler;
import com.mystic.atlantis.networking.packets.clientbound.EnergySyncS2CPacket;
import com.mystic.atlantis.util.ModEnergyStorage;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.network.PacketDistributor;

import java.util.Objects;

import static com.mystic.atlantis.blocks.blockentities.energy.CrystalGenerator.ENERGY_REQ;

public class CrystalStorage extends BlockEntity {
    public CrystalStorage(BlockPos arg2, BlockState arg3) {
        super(TileEntityInit.CRYSTAL_STORAGE.get(), arg2, arg3);
    }

    public final ModEnergyStorage ENERGY_STORAGE = new ModEnergyStorage(25000, 32, 32) {
        @Override
        public void onEnergyChanged() {
            setChanged();
            AtlantisPacketHandler.INSTANCE.send(PacketDistributor.ALL.noArg(), new EnergySyncS2CPacket(this.energy, getBlockPos()));
        }
    };

    private LazyOptional<IEnergyStorage> LazyEnergyHandler = LazyOptional.empty();

    @Override
    public @NotNull <T>LazyOptional<T> getCapability(net.minecraftforge.common.capabilities.@NotNull Capability<T> cap, @Nullable net.minecraft.core.Direction side) {
        if (cap == ForgeCapabilities.ENERGY) {
            return LazyOptional.of(() -> ENERGY_STORAGE).cast();
        }
        return LazyEnergyHandler.cast();
    }

    @Override
    public void onLoad() {
        super.onLoad();
        LazyEnergyHandler = LazyOptional.of(() -> ENERGY_STORAGE);
    }

    @Override
    public void invalidateCaps() {
        LazyEnergyHandler.invalidate();
    }

    @Override
    public void saveAdditional(@NotNull CompoundTag nbt) {
        super.saveAdditional(nbt);
        nbt.putInt("crystal_storage.energy", ENERGY_STORAGE.getEnergyStored());
    }

    @Override
    public void load(@NotNull CompoundTag nbt) {
        super.load(nbt);
        ENERGY_STORAGE.setEnergy(nbt.getInt("crystal_storage.energy"));
    }

    public static void tick(Level level, BlockPos pos, BlockState state, CrystalStorage blockEntity2) {
        if (level.getBlockState(pos.above()).getBlock() == BlockInit.CRYSTAL_GENERATOR.get() && Objects.requireNonNull(level.getBlockEntity(pos.above())) instanceof CrystalGenerator blockEntity) {
            if (blockEntity.ENERGY_STORAGE.getEnergyStored() < blockEntity.ENERGY_STORAGE.getMaxEnergyStored()) {
                if (blockEntity.ENERGY_STORAGE.getEnergyStored() >= ENERGY_REQ) {
                    blockEntity.ENERGY_STORAGE.extractEnergy(ENERGY_REQ, false);
                    blockEntity2.ENERGY_STORAGE.receiveEnergy(ENERGY_REQ, false);
                }
            }
        } else if (level.getBlockState(pos.below()).getBlock() == BlockInit.CRYSTAL_GENERATOR.get() && level.getBlockEntity(pos.below()) instanceof CrystalGenerator blockEntity) {
            if (blockEntity2.ENERGY_STORAGE.getEnergyStored() < blockEntity2.ENERGY_STORAGE.getMaxEnergyStored()) {
                if (blockEntity2.ENERGY_STORAGE.getEnergyStored() >= ENERGY_REQ) {
                    blockEntity2.ENERGY_STORAGE.extractEnergy(ENERGY_REQ, false);
                    blockEntity.ENERGY_STORAGE.receiveEnergy(ENERGY_REQ, false);
                }
            }
        }
    }
}
