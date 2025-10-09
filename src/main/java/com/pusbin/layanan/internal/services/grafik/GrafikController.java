package com.pusbin.layanan.internal.services.grafik;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pusbin.layanan.common.dto.ApiResponse;
import com.pusbin.layanan.internal.services.grafik.dto.GrafikKategori;

@RestController
@RequestMapping("/api/v1/grafik")
public class GrafikController {

    private final GrafikService service;

    public GrafikController(GrafikService service){
        this.service = service;
    }
    
    @GetMapping("/persentase-jf-masn")
    public ApiResponse<List<GrafikKategori>> persentaseJFMASN(){
        try {
            return ApiResponse.success("OK", service.getGrafikPersentaseJFMASN()); // kirim data (kalau berhasil)
        } catch (Exception e) {
            return ApiResponse.fail(e.getMessage()); // kirim pesan error
        }
    }
}
