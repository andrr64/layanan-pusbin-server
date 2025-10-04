package com.pusbin.layanan.internal.services.nama_jabatan.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RequestTambahNamaJabatan {
    @NotBlank(message = "Nama Jabatan Tidak Boleh kosong")
    @Size(max = 100, message = "Nama Jabatan maksimal 100 karakter")
    private String nama_jabatan;
    private String nomenklatur;
}