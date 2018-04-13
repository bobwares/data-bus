package com.bobwares.databus.server.config;

import com.bobwares.core.bean.Registry;
import com.bobwares.databus.server.registry.model.ServiceDefinition;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class ServiceConfigurationTest {

    @Test
    public void testServiceDefinitionRegistry() throws Exception {
        ServiceConfiguration serviceConfiguration = new ServiceConfiguration();
        Registry<ServiceDefinition> serviceDefinitionRegistry = serviceConfiguration.serviceDefinitionRegistry();
        assertNotNull(serviceDefinitionRegistry);
    }
}