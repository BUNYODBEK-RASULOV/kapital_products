package com.example.kapital_products.sugurtabozor.payload.request;

import com.example.kapital_products.commons.payload.request.ApiRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Valid
public class SugurtaBozorRequest extends ApiRequest {

    @Schema(description = "uuid",example ="a110eea6-00eb-4d74-9add-3ee3732161de")
    @NotEmpty(message = "uuid cannot be Empty")
    private String uuid;

    @Schema(description = "pdf_url",example ="https://staging-back-api.sugurtabozor.uz/car-inspection/get-act-inspection?uuid=071030c0-8998-45dc-b26a-be37bf3ca0a0")
    @NotEmpty(message = "pdf_url cannot be Empty")
    private String pdf_url;

}
