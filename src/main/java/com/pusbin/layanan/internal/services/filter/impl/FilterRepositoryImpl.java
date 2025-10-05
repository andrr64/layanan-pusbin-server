package com.pusbin.layanan.internal.services.filter.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.pusbin.layanan.common.dto.FilterValue;
import com.pusbin.layanan.common.dto.GetFilter;
import com.pusbin.layanan.internal.services.filter.FilterRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class FilterRepositoryImpl implements FilterRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public GetFilter getDataAgregatFilterByKey(String key) {
        String label;
        String jpqlQuery;

        // Menggunakan switch untuk memetakan 'key' ke query dan label yang sesuai
        switch (key) {
            case "instansiId" -> {
                label = "Instansi";
                // Perhatikan: constructor FilterValue(label, value), dan ID di-CAST ke int
                jpqlQuery = "SELECT new com.pusbin.layanan.common.dto.FilterValue(e.namaInstansi, CAST(e.idInstansi AS int)) FROM Instansi e ORDER BY e.namaInstansi";
            }
            case "namaJabatanId" -> {
                label = "Jabatan";
                jpqlQuery = "SELECT new com.pusbin.layanan.common.dto.FilterValue(e.namaJabatan, CAST(e.idNamaJabatan AS int)) FROM NamaJabatan e ORDER BY e.namaJabatan";
            }
            case "jenisAsnId" -> {
                label = "Jenis ASN";
                jpqlQuery = "SELECT new com.pusbin.layanan.common.dto.FilterValue(e.jenisAsn, CAST(e.idAsn AS int)) FROM Asn e ORDER BY e.jenisAsn";
            }
            case "jenjangId" -> {
                label = "Jenjang Jabatan";
                jpqlQuery = "SELECT new com.pusbin.layanan.common.dto.FilterValue(e.jenjang, CAST(e.idJenjang AS int)) FROM Jenjang e ORDER BY e.jenjang";
            }
            case "nomenklaturId" -> {
                label = "Nomenklatur Jabatan";
                jpqlQuery = "SELECT new com.pusbin.layanan.common.dto.FilterValue(e.namaNomenklatur, CAST(e.idNomenklatur AS int)) FROM Nomenklatur e ORDER BY e.namaNomenklatur";
            }
            case "kategoriInstansiId" -> {
                label = "Kategori Instansi";
                jpqlQuery = "SELECT new com.pusbin.layanan.common.dto.FilterValue(e.kategoriInstansi, CAST(e.idKategoriInstansi AS int)) FROM KategoriInstansi e ORDER BY e.kategoriInstansi";
            }
            case "wilayahKerjaId" -> {
                label = "Wilayah Kerja";
                jpqlQuery = "SELECT new com.pusbin.layanan.common.dto.FilterValue(e.namaWilayah, CAST(e.idWilayah AS int)) FROM WilayahKerja e ORDER BY e.namaWilayah";
            }
            case "jenisInstansiId" -> {
                label = "Jenis Instansi";
                jpqlQuery = "SELECT new com.pusbin.layanan.common.dto.FilterValue(e.jenisInstansi, CAST(e.idJenisInstansi AS int)) FROM JenisInstansi e ORDER BY e.jenisInstansi";
            }
            case "pokjaId" -> {
                label = "Pokja";
                jpqlQuery = "SELECT new com.pusbin.layanan.common.dto.FilterValue(e.namaPokja, CAST(e.idPokja AS int)) FROM Pokja e ORDER BY e.namaPokja";
            }
            default -> {
                // Jika key tidak cocok, kembalikan null
                return null;
            }
        }

        List<FilterValue> values = fetchValues(jpqlQuery);
        return new GetFilter(key, label, values);
    }

    @Override
    public List<GetFilter> getDataAgregatFilter() {
        // 1. Definisikan semua key filter yang ada
        List<String> filterKeys = List.of(
                "instansiId",
                "namaJabatanId",
                "jenisAsnId",
                "jenjangId",
                "nomenklaturId",
                "kategoriInstansiId",
                "wilayahKerjaId",
                "jenisInstansiId",
                "pokjaId"
        );

        List<GetFilter> allFilters = new ArrayList<>();

        // 2. Loop setiap key dan panggil method yang sudah ada
        for (String key : filterKeys) {
            GetFilter filterData = this.getDataAgregatFilterByKey(key);
            if (filterData != null) {
                allFilters.add(filterData);
            }
        }

        return allFilters;
    }

    /**
     * Helper method untuk menjalankan query dan mengambil hasilnya.
     */
    private List<FilterValue> fetchValues(String jpqlQuery) {
        return em.createQuery(jpqlQuery, FilterValue.class).getResultList();
    }
}
