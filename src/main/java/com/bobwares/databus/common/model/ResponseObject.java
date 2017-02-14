package com.bobwares.databus.common.model;

public class ResponseObject {
    private ResultsModel resultsModel;
    private String message;

    public ResultsModel getResultsModel() {
        return resultsModel;
    }

    public void setResultsModel(ResultsModel resultsModel) {
        this.resultsModel = resultsModel;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

