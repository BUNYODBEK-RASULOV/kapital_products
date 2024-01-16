package com.example.kapital_products.sugurtabozor.payload.reponse;

import com.example.kapital_products.commons.payload.response.ApiResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SugurtaBozorCreatResponse extends ApiResponse {

    @Schema(description = "monitoring url", example = "https://monitoring-staging.sugurtabozor.uz//uz/08b3d01d-3fdd-48fa-b835-009e6a8c1907")
    private String monitoring_url;

    public SugurtaBozorCreatResponse(int result, String result_message, Integer anketaId, String monitoring_url) {
        super(result, result_message, anketaId);
        this.monitoring_url = monitoring_url;
    }

    public SugurtaBozorCreatResponse(int result, String result_message, Integer anketaId) {
        super(result, result_message, anketaId);
    }
}
