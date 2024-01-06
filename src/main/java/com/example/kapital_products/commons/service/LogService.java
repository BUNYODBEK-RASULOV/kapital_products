package com.example.kapital_products.commons.service;

import com.example.kapital_products.commons.entity.LogEntity;
import com.example.kapital_products.commons.entity.UserEntity;
import com.example.kapital_products.commons.payload.request.ApiRequest;
import com.example.kapital_products.commons.payload.response.ApiResponse;
import com.example.kapital_products.commons.repository.LogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogService {
    private final LogRepository repository;

    public void addLog(ApiRequest req, ApiResponse res, String method){

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserEntity users=(UserEntity) authentication.getPrincipal();

            LogEntity log = new LogEntity();
            log.setMethod(method);
            log.setReqContent(req.toString());
            log.setResCode(res.getResult());
            log.setResMessage(res.getResult_message());
            log.setResContent(res.toString());
            log.setAnketaId(res.getAnketaId());
            log.setUserId(users.getTbId());
            repository.save(log);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
