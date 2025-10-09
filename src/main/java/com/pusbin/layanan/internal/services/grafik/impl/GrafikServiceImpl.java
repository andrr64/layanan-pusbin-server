package com.pusbin.layanan.internal.services.grafik.impl;

import com.pusbin.layanan.internal.services.grafik.dto.GrafikPersentaseJFMASN;
import com.pusbin.layanan.internal.services.grafik.GrafikService;

public class GrafikServiceImpl implements GrafikService {

    @Autowired
    GrafikRepository repository;

    @Override
    public GrafikPersentaseJFMASN getGrafikPersentaseJFMASN() {
        GrafikPesentaseJFMASN dataGrafik = new GrafikPresentaseJFMASN();
        try {
            //ambil data dari repo 
            List<GrafikKategori> dataDariRepo = repository.readGrafikPresentaseJFMASN();

            //set data grafik
            dataGrafik.setData(dataDariRepo);
        } catch (Exception e) {
            //buat error, kosngin dl
        }

        //Kirim data yg udah siap
        return dataGrafik;
    }
}
