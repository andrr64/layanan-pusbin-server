package com.pusbin.layanan.internal.services.kategori_instansi;

import java.util.List;
import java.util.Optional;

import com.pusbin.layanan.common.dto.GetFilter;
import com.pusbin.layanan.internal.models.KategoriInstansi;

public interface KategoriInstansiService {
    KategoriInstansi createKategoriInstansi(String kategoriInstansi);
    Optional<KategoriInstansi> getKategoriInstansiById(Long id);
    List<KategoriInstansi> getAllKategoriInstansi();
    KategoriInstansi updateKategoriInstansi(Long id, String kategoriInstansi);
    void deleteKategoriInstansi(Long id);

    GetFilter getFilters();
}
