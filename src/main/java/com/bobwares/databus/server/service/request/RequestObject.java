package com.bobwares.databus.server.service.request;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.Map;

public class RequestObject {
    private final RequestSource requestSource ;
    private final String resourceUri;
    private Map<String, String> filters;
    private MultiValueMap<String, Object> parameters;

    public RequestObject(MultiValueMap<String, Object> parameters, String resourceUri, RequestSource requestSource) {
        this.requestSource = requestSource;
        this.resourceUri = resourceUri;
        this.parameters= parameters;
    }

    public RequestObject(RequestObject requestObject, String resourceUri, RequestSource requestSource) {
        this.requestSource = requestSource;
        this.resourceUri = resourceUri;
        this.filters= this.cloneFilters(requestObject.filters);
        this.parameters= this.cloneParameters(requestObject.parameters);
    }

    public RequestSource getRequestSource() {
        return requestSource;
    }

    public String getResourceUri() {
        return resourceUri;
    }

    public Map<String, String> getFilters() {
        return filters;
    }


    private Map<String, String> cloneFilters(Map<String, String> filters) {
        Map<String, String> newFilters = new HashMap<>();
        newFilters.putAll(filters);
        return newFilters;
    }

    private MultiValueMap<String, Object> cloneParameters(MultiValueMap<String, Object> filters) {
        MultiValueMap<String, Object> newFilters = new LinkedMultiValueMap<>();
        newFilters.putAll(filters);
        return newFilters;
    }

    public MultiValueMap<String, Object> getParameters() {

        return this.parameters;
    }
}
