package com.bobwares.databus.common.model;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ResourceConfiguration {
    String resourcekey;
    Map<String,Path> paths;
    Info info;
    String scope;
    String uri;
    String httpMethod;
    List<Field> parameters;
    List<Field> fields;
    String authKey;
    String serviceKey;
    List<String> resources;
    String parentNode;
    List<Header> headers;
    String title;
    String fileName;
    String orientation;

    public Map<String, Path> getPaths() {
        return paths;
    }

    public void setPaths(Map<String, Path> paths) {
        this.paths = paths;
    }

    public String getResourcekey() {
        return resourcekey;
    }

    public void setResourcekey(String resourcekey) {
        this.resourcekey = resourcekey;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public String getParentNode() {
        return parentNode;
    }

    public void setParentNode(String parentNode) {
        this.parentNode = parentNode;
    }

    public List<String> getResources() {
        return resources;
    }

    public void setResources(List<String> resources) {
        this.resources = resources;
    }

    public String getServiceKey() {
        return serviceKey != null
        	? serviceKey
        	: "default"
        ;
    }

    public void setServiceKey(String serviceKey) {
        this.serviceKey = serviceKey;
    }

    public String getAuthKey() {
        return authKey != null
        	? authKey
        	: "default"
        ;
    }

    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }


    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public List<Field> getParameters() {
        return parameters;
    }

    public void setParameters(List<Field> parameters) {
        this.parameters = parameters;
    }

    public List<Field> getFields() {
        return fields;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }
}
