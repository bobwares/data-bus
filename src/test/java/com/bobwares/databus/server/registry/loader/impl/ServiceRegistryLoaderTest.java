package com.bobwares.databus.server.registry.loader.impl;

import com.bobwares.core.bean.Registry;
import com.bobwares.databus.common.service.DataBusService;
import com.bobwares.databus.common.service.StoredProcedureService;
import com.bobwares.databus.server.registry.model.ServiceDefinition;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationContext;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class ServiceRegistryLoaderTest {

    private ServiceRegistryLoader serviceRegistryLoader;

    private List<ServiceDefinition> serviceDefinitions;

    private DataBusService bean1;

    private Registry<ServiceDefinition> serviceDefinitionRegistry;

    @Mock
    private ApplicationContext applicationContext;

    @Before
    public void setup() {
        serviceDefinitionRegistry = new Registry<>();
        serviceRegistryLoader = new ServiceRegistryLoader();
        MockitoAnnotations.initMocks(this);
        serviceRegistryLoader.setApplicationContext(applicationContext);
        serviceRegistryLoader.setServiceDefinitionRegistry(serviceDefinitionRegistry);
        bean1 = new StoredProcedureService();
    }

    @Test
    public void test() {
        when(applicationContext.getBean("storedProcedureService")).thenReturn(bean1);
        serviceRegistryLoader.load();
        assertNotNull(serviceDefinitionRegistry);
        assertTrue(serviceDefinitionRegistry.getCollection().size() == 2);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testBeanNotFound() {
        when(applicationContext.getBean("dataBusService")).thenReturn(null);
        serviceRegistryLoader.load();
        assertNotNull(serviceDefinitionRegistry);
        assertTrue(serviceDefinitionRegistry.getCollection().size() == 2);
    }

}
