package com.pusbin.layanan.internal.services.jenjang.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RequestUpdateJenjang {
    @NotBlank(message = "Nama jenjang tidak boleh kosong")
    @Size(max = 100, message = "Nama jenjang maksimal 100 karakter")
    private String jenjang;
}

