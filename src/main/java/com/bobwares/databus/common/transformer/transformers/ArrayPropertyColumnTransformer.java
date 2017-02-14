package com.bobwares.databus.common.transformer.transformers;

import com.bobwares.databus.common.annotation.ColumnTransformerComponent;
import com.bobwares.databus.common.model.ResourceConfiguration;
import com.bobwares.databus.common.model.Field;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@ColumnTransformerComponent
public class ArrayPropertyColumnTransformer implements ColumnTransformer {

    @Override
    public Map<String, Object> transform(ResourceConfiguration configuration, Map<String, Object> row) {
        List<String> keys = getArrayFieldKeys(configuration.getFields());
        if (keys.isEmpty()) {
            return row;
        }
        for (String key : keys) {
            row= process(key, row);
        }
        return row;
    }

    @Override
    public boolean needsTransforming(ResourceConfiguration configuration) {
        List<String> keys = getArrayFieldKeys(configuration.getFields());
        return keys.isEmpty() ? false : true;
    }

    protected Map<String, Object> process(String keyToMatch, Map<String, Object> results) {
        Map<String, Object> newResult = new LinkedHashMap<>();
        List<Object> newValueList = new ArrayList<>();

        for (Map.Entry<String,Object> entry : results.entrySet()) {
        	if (entry.getKey().contains(keyToMatch)) {
                newValueList.add(entry.getValue());
            }
            else {
                newResult.put(entry.getKey(), entry.getValue());
            }
        }
        if (!newValueList.isEmpty()) newResult.put(keyToMatch,newValueList.toArray());

        return newResult;
    }


    private List<String> getArrayFieldKeys(List<Field> fields) {
        List<String> keys = new ArrayList<>();
        for (Field field : fields) {
            if (field.getType().equalsIgnoreCase("array")) {
                keys.add(field.getProperty());
            }
        }
        return keys;
    }
}
