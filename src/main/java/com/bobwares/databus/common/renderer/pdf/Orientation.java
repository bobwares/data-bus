package com.bobwares.databus.common.renderer.pdf;

public enum Orientation {
    Portrait,
    Landscape;

    public static Orientation lookup(String orientation) {
        assert (!("".equals(orientation) || orientation == null)) : "orientation cannot be null or empty";
        if ("portrait".equals(orientation.toLowerCase())) {
            return Portrait;
        }
        if ("landscape".equals(orientation.toLowerCase())) {
            return Landscape;
        }
        return null;
    }
}
