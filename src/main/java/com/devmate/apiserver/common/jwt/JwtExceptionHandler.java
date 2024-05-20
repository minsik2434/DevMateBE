package com.devmate.apiserver.common.jwt;

import com.devmate.apiserver.dto.FailResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.time.LocalDateTime;

public class JwtExceptionHandler extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws ServletException, IOException {
        try{
            chain.doFilter(req,resp);
        }catch (JwtTokenException e){
            resp.setStatus(HttpStatus.UNAUTHORIZED.value());
            resp.setContentType(MediaType.APPLICATION_JSON_VALUE);
            FailResponseDto failResponseDto = getFailResponseDto(req, e);
            getMapper().writeValue(resp.getOutputStream(), failResponseDto);
        }
    }

    private FailResponseDto getFailResponseDto(HttpServletRequest req, JwtTokenException e) {
        FailResponseDto failResponseDto = new FailResponseDto();
        failResponseDto.setPath(req.getServletPath());
        failResponseDto.setResponseTime(LocalDateTime.now());
        failResponseDto.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        failResponseDto.setError(HttpStatus.UNAUTHORIZED);
        failResponseDto.setErrorMessage(e.getMessage());
        return failResponseDto;
    }

    private ObjectMapper getMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }
}
