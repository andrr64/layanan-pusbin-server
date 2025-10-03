package com.pusbin.layanan.user.dto;

import lombok.Data;

@Data
public class LoginResponse {
    private String access_token;
    private UserData user;
}
