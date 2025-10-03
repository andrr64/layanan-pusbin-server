package com.pusbin.layanan.data_agregat.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.pusbin.layanan.data_agregat.DataAgregat;
import com.pusbin.layanan.data_agregat.DataAgregatRepository;
import com.pusbin.layanan.data_agregat.DataAgregatService;
import com.pusbin.layanan.data_agregat.DataAgregatSpecification;
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

@Service
public class DataAgregatServiceImpl implements DataAgregatService {

    private final DataAgregatRepository repository;

    @Autowired
    public DataAgregatServiceImpl(DataAgregatRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ResponseGetDataAgregat> getAll(Long idInstansi, Long idPokja, Long idJenisInstansi, Long idAsn, Long idJenjang, Long idJabatan, Long idNamaJabatan, Long idKategoriInstansi, Long idNomenklatur, Long idWilayah) {
        Specification<DataAgregat> spec = (root, query, cb) -> cb.conjunction();

        if (idInstansi != null) {
            spec = spec.and(DataAgregatSpecification.hasInstansi(idInstansi));
        }

        if (idPokja != null) {
            spec = spec.and(DataAgregatSpecification.hasPokja(idPokja));
        }

        if (idJenisInstansi != null) {
            spec = spec.and(DataAgregatSpecification.hasJenis_instansi(idJenisInstansi));
        }

        if (idAsn != null) {
            spec = spec.and(DataAgregatSpecification.hasAsn(idAsn));
        }

        if (idJenjang != null) {
            spec = spec.and(DataAgregatSpecification.hasJenjang(idJenjang));
        }

        if (idJabatan != null) {
            spec = spec.and(DataAgregatSpecification.hasJabatan(idJabatan));
        }

        if (idNamaJabatan != null) {
            spec = spec.and(DataAgregatSpecification.hasNama_jabatan(idNamaJabatan));
        }

        if (idKategoriInstansi != null) {
            spec = spec.and(DataAgregatSpecification.hasKategori_instansi(idKategoriInstansi));
        }

        if (idNomenklatur != null) {
            spec = spec.and(DataAgregatSpecification.hasNomenklatur(idNomenklatur));
        }

        if (idWilayah != null) {
            spec = spec.and(DataAgregatSpecification.hasWilayah_kerja(idWilayah));
        }

        List<DataAgregat> listDataRaw = repository.findAll(spec);
        List<ResponseGetDataAgregat> listDataAgregat = new ArrayList<>();

        for (DataAgregat data : listDataRaw) {
            Long kode_instansi = data.getInstansi().getIdInstansi();
            String instansi = data.getInstansi().getNamaInstansi();
            String kategori_instansi = data.getInstansi().getKategoriInstansi().getKategoriInstansi();
            String jenis_instansi = data.getInstansi().getJenisInstansi().getJenisInstansi();
            String nama_jabatan = data.getJabatan().getNamaJabatan().getNamaJabatan();
            String jenjang = data.getJabatan().getJenjang().getJenjang();
            String jabatan = String.format("%s, %s", nama_jabatan, jenjang);
            String nomenklatur = data.getJabatan().getNamaJabatan().getNomenklatur().getNamaNomenklatur();
            String jenis_asn = data.getAsn().getJenisAsn();
            Long jumlah = data.getJumlah();
            String wilayah_kerja = data.getInstansi().getWilayahKerja().getNamaWilayah();
            String pokja = data.getInstansi().getPokja().getNamaPokja();

            ResponseGetDataAgregat dataAgregat = new ResponseGetDataAgregat();
            dataAgregat.setKode_instansi(kode_instansi);
            dataAgregat.setInstansi(instansi);
            dataAgregat.setKategori_instansi(kategori_instansi);
            dataAgregat.setJenis_instansi(jenis_instansi);
            dataAgregat.setNama_jabatan(nama_jabatan);
            dataAgregat.setJenjang(jenjang);
            dataAgregat.setJabatan(jabatan);
            dataAgregat.setNomenklatur(nomenklatur);
            dataAgregat.setJenis_asn(jenis_asn);
            dataAgregat.setJumlah(jumlah);
            dataAgregat.setWilayah_kerja(wilayah_kerja);
            dataAgregat.setPokja(pokja);

            // tambahkan ke list response kalau perlu
            listDataAgregat.add(dataAgregat);
        }
        return listDataAgregat;
    }

    @Override
    public ResponseGetTotalPegawai getTotalPegawai() {
        Long total = repository.getTotalPegawai();

        ResponseGetTotalPegawai response = new ResponseGetTotalPegawai();
        response.setJumlah(total != null ? total : 0L);
        return response;
    }

    @Override
    public List<ResponseGetJabatan> getJabatan() {
        List<ResponseGetJabatan> listJabatan = repository.getJumlahByJabatan();
        // TODO Auto-generated method stub
        return listJabatan;
    }

    @Override
    public List<ResponseGetWilayahKerja> getWilayahKerja() {
        List<ResponseGetWilayahKerja> listWilayahKerja = repository.getJumlahByWilayahKerja();
        return listWilayahKerja;
    }

    @Override
    public List<ResponseGetInstansi> getInstansi(FilterRequestTotalPegawaiByInstansi filterRequest) {
        // Mulai dari spec kosong
        Specification<DataAgregat> spec = (root, query, cb) -> cb.conjunction();

        // Tambahin filter satu per satu kalau ada
        if (filterRequest.getInstansiId() != null && !filterRequest.getInstansiId().isEmpty()) {
            spec = spec.and(DataAgregatSpecification.hasMultiInstansi(filterRequest.getInstansiId()));
        }
        if (filterRequest.getJenisAsnId() != null && !filterRequest.getJenisAsnId().isEmpty()) {
            spec = spec.and(DataAgregatSpecification.hasMultiAsn(filterRequest.getJenisAsnId()));
        }
        if (filterRequest.getNamaJabatanId() != null && !filterRequest.getNamaJabatanId().isEmpty()) {
            spec = spec.and(DataAgregatSpecification.hasMultiNamaJabatan(filterRequest.getNamaJabatanId()));
        }
        if (filterRequest.getJenjangId() != null && !filterRequest.getJenjangId().isEmpty()) {
            spec = spec.and(DataAgregatSpecification.hasMultiJenjang(filterRequest.getJenjangId()));
        }
        if (filterRequest.getNomenklaturId() != null && !filterRequest.getNomenklaturId().isEmpty()) {
            spec = spec.and(DataAgregatSpecification.hasMultiNomenklatur(filterRequest.getNomenklaturId()));
        }
        if (filterRequest.getKategoriInstansiId() != null && !filterRequest.getKategoriInstansiId().isEmpty()) {
            spec = spec.and(DataAgregatSpecification.hasMultiKategoriInstansi(filterRequest.getKategoriInstansiId()));
        }
        if (filterRequest.getWilayahKerjaId() != null && !filterRequest.getWilayahKerjaId().isEmpty()) {
            spec = spec.and(DataAgregatSpecification.hasMultiWilayahKerja(filterRequest.getWilayahKerjaId()));
        }
        if (filterRequest.getJenisInstansiId() != null && !filterRequest.getJenisInstansiId().isEmpty()) {
            spec = spec.and(DataAgregatSpecification.hasMultiJenisInstansi(filterRequest.getJenisInstansiId()));
        }
        if (filterRequest.getPokjaId() != null && !filterRequest.getPokjaId().isEmpty()) {
            spec = spec.and(DataAgregatSpecification.hasMultiPokja(filterRequest.getPokjaId()));
        }

        // Ambil data dari repository pakai spec
        List<DataAgregat> result = repository.findAll(spec);

        // Mapping ke ResponseGetInstansi (anggap ada mapper/constructor)
        return result.stream()
                .collect(Collectors.groupingBy(
                        // 1. Kelompokkan berdasarkan nama instansi
                        data -> data.getInstansi().getNamaInstansi(),
                        // 2. Jumlahkan field 'jumlah' untuk setiap kelompok
                        Collectors.summingLong(DataAgregat::getJumlah)
                ))
                // 3. Ubah hasil Map<String, Long> menjadi List<ResponseGetInstansi>
                .entrySet().stream()
                .map(entry -> new ResponseGetInstansi(entry.getKey(), entry.getValue()))
                .toList();
    }

    @Override
    public List<ResponseGetSebaran> getSebaran() {
        List<ResponseGetSebaran> listSebaran = repository.getJumlahBySebaran();
        return listSebaran;
    }

    @Override
    public List<ResponseGetSebaranInstansi> getSebaranInstansi() {
        List<ResponseGetSebaranInstansi> listSebaranInstansi = repository.getJumlahBySebaranInstansi();
        return listSebaranInstansi;
    }

    @Override
    public List<ResponseGetSebaranJenjang> getSebaranJenjang() {
        List<ResponseGetSebaranJenjang> listSebaranJenjang = repository.getJumlahBySebaranJenjang();
        return listSebaranJenjang;
    }

    @Override
    public List<ResponseGetGrafikPresentase> getGrafikPresentase() {
        List<ResponseGetGrafikPresentase> listGrafikPresentase = repository.getJumlahByGrafikPresentase();
        return listGrafikPresentase;
    }
}
