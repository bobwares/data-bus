package com.bobwares.databus.server.registry.service;

import com.bobwares.core.bean.Registry;
import com.bobwares.databus.common.model.Configuration;
import com.bobwares.databus.server.registry.model.ResourceDefinition;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertNull;

public class ResourceRegistryTest {

    public static final String RESOURCE_KEY_1 = "resourceKey_1";
    public static final String RESOURCE_KEY_2 = "resourceKey_2";
    public static final String RESOURCE_KEY_3 = "resourceKey_3";

    private ResourceRegistry resourceRegistry;

    @Before
    public void setup() {
        Configuration configuration = new Configuration();

        Registry<ResourceDefinition> registry = new Registry<>();
        registry.add(new ResourceDefinition(RESOURCE_KEY_1,configuration));
        registry.add(new ResourceDefinition(RESOURCE_KEY_2,configuration));
        resourceRegistry = new ResourceRegistry();
        resourceRegistry.setResourceDefinitionRegistry(registry);
    }

    @Test
    public void testGetEntry() {
        ResourceDefinition entry = resourceRegistry.getEntry(RESOURCE_KEY_1);
        assertTrue("Entry is resource 1", entry.getResourceUri().equalsIgnoreCase(RESOURCE_KEY_1));

        entry = resourceRegistry.getEntry(RESOURCE_KEY_2);
        assertTrue("Entry is resource 1", entry.getResourceUri().equalsIgnoreCase(RESOURCE_KEY_2));
    }

    @Test
    public void testGetEntries() {
        Map<String, ResourceDefinition> entries = resourceRegistry.getEntries();
        assertTrue("2 entries.", entries.size() == 2);
    }

    @Test
    public void testGetEntriesNotFound() {
        ResourceDefinition entry = resourceRegistry.getEntry(RESOURCE_KEY_3);
        assertNull("Entry is not null.", entry);
    }
}
