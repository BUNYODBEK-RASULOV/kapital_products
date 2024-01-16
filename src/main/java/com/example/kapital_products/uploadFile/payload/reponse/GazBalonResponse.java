package com.example.kapital_products.uploadFile.payload.reponse;

import com.example.kapital_products.commons.payload.response.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GazBalonResponse extends ApiResponse {
    private String contract_id;
    private String insurance_premium ;
    private String insurance_liability ;

    public GazBalonResponse(int result, String result_message, String contract_id, String insurance_premium, String insurance_liability) {
        super(result, result_message);
//        super.setAnketaId(contract_id);
        this.contract_id = contract_id;
        this.insurance_premium = insurance_premium;
        this.insurance_liability = insurance_liability;
    }

    public GazBalonResponse(int result, String result_message) {
        super(result, result_message);
    }
}
