package com.example.kapital_products.commons.payload.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {
    @Schema(description = "Response code (0 - Transaction completed successfully)", example = "0")
    private Integer result;
    @Schema(description = "Response information", example = "Successful request processing")
    private String result_message;

    @JsonIgnore
    private Integer anketaId=0;

    public ApiResponse(int result, String result_message) {
        this.result = result;
        this.result_message = result_message;
    }

    public ApiResponse(int result, String result_message,Integer anketaId) {
        this.result = result;
        this.result_message = result_message;
        this.anketaId = anketaId;
    }
}
