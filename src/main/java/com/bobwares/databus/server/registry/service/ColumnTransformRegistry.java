package com.bobwares.databus.server.registry.service;

import com.bobwares.core.bean.Registry;
import com.bobwares.databus.common.annotation.RegistryServiceComponent;
import com.bobwares.databus.server.registry.model.ColumnTransformerDefinition;

import javax.inject.Inject;
import java.util.*;

@RegistryServiceComponent
public class ColumnTransformRegistry implements RegistryService<ColumnTransformerDefinition> {

    private Registry<ColumnTransformerDefinition> columnTransformDefinitionRegistry;
    private  Map<String, ColumnTransformerDefinition> columnTransformerDefinitionMap;

    @Inject
    public void setColumnTransformDefinitionRegistry(Registry<ColumnTransformerDefinition> columnTransformDefinitionRegistry) {
        this.columnTransformDefinitionRegistry = columnTransformDefinitionRegistry;
    }

    @Override
	public ColumnTransformerDefinition getEntry(String key) {
        return getEntries().get(key);
    }

    @Override
	public Map<String, ColumnTransformerDefinition> getEntries() {
        if (columnTransformerDefinitionMap == null) {
        	columnTransformerDefinitionMap = buildConfigurationMap();
        }
        return columnTransformerDefinitionMap;
    }

    @Override
    public List<ColumnTransformerDefinition> getList() {
        return new ArrayList<>(getEntries().values());
    }

    protected Map<String, ColumnTransformerDefinition> buildConfigurationMap() {
        Map<String, ColumnTransformerDefinition> resourceMap = new HashMap<>();
        Collection<ColumnTransformerDefinition> columnTransformerDefinitions = columnTransformDefinitionRegistry.getCollection();
        for (ColumnTransformerDefinition columnTransformerDefinition : columnTransformerDefinitions) {
            resourceMap.put(columnTransformerDefinition.getColumnTransformerKey(), columnTransformerDefinition);
        }
        return resourceMap;
    }

}
