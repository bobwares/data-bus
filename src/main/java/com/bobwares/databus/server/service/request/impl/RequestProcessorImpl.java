package com.bobwares.databus.server.service.request.impl;

import com.bobwares.databus.common.rest.NotFoundException;
import com.bobwares.databus.common.model.ResponseObject;
import com.bobwares.databus.common.service.DataBusService;
import com.bobwares.databus.server.authorization.DataBusAuthorizationService;
import com.bobwares.databus.server.registry.model.ResourceDefinition;
import com.bobwares.databus.server.service.locator.ResourceLocator;
import com.bobwares.databus.server.service.locator.ServiceLocator;
import com.bobwares.databus.server.service.request.RequestObject;
import com.bobwares.databus.server.service.request.RequestProcessor;
import com.bobwares.databus.server.service.request.RequestSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service("requestProcessor")
public class RequestProcessorImpl implements RequestProcessor {

    final Logger logger = LoggerFactory.getLogger(getClass());

    private DataBusAuthorizationService dataBusAuthorizationService;

    @Inject
    public void setDataBusAuthorizationService(DataBusAuthorizationService dataBusAuthorizationService) {
        this.dataBusAuthorizationService = dataBusAuthorizationService;
    }

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

    @Override
	public ResponseObject processRequest(RequestObject requestObject) {
    	//get the resource definition
        ResourceDefinition resourceDefinition = resourceLocator.getResource(requestObject.getResourceUri());

        //check the scope

        if (requestObject.getRequestSource() == RequestSource.EXTERNAL) {
            if (resourceDefinition.getScope() != null && resourceDefinition.getScope().equalsIgnoreCase("private")) {
                throw new NotFoundException("resourceKey", "resourceKey not found: " + requestObject.getResourceUri());
            }
        }

        //check the authorization
        dataBusAuthorizationService.isAuthorized(resourceDefinition.getAuthKey());

        //get the service and return the data
        DataBusService dataBusService = serviceLocator.getService(resourceDefinition.getServiceKey());
        return dataBusService.getRecords(resourceDefinition, requestObject);
    }
}
