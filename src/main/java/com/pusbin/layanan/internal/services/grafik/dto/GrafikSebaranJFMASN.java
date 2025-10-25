package com.pusbin.layanan.internal.services.grafik.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GrafikSebaranJFMASN {
    String nama;
    long jenjangId;
    long jumlah; 
    String kategori;
    long kategoriInstansiId;
}
