package com.pusbin.common.dto;

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

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(message, data, null);
    }

    public static <T> ApiResponse<T> error(String error, T data) {
        return new ApiResponse<>(null, data, error);
    }

    public static <T> ApiResponse<T> fail(String error) {
        return new ApiResponse<>(null, null, error);
    }
}