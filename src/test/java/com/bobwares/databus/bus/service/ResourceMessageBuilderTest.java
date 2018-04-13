package com.bobwares.databus.bus.service;

import com.bobwares.databus.ParamBuilder;
import com.bobwares.databus.bus.model.ResourceMessage;
import com.bobwares.databus.common.model.Configuration;
import com.bobwares.databus.server.registry.model.ResourceDefinition;
import com.bobwares.databus.server.service.request.RequestObject;
import com.bobwares.databus.server.service.request.RequestSource;
import org.junit.Test;
import org.springframework.util.MultiValueMap;

import static org.junit.Assert.assertTrue;

public class ResourceMessageBuilderTest {

    private static final String RESOURCE_KEY = "resourceKey";

    @Test
    public void testBuild() throws Exception {
        ResourceMessageBuilder resourceMessageBuilder = new ResourceMessageBuilderImpl();
        MultiValueMap<String, String> parameters = ParamBuilder.build();
        RequestObject requestObject = new RequestObject(parameters, RESOURCE_KEY, RequestSource.EXTERNAL);
        Configuration configuration = new Configuration();
        ResourceDefinition resourceDefinition = new ResourceDefinition(RESOURCE_KEY,configuration);
        ResourceMessage resourceMessage = resourceMessageBuilder.build(requestObject, resourceDefinition);
        assertTrue(resourceMessage.getFilters() != null);
        assertTrue(resourceMessage.getResourceUri() != null);
        assertTrue(resourceMessage.getRequestObject() != null);
    }
}