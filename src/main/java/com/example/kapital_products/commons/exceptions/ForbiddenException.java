package com.example.kapital_products.commons.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
@Data
public class ForbiddenException extends RuntimeException {
    private String type;
    private String message;

    public ForbiddenException(String message,  String type) {
        this.type = type;
        this.message = message;
    }

    public ForbiddenException(String message) {
        this.message = message;
    }

}
