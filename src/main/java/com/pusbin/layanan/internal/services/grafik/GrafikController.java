package com.pusbin.layanan.internal.services.grafik;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pusbin.layanan.common.dto.ApiResponse;
import com.pusbin.layanan.internal.common.types.FilterDataAgregat;
import com.pusbin.layanan.internal.services.grafik.dto.GrafikKategori;
import com.pusbin.layanan.internal.services.grafik.dto.GrafikSebaranJFMASN;
import com.pusbin.layanan.internal.services.grafik.dto.GrafikSebaranJFK;
import com.pusbin.layanan.internal.services.grafik.dto.GrafikSebaranASNJFK;

@RestController
@RequestMapping("/api/v1/grafik")
public class GrafikController {

    private final GrafikService service;

    public GrafikController(GrafikService service) {
        this.service = service;
    }

    @GetMapping("/persentase-jf-masn")
    public ApiResponse<List<GrafikKategori>> persentaseJFMASN(
        @ModelAttribute FilterDataAgregat filter
    ) {
        try {
            return ApiResponse.success("OK", service.getGrafikPersentaseJFMASN(filter));
        } catch (Exception e) {
            return ApiResponse.fail(e.getMessage());
        }
    }

    @GetMapping("/sebaran-jf-masn-per-jenjang")
    public ApiResponse<List<GrafikSebaranJFMASN>> sebaranJFMASNbyJenjang(
        @ModelAttribute FilterDataAgregat filter
    ) {

        try {
            return ApiResponse.success("OK bro", service.getGrafikSebaranJFMASN(filter));
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage(), null);
        }
    }

    @GetMapping("/sebaran-jfk-per-kategori")
    public ApiResponse<List<GrafikSebaranJFK>> sebaranJFKbyKategori(
        @ModelAttribute FilterDataAgregat filter
    ) {
        try {
            return ApiResponse.success("OK bro", service.getGrafikSebaranJFK(filter));
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage(), null);
        }
    } // <── kurung tutup yang tadinya hilang

    @GetMapping("/sebaran-asnjfk")
    public ApiResponse<List<GrafikSebaranASNJFK>> sebaranASNJFKbyJenis(
        @ModelAttribute FilterDataAgregat filter
    ) {
        try {
            return ApiResponse.success("OK bro", service.getGrafikSebaranASNJFK(filter));
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage(), null);
        }
    }
}
