package com.pusbin.layanan.internal.services.nama_jabatan;

import java.util.List;
import java.util.Optional;

import com.pusbin.layanan.common.dto.GetFilter;
import com.pusbin.layanan.internal.models.NamaJabatan;

public interface NamaJabatanService {
    NamaJabatan createNamaJabatan(
        String namaJabatan,
        Long idNomenklatur
    );

    Optional<NamaJabatan> getNamaJabatanById(Long id_nama_jabatan);

    List<NamaJabatan> getAllNamaJabatan();

    NamaJabatan updateNamaJabatan(
        Long id_nama_jabatan,
        String namaJabatan,
        Long idNomenklatur
    );

    void deleteNamaJabatan(Long id_nama_jabatan);
    GetFilter getFilters();

}
