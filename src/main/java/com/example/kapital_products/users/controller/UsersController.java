package com.example.kapital_products.users.controller;

import com.example.kapital_products.users.service.UsersService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "download file api",description = "API for File")
public class UsersController {
    private final UsersService service;


    @GetMapping("/europrotocol/{cid}/{aid}")
    public HttpEntity<?> europrotocol_doc(@PathVariable("cid") Integer cid, @PathVariable("aid") Integer aid )  {
        var headers = new HttpHeaders();
        try {
            headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "Europrotocol.pdf");
            return new ResponseEntity<>(service.europrotocol_doc(cid, aid), headers, HttpStatus.OK);
        } catch (Exception e) {
            headers = new HttpHeaders();
            headers.setContentType(MediaType.TEXT_PLAIN);
            return new ResponseEntity<>("\uD83D\uDE14 Europrotocol document not found", headers, HttpStatus.NOT_FOUND);
        }
    }
}
