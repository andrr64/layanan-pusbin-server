package com.pusbin.layanan.internal.services.tabel;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.pusbin.layanan.internal.services.common.dto.TabelPegawai;
import com.pusbin.layanan.internal.services.common.types.FilterDataAgregat;

public interface TabelRepository {

    Page<TabelPegawai> getJumlahPegawaiBerdasarkanInstansiWithFilter(
            FilterDataAgregat filter,
            Pageable pageable
    );

    Page<TabelPegawai> getJumlahPegawaiBerdasarkanJabatanWithFilter(
            FilterDataAgregat filter,
            Pageable pageable
    );

    Page<TabelPegawai> getJumlahPegawaiBerdasarkanWilkerWithFilter(
            FilterDataAgregat filter,
            Pageable pageable
    );
}
