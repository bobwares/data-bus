package com.bobwares.databus.server.registry.service;

import com.bobwares.core.bean.Registry;
import com.bobwares.databus.common.annotation.RegistryServiceComponent;
import com.bobwares.databus.server.registry.model.ServiceDefinition;

import javax.inject.Inject;
import java.util.*;

@RegistryServiceComponent
public class ServiceRegistry implements RegistryService<ServiceDefinition> {

    private Registry<ServiceDefinition> serviceDefinitionRegistry;
    private Map<String, ServiceDefinition> serviceDefinitionMap;

    @Inject
    public void setServiceDefinitionRegistry(Registry<ServiceDefinition> serviceDefinitionRegistry) {
        this.serviceDefinitionRegistry = serviceDefinitionRegistry;
    }

    @Override
	public ServiceDefinition getEntry(String key) {
        return getEntries().get(key);
    }

    @Override
	public Map<String, ServiceDefinition> getEntries() {
        if (serviceDefinitionMap == null) {
        	serviceDefinitionMap = buildConfigurationMap();
        }
        return serviceDefinitionMap;
    }

    @Override
    public List<ServiceDefinition> getList() {
        return new ArrayList<>(getEntries().values());
    }

    protected   Map<String, ServiceDefinition> buildConfigurationMap() {
        Map<String, ServiceDefinition> itemMap = new HashMap<>();
        Collection<ServiceDefinition> items = serviceDefinitionRegistry.getCollection();
        for (ServiceDefinition item : items) {
            itemMap.put(item.getServiceKey(), item);
        }
        return itemMap;
    }

}
