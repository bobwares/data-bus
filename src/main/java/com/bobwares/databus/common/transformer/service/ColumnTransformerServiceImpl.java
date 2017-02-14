package com.bobwares.databus.common.transformer.service;


import com.bobwares.databus.common.transformer.transformers.ColumnTransformer;
import com.bobwares.databus.common.transformer.transformers.RemoveExtraFieldsColumnTransformer;
import com.bobwares.databus.server.registry.model.ColumnTransformerDefinition;
import com.bobwares.databus.server.registry.model.ResourceDefinition;
import com.bobwares.databus.server.service.locator.ResourceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ColumnTransformerServiceImpl implements ColumnTransformerService {
	final Logger logger = LoggerFactory.getLogger(getClass());

    private ResourceLocator resourceLocator;

    @Inject
    public void setResourceLocator(ResourceLocator resourceLocator) {
        this.resourceLocator = resourceLocator;
    }

    @Override
    public List<Map<String, Object>> transform(String resourceKey, List<Map<String, Object>> rows)  {
        ResourceDefinition resourceDefinition = null;
        try {
            resourceDefinition = resourceLocator.getResource(resourceKey);
        } catch (Exception e) {
        	logger.error("could not find resource {}", resourceKey, e);
            return rows;
        }

        RemoveExtraFieldsColumnTransformer removeExtraFieldsColumnTransformer = new RemoveExtraFieldsColumnTransformer();
        List<Map<String, Object>> transformedItems = new ArrayList<>();

        for (Map<String, Object> row : rows) {
            if (resourceDefinition.getColumnTransformerDefinitions() != null) {
                for (ColumnTransformerDefinition columnTransformerDefinition : resourceDefinition.getColumnTransformerDefinitions()) {
                    ColumnTransformer columnTransformer = columnTransformerDefinition.getColumnTransformer();
                    Map<String, Object> results = columnTransformer.transform(resourceDefinition.getResourceConfiguration(), row);
                    row = results;
                }
            }
            Map<String, Object> transformedRow = removeExtraFieldsColumnTransformer.transform(resourceDefinition.getResourceConfiguration(), row);
            transformedItems.add(transformedRow);
        }
        return transformedItems;
    }

}