package com.bobwares.databus.bus.service;

import com.bobwares.databus.LoadConfiguration;
import com.bobwares.databus.bus.model.ResourceMessage;
import com.bobwares.databus.bus.service.endpoint.ParametersTransformer;
import com.bobwares.databus.common.model.Configuration;
import com.bobwares.databus.server.registry.model.ResourceDefinition;
import com.bobwares.databus.server.service.request.RequestObject;
import com.bobwares.databus.server.service.request.RequestSource;
import com.bobwares.test.AbstractDatabaseTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.junit.Assert.assertNotNull;

public class ParametersTransformerTest extends AbstractDatabaseTest {

    private static final String PATH_DATABUS = "databus";
    private static final String BUS_TESTS_PAGE_TEST = "bus-tests/page-test";

    private Configuration configuration;

    @Before
    public void setup() {
        configuration = LoadConfiguration.loadConfiguration(BUS_TESTS_PAGE_TEST, PATH_DATABUS);
        assertNotNull(configuration);

    }

    @Test
    public void testHandle() throws Exception {
        MultiValueMap<String, String> parameters =  new LinkedMultiValueMap<>();
        RequestObject requestObject = new RequestObject(parameters, BUS_TESTS_PAGE_TEST, RequestSource.EXTERNAL);

        ParametersTransformer parametersTransformer = new ParametersTransformer();
        ResourceDefinition resourceDefinition = new ResourceDefinition(BUS_TESTS_PAGE_TEST, configuration);


        ResourceMessage resourceMessage = new ResourceMessage(requestObject, resourceDefinition);

        ResourceMessage processedParameters = parametersTransformer.handle(resourceMessage);
        assertNotNull(processedParameters);


    }

}