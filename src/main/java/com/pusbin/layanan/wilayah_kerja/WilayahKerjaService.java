package com.pusbin.layanan.wilayah_kerja;

import java.util.List;
import java.util.Optional;

import com.pusbin.common.dto.GetFilter;


public interface WilayahKerjaService {
    WilayahKerja createWilayah(String namaWilayah);
    Optional<WilayahKerja> getWilayahById(Long id);
    List<WilayahKerja> getAllWilayah();
    WilayahKerja updateWilayah(Long id, String namaWilayah);
    void deleteWilayah(Long id);

    GetFilter getFilters();
}