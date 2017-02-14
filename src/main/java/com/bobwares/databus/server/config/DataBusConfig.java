package com.bobwares.databus.server.config;

public class DataBusConfig {
    final String pathToResourceDefinitions;
    final String pathToAuthorizationDefinitions;

    public DataBusConfig(String pathToResourceDefinitions, String pathToAuthorizationDefinitions) {
        this.pathToResourceDefinitions = pathToResourceDefinitions;
        this.pathToAuthorizationDefinitions = pathToAuthorizationDefinitions;
    }

    public String getPathToResourceDefinitions() {
        return pathToResourceDefinitions;
    }

    public String getPathToAuthorizationDefinitions() {
        return pathToAuthorizationDefinitions;
    }
}
