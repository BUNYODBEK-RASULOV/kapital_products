package com.example.kapital_products.commons.payload.enams;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseEnum {

    BEARER(0,"Bearer "),
    BASIC(0,"Basic "),
    SUCCESS(0,"Successful"),
    YOUR_TOKEN(0,"Your token"),
    LOG_PAS_ERROR(403,"login or password error"),

    ERROR(-1,"error");

    private final Integer result;
    private final String text;

}
