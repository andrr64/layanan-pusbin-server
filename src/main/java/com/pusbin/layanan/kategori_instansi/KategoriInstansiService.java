package com.pusbin.layanan.kategori_instansi;

import java.util.List;
import java.util.Optional;

import com.pusbin.common.dto.GetFilter;

public interface KategoriInstansiService {
    KategoriInstansi createKategoriInstansi(String kategoriInstansi);
    Optional<KategoriInstansi> getKategoriInstansiById(Long id);
    List<KategoriInstansi> getAllKategoriInstansi();
    KategoriInstansi updateKategoriInstansi(Long id, String kategoriInstansi);
    void deleteKategoriInstansi(Long id);

    GetFilter getFilters();
}
