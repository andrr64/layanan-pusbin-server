package com.pusbin.layanan.nama_jabatan;

import java.util.List;
import java.util.Optional;

import com.pusbin.common.dto.GetFilter;

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
