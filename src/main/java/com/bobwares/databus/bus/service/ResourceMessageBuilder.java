package com.bobwares.databus.bus.service;

import com.bobwares.databus.bus.model.ResourceMessage;
import com.bobwares.databus.server.registry.model.ResourceDefinition;
import com.bobwares.databus.server.service.request.RequestObject;

public interface ResourceMessageBuilder {
    ResourceMessage build(RequestObject requestObject, ResourceDefinition resourceDefinition);
}
