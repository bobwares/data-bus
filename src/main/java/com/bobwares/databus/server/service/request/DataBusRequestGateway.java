package com.bobwares.databus.server.service.request;

import com.bobwares.databus.common.model.Field;
import com.bobwares.databus.common.model.ResponseObject;
import org.springframework.util.MultiValueMap;

import java.util.List;

public interface DataBusRequestGateway {

    ResponseObject processRequest(MultiValueMap<String, Object> parameters, String resourceKey) throws Exception;

    List<Field> getResourceFields(String resourceKey);
}
