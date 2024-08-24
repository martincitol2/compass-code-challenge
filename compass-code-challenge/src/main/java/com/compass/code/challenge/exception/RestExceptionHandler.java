package com.compass.code.challenge.exception;

import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {

    public static final String BAD_REQUEST = "Bad Request";
    public static final String INTERNAL_SERVER_ERROR = "Internal Server Error";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    protected ApiError onMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream().map(FieldError::getDefaultMessage)
                .toList().toString();
        final ApiError apiError = new ApiError(new Date().getTime(), HttpStatus.BAD_REQUEST.value(),
                BAD_REQUEST, ((ServletWebRequest) request).getRequest().getRequestURI(), errorMessage);
        log.error("onMethodArgumentNotValidException on {} message {}",
                ((ServletWebRequest) request).getRequest().getRequestURI(),
                errorMessage);
        return apiError;
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    protected ApiError onValidationException(ValidationException ex, WebRequest request) {
        final ApiError apiError = new ApiError(new Date().getTime(), HttpStatus.BAD_REQUEST.value(),
                BAD_REQUEST, ((ServletWebRequest) request).getRequest().getRequestURI(), ex.getMessage());
        log.error("onValidationException on {} message {}",
                ((ServletWebRequest) request).getRequest().getRequestURI(),
                ex.getMessage());
        return apiError;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    protected ApiError onException(Exception ex, WebRequest request) {
        final ApiError apiError = new ApiError(new Date().getTime(), HttpStatus.INTERNAL_SERVER_ERROR.value(),
                INTERNAL_SERVER_ERROR, ((ServletWebRequest) request).getRequest().getRequestURI(), ex.getMessage());
        log.error("onException on {} message {}",
                ((ServletWebRequest) request).getRequest().getRequestURI(),
                ex.getMessage());
        return apiError;
    }

}
