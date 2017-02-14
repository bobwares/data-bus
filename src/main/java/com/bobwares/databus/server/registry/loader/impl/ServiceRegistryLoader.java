package com.bobwares.databus.server.registry.loader.impl;

import com.bobwares.core.bean.Registry;
import com.bobwares.databus.common.annotation.RegistryLoaderComponent;
import com.bobwares.databus.common.service.DataBusService;
import com.bobwares.databus.server.registry.loader.RegistryLoader;
import com.bobwares.databus.server.registry.model.ServiceDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;

import javax.inject.Inject;
import java.util.List;

@RegistryLoaderComponent
public class ServiceRegistryLoader implements RegistryLoader<ServiceDefinition> {

    private ApplicationContext applicationContext;

    @Inject
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    private List<ServiceDefinition> serviceDefinitions;

    @Inject
    public void setServiceDefinitions(List<ServiceDefinition> serviceDefinitions) {
        this.serviceDefinitions = serviceDefinitions;
    }

    private Registry<ServiceDefinition> serviceDefinitionRegistry;

    @Inject
    public void setServiceDefinitionRegistry(Registry<ServiceDefinition> serviceDefinitionRegistry) {
        this.serviceDefinitionRegistry = serviceDefinitionRegistry;
    }

    @Override
    public void load() {
       serviceDefinitionRegistry.clear();

       //DataBusService bean = (DataBusService) applicationContext.getBean("storedProcedureService");
       //Assert.notNull(bean, "Could not retrieve dataBusService bean from the application context.");

       //ServiceDefinition serviceDefinition = new ServiceDefinition("storedProcedureService", bean);
       //serviceDefinitionRegistry.add(serviceDefinition);

       // add as default service
       //ServiceDefinition serviceDefinition = new ServiceDefinition("default", bean);
      // serviceDefinitionRegistry.add(serviceDefinition);

       // pick up other services definitions defined by the client as well as EnterpriseServiceBusService's definition.
        serviceDefinitionRegistry.add(serviceDefinitions);
   }
}
