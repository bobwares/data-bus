package com.bobwares.databus.server.service.request;

import org.springframework.util.MultiValueMap;

public interface RequestObjectBuilder {
    RequestObject build(MultiValueMap<String, Object> parameters, String resourceKey, RequestSource requestSource);
}
