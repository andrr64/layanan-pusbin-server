package com.pusbin.layanan.internal.services.data_agregat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseGetInstansi {
    private String instansi;
    private Long jumlah;
}
