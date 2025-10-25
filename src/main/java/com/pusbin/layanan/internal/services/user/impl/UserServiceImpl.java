package com.pusbin.layanan.internal.services.user.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pusbin.layanan.common.utils.HashUtil;
import com.pusbin.layanan.exception.ConflictException;
import com.pusbin.layanan.exception.NotFoundException;
import com.pusbin.layanan.internal.models.User;
import com.pusbin.layanan.internal.services.user.UserRepository;
import com.pusbin.layanan.internal.services.user.UserService;
import com.pusbin.layanan.internal.services.user.dto.LoginRequest;
import com.pusbin.layanan.internal.services.user.dto.LoginResponse;
import com.pusbin.layanan.internal.services.user.dto.RegisterRequest;
import com.pusbin.layanan.internal.services.user.dto.UserData;
import com.pusbin.layanan.security.JwtUtil;


import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Transactional
    @Override
    public UserData register(RegisterRequest request) {
        if (repository.findByNip(request.getNip()).isPresent()){
            throw new RuntimeException("data telah digunakan");
        }

        if (repository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("data telah digunakan");
        }

        String hashPassword = HashUtil.hash(request.getPassword());

        User newUser = repository.createUser(request.getNip(), request.getEmail(), request.getNama(), hashPassword);

        UserData data = new UserData();

        data.setNip(newUser.getNip());
        data.setEmail(newUser.getEmail());
        data.setNama(newUser.getNama());
        return data;
    }

@Override
public LoginResponse login(LoginRequest request) {

    // Cari user berdasarkan NIP
    Optional<User> optionalUser = repository.findByNip(request.getNip());
    
    // Kalau user tidak ditemukan, lempar error
    if (optionalUser.isEmpty()) {
        throw new NotFoundException("User dengan NIP tersebut tidak ditemukan!");
    }

    User user = optionalUser.get();

    // Validasi password (pakai HashUtil.validate)
    boolean valid = HashUtil.validate(request.getPassword(), user.getPassword());
    if (!valid) {
        throw new NotFoundException("Password salah!");
    }

    // Jika lolos validasi, buat response
    LoginResponse response = new LoginResponse();
    String accessToken = JwtUtil.generateToken(user.getNip());
    response.setUser(UserData.fromUserModel(user));
    response.setAccess_token(accessToken);

    return response;
}


}
