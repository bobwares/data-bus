package com.bobwares.databus.server.registry.loader.impl;

import com.bobwares.core.bean.Registry;
import com.bobwares.databus.common.annotation.RegistryLoaderComponent;
import com.bobwares.databus.common.transformer.transformers.ColumnTransformer;
import com.bobwares.databus.server.registry.loader.RegistryLoader;
import com.bobwares.databus.server.registry.model.ColumnTransformerDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.inject.Inject;
import java.util.List;

@RegistryLoaderComponent
public class ColumnTransformRegistryLoader implements RegistryLoader<ColumnTransformer> {

    private ApplicationContext applicationContext;

    @Inject
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    private List<ColumnTransformerDefinition> columnTransformerDefinitions;

    @Autowired(required = false)
    public void setColumnTransformerDefinitions(List<ColumnTransformerDefinition> columnTransformerDefinitions) {
        this.columnTransformerDefinitions = columnTransformerDefinitions;
    }

    private Registry<ColumnTransformerDefinition> columnTransformerDefinitionRegistry;

    @Inject
    public void setColumnTransformerDefinitionRegistry(Registry<ColumnTransformerDefinition> columnTransformerDefinitionRegistry) {
        this.columnTransformerDefinitionRegistry = columnTransformerDefinitionRegistry;
    }




    @Override
    public void load() {
        columnTransformerDefinitionRegistry.clear();

        ColumnTransformer columnTransformer = (ColumnTransformer) applicationContext.getBean("arrayPropertyColumnTransformer");
        ColumnTransformerDefinition columnTransformerDefinition = new ColumnTransformerDefinition("arrayColumnTransformer", columnTransformer);
        columnTransformerDefinitionRegistry.add(columnTransformerDefinition);

        columnTransformer = (ColumnTransformer) applicationContext.getBean("propertyAliasColumnTransformer");
        columnTransformerDefinition = new ColumnTransformerDefinition("propertyAliasColumnTransformer", columnTransformer);
        columnTransformerDefinitionRegistry.add(columnTransformerDefinition);

        columnTransformer = (ColumnTransformer) applicationContext.getBean("qualifiedPropertyColumnTransformer");
        columnTransformerDefinition = new ColumnTransformerDefinition("qualifiedColumnTransformer", columnTransformer);
        columnTransformerDefinitionRegistry.add(columnTransformerDefinition);

        columnTransformerDefinitionRegistry.add(columnTransformerDefinitions);
    }
}
