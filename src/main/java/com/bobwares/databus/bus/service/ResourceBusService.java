package com.bobwares.databus.bus.service;

import com.bobwares.databus.bus.model.ResourceMessage;
import com.bobwares.databus.bus.model.ResponseMessage;
import com.bobwares.databus.bus.service.gateway.ResourceBusGateway;
import com.bobwares.databus.common.annotation.DataBusServiceComponent;
import com.bobwares.databus.common.model.ResourceBusObject;
import com.bobwares.databus.common.model.ResponseObject;
import com.bobwares.databus.common.model.ResultsModel;
import com.bobwares.databus.common.service.DataBusService;
import com.bobwares.databus.server.registry.model.ResourceDefinition;
import com.bobwares.databus.server.service.request.RequestObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@DataBusServiceComponent
@Named("resource-bus")
public class ResourceBusService implements DataBusService {
    final Logger logger = LoggerFactory.getLogger(getClass());

    private ResourceBusGateway resourceBusGateway;

    @Inject
    public void setResourceBusGateway(ResourceBusGateway resourceBusGateway) {
        this.resourceBusGateway = resourceBusGateway;
    }

    @Override
    public ResponseObject getRecords(ResourceDefinition resourceDefinition, RequestObject requestObject) {
        ResponseMessage responseMessage = getModel(resourceDefinition, requestObject);
        ResponseObject responseObject = buildResponseObject(responseMessage);
        return responseObject;
    }

    private ResponseObject buildResponseObject(ResponseMessage responseMessage) {
        ResponseObject responseObject = new ResponseObject();
        responseObject.setMessage(responseMessage.getErrorMessage());
        ResultsModel resultsModel = new ResourceBusObject();
        List<Map<String, Object>> results = new ArrayList<>();
        results.add(responseMessage.getNodes());
        resultsModel.setItems(results);
        responseObject.setResultsModel(resultsModel);
        return responseObject;
    }

    private ResponseMessage getModel(ResourceDefinition resourceDefinition, RequestObject requestObject) {
        ResourceMessage resourceMessage = new ResourceMessage(requestObject, resourceDefinition);
        ResponseMessage responseMessage=null;
        try {
            responseMessage = resourceBusGateway.getModel(resourceMessage);
        } catch (Exception e) {
            logger.error("failed to get model from resource", e);

            responseMessage = new ResponseMessage();
            responseMessage.setErrorMessage("Service Unavailable.");
        }
        return responseMessage;
    }

}
