package com.pusbin.layanan.internal.services.total;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pusbin.layanan.common.dto.ApiResponse;
import com.pusbin.layanan.internal.common.types.FilterDataAgregat;
import com.pusbin.layanan.internal.services.total.dto.TotalData;

@RestController
@RequestMapping("/api/v1/total")
public class TotalController {
    private final TotalService service;

    public TotalController(TotalService service) {
        this.service = service;
    }

    @GetMapping("/total-pegawai")
    public ApiResponse<TotalData> totalPegawai(
            @ModelAttribute FilterDataAgregat filter
    ) {
        TotalData totalPegawai = service.totalPegawai(filter);
        return ApiResponse.success("OK", totalPegawai);
    }

    @GetMapping("/total-instansi")
    public ApiResponse<TotalData> totalInstansi(
            @ModelAttribute FilterDataAgregat filter
    ) {
        TotalData totalInstansi = service.totalInstansi(filter);
        return ApiResponse.success("OK", totalInstansi);
    }
}
