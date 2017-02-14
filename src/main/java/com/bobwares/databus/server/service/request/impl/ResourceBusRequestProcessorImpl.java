package com.bobwares.databus.server.service.request.impl;

import com.bobwares.databus.common.model.ResponseObject;
import com.bobwares.databus.common.service.DataBusService;
import com.bobwares.databus.server.registry.model.ResourceDefinition;
import com.bobwares.databus.server.service.locator.ResourceLocator;
import com.bobwares.databus.server.service.locator.ServiceLocator;
import com.bobwares.databus.server.service.request.RequestObject;
import com.bobwares.databus.server.service.request.RequestProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service("resourceBusRequestProcessor")
public class ResourceBusRequestProcessorImpl implements RequestProcessor {

    final Logger logger = LoggerFactory.getLogger(getClass());

    private ResourceLocator resourceLocator;

    @Inject
    public void setResourceLocator(ResourceLocator resourceLocator) {
        this.resourceLocator = resourceLocator;
    }

    private ServiceLocator serviceLocator;

    @Inject
    public void setServiceLocator(ServiceLocator serviceLocator) {
        this.serviceLocator = serviceLocator;
    }

    public ResponseObject processRequest(RequestObject requestObject)  {

        ResourceDefinition resourceDefinition = resourceLocator.getResource(requestObject.getResourceUri());

        DataBusService dataBusService = serviceLocator.getService(resourceDefinition.getServiceKey());

        return dataBusService.getRecords(resourceDefinition, requestObject);
    }
}
