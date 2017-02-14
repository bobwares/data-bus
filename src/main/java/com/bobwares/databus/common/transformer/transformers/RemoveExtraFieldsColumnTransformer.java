package com.bobwares.databus.common.transformer.transformers;

import com.bobwares.databus.common.model.ResourceConfiguration;
import com.bobwares.databus.common.model.Field;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.Map;

public class RemoveExtraFieldsColumnTransformer implements ColumnTransformer {

    @Override
    public Map<String, Object> transform(ResourceConfiguration configuration, Map<String, Object> row) {
        Map<String, Object> newRow = new LinkedHashMap<>();
        for (Field field : configuration.getFields()) {
            if (field.getPropertyAlias() != null) {
                newRow.put(field.getPropertyAlias(), row.get(field.getPropertyAlias()));
            } else {
                newRow.put(field.getProperty(),row.get(field.getProperty()));
            }
        }
        return newRow;
    }

    @Override
    public boolean needsTransforming(ResourceConfiguration configuration) {
        return true;
    }
}
