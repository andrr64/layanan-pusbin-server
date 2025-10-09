package com.pusbin.layanan.internal.services.grafik;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.pusbin.layanan.internal.services.grafik.dto.GrafikPersentaseJFMASN;
import com.pusbin.layanan.common.dto.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/api/v1/grafik")
public class GrafikController {
    @Autowired
    private GrafikService service;

    @GetMapping("/persentase-jf-masn")
    public ApiResponse<GrafikPersentaseJFMASN> presentaseJFMASN() {
        try{
            GrafikPersentaseJFMASN result = service.getGrafikPersentaseJFMASN();
            return ApiResponse.success("OK", result);
        } catch (Exception e) {
            return ApiResponse.fail(e.getMessage());
        }
    }
}
