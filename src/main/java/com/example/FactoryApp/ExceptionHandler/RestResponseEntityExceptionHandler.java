package com.example.FactoryApp.ExceptionHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(RestResponseEntityExceptionHandler.class.getSimpleName());

    public RestResponseEntityExceptionHandler() {
        super();
    }
    @ExceptionHandler(value = { IllegalStateException.class, RuntimeException.class})
    protected ResponseEntity<Object> handleBadRequest(RuntimeException e, WebRequest request) throws Exception {
        logger.error("Application error in[" + e.getClass().getName() + "]", e);

        return createResponseEntity(new ExceptionResponseModel(e.toString()), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}
