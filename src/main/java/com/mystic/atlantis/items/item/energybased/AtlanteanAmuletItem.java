package com.mystic.atlantis.items.item.energybased;

import com.mystic.atlantis.blocks.blockentities.energy.CrystalGenerator;
import com.mystic.atlantis.capiablities.energy.AtlanteanCrystalEnergy;
import com.mystic.atlantis.capiablities.energy.AtlanteanCrystalEnergyCapability;
import com.mystic.atlantis.items.item.DefaultItem;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.SlotTypePreset;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;
import java.util.Objects;

public class AtlanteanAmuletItem extends DefaultItem implements ICurioItem, ICapabilitySerializable<CompoundTag> {
    public AtlanteanAmuletItem(Properties settings) {
        super (settings
                .stacksTo(1));
    }

    public static AtlanteanCrystalEnergy getEnergyStorage(ItemStack stack) {
        return stack.getCapability(AtlanteanCrystalEnergyCapability.ATLANTEAN_CRYSTAL_ENERGY_CAPABILITY, null).resolve().get();
    }

    @Override
    public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new ICapabilityProvider() {
            @Override
            public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
                if (cap == AtlanteanCrystalEnergyCapability.ATLANTEAN_CRYSTAL_ENERGY_CAPABILITY) {
                    return LazyOptional.of(() -> AtlanteanCrystalEnergyCapability.createEnergyCap(500, stack)).cast();
                }

                return LazyOptional.empty();
            }
        };
    }

    public static void chargeItem(ItemStack stack, CrystalGenerator generator) {
        if (stack.getItem() instanceof AtlanteanAmuletItem item) {
            if(!getEnergyStorage(stack).isFullyCharged()) {
                generator.ENERGY_STORAGE.extractEnergy(32, false);
                getEnergyStorage(stack).receiveEnergy(32, false);
            }
        }
    }

    public static void dischargeItem(ItemStack stack) {
        if (stack.getItem() instanceof AtlanteanAmuletItem) {
            getEnergyStorage(stack).extractEnergy(5, false);
        }
    }

    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return isSlotTypePresetNecklace(slotContext);
    }

    private boolean isSlotTypePresetNecklace(SlotContext slotContext) {
        return SlotTypePreset.NECKLACE.getIdentifier().equals(slotContext.identifier());
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> components, @NotNull TooltipFlag flag) {
        if(stack.getItem() instanceof AtlanteanAmuletItem) {
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

    private boolean isAmuletCharged(ItemStack stack) {
        return getEnergyStorage(stack).getEnergyStored() == 50;
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if (this.isAmuletCharged(stack) && isSlotTypePresetNecklace(slotContext)) {
            dischargeItem(stack);
            slotContext.entity().addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 20, 1, false, false));
        } else {
            slotContext.entity().removeEffect(MobEffects.FIRE_RESISTANCE);
        }
    }

    @NotNull
    @Override
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
        return new ICurio.SoundInfo(SoundEvents.LODESTONE_PLACE, 1.0F, 5.0F);
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

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return Objects.requireNonNull(initCapabilities(this.getDefaultInstance(), null)).getCapability(cap, side);
    }
}
