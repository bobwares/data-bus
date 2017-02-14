package com.bobwares.databus.common.transformer.transformers;

import com.bobwares.databus.common.annotation.ColumnTransformerComponent;
import com.bobwares.databus.common.model.ResourceConfiguration;
import com.bobwares.databus.common.model.Field;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@ColumnTransformerComponent
public class PropertyAliasColumnTransformer implements ColumnTransformer{

    @Override
    public Map<String, Object> transform(ResourceConfiguration resourceConfiguration, Map<String, Object> row) {
        Map<String, String> aliasFields = findAliasFields(resourceConfiguration, row);
        Map<String, Object> newRow = new LinkedHashMap<>();

        for (Map.Entry<String,Object> entry : row.entrySet()) {
        	String aliasField = aliasFields.get(entry.getKey());
            newRow.put(aliasField != null ? aliasField : entry.getKey(), entry.getValue());
        }

        return newRow;
    }

    private Map<String,String> findAliasFields(ResourceConfiguration resourceConfiguration, Map<String, Object> row) {
        Map<String,String> aliasFields = new HashMap<>();

        for (Field field : resourceConfiguration.getFields()) {
            if (field.getPropertyAlias() != null) {
                aliasFields.put(field.getProperty(),field.getPropertyAlias());
            }
        }
        return aliasFields;
    }

    @Override
    public boolean needsTransforming(ResourceConfiguration resourceConfiguration) {
        for (Field field : resourceConfiguration.getFields()) {
            if (field.getPropertyAlias() != null && !field.getPropertyAlias().isEmpty()) {
                return true;
            }
        }
        return false;
    }
}
