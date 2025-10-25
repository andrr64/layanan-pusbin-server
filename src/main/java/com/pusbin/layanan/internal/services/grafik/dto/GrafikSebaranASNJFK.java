package com.pusbin.layanan.internal.services.grafik.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GrafikSebaranASNJFK {
    String jenis;
    long jenisAsnId;
    long jumlah;
    String jenjang;
    long jenjangId;
}
