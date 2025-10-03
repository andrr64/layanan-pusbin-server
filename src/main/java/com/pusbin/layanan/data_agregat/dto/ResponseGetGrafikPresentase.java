package com.pusbin.layanan.data_agregat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseGetGrafikPresentase {
    private String namaJabatan;
    private Long jumlah;
}
 