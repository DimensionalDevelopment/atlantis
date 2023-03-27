package com.mystic.atlantis.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.ItemCombinerMenu;

@Mixin(ItemCombinerMenu.class)
public abstract class ItemCombinerMenuMixin {
    @Shadow @Final protected Container inputSlots;
}
