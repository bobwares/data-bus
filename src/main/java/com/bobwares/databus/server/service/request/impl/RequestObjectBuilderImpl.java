package com.bobwares.databus.server.service.request.impl;

import com.bobwares.databus.server.service.request.RequestObject;
import com.bobwares.databus.server.service.request.RequestObjectBuilder;
import com.bobwares.databus.server.service.request.RequestSource;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

@Service
public class RequestObjectBuilderImpl implements RequestObjectBuilder {

    @Override
    public RequestObject build(MultiValueMap<String, Object> parameters, String resourceKey, RequestSource requestSource) {
        return new RequestObject(parameters, resourceKey, requestSource);
    }
}
