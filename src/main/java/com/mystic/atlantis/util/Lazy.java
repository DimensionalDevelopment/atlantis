package com.mystic.atlantis.util;

import java.util.function.Supplier;

public class Lazy<T> implements Supplier<T> {
    private final Supplier<T> initializer;
    private T value;
    private boolean initialized = false;

    public Lazy(Supplier<T> initializer) {
        this.initializer = initializer;
    }

    @Override
    public synchronized T get() {
        if (!initialized) {
            initialized = true;
            value = initializer.get();
        } return value;
    }
}
