package com.bobwares.databus.server.service.request.impl;

import com.bobwares.databus.common.model.Field;
import com.bobwares.databus.common.model.ResponseObject;
import com.bobwares.databus.server.registry.model.ResourceDefinition;
import com.bobwares.databus.server.service.locator.ResourceLocator;
import com.bobwares.databus.server.service.request.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import javax.inject.Inject;
import java.util.List;

@Service
public class DataBusInternalRequestGateway implements DataBusRequestGateway {

    private ResourceLocator resourceLocator;

    @Inject
    public void setResourceLocator(ResourceLocator resourceLocator) {
        this.resourceLocator = resourceLocator;
    }

    private RequestObjectBuilder requestObjectBuilder;

    @Inject
    public void setRequestObjectBuilder(RequestObjectBuilder requestObjectBuilder) {
        this.requestObjectBuilder = requestObjectBuilder;
    }

    private RequestProcessor requestProcessor;

    @Inject
    public void setRequestProcessor(RequestProcessor requestProcessor) {
        this.requestProcessor = requestProcessor;
    }


    @Override
    public ResponseObject processRequest(MultiValueMap<String, Object> parameters, String resourceKey) throws Exception {
        RequestObject requestObject = requestObjectBuilder.build(parameters, resourceKey, RequestSource.INTERNAL);
        return requestProcessor.processRequest(requestObject);
    }

    @Override
    public List<Field> getResourceFields(String resourceKey) {
        ResourceDefinition resourceDefinition = resourceLocator.getResource(resourceKey);
        return resourceDefinition.getResourceConfiguration().getFields();
    }
}
