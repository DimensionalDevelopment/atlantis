package com.mystic.atlantis.blocks;

import com.mystic.atlantis.Atlantis;

import net.minecraft.resources.ResourceLocation;

public enum LinguisticGlyph {
    BLANK(""),
    A("a"),
    B("b"),
    C("c"),
    D("d"),
    E("e"),
    F("f"),
    G("g"),
    H("h"),
    I("i"),
    J("j"),
    K("k"),
    L("l"),
    M("m"),
    N("n"),
    O("o"),
    P("p"),
    Q("q"),
    R("r"),
    S("s"),
    T("t"),
    U("u"),
    V("v"),
    W("w"),
    X("x"),
    Y("y"),
    Z("z"),
    ZERO("0"),
    ONE("1"),
    TWO("2"),
    THREE("3"),
    FOUR("4"),
    FIVE("5"),
    SIX("6"),
    SEVEN("7"),
    EIGHT("8"),
    NINE("9");

    private final ResourceLocation texture;
    private final String properName;
    private final String id;

    LinguisticGlyph(String properName) {
        this.id = properName;
        this.properName = properName.isEmpty() ? "" : "_" + properName;
        texture = Atlantis.id("textures/block/blank/blank_side" + this.toString() + ".png");
    }

    public static LinguisticGlyph getFromStringChar(String name) {
        for(LinguisticGlyph glyph : values()) {
            if(glyph.id.equals(name)) return glyph;
        }

        return null;
    }

    @Override
    public String toString() {
        return properName;
    }

    public ResourceLocation getTexture() {
        return texture;
    }
}
