package com.pusbin.layanan.internal.services.user.dto;

import lombok.Data;

@Data
public class LoginResponse {
    private String access_token;
    private UserData user;
}
