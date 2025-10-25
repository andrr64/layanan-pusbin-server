package com.pusbin.layanan.internal.services.grafik;

import java.util.List;

import com.pusbin.layanan.internal.common.types.FilterDataAgregat;
import com.pusbin.layanan.internal.services.grafik.dto.GrafikKategori;
import com.pusbin.layanan.internal.services.grafik.dto.GrafikSebaranASNJFK;
import com.pusbin.layanan.internal.services.grafik.dto.GrafikSebaranJFK;
import com.pusbin.layanan.internal.services.grafik.dto.GrafikSebaranJFMASN;

public interface GrafikRepository {
    List<GrafikKategori> readGrafikPersentaseJFMASN(FilterDataAgregat filter);
    List<GrafikSebaranJFMASN> readGrafikSebaranJFMASN(FilterDataAgregat filter);
    List<GrafikSebaranJFK> readGrafikSebaranJFK(FilterDataAgregat filter);
    List<GrafikSebaranASNJFK> readGrafikSebaranASNJFK(FilterDataAgregat filter);
}