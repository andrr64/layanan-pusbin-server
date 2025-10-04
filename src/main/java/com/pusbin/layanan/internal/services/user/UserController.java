package com.pusbin.layanan.internal.services.user;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pusbin.layanan.common.dto.ApiResponse;
import com.pusbin.layanan.internal.services.user.dto.LoginRequest;
import com.pusbin.layanan.internal.services.user.dto.LoginResponse;
import com.pusbin.layanan.internal.services.user.dto.RegisterRequest;
import com.pusbin.layanan.internal.services.user.dto.UserData;
import com.pusbin.layanan.security.JwtUtil;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/auth/login")
    public ApiResponse<LoginResponse> login(
            @Valid @RequestBody LoginRequest request,
            HttpServletResponse httpResponse
    ) {
        LoginResponse response = service.login(request);

        Cookie cookie = new Cookie("access_token", response.getAccess_token());
        cookie.setHttpOnly(false);
        cookie.setSecure(false); // aktifkan kalau pakai HTTPS
        cookie.setPath("/");
        cookie.setMaxAge((int) JwtUtil.getExpiredTime()); // 1 jam
        httpResponse.addCookie(cookie);

        return ApiResponse.success("OK bro", response);
    }

    @PostMapping("/auth/register")
    public ApiResponse<UserData> register(
            @Valid @RequestBody RegisterRequest request // @Valid -> memastikan user meng-input semua data (gak boleh kosong!)
    ) {
        UserData dataUser = service.register(request);
        return ApiResponse.success("OK bro", dataUser);
    }

}
