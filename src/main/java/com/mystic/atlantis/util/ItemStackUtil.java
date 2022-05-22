package com.mystic.atlantis.util;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.function.Predicate;
import java.util.regex.Pattern;

public class ItemStackUtil {
    private static final Predicate<String> pattern = Pattern.compile("^[a-zA-Z0-9]{1}$").asMatchPredicate();
    public static boolean isGlyphNameTag(ItemStack tag) {
        if (tag.is(Items.NAME_TAG)) {
            String string = tag.getHoverName().getContents();

            return pattern.test(string);
        }
        return false;
    }
}
