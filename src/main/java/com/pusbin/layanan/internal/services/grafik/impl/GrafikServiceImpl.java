package com.pusbin.layanan.internal.services.grafik.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pusbin.layanan.internal.common.types.FilterDataAgregat;
import com.pusbin.layanan.internal.services.grafik.GrafikRepository;
import com.pusbin.layanan.internal.services.grafik.GrafikService;
import com.pusbin.layanan.internal.services.grafik.dto.GrafikKategori;
import com.pusbin.layanan.internal.services.grafik.dto.GrafikSebaranASNJFK;
import com.pusbin.layanan.internal.services.grafik.dto.GrafikSebaranJFK;
import com.pusbin.layanan.internal.services.grafik.dto.GrafikSebaranJFMASN;

// anotasi Service
@Service
public class GrafikServiceImpl implements GrafikService {

    // tukang ngambil data dari gudang/repository
    @Autowired
    GrafikRepository repository;

    @Override
    public List<GrafikKategori> getGrafikPersentaseJFMASN(FilterDataAgregat filter) {
        try {
            // ambil data dari repo
            return repository.readGrafikPersentaseJFMASN(filter);
        } catch (Exception e) {
            throw new RuntimeException("Maaf ya, terjadi kesalahan >_<");
        }
    }

    @Override
    public List<GrafikSebaranJFMASN> getGrafikSebaranJFMASN(FilterDataAgregat filter) {
        try {
            return repository.readGrafikSebaranJFMASN(filter);
        } catch (Exception e) {
            System.out.println(e.toString());
            throw new RuntimeException("Not supported yet.");
        }

    }

    @Override
    public List<GrafikSebaranJFK> getGrafikSebaranJFK(FilterDataAgregat filter) {
        try {
            return repository.readGrafikSebaranJFK(filter);
        } catch (Exception e) {
            System.out.println(e.toString());
            throw new RuntimeException("Not supported yet.");
        }

    }

    @Override
    public List<GrafikSebaranASNJFK> getGrafikSebaranASNJFK(FilterDataAgregat filter) {
        try {
            return repository.readGrafikSebaranASNJFK(filter);
        } catch (Exception e) {
            System.out.println(e.toString());
            throw new RuntimeException("Not supported yet.");
        }

    }

}
