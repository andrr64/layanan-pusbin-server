package com.pusbin.layanan.internal.services.data_agregat;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pusbin.layanan.common.dto.ApiResponse;
import com.pusbin.layanan.internal.services.data_agregat.dto.ResponseGetDataAgregat;
import com.pusbin.layanan.internal.services.data_agregat.dto.ResponseGetGrafikPresentase;
import com.pusbin.layanan.internal.services.data_agregat.dto.ResponseGetInstansi;
import com.pusbin.layanan.internal.services.data_agregat.dto.ResponseGetJabatan;
import com.pusbin.layanan.internal.services.data_agregat.dto.ResponseGetSebaran;
import com.pusbin.layanan.internal.services.data_agregat.dto.ResponseGetSebaranInstansi;
import com.pusbin.layanan.internal.services.data_agregat.dto.ResponseGetSebaranJenjang;
import com.pusbin.layanan.internal.services.data_agregat.dto.ResponseGetTotalPegawai;
import com.pusbin.layanan.internal.services.data_agregat.dto.ResponseGetWilayahKerja;
import com.pusbin.layanan.internal.services.data_agregat.dto.params.FilterRequestTotalPegawaiByInstansi;

@RestController
@RequestMapping("/api/v1/data-agregat")
public class DataAgregatController {

    private final DataAgregatService service;

    public DataAgregatController(DataAgregatService service) {
        this.service = service;
    }

    @GetMapping
    public ApiResponse<List<ResponseGetDataAgregat>> getAll(
            @RequestParam(name = "instansi", required = false) Long idInstansi,
            @RequestParam(name = "pokja", required = false) Long idPokja,
            @RequestParam(name = "jenis_instansi", required = false) Long idJenisInstansi,
            @RequestParam(name = "asn", required = false) Long idAsn,
            @RequestParam(name = "jenjang", required = false) Long idJenjang,
            @RequestParam(name = "jabatan", required = false) Long idJabatan,
            @RequestParam(name = "nama_jabatan", required = false) Long idNamaJabatan,
            @RequestParam(name = "kategori_instansi", required = false) Long idKategoriInstansi,
            @RequestParam(name = "nomenklatur", required = false) Long idNomenklatur,
            @RequestParam(name = "wilayah_kerja", required = false) Long idWilayah
    ) {
        return ApiResponse.success(
                "Data agregat berhasil ditemukan",
                service.getAll(
                        idInstansi,
                        idPokja,
                        idJenisInstansi,
                        idAsn,
                        idJenjang,
                        idJabatan,
                        idNamaJabatan,
                        idKategoriInstansi,
                        idNomenklatur,
                        idWilayah
                )
        );
    }

    @GetMapping("/total-pegawai")
    public ApiResponse<ResponseGetTotalPegawai> getTotalPegawai() {
        return ApiResponse.success(
                "Total pegawai berhasil ditemukan",
                service.getTotalPegawai()
        );
    }

    @GetMapping("/tabel-total-pegawai-berdasarkan-jabatan")
    public ApiResponse<List<ResponseGetJabatan>> getJabatan() {
        return ApiResponse.success(
                "Data total pegawai berdasarkan jabatan berhasil ditemukan",
                service.getJabatan()
        );
    }

    @GetMapping("/tabel-total-pegawai-berdasarkan-wilayah-kerja")
    public ApiResponse<List<ResponseGetWilayahKerja>> getWilayahKerja() {
        return ApiResponse.success(
                "Data total pegawai berdasarkan wilayah kerja berhasil ditemukan",
                service.getWilayahKerja()
        );
    }

    @GetMapping("/tabel-total-pegawai-berdasarkan-instansi")
    public ApiResponse<List<ResponseGetInstansi>> getInstansi(
            @ModelAttribute FilterRequestTotalPegawaiByInstansi filterRequest
    ) {
        return ApiResponse.success(
                "Data total pegawai berdasarkan instansi berhasil ditemukan",
                service.getInstansi(filterRequest)
        );
    }

    @GetMapping("/grafik-sebaran")
    public ApiResponse<List<ResponseGetSebaran>> getSebaran() {
        return ApiResponse.success(
                "Data grafik sebaran berhasil ditemukan",
                service.getSebaran()
        );
    }

    @GetMapping("/grafik-sebaran-instansi")
    public ApiResponse<List<ResponseGetSebaranInstansi>> getSebaranInstansi() {
        return ApiResponse.success(
                "Data grafik sebaran instansi berhasil ditemukan",
                service.getSebaranInstansi()
        );
    }

    @GetMapping("/grafik-sebaran-jenjang")
    public ApiResponse<List<ResponseGetSebaranJenjang>> getSebaranJenjang() {
        return ApiResponse.success(
                "Data grafik sebaran jenjang berhasil ditemukan",
                service.getSebaranJenjang()
        );
    }

    @GetMapping("/grafik-presentase")
    public ApiResponse<List<ResponseGetGrafikPresentase>> getGrafikPresentase() {
        return ApiResponse.success(
                "Data grafik presentase berhasil ditemukan",
                service.getGrafikPresentase()
        );
    }
}
