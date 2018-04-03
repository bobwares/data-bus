package com.bobwares.databus.common.renderer.pdf;

import com.google.common.base.Objects;

public class ColumnGroup {
    private final String name;
    private final float width;

    public ColumnGroup(String name, float width) {
        this.name = name;
        this.width = width;
    }

    public String getName() {
        return name;
    }

    public float getWidth() {
        return width;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ColumnGroup that = (ColumnGroup) o;
        return Objects.equal(width, that.width) &&
            Objects.equal(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, width);
    }

    @Override
    public String toString() {
        return "ColumnGroup{" +
            "name='" + name + '\'' +
            ", width=" + width +
            '}';
    }
}
