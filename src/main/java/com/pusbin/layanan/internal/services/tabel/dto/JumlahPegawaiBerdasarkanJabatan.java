package com.pusbin.layanan.internal.services.tabel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JumlahPegawaiBerdasarkanJabatan {
    private String jabatan; 
    long id;
    private Long jumlah; 
}
