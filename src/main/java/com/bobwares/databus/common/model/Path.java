package com.bobwares.databus.common.model;


import org.springframework.http.HttpMethod;

public class Path {
    String uri;
    HttpMethod httpMethod;


    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
    }
}
