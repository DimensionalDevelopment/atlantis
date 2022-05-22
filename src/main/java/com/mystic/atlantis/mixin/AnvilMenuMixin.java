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

    @Inject(method = "Lnet/minecraft/world/inventory/AnvilMenu;onTake(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/item/ItemStack;)V", at = @At("HEAD"))
    public void checkIfGlyph(Player player, ItemStack stack, CallbackInfo callbackInfo) {
        isGlyph = this.inputSlots.getItem(0).getItem() instanceof LinguisticGlyphScrollItem;
    }

    @Redirect(method = "Lnet/minecraft/world/inventory/AnvilMenu;onTake(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/item/ItemStack;)V", at = @At(value = "FIELD", target = "Lnet/minecraft/world/item/ItemStack;EMPTY:Lnet/minecraft/world/item/ItemStack;", ordinal = 3, opcode = Opcodes.H_GETSTATIC))
    public ItemStack check(ItemStack stack) {
        if(isGlyph) {
            ItemStack tag = this.inputSlots.getItem(1);

            if(ItemStackUtil.isGlyphNameTag(tag)) {
                return tag;
            }
        }

        return stack;
    }
}
