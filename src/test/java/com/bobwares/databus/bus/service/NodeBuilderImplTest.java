package com.bobwares.databus.bus.service;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.util.MultiValueMap;

import com.bobwares.databus.ParamBuilder;
import com.bobwares.databus.bus.model.ResourceMessage;
import com.bobwares.databus.bus.util.NodeBuilderImpl;
import com.bobwares.databus.common.model.Configuration;
import com.bobwares.databus.common.model.PagingObject;
import com.bobwares.databus.common.model.ResponseObject;
import com.bobwares.databus.server.registry.model.ResourceDefinition;
import com.bobwares.databus.server.service.request.RequestObject;
import com.bobwares.databus.server.service.request.RequestSource;
import org.junit.Before;
import org.junit.Test;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertNotNull;

public class NodeBuilderImplTest {
    private static final String DIY_MESSAGES_WELCOME_MESSAGE_URI = "diy/messages/welcome-message";
    private List<Map<String, Object>> items;

    @Before
    public void setup() {
        items = new ArrayList<>();
        Map<String, Object> row = new LinkedHashMap<>();
        row.put("recordCount", 1);
        row.put("field1", 100);
        items.add(row);
    }

    @Test
    public void testBuild() throws Exception {
        PagingObject pagingObject = new PagingObject(1, -1, items);
        ResponseObject responseObject = new ResponseObject();
        responseObject.setResultsModel(pagingObject);

        Configuration configuration = new Configuration();
        configuration.setParentNode("parent-node");
        ResourceDefinition resourceDefinition = new ResourceDefinition(DIY_MESSAGES_WELCOME_MESSAGE_URI,configuration);

        NodeBuilderImpl nodeBuilder = new NodeBuilderImpl();

        List<ResourceMessage> resourceMessages = new ArrayList<>();
        MultiValueMap<String, String> params = ParamBuilder.build();
        RequestObject requestObject = new RequestObject(params, DIY_MESSAGES_WELCOME_MESSAGE_URI, RequestSource.EXTERNAL);

        ResourceMessage resourceMessage = new ResourceMessage(requestObject, resourceDefinition);



        resourceMessage.setResponseObject(responseObject);
        resourceMessage.setResourceDefinition(resourceDefinition);

        resourceMessages.add(resourceMessage);
        Map<String, Object> nodes = nodeBuilder.build(resourceMessages);
        assertNotNull(nodes);

        /*
        ObjectMapper mapperObj = new ObjectMapper();
        try {
            // get Employee object as a json string
            String jsonStr = mapperObj.writeValueAsString(nodes);
            System.out.println(jsonStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
    }
}