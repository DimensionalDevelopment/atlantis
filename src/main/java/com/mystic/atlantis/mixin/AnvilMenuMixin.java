package com.mystic.atlantis.mixin;

import com.mystic.atlantis.items.item.LinguisticGlyphScrollItem;
import com.mystic.atlantis.util.ItemStackUtil;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Predicate;
import java.util.regex.Pattern;

@Mixin(AnvilMenu.class)
public abstract class AnvilMenuMixin extends ItemCombinerMenuMixin {
    @Unique boolean isGlyph;

    @Unique ItemStack stack = ItemStack.EMPTY;

    @Inject(method = "Lnet/minecraft/world/inventory/AnvilMenu;onTake(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/item/ItemStack;)V", at = @At("HEAD"))
    public void checkIfGlyph(Player player, ItemStack stack, CallbackInfo callbackInfo) {
        isGlyph = this.inputSlots.getItem(0).getItem() instanceof LinguisticGlyphScrollItem;
    }

    @Inject(method = "onTake", at = @At(value = "INVOKE", target = "net/minecraft/world/Container.setItem(ILnet/minecraft/world/item/ItemStack;)V", ordinal = 3))
    public void checkBefore(Player player, ItemStack itemStack, CallbackInfo callbackInfo) {
        if(isGlyph) {
            ItemStack tag = this.inputSlots.getItem(1);

            if(ItemStackUtil.isGlyphNameTag(tag)) {
                this.stack = tag;
                return;
            }
        }

        stack = ItemStack.EMPTY;
    }

    @Inject(method = "onTake", at = @At(value = "INVOKE", target = "net/minecraft/world/Container.setItem(ILnet/minecraft/world/item/ItemStack;)V", ordinal = 3, shift = At.Shift.AFTER))
    public void checkAfter(Player player, ItemStack itemStack, CallbackInfo callbackInfo) {
        if(!stack.isEmpty()) {
            this.inputSlots.setItem(1, stack);
        }
    }
}
