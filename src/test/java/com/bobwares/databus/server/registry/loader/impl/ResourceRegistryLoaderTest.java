package com.bobwares.databus.server.registry.loader.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.bobwares.core.bean.Registry;
import com.bobwares.core.util.MapBuilder;
import com.bobwares.databus.common.model.Configuration;
import com.bobwares.databus.common.util.ConfigurationLoader;
import com.bobwares.databus.server.config.DataBusConfig;
import com.bobwares.databus.server.registry.model.ResourceDefinition;

public class ResourceRegistryLoaderTest {

    @Mock ConfigurationLoader configurationLoader;
    @Mock DataBusConfig dataBusConfig;

    String validPath = "databus";

    Registry<ResourceDefinition> resourceDefinitionRegistry;
    ResourceRegistryLoader resourceRegistryLoader;

    @Before
    public void setup() {
        resourceDefinitionRegistry = new Registry<>();
        resourceRegistryLoader = new ResourceRegistryLoader();
        MockitoAnnotations.initMocks(this);

        resourceRegistryLoader.setConfigurationLoader(configurationLoader);
        resourceRegistryLoader.setResourceDefinitionRegistry(resourceDefinitionRegistry);
        resourceRegistryLoader.setDataBusConfig(dataBusConfig);
        when(dataBusConfig.getPathToResourceDefinitions()).thenReturn(validPath);
    }

    @Test
     public void load() {
        Configuration configuration = new Configuration();

        when(configurationLoader.loadConfigurations(validPath, Configuration.class)).thenReturn(MapBuilder.<String,Configuration>newHashMap()
    		.add("file1.json", configuration)
    		.build()
        );

        resourceRegistryLoader.load();

        assertNotNull(resourceDefinitionRegistry);
        assertTrue(resourceDefinitionRegistry.getCollection().size() == 1);
    }

    @Test
    public void load_no_files_found() {
    	when(configurationLoader.loadConfigurations(validPath, Configuration.class)).thenReturn(MapBuilder.<String,Configuration>newHashMap()
        	.build()
        );

        resourceRegistryLoader.load();

        assertNotNull(resourceDefinitionRegistry);
        assertTrue(resourceDefinitionRegistry.getCollection().size() == 0);
    }

}
