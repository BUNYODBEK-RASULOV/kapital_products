package com.example.kapital_products.uploadFile.payload.request;

import com.example.kapital_products.commons.payload.request.ApiRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Valid
public class GazBalonCreatRequest extends ApiRequest {



    @Schema(description = "start date ",example ="24.10.2023")
    @NotEmpty(message = "start date cannot be Empty")
    private String s_date;

    @Schema(description = "promo code to get discount",example ="TEST")
    private String promo_code = " ";



    public String getS_date() {
        if (s_date.isEmpty())
            return s_date;

        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate date = LocalDate.parse(s_date, inputFormatter);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return date.format(outputFormatter);
    }
}
