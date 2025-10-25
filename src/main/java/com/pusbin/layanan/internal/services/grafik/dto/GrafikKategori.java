package com.pusbin.layanan.internal.services.grafik.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GrafikKategori {
    String nama;
    long id;
    long jumlah;
}
