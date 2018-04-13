package com.bobwares.databus.server.registry.service;

import com.bobwares.core.bean.Registry;
import com.bobwares.databus.common.service.DataBusService;
import com.bobwares.databus.common.service.StoredProcedureService;
import com.bobwares.databus.server.registry.model.ServiceDefinition;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertNull;

public class ServiceRegistryTest {

    private static final String SERVICE_KEY_1 = "serviceKey_1";
    private static final String SERVICE_KEY_2 = "serviceKey_2";
    private static final String SERVICE_KEY_3 = "serviceKey_3";

    private ServiceRegistry serviceRegistry;

    @Before
    public void setup() {
        Registry<ServiceDefinition> registry = new Registry<>();
        registry.clear();
        DataBusService bean = new StoredProcedureService();
        registry.add(new ServiceDefinition(SERVICE_KEY_1,bean));
        registry.add(new ServiceDefinition(SERVICE_KEY_2,bean));
        serviceRegistry = new ServiceRegistry();
        serviceRegistry.setServiceDefinitionRegistry(registry);
    }

    @Test
    public void testGetEntry() {
        ServiceDefinition entry = serviceRegistry.getEntry(SERVICE_KEY_1);
        assertTrue("Entry is service 1", entry.getServiceKey().equalsIgnoreCase(SERVICE_KEY_1));

        entry = serviceRegistry.getEntry(SERVICE_KEY_2);
        assertTrue("Entry is service 1", entry.getServiceKey().equalsIgnoreCase(SERVICE_KEY_2));
    }
    @DirtiesContext(classMode= DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
    @Test
    public void testGetEntries() {
        List<ServiceDefinition> entries = serviceRegistry.getList();
        assertTrue("2 entries.", entries.size() == 2);
    }
    @DirtiesContext(classMode= DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
    @Test
    public void testGetEntriesNotFound() {
        ServiceDefinition entry = serviceRegistry.getEntry(SERVICE_KEY_3);
        assertNull("Entry is not null.", entry);
    }
    @DirtiesContext(classMode= DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
    @Test
    public void testGetList() {
        List<ServiceDefinition> list = serviceRegistry.getList();
        assertTrue("1 entries.", list.size() == 2);
    }
}
