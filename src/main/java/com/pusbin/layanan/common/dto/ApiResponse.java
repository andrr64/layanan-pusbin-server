package com.pusbin.layanan.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private String message;
    private T data;
    private String error;
    private boolean  success;

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(message, data, null, true);
    }

    public static <T> ApiResponse<T> error(String error, T data) {
        return new ApiResponse<>(null, data, error, false);
    }

    public static <T> ApiResponse<T> fail(String error) {
        return new ApiResponse<>(null, null, error, false);
    }
}