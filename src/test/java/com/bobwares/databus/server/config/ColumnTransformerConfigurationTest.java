package com.bobwares.databus.server.config;

import com.bobwares.core.bean.Registry;
import com.bobwares.databus.server.registry.model.ColumnTransformerDefinition;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class ColumnTransformerConfigurationTest {

    @Test
    public void testColumnTransformerRegistry() throws Exception {
        ColumnTransformRegistryConfiguration columnTransformerConfiguration = new ColumnTransformRegistryConfiguration();
        Registry<ColumnTransformerDefinition> columnTransformerDefinitionRegistry = columnTransformerConfiguration.columnTransformDefinitionRegistry();
        assertNotNull(columnTransformerDefinitionRegistry);
    }
}