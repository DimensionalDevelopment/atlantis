package com.mystic.atlantis.items.item.energybased;

import com.mystic.atlantis.blocks.blockentities.energy.CrystalGenerator;
import com.mystic.atlantis.capiablities.energy.AtlanteanCrystalEnergy;
import com.mystic.atlantis.capiablities.energy.AtlanteanCrystalEnergyCapability;
import com.mystic.atlantis.capiablities.energy.IAtlanteanCrystalEnergy;
import com.mystic.atlantis.items.DefaultItem;
import com.mystic.atlantis.util.ModEnergyStorage;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class AtlanteanSpearItem extends DefaultItem {
    public AtlanteanSpearItem(Properties settings) {
        super(settings
                .stacksTo(1));
    }

    public static ModEnergyStorage getEnergyStorage(ItemStack stack) {
        return stack.getCapability(AtlanteanCrystalEnergyCapability.ATLANTEAN_CRYSTAL_ENERGY_CAPABILITY, null).resolve().get();
    }

    @Override
    public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new ICapabilitySerializable<CompoundTag>() {
            AtlanteanCrystalEnergy storage = new AtlanteanCrystalEnergy(2000, 20, 200);
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
        if (stack.getItem() instanceof AtlanteanSpearItem) {
            if (!getEnergyStorage(stack).isFullyCharged()) {
                generator.ENERGY_STORAGE.extractEnergy(32, false);
                getEnergyStorage(stack).receiveEnergy(32, false);
            }
        }
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> components, @NotNull TooltipFlag flag) {
        if (stack.getItem() instanceof AtlanteanSpearItem) {
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
    public @NotNull InteractionResult useOn(UseOnContext context) {
        ItemStack stack = Objects.requireNonNull(context.getPlayer()).getItemInHand(context.getHand());
        if (getEnergyStorage(stack).getEnergyStored() >= 200) {
            if (!context.getLevel().isClientSide && context.getLevel() instanceof ServerLevel serverLevel) {
                getEnergyStorage(stack).extractEnergy(200, false);
                LightningBolt lightningBoltEntity = EntityType.LIGHTNING_BOLT.create(serverLevel, null, null,
                        context.getClickedPos(), MobSpawnType.MOB_SUMMONED, false, false);
                assert lightningBoltEntity != null;
                serverLevel.addFreshEntity(lightningBoltEntity);
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    @SuppressWarnings("unchecked")
    public @Nullable CompoundTag getShareTag(ItemStack stack) {
        INBTSerializable<Tag> storage = getEnergyStorage(stack);
        CompoundTag tag = super.getShareTag(stack);
        if (tag == null) tag = new CompoundTag();
        tag.put("energy", storage.serializeNBT());
        return tag;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void readShareTag(ItemStack stack, @Nullable CompoundTag nbt) {
        if (nbt != null) {
            INBTSerializable<Tag> storage = getEnergyStorage(stack);
            storage.deserializeNBT(nbt.get("energy"));
            nbt.remove("energy");
        }
        super.readShareTag(stack, nbt);
    }
}


