package com.seokho.crash.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.seokho.crash.model.error.ErrorResponse;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtExceptionFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        }catch (JwtException exception){
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setCharacterEncoding("UTF-8");

         var errorResponse =   new ErrorResponse(HttpStatus.UNAUTHORIZED, exception.getMessage());
            //ObjectMapper는 Jackson 라이브러리에서 제공하는 클래스입니다. 주로 Java 객체를 JSON 형식으로 변환하거나, JSON 데이터를 Java 객체로 변환할 때 사용
            ObjectMapper objectMapper = new ObjectMapper();
            String responseJson = objectMapper.writeValueAsString(errorResponse);
            response.getWriter().write(responseJson);

        }

    }
}
