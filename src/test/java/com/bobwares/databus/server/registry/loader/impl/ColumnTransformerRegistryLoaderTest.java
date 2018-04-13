package com.bobwares.databus.server.registry.loader.impl;

import com.bobwares.core.bean.Registry;
import com.bobwares.databus.common.transformer.transformers.ArrayPropertyColumnTransformer;
import com.bobwares.databus.common.transformer.transformers.ColumnTransformer;
import com.bobwares.databus.server.registry.model.ColumnTransformerDefinition;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationContext;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

public class ColumnTransformerRegistryLoaderTest {

    @Mock
    private ApplicationContext applicationContext;

    private Registry<ColumnTransformerDefinition> columnTransformerDefinitionRegistry;

    private List<ColumnTransformerDefinition> columnTransformerDefinitions;

    private ColumnTransformRegistryLoader columnTransformerRegistryLoader;
    
    @Before
    public void setup() {
        columnTransformerDefinitionRegistry = new Registry<>();
        columnTransformerRegistryLoader = new ColumnTransformRegistryLoader();
        MockitoAnnotations.initMocks(this);
        columnTransformerRegistryLoader.setColumnTransformerDefinitionRegistry(columnTransformerDefinitionRegistry);
        columnTransformerRegistryLoader.setApplicationContext(applicationContext);
    }

    @Test
    public void test() {
        ColumnTransformer columnTransformer = new ArrayPropertyColumnTransformer();
        when(applicationContext.getBean("arrayPropertyColumnTransformer")).thenReturn(columnTransformer);

        columnTransformer = new ArrayPropertyColumnTransformer();
        when(applicationContext.getBean("propertyAliasColumnTransformer")).thenReturn(columnTransformer);

        columnTransformer = new ArrayPropertyColumnTransformer();
        when(applicationContext.getBean("qualifiedPropertyColumnTransformer")).thenReturn(columnTransformer);


        columnTransformerRegistryLoader.load();
        assertNotNull(columnTransformerDefinitionRegistry);
    }

}
