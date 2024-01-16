package com.example.kapital_products.commons.config;

import com.example.kapital_products.commons.entity.UserEntity;
import com.example.kapital_products.commons.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    private final UserRepository userRepository;

    @Override
    public void run(String... args)  {
        Optional<UserEntity> byId = userRepository.findByTbLogin("BUNYOD");
        System.out.println("https://openapi.ksc.uz/swagger");
        if (byId.isPresent())
            System.out.println(byId.get().getTbLogin());
        else
            System.out.println("topmadoyiv");
    }
}
