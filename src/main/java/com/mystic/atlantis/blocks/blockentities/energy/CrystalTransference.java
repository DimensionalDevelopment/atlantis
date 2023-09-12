package com.mystic.atlantis.blocks.blockentities.energy;

import com.mystic.atlantis.blocks.power.energy.CrystalTransferenceBlock;
import com.mystic.atlantis.init.BlockInit;
import com.mystic.atlantis.init.TileEntityInit;
import com.mystic.atlantis.networking.AtlantisPacketHandler;
import com.mystic.atlantis.networking.packets.clientbound.EnergySyncS2CPacket;
import com.mystic.atlantis.util.ModEnergyStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RedStoneWireBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

import static com.mystic.atlantis.blocks.blockentities.energy.CrystalGenerator.ENERGY_REQ;

public class CrystalTransference extends BlockEntity {
    public CrystalTransference(BlockPos arg2, BlockState arg3) {
        super(TileEntityInit.CRYSTAL_TRANSFERENCE.get(), arg2, arg3);
    }

    public final ModEnergyStorage ENERGY_STORAGE = new ModEnergyStorage(300, 32, 5) {
        @Override
        public void onEnergyChanged() {
            setChanged();
            AtlantisPacketHandler.INSTANCE.send(PacketDistributor.ALL.noArg(), new EnergySyncS2CPacket(this.energy, getBlockPos()));
        }
    };

    private LazyOptional<IEnergyStorage> LazyEnergyHandler = LazyOptional.empty();

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(net.minecraftforge.common.capabilities.@NotNull Capability<T> cap, @Nullable net.minecraft.core.Direction side) {
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
        nbt.putInt("crystal_transference.ticklifetime", this.lifetimeTick);
        nbt.putInt("crystal_transference.energy", ENERGY_STORAGE.getEnergyStored());
    }

    @Override
    public void load(@NotNull CompoundTag nbt) {
        super.load(nbt);
        this.lifetimeTick = nbt.getInt("crystal_transference.ticklifetime");
        ENERGY_STORAGE.setEnergy(nbt.getInt("crystal_transference.energy"));
    }

    public int lifetimeTick = 0;

    public static void tick(Level level, BlockPos pos, BlockState state, CrystalTransference blockEntity2) {

        if (level.getBlockState(pos.above()).getBlock() == BlockInit.CRYSTAL_GENERATOR.get() && Objects.requireNonNull(level.getBlockEntity(pos.above())) instanceof CrystalGenerator blockEntity) {
            if (blockEntity.ENERGY_STORAGE.getEnergyStored() < blockEntity.ENERGY_STORAGE.getMaxEnergyStored()) {
                if (blockEntity.ENERGY_STORAGE.getEnergyStored() >= ENERGY_REQ) {
                    blockEntity.ENERGY_STORAGE.extractEnergy(ENERGY_REQ, false);
                    blockEntity2.ENERGY_STORAGE.receiveEnergy(ENERGY_REQ, false);
                }
            }
        } else if (level.getBlockState(pos.above()).getBlock() == BlockInit.CRYSTAL_STORAGE.get() && level.getBlockEntity(pos.above()) instanceof CrystalStorage blockEntity) {
            if (blockEntity.ENERGY_STORAGE.getEnergyStored() < blockEntity.ENERGY_STORAGE.getMaxEnergyStored()) {
                if (blockEntity.ENERGY_STORAGE.getEnergyStored() >= ENERGY_REQ) {
                    blockEntity.ENERGY_STORAGE.extractEnergy(ENERGY_REQ, false);
                    blockEntity2.ENERGY_STORAGE.receiveEnergy(ENERGY_REQ, false);
                }
            }
        }

        blockEntity2.lifetimeTick++;

        if (blockEntity2.lifetimeTick % 20 == 0) {
            blockEntity2.ENERGY_STORAGE.extractEnergy(5, false);
            for (Direction direction : Direction.values()) {
                level.updateNeighborsAt(pos, state.getBlock());
            }
        }
    }
}
