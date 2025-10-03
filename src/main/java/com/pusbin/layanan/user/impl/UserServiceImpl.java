package com.pusbin.layanan.user.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pusbin.common.utils.HashUtil;
import com.pusbin.layanan.exception.ConflictException;
import com.pusbin.layanan.security.JwtUtil;
import com.pusbin.layanan.user.User;
import com.pusbin.layanan.user.UserRepository;
import com.pusbin.layanan.user.UserService;
import com.pusbin.layanan.user.dto.LoginRequest;
import com.pusbin.layanan.user.dto.LoginResponse;
import com.pusbin.layanan.user.dto.RegisterRequest;
import com.pusbin.layanan.user.dto.UserData;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional // Jika ada kesalahan, maka data tidak akan tersimpan (rollback)
    public UserData register(RegisterRequest request) {
        repository.findByUsername(request.getUsername())
                .ifPresent(u -> {
                    throw new ConflictException("Username already taken");
                });
        repository.findByEmail(request.getEmail())
                .ifPresent(u -> {
                    throw new ConflictException("Email already registered");
                });

        // Simpan user baru
        User user = new User();
        String hashPassword = HashUtil.hash(request.getPassword());

        user.setNama(request.getNama());
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(hashPassword);

        User saved = repository.save(user);

        UserData data = UserData.fromUserModel(saved);
        return data;
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        // Cari user berdasarkan username
        User user = repository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User tidak ditemukan"));

        // Validasi password (pakai HashUtil.validate)
        boolean valid = HashUtil.validate(request.getPassword(), user.getPassword());
        if (!valid) {
            throw new RuntimeException("Username atau password salah");
        }

        // Jika lolos validasi, buat response
        LoginResponse response = new LoginResponse();
        String accessToken = JwtUtil.generateToken(user.getUsername());
        response.setUser(UserData.fromUserModel(user));
        response.setAccess_token(accessToken);

        return response;
    }

}
