package com.mystic.atlantis.util;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Unique;

import java.util.function.Predicate;
import java.util.regex.Pattern;

public class ItemStackUtil {
    private static Predicate<String> pattern = Pattern.compile("^[a-zA-Z0-9]{1}$").asMatchPredicate();
    public static boolean isGlyphNameTag(ItemStack tag) {
        return tag.is(Items.NAME_TAG) && pattern.test(tag.getDisplayName().plainCopy().getString());
    }
}
