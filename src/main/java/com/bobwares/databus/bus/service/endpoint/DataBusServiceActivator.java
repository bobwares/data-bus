package com.bobwares.databus.bus.service.endpoint;

import com.bobwares.databus.bus.model.ResourceMessage;
import com.bobwares.databus.common.model.ResponseObject;
import com.bobwares.databus.server.service.request.RequestProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class DataBusServiceActivator {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private RequestProcessor resourceBusRequestProcessor;

    @Inject
    public void setResourceBusRequestProcessor(RequestProcessor resourceBusRequestProcessor) {
        this.resourceBusRequestProcessor = resourceBusRequestProcessor;
    }


    @ServiceActivator(inputChannel="serviceChannel", outputChannel = "aggregatorChannel", requiresReply = "true")
    public ResourceMessage getRecords(ResourceMessage resourceMessage) {

        try{
            ResponseObject responseObject = resourceBusRequestProcessor.processRequest(resourceMessage.getRequestObject());
            resourceMessage.setResponseObject(responseObject);
        } catch (Exception e) {
            resourceMessage.setErrorMessage(e.getMessage());
            logger.error(e.getMessage());
        }
        return resourceMessage;
    }
}


