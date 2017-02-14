package com.bobwares.databus.server.registry.service;

import com.bobwares.core.bean.Registry;
import com.bobwares.databus.common.annotation.RegistryServiceComponent;
import com.bobwares.databus.server.registry.model.ResourceDefinition;

import javax.inject.Inject;
import java.util.*;

@RegistryServiceComponent
public class ResourceRegistry implements RegistryService<ResourceDefinition> {

    private Registry<ResourceDefinition> resourceDefinitionRegistry;
    private Map<String, ResourceDefinition> resourceDefinitionMap;

    @Inject
    public void setResourceDefinitionRegistry(Registry<ResourceDefinition> resourceDefinitionRegistry) {
        this.resourceDefinitionRegistry = resourceDefinitionRegistry;
    }

    @Override
    public ResourceDefinition getEntry(String key) {
        return getEntries().get(key.toLowerCase());
    }

    @Override
    public Map<String, ResourceDefinition> getEntries() {
        if (resourceDefinitionMap == null) {
        	resourceDefinitionMap = buildConfigurationMap();
        }
        return resourceDefinitionMap;
    }

    @Override
    public List<ResourceDefinition> getList() {
        return new ArrayList<>(getEntries().values());
    }

    private  Map<String, ResourceDefinition> buildConfigurationMap() {
        Map<String, ResourceDefinition> resourceMap = new HashMap<>();
        Collection<ResourceDefinition> resourceDefinitions = resourceDefinitionRegistry.getCollection();
        for (ResourceDefinition resourceDefinition : resourceDefinitions) {
            resourceMap.put(resourceDefinition.getResourceUri().toLowerCase(), resourceDefinition);
        }
        return resourceMap;
    }

}