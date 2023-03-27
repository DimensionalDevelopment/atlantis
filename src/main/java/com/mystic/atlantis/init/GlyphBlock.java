package com.mystic.atlantis.init;

import com.mystic.atlantis.blocks.LinguisticGlyph;

import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class GlyphBlock extends Block {
    private LinguisticGlyph glyph;
    private DyeColor dyeColor;

    public GlyphBlock(LinguisticGlyph glyph, DyeColor dyeColor, BlockBehaviour.Properties properties) {
        super(properties);
        this.glyph = glyph;
        this.dyeColor = dyeColor;
    }

    public LinguisticGlyph getGlyph() {
        return glyph;
    }

    public DyeColor getDyeColor() {
        return dyeColor;
    }
}
