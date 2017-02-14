package com.bobwares.databus.server.config;

import com.bobwares.core.bean.Registry;
import com.bobwares.databus.server.registry.model.ResourceDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ResourceConfiguration {

    final Logger logger = LoggerFactory.getLogger(getClass());

    public ResourceConfiguration() {
        logger.info("Start ResourceConfiguration");
    }

    @Bean
    public Registry<ResourceDefinition> resourceDefinitionRegistry() {
        return new Registry<ResourceDefinition>();
    }

}
