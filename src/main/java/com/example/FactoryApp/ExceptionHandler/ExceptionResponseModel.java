package com.example.FactoryApp.ExceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.ErrorResponse;
import org.springframework.web.ErrorResponseException;

public class ExceptionResponseModel extends ErrorResponseException {

    public ExceptionResponseModel(String messageDetail) {
        super(HttpStatus.BAD_REQUEST, null, null, messageDetail, null);
    }
}
