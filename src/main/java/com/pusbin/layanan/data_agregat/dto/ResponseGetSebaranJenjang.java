package com.pusbin.layanan.data_agregat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ResponseGetSebaranJenjang {
    private String namaJenjang;
    private String jenisAsn;
    private Long jumlah;
}
