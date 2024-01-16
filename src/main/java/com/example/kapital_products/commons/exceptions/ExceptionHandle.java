package com.example.kapital_products.commons.exceptions;


import com.example.kapital_products.commons.payload.enams.ResponseEnum;
import com.example.kapital_products.commons.payload.response.ApiResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandle extends ResponseEntityExceptionHandler {
    private static final Logger logger = LogManager.getLogger(ExceptionHandle.class);

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse ProviderException(Exception ex) {
        logger.error("An error occurred: ",ex);
        return new ApiResponse(ResponseEnum.ERROR.getResult(),ex.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String,String> errors=new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) ->{
            String fieldName=((FieldError) error).getField();
            String message=error.getDefaultMessage();
            errors.put(fieldName,message);
        });
        StringBuilder st=new StringBuilder();
        errors.forEach((key,value) -> { st.append(key).append(" ").append(value); });
        logger.error("handle Method Argument Not Valid :",ex);
        return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST.value(),st.toString()),HttpStatus.BAD_REQUEST);
    }
}
