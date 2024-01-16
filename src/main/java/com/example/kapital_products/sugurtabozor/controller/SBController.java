package com.example.kapital_products.sugurtabozor.controller;


import com.example.kapital_products.commons.payload.request.ContractRequest;
import com.example.kapital_products.commons.service.LogService;
import com.example.kapital_products.sugurtabozor.payload.reponse.SugurtaBozorCreatResponse;
import com.example.kapital_products.sugurtabozor.payload.request.SugurtaBozorRequest;
import com.example.kapital_products.sugurtabozor.service.SBService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/sugurtabozor")
@RequiredArgsConstructor
@Tag(name = "Sug'urta bozor ",description = "API for Sug'urta bozor")
public class SBController {
    private final SBService service;
    private final LogService logService;

    @Operation(summary = "Upload files to the database",
            description = "Uploads files to the database associated with a specific anketa_id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Files uploaded successfully",
                    content = {@Content(mediaType = "application/json")})
    })

    @PostMapping("/fileByUUID")
    public com.example.kapital_products.commons.payload.response.ApiResponse save(
            @RequestBody SugurtaBozorRequest request
    )  {
        return service.savesDB(request);
    }


    @Operation(summary = "car-inspection",
            description = "Submit a request for vehicle data analysis. It is done through contract id. Use this method after \"creat\"")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successfully",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = SugurtaBozorCreatResponse.class))}
            ),
            @ApiResponse(responseCode = "400", description = "error",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = com.example.kapital_products.commons.payload.response.ApiResponse.class))}
            )
    })
    @PostMapping("/car-inspection")
    public HttpEntity<?> creat( @Valid  @RequestBody ContractRequest request)  {
        var res =service.creat(request);
        logService.addLog(request,res,"CAR_INSPECTION");
        return ResponseEntity.ok(res);
    }

}
