package com.pusbin.layanan.common.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class HashUtil {
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    // Hash password
    public static String hash(String rawPassword) {
        return encoder.encode(rawPassword);
    }

    // Validasi password 
    public static boolean validate(
        String rawPassword, 
        String hashedPassword
    ) {
        return encoder.matches(rawPassword, hashedPassword);
    }
}
