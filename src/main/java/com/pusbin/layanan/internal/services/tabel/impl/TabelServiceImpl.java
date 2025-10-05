package com.pusbin.layanan.internal.services.tabel.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.pusbin.layanan.internal.common.dto.TabelPegawai;
import com.pusbin.layanan.internal.common.types.FilterDataAgregat;
import com.pusbin.layanan.internal.services.tabel.TabelRepository;
import com.pusbin.layanan.internal.services.tabel.TabelService;

@Service
public class TabelServiceImpl implements TabelService {

    @Autowired
    TabelRepository tabelRepository;

    @Override
    public Page<TabelPegawai> getJumlahPegawaiBerdasarkanJabatan(FilterDataAgregat filter, Pageable pageable) {
        return tabelRepository.getJumlahPegawaiBerdasarkanJabatanWithFilter(filter, pageable);
    }

    @Override
    public Page<TabelPegawai> getJumlahPegawaiBerdasarkanInstansi(FilterDataAgregat filter, Pageable pageable) {
        return tabelRepository.getJumlahPegawaiBerdasarkanInstansiWithFilter(filter, pageable);
    }

    @Override
    public Page<TabelPegawai> getJumlahPegawaiBerdasarkanWilker(FilterDataAgregat filter, Pageable pageable) {
        return tabelRepository.getJumlahPegawaiBerdasarkanWilkerWithFilter(filter, pageable);
    }
}
