package com.mystic.atlantis.blocks;

public enum LinguisticGlyph {
    BLANK,
    A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z,
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

    private String properName;

    LinguisticGlyph(String properName) {
        this.properName = "_" + properName;
    }

    LinguisticGlyph() {
        properName = "_" + name().toLowerCase();
    }

    @Override
    public String toString() {
        return this != BLANK ? properName : "";
    }
}
