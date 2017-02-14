package com.bobwares.databus.common.rest;

import org.springframework.http.HttpStatus;

public class NotFoundException extends ErrorMessageException {


    public NotFoundException(String field) {

        addErrorMessage(new NotFoundErrorMessage(field));
    }

    public NotFoundException(String field, String message) {
        ;
        addErrorMessage(new NotFoundErrorMessage(field, message));
    }

}
