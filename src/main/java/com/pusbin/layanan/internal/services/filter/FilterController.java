package com.pusbin.layanan.internal.services.filter;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pusbin.layanan.common.dto.ApiResponse;

@RestController
@RequestMapping("/api/v1/filters") // Disarankan untuk memberi nama resource yang jelas
public class FilterController {

    private final FilterService service; // Asumsi ada FilterService

    public FilterController(FilterService service) {
        this.service = service;
    }

    @GetMapping("/data-agregat")
    public ApiResponse<Object> getDataAgregatFilters(
            @RequestParam(value = "key", required = false) String key
    ) {
        try {
            // Skenario 1: Jika 'key' tidak disediakan (kosong), ambil semua filter
            if (key == null || key.isBlank()) {
                var allFilters = service.getDataAgregatFilter(); // Panggil method untuk semua filter
                return ApiResponse.success("Semua data filter berhasil ditemukan", allFilters);
            } 
            // Skenario 2: Jika 'key' disediakan, ambil filter spesifik
            else {
                var singleFilter = service.getDataAgregatFilterByKey(key); // Panggil method untuk satu filter
                if (singleFilter == null) {
                    return ApiResponse.fail("Filter dengan key '" + key + "' tidak ditemukan");
                }
                return ApiResponse.success("Data filter untuk key '" + key + "' berhasil ditemukan", singleFilter);
            }
        } catch (Exception e) {
            return ApiResponse.fail(e.getMessage());
        }
    }
}