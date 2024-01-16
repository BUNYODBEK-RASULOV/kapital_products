package com.example.kapital_products.commons.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContractRequest extends ApiRequest {
    @Schema(description = "contractId (anketa_id, order_id)",example = "1234567")
    @NotNull(message = "contractId must not be Not Null")
    private Integer contractId;
}
