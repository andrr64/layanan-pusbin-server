package com.pusbin.layanan.internal.services.grafik.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GrafikSebaranJFK {
    String kategori;
    long kategoriInstansiId;
    long jumlah;
    String nama;
    long namaJabatanId;
}
