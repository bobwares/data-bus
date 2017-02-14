package com.bobwares.databus.server.service.request;

import com.bobwares.databus.common.model.ResponseObject;

public interface RequestProcessor {

    ResponseObject processRequest(RequestObject requestObject) throws NoSuchMethodException;

}
