package com.example.kapital_products.commons.service;

import com.example.kapital_products.commons.entity.UserEntity;
import com.example.kapital_products.commons.exceptions.ForbiddenException;
import com.example.kapital_products.commons.payload.request.LoginDTO;
import com.example.kapital_products.commons.payload.response.ApiResponse;
import com.example.kapital_products.commons.repository.UserRepository;
import com.example.kapital_products.commons.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import static com.example.kapital_products.commons.payload.enams.ResponseEnum.*;


@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    private final UserRepository userRepository;

    @Lazy
    @Autowired
    AuthenticationManager authenticationManager;

    private final JwtProvider jwtProvider;

    @Override
    public UserDetails loadUserByUsername(String login) throws ForbiddenException {
        return userRepository.findByTbLogin(login).orElseThrow(() -> new ForbiddenException("User not found username:" + login));
    }


//    qayta ko'rish kerak bu joyini'
    public ApiResponse loginUser(LoginDTO loginDTO) {

        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginDTO.getLogin(),
                    loginDTO.getPassword()
            ));
            UserEntity user = (UserEntity) authenticate.getPrincipal();
            if (user.getToken() == null || !user.getToken().startsWith(BEARER.getText()) || jwtProvider.getUsernameFromToken(user.getToken().substring(7)) == null) {
                String token = jwtProvider.generateToken(user.getTbLogin());
                user.setToken(BEARER.getText() + token);
                userRepository.save(user);
            }
            return new ApiResponse( 0, user.getToken());
        }catch (BadCredentialsException badCredentialsException){
            return new ApiResponse(-1,"login or password error");
        }

    }
}
