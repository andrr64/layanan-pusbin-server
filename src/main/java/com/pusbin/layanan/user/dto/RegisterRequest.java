package com.pusbin.layanan.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank
    private String nama;

    @NotBlank(message = "Username tidak boleh kosong")
    @Size(min = 6, message = "Username minimal 4 karakter")
    private String username;

    @NotBlank(message = "Email tidak boleh kosong")
    @Email(message = "Format email tidak valid")
    private String email;

    @NotBlank(message = "Password tidak boleh kosong")
    @Size(min = 10, message = "Password minimal 10 karakter")
    private String password;
}
