package com.pusbin.layanan.internal.services.wilayah_kerja;

import java.util.List;
import java.util.Optional;

import com.pusbin.layanan.common.dto.GetFilter;
import com.pusbin.layanan.internal.models.WilayahKerja;


public interface WilayahKerjaService {
    WilayahKerja createWilayah(String namaWilayah);
    Optional<WilayahKerja> getWilayahById(Long id);
    List<WilayahKerja> getAllWilayah();
    WilayahKerja updateWilayah(Long id, String namaWilayah);
    void deleteWilayah(Long id);

    GetFilter getFilters();
}