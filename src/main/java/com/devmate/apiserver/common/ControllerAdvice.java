package com.devmate.apiserver.common;

import com.devmate.apiserver.common.exception.ConfirmPasswordNotMatchException;
import com.devmate.apiserver.dto.ValidFailResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;

@RestControllerAdvice
@Slf4j
public class ControllerAdvice {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidFailResponseDto> handleValidationExceptions(MethodArgumentNotValidException ex, HttpServletRequest request) {
        ValidFailResponseDto validFailResponseDto = getValidFailResponseDto(ex, request, HttpStatus.BAD_REQUEST, HttpServletResponse.SC_BAD_REQUEST);

        return ResponseEntity.badRequest().body(validFailResponseDto);
    }

    @ExceptionHandler(ConfirmPasswordNotMatchException.class)
    public ResponseEntity<ValidFailResponseDto> handleValidationException(ConfirmPasswordNotMatchException ex, HttpServletRequest request) {
        ValidFailResponseDto validFailResponseDto = getValidFailResponseDto(ex,request, HttpStatus.BAD_REQUEST, HttpServletResponse.SC_BAD_REQUEST);
        return ResponseEntity.badRequest().body(validFailResponseDto);
    }
    private ValidFailResponseDto getValidFailResponseDto(Exception ex, HttpServletRequest request, HttpStatus httpStatus,
                                                         int errorCode) {
        ValidFailResponseDto validFailResponseDto = new ValidFailResponseDto();
        validFailResponseDto.setResponseTime(LocalDateTime.now());
        validFailResponseDto.setStatus(errorCode);
        validFailResponseDto.setPath(request.getRequestURL().toString());
        validFailResponseDto.setError(httpStatus);
        if(ex instanceof MethodArgumentNotValidException validException){
            String message = getMessageString(validException);
            validFailResponseDto.setMessage(message);
        }
        else if(ex instanceof ConfirmPasswordNotMatchException){
            validFailResponseDto.setMessage("Confirm Password Not Matched");
        }
        return validFailResponseDto;
    }

    private String getMessageString(MethodArgumentNotValidException ex) {
        StringBuilder sb = new StringBuilder();
        List<FieldError> errors = ex.getBindingResult().getFieldErrors();
        IntStream.range(0, errors.size())
                .forEach(i -> {
                    sb.append(errors.get(i).getDefaultMessage());
                    if (i < errors.size() - 1) {
                        sb.append(", ");
                    }
                });
        return sb.toString();
    }
}
