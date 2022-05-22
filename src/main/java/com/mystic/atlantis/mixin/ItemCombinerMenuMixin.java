package com.mystic.atlantis.mixin;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.ItemCombinerMenu;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ItemCombinerMenu.class)
public abstract class ItemCombinerMenuMixin {
    @Shadow @Final protected Container inputSlots;
}
