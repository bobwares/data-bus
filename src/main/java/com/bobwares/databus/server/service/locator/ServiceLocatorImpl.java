package com.bobwares.databus.server.service.locator;

import com.bobwares.databus.common.service.DataBusService;
import com.bobwares.databus.server.registry.model.ServiceDefinition;
import com.bobwares.databus.server.registry.service.RegistryService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.inject.Inject;

@Service
public class ServiceLocatorImpl implements ServiceLocator {

    RegistryService<ServiceDefinition> serviceRegistry;

    @Inject
    public void setServiceRegistry(RegistryService<ServiceDefinition> serviceRegistry) {
        this.serviceRegistry = serviceRegistry;
    }

    @Override
    public DataBusService getService(String serviceKey) {
        ServiceDefinition serviceDefinition = serviceRegistry.getEntry(serviceKey);
        Assert.notNull(serviceDefinition, "Could not retrieve service definition from registry for serviceKey " + serviceKey);
        return serviceDefinition.getDataBusService();
    }
}
