package com.mystic.atlantis.capiablities.player;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class PlayerCapProvider implements ICapabilitySerializable<CompoundTag> {

    public static Capability<IPlayerCap> PLAYER_CAP = CapabilityManager.get(new CapabilityToken<>(){});
    private PlayerCap playerCap = null;
    private final LazyOptional<IPlayerCap> instance = LazyOptional.of(this::createPlayerCap);

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return cap == PLAYER_CAP ? instance.cast() : LazyOptional.empty();
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        return getCapability(cap, null);
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        if (instance.isPresent()) {
            instance.ifPresent(cap -> {
                tag.putInt("lightValue", cap.getLightValue());
                tag.putLong("blockPosLong", cap.getLong());
            });
        }
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        if (instance.isPresent()) {
            instance.ifPresent(cap -> {
                cap.setLightValue(nbt.getInt("lightValue"));
                cap.setLong(nbt.getLong("blockPosLong"));
            });
        }
    }

    @Nonnull
    private IPlayerCap createPlayerCap() {
        return playerCap == null ? new PlayerCap() : playerCap;
    }
}