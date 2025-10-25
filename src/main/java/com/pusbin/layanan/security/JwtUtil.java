package com.pusbin.layanan.security;

import java.security.Key;
import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class JwtUtil {

    // ganti ini, sebaiknya ambil dari application.properties
    private static final String SECRET_KEY = "iniSecretKeyPanjangBiarAmanBangetMinimal32Karakter!!!";
    private static final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 jam

    private static final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    public static long getExpiredTime() {
        return EXPIRATION_TIME;
    }

    // Generate token dengan username
    public static String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // Generate token dengan userId juga
    public static String generateTokenWithUserId(int userId, String username) {
        return Jwts.builder()
                .setSubject(username)
                .claim("userId", userId) // custom claim
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // Ambil username dari token
    public static String getUsername(String token) {
        return parseClaims(token).getSubject();
    }

    // Ambil userId dari token
    public static Integer getUserId(String token) {
        return parseClaims(token).get("userId", Integer.class);
    }

    // Validasi token
    public static boolean validateToken(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private static Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
