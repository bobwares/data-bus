package com.bobwares.databus.server.config;


import com.bobwares.core.bean.Registry;
import com.bobwares.databus.server.registry.model.ContentRendererDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ContentRendererConfiguration {

    final Logger logger = LoggerFactory.getLogger(getClass());

    public ContentRendererConfiguration() {
        logger.info("Start");
    }

    @Bean
    public Registry<ContentRendererDefinition> contentRendererDefinitionRegistry() {
        return new Registry<ContentRendererDefinition>();
    }


}
