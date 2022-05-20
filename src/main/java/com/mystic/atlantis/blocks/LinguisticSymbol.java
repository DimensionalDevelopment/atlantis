package com.mystic.atlantis.blocks;

public enum LinguisticSymbol {
    BLANK,A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z,ZERO,ONE,TWO,THREE,FOUR,FIVE,SIX,SEVEN,EIGHT,NINE;

    @Override
    public String toString() {
        return "_" + name().toLowerCase();
    }
}
