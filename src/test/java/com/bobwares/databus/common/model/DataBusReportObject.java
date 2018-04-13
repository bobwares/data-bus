package com.bobwares.databus.common.model;

import com.bobwares.databus.server.registry.model.AuthorizationDefinition;
import com.bobwares.databus.server.registry.model.ResourceDefinition;
import com.bobwares.databus.server.registry.model.ServiceDefinition;

import java.util.Map;

public class DataBusReportObject {
    Map<String, ResourceDefinition> resources;
    Map<String, AuthorizationDefinition> authorizationDefinitions;
    Map<String, ServiceDefinition> serviceDefinitions;

    public Map<String, ResourceDefinition> getResources() {
        return resources;
    }

    public void setResources(Map<String, ResourceDefinition> resources) {
        this.resources = resources;
    }

    public Map<String, AuthorizationDefinition> getAuthorizationDefinitions() {
        return authorizationDefinitions;
    }

    public void setAuthorizationDefinitions(Map<String, AuthorizationDefinition> authorizationDefinitions) {
        this.authorizationDefinitions = authorizationDefinitions;
    }

    public Map<String, ServiceDefinition> getServiceDefinitions() {
        return serviceDefinitions;
    }

    public void setServiceDefinitions(Map<String, ServiceDefinition> serviceDefinitions) {
        this.serviceDefinitions = serviceDefinitions;
    }
}
