package com.pusbin.layanan.nomenklatur.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RequestUpdateNomenklatur {
    @NotBlank(message = "Nama Nomenklatur Tidak Boleh kosong")
    @Size(max = 100, message = "Nama Nomenklatur maksimal 100 karakter")
    private String nama_nomenklatur;
}
