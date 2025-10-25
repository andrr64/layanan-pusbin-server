package com.pusbin.layanan.internal.services.user;

import com.pusbin.layanan.internal.services.user.dto.LoginRequest;
import com.pusbin.layanan.internal.services.user.dto.LoginResponse;
import com.pusbin.layanan.internal.services.user.dto.RegisterRequest;
import com.pusbin.layanan.internal.services.user.dto.UserData;

public interface  UserService {
    UserData register(RegisterRequest request);
    LoginResponse login(LoginRequest request);
}
