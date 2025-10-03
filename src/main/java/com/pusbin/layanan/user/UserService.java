package com.pusbin.layanan.user;


import com.pusbin.layanan.user.dto.LoginRequest;
import com.pusbin.layanan.user.dto.LoginResponse;
import com.pusbin.layanan.user.dto.RegisterRequest;
import com.pusbin.layanan.user.dto.UserData;

public interface  UserService {
    UserData register(RegisterRequest request);
    LoginResponse login(LoginRequest request);
}
