package com.mystic.atlantis.items;

import com.mystic.atlantis.blocks.base.LinguisticGlyph;
import net.minecraft.world.item.Item;

public class LinguisticGlyphScrollItem extends Item {
    private final LinguisticGlyph symbol;

    public LinguisticGlyphScrollItem(LinguisticGlyph symbol) {
        super(new Properties());
        this.symbol = symbol;
    }

    public LinguisticGlyph getSymbol() {
        return symbol;
    }
}
