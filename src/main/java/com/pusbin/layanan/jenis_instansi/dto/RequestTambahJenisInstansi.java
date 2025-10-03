package com.pusbin.layanan.jenis_instansi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RequestTambahJenisInstansi {
    @NotBlank(message = "Jenis Instansi Tidak Boleh kosong")
    @Size(max = 100, message = "Jenis Instansi maksimal 100 karakter")
    private String jenisInstansi;
}