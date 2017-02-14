package com.bobwares.databus.bus.model;

import com.bobwares.databus.common.model.ResourceConfiguration;
import com.bobwares.databus.common.model.ResponseObject;
import com.bobwares.databus.server.registry.model.ResourceDefinition;
import com.bobwares.databus.server.service.request.RequestObject;

import java.util.Map;

public class ResourceMessage  {

    private RequestObject requestObject;
    private ResourceDefinition resourceDefinition;
    private String errorMessage;
    private ResponseObject responseObject;

    public ResourceMessage(RequestObject requestObject, ResourceDefinition resourceDefinition) {
        this.requestObject = requestObject;
        this.resourceDefinition = resourceDefinition;
    }

    public ResponseObject getResponseObject() {
        return responseObject;
    }

    public void setResponseObject(ResponseObject responseObject) {
        this.responseObject = responseObject;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Map<String, String> getFilters() {
        return requestObject.getFilters();
    }

    public RequestObject getRequestObject() {
        return requestObject;
    }

    public void setRequestObject(RequestObject requestObject) {
        this.requestObject = requestObject;
    }

    public ResourceDefinition getResourceDefinition() {
        return resourceDefinition;
    }

    public void setResourceDefinition(ResourceDefinition resourceDefinition) {
        this.resourceDefinition = resourceDefinition;
    }
    public ResourceConfiguration getResourceConfiguration() {
        return resourceDefinition.getResourceConfiguration();
    }

    public String getResourceUri() {
        return this.resourceDefinition.getResourceUri();
    }
}
