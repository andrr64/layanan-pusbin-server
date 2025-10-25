package com.pusbin.layanan.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.pusbin.layanan.common.dto.ApiResponse;
import com.pusbin.layanan.exception.ConflictException;
import com.pusbin.layanan.exception.NotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handler untuk error validasi dari @Valid
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Object> handleValidationErrors(MethodArgumentNotValidException ex) {
        return ApiResponse.error("Required request body is missing or invalid", null);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse<Object> handleNotFound(NotFoundException ex) {
        return ApiResponse.error(ex.getMessage(), null);
    }

    // Handler untuk body request yang hilang atau formatnya salah
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Object> handleMissingRequestBody(HttpMessageNotReadableException ex) {
        return ApiResponse.error("Required request body is missing or invalid", null);
    }

    // Handler untuk semua jenis exception lain
    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiResponse<Object> handleConflict(Exception ex) {
        return ApiResponse.error(ex.getMessage(), null);
    }

    // Handler untuk semua jenis exception lain
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<Object> handleRuntimeException(Exception ex) {
        return ApiResponse.error(ex.getMessage(), null);
    }

    // Handler untuk semua jenis exception lain
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<Object> handleGenericException(Exception ex) {
        return ApiResponse.error("An internal server error occurred", null);
    }
}
