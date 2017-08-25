package com.bobwares.databus.common.model;

import java.util.List;

public class HeaderField {

    String label;
    List<Field> properties;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<Field> getProperties() {
        return properties;
    }

    public void setProperties(List<Field> properties) {
        this.properties = properties;
    }
}
