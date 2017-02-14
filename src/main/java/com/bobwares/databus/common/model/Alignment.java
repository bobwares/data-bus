package com.bobwares.databus.common.model;

import java.util.HashMap;
import java.util.Map;

public enum Alignment {
    LEFT("left"),
    CENTER("center"),
    RIGHT("right");

    final String value;
    private static final Map<String,Alignment> lookups = new HashMap<>();

    static {
        for (Alignment a: values()) {
            lookups.put(a.value, a);
        }
    }

    Alignment(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Alignment lookup(String value) {
        return lookups.get(value.toLowerCase());
    }

    @Override
    public String toString() {
        return value;
    }
}
