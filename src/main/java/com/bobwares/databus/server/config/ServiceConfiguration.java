package com.bobwares.databus.server.config;

import com.bobwares.core.bean.Registry;
import com.bobwares.databus.common.service.DataBusService;
import com.bobwares.databus.server.registry.model.ServiceDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;
import javax.inject.Named;

@Configuration
public class ServiceConfiguration {
    final Logger logger = LoggerFactory.getLogger(getClass());

    public ServiceConfiguration() {
        logger.info("Start Databus Service Service Configuration");
    }

    //@Inject ScheduledExecutorService taskExecutor;
    @Inject
    @Named("rest-gateway")
    DataBusService resourceBusService;



    @Bean
    public Registry<ServiceDefinition> serviceDefinitionRegistry() {
        return new Registry<ServiceDefinition>();
    }

    @Bean
    public ServiceDefinition restGatewayService() {
        return new ServiceDefinition("rest-gateway", resourceBusService);
    }

}
