package com.bobwares.databus.bus.service;

import com.bobwares.databus.ParamBuilder;
import com.bobwares.databus.bus.model.ResourceMessage;
import com.bobwares.databus.bus.service.endpoint.AggregateRequests;
import com.bobwares.databus.common.model.Configuration;
import com.bobwares.databus.server.registry.model.ResourceDefinition;
import com.bobwares.databus.server.service.request.RequestObject;
import com.bobwares.databus.server.service.request.RequestSource;
import org.junit.Test;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class AggregateRequestsTest {

    @Test
    public void testHandle() throws Exception {
        MultiValueMap<String, String> params = ParamBuilder.build();
        RequestObject requestObject = new RequestObject(params, "uri", RequestSource.EXTERNAL);

        Configuration configuration = new Configuration();
        ResourceDefinition resourceDefinition = new ResourceDefinition("uri", configuration);


        AggregateRequests aggregateRequests = new AggregateRequests();

        List<ResourceMessage> resourceMessages = new ArrayList<>();




        ResourceMessage resourceMessage = new ResourceMessage(requestObject, resourceDefinition);
        resourceMessages.add(resourceMessage);
        List<ResourceMessage> aggregatedResourceMessages = aggregateRequests.handle(resourceMessages);
        assertNotNull(aggregatedResourceMessages);
        assertTrue(aggregatedResourceMessages.size()==1);

    }
}