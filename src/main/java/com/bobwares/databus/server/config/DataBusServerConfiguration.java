package com.bobwares.databus.server.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataBusServerConfiguration {

    final Logger logger = LoggerFactory.getLogger(getClass());

    public DataBusServerConfiguration() {
        logger.info("Start DataBusServerConfiguration");
    }

    @Value("${databus.pathToResourceDefinitions:databus}")
    String pathToResourceDefinitions;

    @Value("${databus.pathToAuthorizationDefinitions:databus/config/authorization}")
    String pathToAuthorizationDefinitions;

    @Bean
    public DataBusConfig dataBusConfig() {
        return new DataBusConfig(pathToResourceDefinitions,pathToAuthorizationDefinitions);
    }

}
