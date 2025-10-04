package com.pusbin.layanan.internal.services.user.dto;

import lombok.Data;

@Data
public class LoginRequest {

    private String username;
    private String password;

}
