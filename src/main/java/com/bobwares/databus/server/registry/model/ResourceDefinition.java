package com.bobwares.databus.server.registry.model;


import com.bobwares.databus.common.model.ResourceConfiguration;

import java.util.ArrayList;
import java.util.List;

public class ResourceDefinition {

    private final String resourceUri;
    private ResourceConfiguration resourceConfiguration;
    private List<ColumnTransformerDefinition> columnTransformerDefinitions;
    private boolean valid;

    public ResourceDefinition(String resourceUri, ResourceConfiguration resourceConfiguration) {
        this.resourceUri = resourceUri;
        this.resourceConfiguration = resourceConfiguration;
    }

    public String getResourceUri() {
        return resourceUri;
    }

    public ResourceConfiguration getResourceConfiguration() {
        return resourceConfiguration;
    }

    public String getAuthKey() {
        return resourceConfiguration.getAuthKey();
    }

    public String getServiceKey() {
        return resourceConfiguration.getServiceKey();
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getScope() {
        return resourceConfiguration.getScope();
    }

    public void addColumnTransformerDefinition(ColumnTransformerDefinition columnTransformerDefinition) {
        if (this.columnTransformerDefinitions == null) {
            this.columnTransformerDefinitions = new ArrayList<>();
        }
        this.columnTransformerDefinitions.add(columnTransformerDefinition);
    }

    public List<ColumnTransformerDefinition> getColumnTransformerDefinitions() {
        return columnTransformerDefinitions;
    }


    @Override
    public String toString() {
        //return ToStringBuilder.reflectionToString(this);
        return "ResourceDefinition{" +
                "resourceUri='" + resourceUri + '\'' +
                '}';
    }

}
