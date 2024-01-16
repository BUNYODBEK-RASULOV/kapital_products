package com.example.kapital_products;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@OpenAPIDefinition(
        servers = {@Server(url = "/",description = "https://openapi.ksc.uz - OpenApi host")},
        info = @Info(title = "Products API", version = "1.0", description = "Products Information")
)
public class KapitalProductsApplication {

    public static void main(String[] args) {
        SpringApplication.run(KapitalProductsApplication.class, args);
    }

}
