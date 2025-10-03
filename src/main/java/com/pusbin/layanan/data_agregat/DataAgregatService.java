package com.pusbin.layanan.data_agregat;

import java.util.List;

import com.pusbin.layanan.data_agregat.dto.ResponseGetDataAgregat;
import com.pusbin.layanan.data_agregat.dto.ResponseGetGrafikPresentase;
import com.pusbin.layanan.data_agregat.dto.ResponseGetInstansi;
import com.pusbin.layanan.data_agregat.dto.ResponseGetJabatan;
import com.pusbin.layanan.data_agregat.dto.ResponseGetSebaran;
import com.pusbin.layanan.data_agregat.dto.ResponseGetSebaranInstansi;
import com.pusbin.layanan.data_agregat.dto.ResponseGetSebaranJenjang;
import com.pusbin.layanan.data_agregat.dto.ResponseGetTotalPegawai;
import com.pusbin.layanan.data_agregat.dto.ResponseGetWilayahKerja;
import com.pusbin.layanan.data_agregat.dto.params.FilterRequestTotalPegawaiByInstansi;

public interface DataAgregatService {
    List<ResponseGetDataAgregat> getAll(
        Long idInstansi, 
        Long idPokja, 
        Long idJenisInstansi,
        Long idAsn,
        Long idJenjang,
        Long idJabatan,
        Long idNamaJabatan,
        Long idKategoriInstansi,
        Long idNomenklatur,
        Long idWilayah
    );
    ResponseGetTotalPegawai getTotalPegawai();
    List<ResponseGetJabatan> getJabatan();
    List<ResponseGetWilayahKerja> getWilayahKerja();
    List<ResponseGetInstansi> getInstansi(
        FilterRequestTotalPegawaiByInstansi filterRequest
    );
    List<ResponseGetSebaran> getSebaran();
    List<ResponseGetSebaranInstansi> getSebaranInstansi();
    List<ResponseGetSebaranJenjang> getSebaranJenjang();
    List<ResponseGetGrafikPresentase> getGrafikPresentase();
}