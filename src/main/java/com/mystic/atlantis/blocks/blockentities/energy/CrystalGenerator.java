package com.mystic.atlantis.blocks.blockentities.energy;

import com.mystic.atlantis.blocks.power.energy.CrystalStorageBlock;
import com.mystic.atlantis.init.BlockInit;
import com.mystic.atlantis.init.TileEntityInit;
import com.mystic.atlantis.items.item.energybased.AtlanteanAmuletItem;
import com.mystic.atlantis.items.item.energybased.AtlanteanSpearItem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.mystic.atlantis.init.ItemInit;
import com.mystic.atlantis.inventory.CrystalGeneratorMenu;
import com.mystic.atlantis.networking.AtlantisPacketHandler;
import com.mystic.atlantis.networking.packets.clientbound.EnergySyncS2CPacket;
import com.mystic.atlantis.util.ModEnergyStorage;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.network.PacketDistributor;

public class CrystalGenerator extends BlockEntity implements MenuProvider {
    private static BlockPos pos;

    public CrystalGenerator(BlockPos arg, BlockState arg2) {
        super(TileEntityInit.CRYSTAL_GENERATOR.get(), arg, arg2);
        CrystalGenerator.pos = arg;
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> CrystalGenerator.this.progress;
                    case 1 -> CrystalGenerator.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> CrystalGenerator.this.progress = value;
                    case 1 -> CrystalGenerator.this.maxProgress = value;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    public static CrystalGenerator getCrystalGenerator() {
        return new CrystalGenerator(pos, BlockInit.CRYSTAL_GENERATOR.get().defaultBlockState());
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.literal("Crystal Generator");
    }

    public final ItemStackHandler itemHandler = new ItemStackHandler(2) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return switch (slot) {
                case 0 ->
                        stack.getItem() == ItemInit.ATLANTEAN_CRYSTAL.get() || stack.getItem() == BlockInit.CRYSTAL_STORAGE.get().asItem();
                case 1 ->
                        stack.getItem() == ItemInit.ATLANTEAN_AMULET.get() || stack.getItem() == ItemInit.ATLANTEAN_SPEAR.get() || stack.getItem() == BlockInit.CRYSTAL_STORAGE.get().asItem();
                default -> super.isItemValid(slot, stack);
            };
        }
    };

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 78;

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, @NotNull Inventory inventory, @NotNull Player player) {
        return new CrystalGeneratorMenu(id, inventory, this, this.data);
    }

    public final ModEnergyStorage ENERGY_STORAGE = new ModEnergyStorage(10000, 32, 32) {
        @Override
        public void onEnergyChanged() {
            setChanged();
            AtlantisPacketHandler.INSTANCE.send(PacketDistributor.ALL.noArg(), new EnergySyncS2CPacket(this.energy, getBlockPos()));
        }
    };

    public ModEnergyStorage getEnergyStorage() {
        return ENERGY_STORAGE;
    }

    public static final int ENERGY_REQ = 32;

    private LazyOptional<IEnergyStorage> LazyEnergyHandler = LazyOptional.empty();

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(net.minecraftforge.common.capabilities.@NotNull Capability<T> cap, @Nullable net.minecraft.core.Direction side) {
        if (cap == ForgeCapabilities.ENERGY) {
            return LazyEnergyHandler.cast();
        }

        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            if (side == null) {
                return lazyItemHandler.cast();
            }
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
        LazyEnergyHandler = LazyOptional.of(() -> ENERGY_STORAGE);
        setChanged();
    }

    @Override
    public void invalidateCaps() {
        lazyItemHandler.invalidate();
        LazyEnergyHandler.invalidate();
    }

    @Override
    public void saveAdditional(@NotNull CompoundTag nbt) {
        super.saveAdditional(nbt);
        nbt.put("inventory", itemHandler.serializeNBT());
        nbt.putInt("crystal_generator.progress", this.progress);
        nbt.putInt("crystal_generator.energy", ENERGY_STORAGE.getEnergyStored());
        setChanged();
    }

    @Override
    public void load(@NotNull CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
        progress = nbt.getInt("crystal_generator.progress");
        ENERGY_STORAGE.setEnergy(nbt.getInt("crystal_generator.energy"));
        setChanged();
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, CrystalGenerator blockEntity) {
        if (level.isClientSide()) {
            return;
        }

        if (blockEntity == null) {
            return;
        }

        if (hasCrystalInSlot(blockEntity) && !blockEntity.ENERGY_STORAGE.isFullyCharged()) {
            blockEntity.ENERGY_STORAGE.receiveEnergy(ENERGY_REQ, false);
            blockEntity.ENERGY_STORAGE.comesFromCrystal = true;
            if (blockEntity.ENERGY_STORAGE.receivedEnergyFromCrystal()) {
                blockEntity.itemHandler.getStackInSlot(0).shrink(1);
            }
        } else {
            blockEntity.ENERGY_STORAGE.comesFromCrystal = false;
        }

        if (!blockEntity.itemHandler.getStackInSlot(1).isEmpty()) {
            blockEntity.progress++;
            setChanged(level, pos, state);
            if (blockEntity.progress >= blockEntity.maxProgress && blockEntity.ENERGY_STORAGE.getEnergyStored() > 0) {
                if (blockEntity.itemHandler.getStackInSlot(1).getItem() == ItemInit.ATLANTEAN_SPEAR.get()) {
                    AtlanteanSpearItem.chargeItem(blockEntity.itemHandler.getStackInSlot(1), blockEntity);
                } else if (blockEntity.itemHandler.getStackInSlot(1).getItem() == ItemInit.ATLANTEAN_AMULET.get()) {
                    AtlanteanAmuletItem.chargeItem(blockEntity.itemHandler.getStackInSlot(1), blockEntity);
                }
            }
        } else {
            blockEntity.resetProgress();
            setChanged(level, pos, state);
        }
    }

    private static void extractEnergy(CrystalGenerator blockEntity) {
        blockEntity.ENERGY_STORAGE.extractEnergy(ENERGY_REQ, false);
    }

    private static boolean hasEnoughEnergy(CrystalGenerator blockEntity) {
        return blockEntity.ENERGY_STORAGE.getEnergyStored() >= ENERGY_REQ * blockEntity.maxProgress;
    }

    private void resetProgress() {
        this.progress = 0;
    }

    private static boolean canInsertItemIntoOutputSlot(SimpleContainer inventory, ItemStack stack) {
        return inventory.getItem(2).getItem() == stack.getItem() || inventory.getItem(2).isEmpty();
    }

    private static boolean canInsertAmountIntoOutputSlot(SimpleContainer inventory) {
        return inventory.getItem(2).getMaxStackSize() > inventory.getItem(2).getCount();
    }

    private static boolean hasCrystalInSlot(CrystalGenerator blockEntity) {
        return blockEntity.itemHandler.getStackInSlot(0).getItem() == ItemInit.ATLANTEAN_CRYSTAL.get();
    }

    private static boolean hasCrystalStorageInSlot(CrystalGenerator blockEntity) {
        return blockEntity.itemHandler.getStackInSlot(0).getItem() == BlockInit.CRYSTAL_STORAGE.get().asItem();
    }

    @Override
    public void setChanged() {
        getEnergyStorage().setEnergy(getEnergyStorage().getEnergyStored());

        super.setChanged();
    }
}
