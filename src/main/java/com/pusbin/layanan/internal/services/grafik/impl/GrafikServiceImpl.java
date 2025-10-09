package com.pusbin.layanan.internal.services.grafik.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.pusbin.layanan.internal.services.grafik.dto.GrafikPersentaseJFMASN;
import com.pusbin.layanan.internal.services.grafik.GrafikService;
import com.pusbin.layanan.internal.services.grafik.GrafikRepository;
import com.pusbin.layanan.internal.services.grafik.dto.GrafikKategori;
import com.pusbin.layanan.internal.services.grafik.GrafikPresentaseJFMASN;

public class GrafikServiceImpl implements GrafikService {

    @Autowired
    GrafikRepository repository;

    @Override
    public GrafikPersentaseJFMASN getGrafikPersentaseJFMASN() {
        GrafikPersentaseJFMASN dataGrafik = new GrafikPersentaseJFMASN();
        try {
            //ambil data dari repo 
            List<GrafikKategori> dataDariRepo = repository.readGrafikPresentaseJFMASN();

            //set data grafik
            dataGrafik.setData(dataDariRepo);
        } catch (Exception e) {
            throw new RuntimeException("Maaf, terjadi kesalahan");
        }

        //Kirim data yg udah siap
        return dataGrafik;
    }
}
