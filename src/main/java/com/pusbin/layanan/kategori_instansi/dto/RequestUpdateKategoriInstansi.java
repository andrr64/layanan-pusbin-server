package com.pusbin.layanan.kategori_instansi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RequestUpdateKategoriInstansi {
    @NotBlank(message = "Nama Kategori Instansi tidak boleh kosong")
    @Size(max = 100, message = "Nama Kategori Instansi maksimal 100 karakter")
    private String kategoriInstansi;
}
