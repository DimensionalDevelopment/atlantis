package com.mystic.atlantis.items.item.energybased;

import com.mystic.atlantis.blocks.blockentities.energy.CrystalGenerator;
import com.mystic.atlantis.capiablities.energy.AtlanteanCrystalEnergy;
import com.mystic.atlantis.capiablities.energy.AtlanteanCrystalEnergyCapability;
import com.mystic.atlantis.items.item.DefaultItem;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class AtlanteanSpearItem extends DefaultItem implements ICapabilitySerializable<CompoundTag> {
    public AtlanteanSpearItem(Properties settings) {
        super (settings
                .stacksTo(1));
    }

    public static AtlanteanCrystalEnergy getEnergyStorage(ItemStack stack) {
        return stack.getCapability(AtlanteanCrystalEnergyCapability.ATLANTEAN_CRYSTAL_ENERGY_CAPABILITY, null).resolve().get();
    }

    @Override
    public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag tag) {
        return new ICapabilityProvider() {
            @Override
            public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
                if (cap == AtlanteanCrystalEnergyCapability.ATLANTEAN_CRYSTAL_ENERGY_CAPABILITY) {
                    return LazyOptional.of(() -> AtlanteanCrystalEnergyCapability.createEnergyCap(1000, stack)).cast();
                }

                return LazyOptional.empty();
            }
        };
    }

    public static void chargeItem(ItemStack stack, CrystalGenerator generator) {
        if (stack.getItem() instanceof AtlanteanSpearItem) {
            if (!getEnergyStorage(stack).isFullyCharged()) {
                generator.ENERGY_STORAGE.extractEnergy(32, false);
                getEnergyStorage(stack).receiveEnergy(32, false);
            }
        }
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> components, @NotNull TooltipFlag flag) {
        if(stack.getItem() instanceof AtlanteanSpearItem) {
            if (getEnergyStorage(stack).isFullyCharged()) {
                components.add(Component.literal("Fully Charged").withStyle(ChatFormatting.GREEN));
            } else {
                if (getEnergyStorage(stack).isFullyDepleted()) {
                    components.add(Component.literal("No Charge").withStyle(ChatFormatting.RED));
                } else {
                    components.add(Component.literal("ACE level: " + getEnergyStorage(stack).getEnergyStored() + "/" + getEnergyStorage(stack).getMaxEnergyStored()).withStyle(ChatFormatting.AQUA));
                }
            }
        }
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        if (getEnergyStorage(this.getDefaultInstance()).getEnergyStored() >= 32) {
            if(!level.isClientSide && level instanceof ServerLevel serverLevel) {
                LightningBolt lightningBoltEntity = EntityType.LIGHTNING_BOLT.create(serverLevel, new CompoundTag(), Component.empty(), player, getDistanceForLightningSummon(15, player), MobSpawnType.MOB_SUMMONED, false, false);
                if(lightningBoltEntity != null) {
                    getEnergyStorage(this.getDefaultInstance()).extractEnergy(32, false);
                    serverLevel.addFreshEntity(lightningBoltEntity);
                }
            }
        }
        return InteractionResultHolder.success(new ItemStack(this));
    }

    public static BlockPos getDistanceForLightningSummon(int range, @NotNull Player player) {
        int yaw = (int) player.getYRot() + 180;
        double facing = (yaw - 90) * (Math.PI / 180);
        return new BlockPos(player.getX() + (range * Math.cos(facing)), player.getY(), player.getZ() + (range * Math.sin(facing)));
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return Objects.requireNonNull(initCapabilities(this.getDefaultInstance(), null)).getCapability(cap, side);
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("energy", getEnergyStorage(this.getDefaultInstance()).getEnergyStored());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        getEnergyStorage(this.getDefaultInstance()).setEnergy(tag.getInt("energy"));
    }

    @Override
    public void verifyTagAfterLoad(@NotNull CompoundTag tag) {
        super.verifyTagAfterLoad(tag);
        if (tag.contains("energy")) {
            getEnergyStorage(this.getDefaultInstance()).setEnergy(tag.getInt("energy"));
        }
    }
}


