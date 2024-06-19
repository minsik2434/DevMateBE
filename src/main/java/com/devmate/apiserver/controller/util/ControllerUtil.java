package com.devmate.apiserver.controller.util;

import com.devmate.apiserver.dto.SuccessResponseDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ControllerUtil {
    public <T> SuccessResponseDto<T> createSuccessResponse(T data, int status){
        SuccessResponseDto<T> successResponseDto = new SuccessResponseDto<>();
        successResponseDto.setResponseTime(LocalDateTime.now());
        successResponseDto.setStatus(status);
        successResponseDto.setData(data);
        return successResponseDto;
    }

    public String getAuthorizedLoginId(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }
}
