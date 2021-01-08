package com.mystic.atlantis.items.armor;

import com.mystic.atlantis.init.ItemInit;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.RegistryObject;

public class ItemArmorAtlantis extends ArmorItem {
    public ItemArmorAtlantis(ArmorMaterial materialIn, EquipmentSlot slot, Settings builderIn) {
        super(materialIn, slot, builderIn);
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {

        RegistryObject<ArmorItem> ATLANTEAN_HELMET = ItemInit.AQUAMARINE_HELMET;
        RegistryObject<ArmorItem> ATLANTEAN_CHESTPLATE = ItemInit.AQUAMARINE_CHESTPLATE;
        RegistryObject<ArmorItem> ATLANTEAN_LEGGINGS = ItemInit.AQUAMARINE_LEGGINGS;
        RegistryObject<ArmorItem> ATLANTEAN_BOOTS = ItemInit.AQUAMARINE_BOOTS;

        RegistryObject<ArmorItem> BROWN_WROUGHT_HELMET = ItemInit.BROWN_WROUGHT_HELMET;
        RegistryObject<ArmorItem> BROWN_WROUGHT_CHESTPLATE = ItemInit.BROWN_WROUGHT_CHESTPLATE;
        RegistryObject<ArmorItem> BROWN_WROUGHT_LEGGINGS = ItemInit.BROWN_WROUGHT_LEGGINGS;
        RegistryObject<ArmorItem> BROWN_WROUGHT_BOOTS = ItemInit.BROWN_WROUGHT_BOOTS;
    }
}
