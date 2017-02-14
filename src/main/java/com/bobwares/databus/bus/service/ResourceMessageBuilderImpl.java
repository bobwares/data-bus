package com.bobwares.databus.bus.service;

import com.bobwares.databus.bus.model.ResourceMessage;
import com.bobwares.databus.server.registry.model.ResourceDefinition;
import com.bobwares.databus.server.service.request.RequestObject;
import org.springframework.stereotype.Service;

@Service
public class ResourceMessageBuilderImpl implements ResourceMessageBuilder {

    @Override
    public ResourceMessage build(RequestObject requestObject, ResourceDefinition resourceDefinition) {
        ResourceMessage resourceMessage = new ResourceMessage(requestObject, resourceDefinition);
        return resourceMessage;
    }
}
