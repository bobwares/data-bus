package com.bobwares.databus.server.config;


import com.bobwares.core.bean.Registry;
import com.bobwares.databus.server.registry.model.ColumnTransformerDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ColumnTransformRegistryConfiguration {

    final Logger logger = LoggerFactory.getLogger(getClass());

    public ColumnTransformRegistryConfiguration() {
        logger.info("Start");
    }

    @Bean
    public Registry<ColumnTransformerDefinition> columnTransformDefinitionRegistry() {
        return new Registry<>();
    }
}
