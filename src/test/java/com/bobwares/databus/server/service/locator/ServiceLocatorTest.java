package com.bobwares.databus.server.service.locator;

import com.bobwares.databus.common.service.DataBusService;
import com.bobwares.databus.server.registry.model.ServiceDefinition;
import com.bobwares.databus.server.registry.service.RegistryService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

public class ServiceLocatorTest {

    public static final String SERVICE_KEY_1 = "serviceKey_1";

    @Mock
    RegistryService<ServiceDefinition> serviceRegistry;

    @Mock
    DataBusService dataBusService;
    
    private ServiceLocator serviceLocator;



    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ServiceLocatorImpl serviceLocator= new ServiceLocatorImpl();
        serviceLocator.setServiceRegistry(serviceRegistry);
        this.serviceLocator = serviceLocator;
    }

    @Test
    public void testForValidServiceDefinition() {
        ServiceDefinition serviceDefinition = new ServiceDefinition(SERVICE_KEY_1, dataBusService);
        when(serviceRegistry.getEntry(SERVICE_KEY_1)).thenReturn(serviceDefinition);
        DataBusService service = serviceLocator.getService(SERVICE_KEY_1);
        assertNotNull(service);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testForInvalidServiceDefinition() {
        when(serviceRegistry.getEntry(SERVICE_KEY_1)).thenReturn(null);
        serviceLocator.getService(SERVICE_KEY_1);
    }
}
