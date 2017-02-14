package com.bobwares.databus.bus.service.gateway;


import com.bobwares.databus.bus.model.ResourceMessage;
import com.bobwares.databus.bus.model.ResponseMessage;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway(name = "resourceBusGateway",defaultRequestChannel = "requestChannel")
public interface ResourceBusGateway {

    @Gateway(requestChannel = "requestChannel")
    ResponseMessage getModel(ResourceMessage message);

}
