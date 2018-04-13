package com.bobwares.databus.server.service.request.impl;

import com.bobwares.databus.ParamBuilder;
import com.bobwares.databus.common.model.PagingObject;
import com.bobwares.databus.common.model.ResponseObject;
import com.bobwares.databus.common.model.ResultsModel;
import com.bobwares.databus.server.service.request.RequestObject;
import com.bobwares.databus.server.service.request.RequestObjectBuilder;
import com.bobwares.databus.server.service.request.RequestProcessor;
import com.bobwares.databus.server.service.request.RequestSource;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.util.MultiValueMap;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

public class DataBusRequestGatewayImplTest {
    private static final String RESOURCE_KEY_1 = "resourceKey_1";

    private DataBusExternalRequestGateway dataBusRequestGateway;

    @Mock
    private RequestObjectBuilder requestObjectBuilder;

    @Mock
    private RequestProcessor requestProcessor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        dataBusRequestGateway = new DataBusExternalRequestGateway();
        dataBusRequestGateway.setRequestObjectBuilder(requestObjectBuilder);
        dataBusRequestGateway.setRequestProcessor(requestProcessor);
    }

    @Test
    public void processRequest() throws Exception {
        MultiValueMap<String, String> params = ParamBuilder.build();
        ResponseObject responseObject = new ResponseObject();
        ResultsModel pageObject = new PagingObject();
        responseObject.setResultsModel(pageObject);
        RequestObject requestObject = new RequestObject(params,RESOURCE_KEY_1, RequestSource.EXTERNAL);

        when(requestProcessor.processRequest(requestObject)).thenReturn(responseObject);
        when(requestObjectBuilder.build(params,RESOURCE_KEY_1, RequestSource.EXTERNAL)).thenReturn(requestObject);
        when(requestProcessor.processRequest(requestObject)).thenReturn(responseObject);

        ResponseObject response = dataBusRequestGateway.processRequest(params, RESOURCE_KEY_1);
        assertNotNull(response);

    }

    @Test
    public void getResourceFields() throws Exception {

    }

}