package com.pusbin.layanan.internal.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TabelPegawai {
    private String nama;
    private long id;
    private long jumlah;
}
