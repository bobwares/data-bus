package com.bobwares.databus.bus.model;

import com.bobwares.databus.LoadConfiguration;
import com.bobwares.databus.ParamBuilder;
import com.bobwares.databus.common.model.Configuration;
import com.bobwares.databus.server.registry.model.ResourceDefinition;
import com.bobwares.databus.server.service.request.RequestObject;
import com.bobwares.databus.server.service.request.RequestSource;
import org.junit.Test;
import org.springframework.util.MultiValueMap;

import static org.junit.Assert.assertTrue;

public class ResourceMessageTest {

    private static final String PATH_DATABUS = "databus";
    private static final String BUS_TESTS_PAGE_TEST = "bus-tests/page-test";

    @Test
    public void test() throws Exception {
        
        Configuration configuration = LoadConfiguration.loadConfiguration(BUS_TESTS_PAGE_TEST, PATH_DATABUS);
        ResourceDefinition resourceDefinition = new ResourceDefinition(BUS_TESTS_PAGE_TEST, configuration);

        MultiValueMap<String, String> parameters = ParamBuilder.build("1", "1", "", "false");
        RequestObject requestObject = new RequestObject(parameters, BUS_TESTS_PAGE_TEST, RequestSource.EXTERNAL);
        ResourceMessage resourceMessage = new ResourceMessage(requestObject, resourceDefinition);

        assertTrue(resourceMessage.getRequestObject().getResourceUri().equals(BUS_TESTS_PAGE_TEST));

        resourceMessage.setErrorMessage("Error Message");
        assertTrue(resourceMessage.getErrorMessage().equals("Error Message"));
    }
}