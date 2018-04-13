package com.bobwares.databus.bus.service;

import com.bobwares.databus.LoadConfiguration;
import com.bobwares.databus.ParamBuilder;
import com.bobwares.databus.bus.model.ResourceMessage;
import com.bobwares.databus.bus.model.ResponseMessage;
import com.bobwares.databus.bus.service.gateway.ResourceBusGateway;
import com.bobwares.databus.common.model.ResourceConfiguration;
import com.bobwares.databus.common.model.PagingObject;
import com.bobwares.databus.common.model.ResponseObject;
import com.bobwares.databus.common.util.JsonConfigurationLoader;
import com.bobwares.databus.server.registry.model.ResourceDefinition;
import com.bobwares.databus.server.service.request.RequestObject;
import com.bobwares.databus.server.service.request.RequestSource;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class ResourceBusServiceTest {

    private static final String PATH_DATABUS = "databus";
    private static final String BUS_TESTS_PAGE_TEST = "bus-tests/page-test";

    private ResourceBusService resourceBusService;

    private ResourceConfiguration configuration;

    private RequestObject requestObject;

    @Mock
    private ResourceBusGateway resourceBusGateway;

    @Mock
    private ResourceMessageBuilder resourceMessageBuilder;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        configuration = LoadConfiguration.loadConfiguration(BUS_TESTS_PAGE_TEST, PATH_DATABUS);

        assertNotNull(configuration);

        resourceBusService = new ResourceBusService();
        resourceBusService.setResourceBusGateway(resourceBusGateway);

        MultiValueMap<String, Object> parameters = ParamBuilder.build("1", "1", "", "false");

        requestObject = new RequestObject(parameters, BUS_TESTS_PAGE_TEST, RequestSource.EXTERNAL);
    }

    @Test
    public void testGetRecords() throws Exception {
        //InputStream jsonStream = ResourceUtils.getResourceStream("test-column-transformation-service-page-object.json");
        JsonConfigurationLoader jsonConfigurationLoader = new JsonConfigurationLoader();
        PagingObject pagingObject = jsonConfigurationLoader.loadConfiguration("test-column-transformation-service-page-object.json", PagingObject.class);

        ResourceDefinition resourceDefinition = new ResourceDefinition(BUS_TESTS_PAGE_TEST, configuration);
        ResourceMessage resourceMessage = new ResourceMessage(requestObject, resourceDefinition);
        ResponseObject responseObject = new ResponseObject();
        responseObject.setResultsModel(pagingObject);
        resourceMessage.setRequestObject(requestObject);

        when(resourceMessageBuilder.build(requestObject, resourceDefinition)).thenReturn(resourceMessage);

        ResponseMessage responseMessage = new ResponseMessage();
        Map<String, Object> nodes = new HashMap<>();
        nodes.put("item", "itemValue");
        responseMessage.setNodes(nodes);
        when(resourceBusGateway.getModel(Matchers.<ResourceMessage>anyObject())).thenReturn(responseMessage);
        ResponseObject responseObject1 = resourceBusService.getRecords(resourceDefinition, requestObject);

        assertTrue(responseObject1.getResultsModel().getItems().size() > 0);

    }

    @Test
    public void testException() throws Exception {

        ResourceDefinition resourceDefinition = new ResourceDefinition(BUS_TESTS_PAGE_TEST, configuration);
        ResourceMessage resourceMessage = new ResourceMessage(requestObject, resourceDefinition);
        when(resourceMessageBuilder.build(requestObject, resourceDefinition)).thenReturn(resourceMessage);

        ResponseMessage responseMessage = new ResponseMessage();
        Map<String, Object> nodes = new HashMap<>();
        nodes.put("item", "itemValue");
        responseMessage.setNodes(nodes);
        when(resourceBusGateway.getModel(Matchers.<ResourceMessage>anyObject())).thenThrow(Exception.class);
        ResponseObject responseObject = resourceBusService.getRecords(resourceDefinition, requestObject);
        assertTrue(responseObject.getMessage().equals("Service Unavailable."));

    }
}