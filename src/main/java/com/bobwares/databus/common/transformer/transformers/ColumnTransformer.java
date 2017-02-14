package com.bobwares.databus.common.transformer.transformers;

import com.bobwares.databus.common.model.ResourceConfiguration;

import java.util.Map;

public interface ColumnTransformer {

    Map<String, Object> transform(ResourceConfiguration configuration, Map<String, Object> row);

    boolean needsTransforming(ResourceConfiguration configuration);
}
