package com.bobwares.databus.common.transformer.transformers;

import com.bobwares.databus.common.annotation.ColumnTransformerComponent;
import com.bobwares.databus.common.model.ResourceConfiguration;
import com.bobwares.databus.common.model.Field;

import java.util.*;

@ColumnTransformerComponent
public class QualifiedPropertyColumnTransformer implements ColumnTransformer {


    @Override
    public Map<String, Object> transform(ResourceConfiguration configuration, Map<String, Object> row) {
        List<String> keys = getKeyFields(configuration.getFields());
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
        List<String> keys = getKeyFields(configuration.getFields());
        return keys.isEmpty() ? false : true;
    }

    protected Map<String, Object> process(String keyToMatch, Map<String, Object> results) {
        Map<String, Object> newResult = new LinkedHashMap<>();
        Map<String,Object> dataMap = new HashMap<>();

        for (Map.Entry<String,Object> entry : results.entrySet()) {
        	if (entry.getKey().startsWith(keyToMatch)) {
                String valueKey  = findValueKey(entry.getKey(), keyToMatch);
                dataMap.put(valueKey, entry.getValue());
            }
            else {
                newResult.put(entry.getKey(), entry.getValue());
            }
        }
        if (!dataMap.isEmpty()) newResult.put(keyToMatch,dataMap);

        return newResult;
    }

    private String findValueKey(String key, String keyToMatch) {
        int keyToMatchLength = keyToMatch.length();
        String suffix = key.substring(keyToMatchLength);
        return suffix.isEmpty() ? "data" : Character.toLowerCase(suffix.charAt(0)) + suffix.substring(1);
    }

    private List<String> getKeyFields(List<Field> fields) {
        List<String> keys = new ArrayList<>();
        for (Field field : fields) {
            if (field.isHasQualifiers()) {
                keys.add(field.getProperty());
            }
        }
        return keys;
    }
}
