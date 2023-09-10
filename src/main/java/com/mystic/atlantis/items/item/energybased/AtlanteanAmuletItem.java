package com.mystic.atlantis.items.item.energybased;

import artifacts.forge.curio.WearableArtifactCurio;
import artifacts.item.wearable.WearableArtifactItem;
import com.mystic.atlantis.blocks.blockentities.energy.CrystalGenerator;
import com.mystic.atlantis.capiablities.energy.AtlanteanCrystalEnergy;
import com.mystic.atlantis.capiablities.energy.AtlanteanCrystalEnergyCapability;
import com.mystic.atlantis.capiablities.energy.IAtlanteanCrystalEnergy;
import com.mystic.atlantis.items.DefaultItem;
import com.mystic.atlantis.util.ModEnergyStorage;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import top.theillusivec4.curios.common.capability.CurioItemCapability;
import top.theillusivec4.curios.common.inventory.CurioSlot;
import top.theillusivec4.curios.common.slottype.SlotType;

import java.util.List;

public class AtlanteanAmuletItem extends DefaultItem {
    public AtlanteanAmuletItem() {
        super(new Properties().stacksTo(1));
    }

    public static ModEnergyStorage getEnergyStorage(ItemStack stack) {
        return stack.getCapability(AtlanteanCrystalEnergyCapability.ATLANTEAN_CRYSTAL_ENERGY_CAPABILITY, null).resolve().get();
    }

    @Override
    public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new ICapabilitySerializable<CompoundTag>() {
            AtlanteanCrystalEnergy storage = new AtlanteanCrystalEnergy(5000, 20, 1);
            LazyOptional<ModEnergyStorage> opt = LazyOptional.of(() -> storage);

            @Override
            public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
                return AtlanteanCrystalEnergyCapability.ATLANTEAN_CRYSTAL_ENERGY_CAPABILITY.orEmpty(cap, opt);
            }

            @Override
            public CompoundTag serializeNBT() {
                CompoundTag tag = new CompoundTag();
                tag.put("energy", storage.serializeNBT());
                return tag;
            }

            @Override
            public void deserializeNBT(CompoundTag tag) {
                storage.deserializeNBT(tag.get("energy"));
            }
        };
    }

    public static void chargeItem(ItemStack stack, CrystalGenerator generator) {
        if (stack.getItem() instanceof AtlanteanAmuletItem) {
            if(!getEnergyStorage(stack).isFullyCharged()) {
                generator.ENERGY_STORAGE.extractEnergy(20, false);
                getEnergyStorage(stack).receiveEnergy(20, false);
            }
        }
    }

    public static void dischargeItem(ItemStack stack) {
        if (stack.getItem() instanceof AtlanteanAmuletItem) {
            getEnergyStorage(stack).extractEnergy(1, false);
        }
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
        return getEnergyStorage(stack).getEnergyStored() > 0;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        if (this.isAmuletCharged(stack)) {
            dischargeItem(stack);
            if(pEntity instanceof LivingEntity livingEntity) {
                livingEntity.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 20, 1, false, false));
            }
        } else {
            if(pEntity instanceof LivingEntity livingEntity) {
                livingEntity.removeEffect(MobEffects.FIRE_RESISTANCE);
            }
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public @Nullable CompoundTag getShareTag(ItemStack stack) {
        INBTSerializable<Tag> storage = getEnergyStorage(stack);
        CompoundTag tag = super.getShareTag(stack);
        if(tag == null) tag = new CompoundTag();
        tag.put("energy", storage.serializeNBT());
        return tag;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void readShareTag(ItemStack stack, @Nullable CompoundTag nbt) {
        if(nbt != null) {
            INBTSerializable<Tag> storage = getEnergyStorage(stack);
            storage.deserializeNBT(nbt.get("energy"));
            nbt.remove("energy");
        }
        super.readShareTag(stack, nbt);
    }
}