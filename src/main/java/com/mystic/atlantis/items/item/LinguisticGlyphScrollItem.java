package com.mystic.atlantis.items.item;

import com.mystic.atlantis.blocks.LinguisticGlyph;
import com.mystic.atlantis.itemgroup.AtlantisGroup;

import net.minecraft.world.item.Item;

public class LinguisticGlyphScrollItem extends Item {
    private final LinguisticGlyph symbol;

    public LinguisticGlyphScrollItem(LinguisticGlyph symbol) {
        super(new Properties().tab(AtlantisGroup.GLYPH));
        this.symbol = symbol;
    }

    public LinguisticGlyph getSymbol() {
        return symbol;
    }
}
