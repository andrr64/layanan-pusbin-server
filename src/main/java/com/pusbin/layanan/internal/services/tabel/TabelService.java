package com.pusbin.layanan.internal.services.tabel;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.pusbin.layanan.internal.services.common.dto.TabelPegawai;
import com.pusbin.layanan.internal.services.common.types.FilterDataAgregat;

public interface TabelService {
    Page<TabelPegawai> getJumlahPegawaiBerdasarkanJabatan(FilterDataAgregat filter, Pageable pageable);
    Page<TabelPegawai> getJumlahPegawaiBerdasarkanInstansi(FilterDataAgregat filter,Pageable pageable);
    Page<TabelPegawai> getJumlahPegawaiBerdasarkanWilker(FilterDataAgregat filter,Pageable pageable);
}