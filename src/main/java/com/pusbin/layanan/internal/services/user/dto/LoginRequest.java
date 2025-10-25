package com.pusbin.layanan.internal.services.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank(message = "NIP tidak boleh kosong")
    @Pattern(regexp = "\\d{18}", message = "NIP harus terdiri dari 18 digit angka")
    private String nip;

    @NotBlank(message = "Password tidak boleh kosong")
    @Size(min = 10, message = "Password minimal 10 karakter")
    private String password;
}
