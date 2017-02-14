package com.bobwares.databus.server.registry.loader.impl;

import com.bobwares.core.bean.Registry;

import com.bobwares.databus.common.annotation.RegistryLoaderComponent;
import com.bobwares.databus.common.model.fieldtype.*;
import com.bobwares.databus.server.registry.loader.RegistryLoader;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;

@RegistryLoaderComponent
public class FieldTypeRegistryLoader implements RegistryLoader<FieldTypeDefinition> {


    private Collection<FieldTypeDefinition> fieldTypeDefinitions;
    @Autowired(required = false)
    public void setFieldTypeDefinitions(Collection<FieldTypeDefinition> fieldTypeDefinitions) {
        this.fieldTypeDefinitions = fieldTypeDefinitions;
    }

    private Registry<FieldTypeDefinition> fieldTypeDefinitionRegistry;

    @Inject
    public void setFieldTypeDefinitionRegistry(Registry<FieldTypeDefinition> fieldTypeDefinitionRegistry) {
        this.fieldTypeDefinitionRegistry = fieldTypeDefinitionRegistry;
    }

    @Override
    public void load() {
        fieldTypeDefinitionRegistry.clear();
        fieldTypeDefinitionRegistry
                .add(new SimpleField("autocomplete"))
                .add(new SimpleField("link"))
                .add(new SimpleField("select"))
                .add(new SimpleField("string"))

                .add(new NumberField("dollars"))
                .add(new NumberField("number"))
//
//                .add(new CurrencyField("money"))
//                .add(new CurrencyField("currency"))
//
//                .add(new DateField("datetime", "M/d/yyyy h:mm a"))
//                .add(new DateField("date", "M/d/yyyy"))
//
//                .add(new BooleanField("boolean"))
//                .add(new YearField("year"))
//                .add(new MonthYearField("monthyear"))
//                .add(new PhoneField("phone"))
//                .add(new PercentField("percent"))

                .add(fieldTypeDefinitions);
    }
}
