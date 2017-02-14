package com.bobwares.databus.server.registry.service;

import com.bobwares.core.bean.Registry;
import com.bobwares.databus.common.annotation.RegistryServiceComponent;
import com.bobwares.databus.common.model.fieldtype.FieldTypeDefinition;

import javax.inject.Inject;
import java.util.*;

@RegistryServiceComponent
public class FieldTypeRegistry implements RegistryService<FieldTypeDefinition> {

    private Registry<FieldTypeDefinition> fieldTypeDefinitionRegistry;
    private  Map<String, FieldTypeDefinition> fieldTypeDefinitionMap;

    @Inject
    public void setFieldTypeDefinitionRegistry(Registry<FieldTypeDefinition> fieldTypeDefinitionRegistry) {
        this.fieldTypeDefinitionRegistry = fieldTypeDefinitionRegistry;
    }

    @Override
	public FieldTypeDefinition getEntry(String key) {
        return getEntries().get(key.toLowerCase());
    }

    @Override
	public Map<String, FieldTypeDefinition> getEntries() {
        if (fieldTypeDefinitionMap == null) {
        	fieldTypeDefinitionMap = buildConfigurationMap();
        }
        return fieldTypeDefinitionMap;
    }

    @Override
    public List<FieldTypeDefinition> getList() {
        return new ArrayList<>(getEntries().values());
    }

    protected Map<String, FieldTypeDefinition> buildConfigurationMap() {
        Map<String, FieldTypeDefinition> resourceMap = new HashMap<>();
        Collection<FieldTypeDefinition> fieldTypes = fieldTypeDefinitionRegistry.getCollection();
        for (FieldTypeDefinition fieldType : fieldTypes) {
            resourceMap.put(fieldType.getName().toLowerCase(), fieldType);
        }
        return resourceMap;
    }

}
