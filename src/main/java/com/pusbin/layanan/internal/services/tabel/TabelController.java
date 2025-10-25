package com.pusbin.layanan.internal.services.tabel;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pusbin.layanan.common.dto.ApiResponse;
import com.pusbin.layanan.internal.common.dto.TabelPegawai;
import com.pusbin.layanan.internal.common.types.FilterDataAgregat;

@RestController
@RequestMapping("/api/v1/tabel")
public class TabelController {

    private final TabelService service;

    public TabelController(TabelService service) {
        this.service = service;
    }

    @GetMapping("/pegawai-berdasarkan-jabatan")
    public ApiResponse<Page<TabelPegawai>> tabelPegawaiByJabatan(
            @ModelAttribute FilterDataAgregat filter,
            @PageableDefault(size = 10, page = 0) Pageable pageable // Tambahkan ini
    ) {
        Page<TabelPegawai> data = service.getJumlahPegawaiBerdasarkanJabatan(filter, pageable);
        return ApiResponse.success("Data total pegawai berdasarkan jabatan berhasil ditemukan", data);
    }

    @GetMapping("/pegawai-berdasarkan-instansi")
    public ApiResponse<Page<TabelPegawai>> tabelPegawaiByInstansi(
            @ModelAttribute FilterDataAgregat filter,
            @PageableDefault(size = 10, page = 0) Pageable pageable // Tambahkan ini
    ) {
        Page<TabelPegawai> data = service.getJumlahPegawaiBerdasarkanInstansi(filter, pageable);
        // Pesan respons diperbaiki agar sesuai konteks
        return ApiResponse.success("Data total pegawai berdasarkan instansi berhasil ditemukan", data);
    }

    @GetMapping("/pegawai-berdasarkan-wilker")
    public ApiResponse<Page<TabelPegawai>> tabelPegawaiByWilker( // Nama method diperbaiki
            @ModelAttribute FilterDataAgregat filter,
            @PageableDefault(size = 10, page = 0) Pageable pageable // Tambahkan ini
    ) {
        Page<TabelPegawai> data = service.getJumlahPegawaiBerdasarkanWilker(filter, pageable);
        // Pesan respons diperbaiki agar sesuai konteks
        return ApiResponse.success("Data total pegawai berdasarkan wilayah kerja berhasil ditemukan", data);
    }
}