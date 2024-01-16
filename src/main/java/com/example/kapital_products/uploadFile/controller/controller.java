package com.example.kapital_products.uploadFile.controller;


import com.example.kapital_products.uploadFile.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@RestController
@RequestMapping("/api/v1/file")
@RequiredArgsConstructor
@Tag(name = "upload file api",description = "API for File")
public class controller {
    private final FileService service;

    @Operation(summary = "Upload files to the database",
            description = "Uploads files to the database associated with a specific anketa_id. In the form-data , multiple files can be sent in one request. Max-file-size: 10MB, max-request-size: 50MB ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Files uploaded successfully",
                    content = {@Content(mediaType = "application/json")})
    })

    @PostMapping("/uploadDB")
    public com.example.kapital_products.commons.payload.response.ApiResponse creat(
            MultipartHttpServletRequest request,
            @RequestParam("anketa_id") Integer id
    )  {
        return service.savesDB(request,id);
    }

}
