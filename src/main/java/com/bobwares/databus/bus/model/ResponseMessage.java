package com.bobwares.databus.bus.model;

import java.util.Map;

public class ResponseMessage {

    private String errorMessage;
    private Map<String, Object> nodes;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Map<String, Object> getNodes() {
        return nodes;
    }

    public void setNodes(Map<String, Object> nodes) {
        this.nodes = nodes;
    }
}
