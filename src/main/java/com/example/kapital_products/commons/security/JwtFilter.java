package com.example.kapital_products.commons.security;



import com.example.kapital_products.commons.config.Md5Encoder;
import com.example.kapital_products.commons.exceptions.ForbiddenException;
import com.example.kapital_products.commons.payload.response.ApiResponse;
import com.example.kapital_products.commons.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;

import static com.example.kapital_products.commons.payload.enams.ResponseEnum.*;


@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;
    private final AuthService authService;
    private final Md5Encoder md5Encoder;
    private static final ApiResponse invalidToken=new ApiResponse(HttpStatus.FORBIDDEN.value(),"Invalid token");
    private static ApiResponse checkUser=new ApiResponse(0, SUCCESS.getText());

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");

        if (token != null ){
            if (token.startsWith(BEARER.getText())) {
                token = token.substring(7);
                String username = jwtProvider.getUsernameFromToken(token);

                if (username == null) {
                    responseWrite(response, invalidToken);
                    return;
                }

                UserDetails userDetails ;

                try { userDetails = authService.loadUserByUsername(username);}
                catch (Exception e){responseWrite(response, invalidToken); return;}

                if (check(userDetails)) {
                    responseWrite(response, checkUser);
                    return;
                }

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);

            }
            else if (token.startsWith(BASIC.getText())) {
                token = token.substring(6);
                byte[] decodedBytes = Base64.getDecoder().decode(token);
                String credentials = new String(decodedBytes);
                String[] usernameAndPassword = credentials.split(":");
                UserDetails userDetails ;
                try { userDetails = authService.loadUserByUsername(usernameAndPassword[0].toUpperCase());}
                catch (Exception e) {responseWrite(response,invalidToken); return; }
                if (md5Encoder.matches(usernameAndPassword[1].toUpperCase(),userDetails.getPassword())){
                    if (check(userDetails)) { responseWrite(response, checkUser); return; }
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }else {
                    responseWrite(response,invalidToken); return;
                }
            }
        }
        filterChain.doFilter(request, response);
    }

    public boolean check(UserDetails userDetails){
        if (!userDetails.isAccountNonExpired())
            checkUser = new ApiResponse(HttpStatus.FORBIDDEN.value(), "Account Expired");
        if (!userDetails.isEnabled())
            checkUser = new ApiResponse(HttpStatus.FORBIDDEN.value(), "Account Enabled");
        if (!userDetails.isCredentialsNonExpired())
            checkUser = new ApiResponse(HttpStatus.FORBIDDEN.value(), "Credentials Expired");
        if (!userDetails.isAccountNonLocked())
            checkUser = new ApiResponse(HttpStatus.FORBIDDEN.value(), "Account Locked");
        return checkUser.getResult() != 0;
    }
    public void responseWrite(HttpServletResponse response, ApiResponse apiResponse) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.getWriter().write(new ObjectMapper().writeValueAsString(apiResponse));
    }

}
