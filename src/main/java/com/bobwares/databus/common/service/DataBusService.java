package com.bobwares.databus.common.service;

import com.bobwares.databus.common.model.ResponseObject;
import com.bobwares.databus.server.registry.model.ResourceDefinition;
import com.bobwares.databus.server.service.request.RequestObject;

public interface DataBusService {

	ResponseObject getRecords(ResourceDefinition resourceDefinition, RequestObject requestObject);
}
