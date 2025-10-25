package com.pusbin.layanan.internal.services.grafik;

import java.util.List;

import com.pusbin.layanan.internal.common.types.FilterDataAgregat;
import com.pusbin.layanan.internal.services.grafik.dto.GrafikKategori;
import com.pusbin.layanan.internal.services.grafik.dto.GrafikSebaranJFK;
import com.pusbin.layanan.internal.services.grafik.dto.GrafikSebaranJFMASN;
import com.pusbin.layanan.internal.services.grafik.dto.GrafikSebaranASNJFK;

public interface  GrafikService {
    List<GrafikKategori> getGrafikPersentaseJFMASN(FilterDataAgregat filter);
    List<GrafikSebaranJFMASN> getGrafikSebaranJFMASN(FilterDataAgregat filter);
    List<GrafikSebaranJFK> getGrafikSebaranJFK(FilterDataAgregat filter);
    List<GrafikSebaranASNJFK> getGrafikSebaranASNJFK(FilterDataAgregat filter);
}
