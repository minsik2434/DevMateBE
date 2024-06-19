package com.devmate.apiserver.common;

import com.devmate.apiserver.common.exception.ConfirmPasswordNotMatchException;
import com.devmate.apiserver.common.exception.DuplicateResourceException;
import com.devmate.apiserver.common.exception.IdOrPasswordIncorrectException;
import com.devmate.apiserver.common.exception.LackOfPermissionException;
import com.devmate.apiserver.dto.FailResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;

@RestControllerAdvice
@Slf4j
public class ControllerAdvice {
    @ExceptionHandler(IdOrPasswordIncorrectException.class)
    public ResponseEntity<FailResponseDto> handleFailLogin(BadCredentialsException ex,
                                                           HttpServletRequest request){
        FailResponseDto failResponseDto = getFailResponseDto(ex, request, HttpStatus.UNAUTHORIZED, HttpServletResponse.SC_UNAUTHORIZED);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(failResponseDto);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<FailResponseDto> handleValidationExceptions(MethodArgumentNotValidException ex,
                                                                      HttpServletRequest request) {
        FailResponseDto failResponseDto = getFailResponseDto(ex, request, HttpStatus.BAD_REQUEST,
                HttpServletResponse.SC_BAD_REQUEST);

        return ResponseEntity.badRequest().body(failResponseDto);
    }

    @ExceptionHandler(ConfirmPasswordNotMatchException.class)
    public ResponseEntity<FailResponseDto> handleConfirmPasswordNotMatchException(ConfirmPasswordNotMatchException ex,
                                                                                  HttpServletRequest request) {
        FailResponseDto failResponseDto = getFailResponseDto(ex,request, HttpStatus.BAD_REQUEST,
                HttpServletResponse.SC_BAD_REQUEST);
        return ResponseEntity.badRequest().body(failResponseDto);
    }
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<FailResponseDto> handleNoSuchElementException(NoSuchElementException ex,
                                                                        HttpServletRequest request){
        FailResponseDto failResponseDto = getFailResponseDto(ex, request, HttpStatus.NOT_FOUND, HttpServletResponse.SC_NOT_FOUND);
        return ResponseEntity.badRequest().body(failResponseDto);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<FailResponseDto> handleDuplicateResourceException(DuplicateResourceException ex, HttpServletRequest request){
        FailResponseDto failResponseDto = getFailResponseDto(ex, request, HttpStatus.CONFLICT,
                HttpServletResponse.SC_CONFLICT);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(failResponseDto);
    }

    @ExceptionHandler(LackOfPermissionException.class)
    public ResponseEntity<FailResponseDto> handleLackOfPermissionException(LackOfPermissionException ex, HttpServletRequest request){
        FailResponseDto failResponseDto = getFailResponseDto(ex, request, HttpStatus.UNAUTHORIZED,
                HttpServletResponse.SC_UNAUTHORIZED);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(failResponseDto);
    }
    private FailResponseDto getFailResponseDto(Exception ex, HttpServletRequest request, HttpStatus httpStatus,
                                               int errorCode) {
        FailResponseDto failResponseDto = new FailResponseDto();
        failResponseDto.setResponseTime(LocalDateTime.now());
        failResponseDto.setStatus(errorCode);
        failResponseDto.setPath(request.getServletPath());
        failResponseDto.setError(httpStatus);
        if(ex instanceof MethodArgumentNotValidException validException){
            String message = getValidFailMessageString(validException);
            failResponseDto.setErrorMessage(message);
        }
        else if(ex instanceof ConfirmPasswordNotMatchException){
            failResponseDto.setErrorMessage(ex.getMessage());
        }
        else{
            failResponseDto.setErrorMessage(ex.getMessage());
        }
        return failResponseDto;
    }

    private String getValidFailMessageString(MethodArgumentNotValidException ex) {
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
