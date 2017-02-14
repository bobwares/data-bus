package com.bobwares.databus.common.model;

import java.util.List;


public class FieldMapping {
    String fieldMappingKey;
    List<Field> fields;

    public String getFieldMappingKey() {
        return fieldMappingKey;
    }

    public void setFieldMappingKey(String fieldMappingKey) {
        this.fieldMappingKey = fieldMappingKey;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }
}
