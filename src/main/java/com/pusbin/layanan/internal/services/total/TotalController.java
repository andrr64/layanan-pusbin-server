package com.pusbin.layanan.internal.services.total;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pusbin.layanan.common.dto.ApiResponse;
import com.pusbin.layanan.internal.services.total.dto.TotalData;

@RestController
@RequestMapping("/api/v1/total")
public class TotalController {
    private final TotalService service;

    public TotalController(TotalService service) {
        this.service = service;
    }

    @GetMapping("/total-pegawai")
    public ApiResponse<TotalData> totalPegawai() {
        TotalData totalPegawai = service.totalPegawai();   
        return ApiResponse.success("OK", totalPegawai);
    }

    @GetMapping("/total-instansi")
    public ApiResponse<TotalData> totalInstansi() {
        TotalData totalInstansi = service.totalInstansi();
        return ApiResponse.success("OK", totalInstansi);
    }
}

