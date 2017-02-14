package com.bobwares.databus.server.config;

import com.bobwares.core.bean.Registry;
import com.bobwares.databus.common.model.fieldtype.FieldTypeDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FieldTypeDefinitionRegistryConfiguration {

    final Logger logger = LoggerFactory.getLogger(getClass());

    public FieldTypeDefinitionRegistryConfiguration() {
        logger.info("Start");
    }



    @Bean
    public Registry<FieldTypeDefinition> fieldTypeDefinitionRegistry() {
        return new Registry<FieldTypeDefinition>();
    }
}
