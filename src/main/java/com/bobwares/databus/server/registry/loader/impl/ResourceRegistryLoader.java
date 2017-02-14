package com.bobwares.databus.server.registry.loader.impl;

import com.bobwares.core.bean.Registry;
import com.bobwares.databus.common.annotation.RegistryLoaderComponent;
import com.bobwares.databus.common.model.ResourceConfiguration;
import com.bobwares.databus.common.util.ConfigurationLoader;
import com.bobwares.databus.server.config.DataBusConfig;
import com.bobwares.databus.server.registry.loader.RegistryLoader;
import com.bobwares.databus.server.registry.model.ResourceDefinition;
import com.bobwares.databus.server.service.locator.ResourceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.Map;


@RegistryLoaderComponent
public class ResourceRegistryLoader implements RegistryLoader<ResourceDefinition> {

    final Logger logger = LoggerFactory.getLogger(getClass());

    private DataBusConfig dataBusConfig;

    @Inject
    public void setDataBusConfig(DataBusConfig dataBusConfig) {
        this.dataBusConfig = dataBusConfig;
    }

    private ConfigurationLoader configurationLoader;

    @Inject
    public void setConfigurationLoader(ConfigurationLoader configurationLoader) {
        this.configurationLoader = configurationLoader;
    }

    private ResourceLocator resourceLocator;

    @Inject
    public void setResourceLocator(ResourceLocator resourceLocator) {
        this.resourceLocator = resourceLocator;
    }

    private Registry<ResourceDefinition> resourceDefinitionRegistry;


    @Inject
    public void setResourceDefinitionRegistry(Registry<ResourceDefinition> resourceDefinitionRegistry) {
        this.resourceDefinitionRegistry = resourceDefinitionRegistry;
    }

    @Override
    public void load() {
        resourceDefinitionRegistry.clear();

        Map<String,ResourceConfiguration> configurationMap = configurationLoader.loadConfigurations(dataBusConfig.getPathToResourceDefinitions(), ResourceConfiguration.class);
        for (Map.Entry<String, ResourceConfiguration> entry : configurationMap.entrySet()) {
        	ResourceDefinition resourceDefinition = new ResourceDefinition(entry.getKey(), entry.getValue());
            resourceDefinitionRegistry.add(resourceDefinition);
        }

        for (Object resource : resourceDefinitionRegistry.getCollection()) {
            logger.info("Resource - " + resource.toString());
        }
    }

}
