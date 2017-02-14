package com.bobwares.databus.service;

import com.bobwares.databus.LoadConfiguration;
import com.bobwares.databus.ParamBuilder;
import com.bobwares.databus.common.model.ResourceConfiguration;
import com.bobwares.databus.common.model.ResponseObject;
import com.bobwares.databus.common.service.DataBusService;
import com.bobwares.databus.server.registry.model.ResourceDefinition;
import com.bobwares.databus.server.service.gateway.RestGateway;
import com.bobwares.databus.server.service.request.RequestObject;
import com.bobwares.databus.server.service.request.RequestSource;
import org.junit.Test;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class RestGatewayTest {

    public static final String RESOURCE_URI = "upload/inventory-location";

    @Test
    public void test() throws IOException, URISyntaxException {

        ResourceConfiguration resourceConfiguration = LoadConfiguration.loadConfiguration(RESOURCE_URI, "databus");
        assertNotNull(resourceConfiguration);

        DataBusService restGateway = new RestGateway();
        ResourceDefinition resourceDefinition = new ResourceDefinition(RESOURCE_URI, resourceConfiguration);

        MultiValueMap<String, Object> params = ParamBuilder.build2();
        assertTrue(params.size() != 0);

        RequestObject requestObject = new RequestObject(params, RESOURCE_URI, RequestSource.INTERNAL);


        ResponseObject responseObject = restGateway.getRecords(resourceDefinition, requestObject);
        assertNotNull(responseObject);

    }

}