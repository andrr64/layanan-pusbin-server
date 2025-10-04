package com.pusbin.layanan.internal.services.jabatan.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RequestUpdateJabatan {
    @NotBlank(message = "Nama jabatan tidak boleh kosong")
    @Size(max = 100, message = "Nama jabatan maksimal 100 karakter")
    private String namaJabatan;
    private String jenjang;
}
