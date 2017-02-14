package com.bobwares.databus.bus.config;


import com.bobwares.databus.common.service.DataBusService;
import com.bobwares.databus.server.registry.model.ServiceDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.ExecutorChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.messaging.MessageChannel;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.ScheduledExecutorService;

@Configuration
@EnableIntegration
@IntegrationComponentScan("resourceConfiguration.databus.bus.service")
public class ResourceBusConfig {
    final Logger logger = LoggerFactory.getLogger(getClass());

    //@Inject ScheduledExecutorService taskExecutor;
    @Inject @Named("resource-bus") DataBusService resourceBusService;

    public ResourceBusConfig() {
        logger.info("Start");
    }

    @Bean
    public ServiceDefinition resourceBusService() {
    	return new ServiceDefinition("resource-bus", resourceBusService);
    }

    @Bean
    @Description("Entry to the messaging system through the gateway.")
    public MessageChannel requestChannel() {
        return new DirectChannel();
    }

}
