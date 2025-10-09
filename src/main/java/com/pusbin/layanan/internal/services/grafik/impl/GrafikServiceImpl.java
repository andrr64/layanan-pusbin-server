package com.pusbin.layanan.internal.services.grafik.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pusbin.layanan.internal.services.grafik.GrafikRepository;
import com.pusbin.layanan.internal.services.grafik.GrafikService;
import com.pusbin.layanan.internal.services.grafik.dto.GrafikKategori;

// anotasi Service
@Service
public class GrafikServiceImpl implements GrafikService {

    // tukang ngambil data dari gudang/repository
    @Autowired
    GrafikRepository repository;

    @Override
    public List<GrafikKategori> getGrafikPersentaseJFMASN() {
        try {
            // ambil data dari repo
            return repository.readGrafikPersentaseJFMASN();
        } catch (Exception e) {
            throw new RuntimeException("Maaf ya, terjadi kesalahan >_<");
        }
    }

}
