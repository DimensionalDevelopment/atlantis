package com.mystic.atlantis.items.item;

import com.mystic.atlantis.Atlantis;
import com.mystic.atlantis.blocks.LinguisticSymbol;
import com.mystic.atlantis.itemgroup.AtlantisGroup;
import net.minecraft.world.item.Item;

public class LinguisticSymbolItem extends Item {
    private final LinguisticSymbol symbol;

    public LinguisticSymbolItem(LinguisticSymbol symbol, Properties arg) {
        super(arg.tab(AtlantisGroup.INSTANCE));
        this.symbol = symbol;
    }

    public LinguisticSymbol getSymbol() {
        return symbol;
    }
}
