package com.pusbin.layanan.internal.services.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotBlank(message = "Nama tidak boleh kosong")
    private String nama;

    @NotBlank(message = "NIP tidak boleh kosong")
    @Pattern(regexp = "\\d{18}", message = "NIP harus terdiri dari 18 digit angka")
    private String nip;

    @NotBlank(message = "Email tidak boleh kosong")
    @Email(message = "Format email tidak valid")
    private String email;

    @NotBlank(message = "Password tidak boleh kosong")
    @Size(min = 10, message = "Password minimal 10 karakter")
    private String password;
}
