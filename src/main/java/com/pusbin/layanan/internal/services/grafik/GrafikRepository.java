package com.pusbin.layanan.internal.services.grafik;

import java.util.List;

import com.pusbin.layanan.internal.services.grafik.dto.GrafikKategori;

public interface GrafikRepository {
    List<GrafikKategori> readGrafikPersentaseJFMASN();
}