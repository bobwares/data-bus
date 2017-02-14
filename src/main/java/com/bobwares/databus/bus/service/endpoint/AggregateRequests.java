package com.bobwares.databus.bus.service.endpoint;

import com.bobwares.databus.bus.model.ResourceMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.Aggregator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AggregateRequests {

    final Logger logger = LoggerFactory.getLogger(getClass());

    @Aggregator(inputChannel = "aggregatorChannel", outputChannel = "transformerChannel")
    public List<ResourceMessage> handle(List<ResourceMessage> resourceMessages) {
        return resourceMessages;
    }
}
