package com.bobwares.databus.server.config;

import com.bobwares.core.bean.Registry;
import com.bobwares.databus.server.registry.model.ResourceDefinition;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class ResourceConfigurationTest {

    @Test
    public void testResourceDefinitionRegistry() throws Exception {
        ResourceConfiguration resourceConfiguration = new ResourceConfiguration();
        Registry<ResourceDefinition> resourceDefinitionRegistry = resourceConfiguration.resourceDefinitionRegistry();
        assertNotNull(resourceDefinitionRegistry);
    }
}