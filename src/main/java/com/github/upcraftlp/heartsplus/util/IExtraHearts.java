package com.github.upcraftlp.heartsplus.util;

/**
 * @author UpcraftLP
 */
public interface IExtraHearts {

    void addRedHearts(float toAdd);

    default void addRedHeart() {
        this.addRedHearts(1.0F);
    }

    float getRedHearts();

    float getBlackHearts();

    default void addBlackHeart() {
        this.addBlackHearts(1.0F);
    }

    void addBlackHearts(float toAdd);

    void setBlackHearts(float hearts);

    void setHearts(float red, float black, boolean white);

    boolean hasWhiteHeart();

    void setWhiteHeart(boolean whiteHeart);
}
