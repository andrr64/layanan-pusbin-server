package com.pusbin.layanan.internal.services.instansi;

import java.util.List;
import java.util.Optional;

import com.pusbin.layanan.common.dto.GetFilter;
import com.pusbin.layanan.internal.models.Instansi;
import com.pusbin.layanan.internal.services.instansi.dto.ResponseGetTotalInstansi;

public interface InstansiService {
    Instansi createInstansi(
        String namaInstansi,
        Long idKategoriInstansi,
        Long idJenisInstansi,
        Long idWilayahKerja,
        Long idPokja
    );

    Optional<Instansi> getInstansiById(Long id_instansi);

    List<Instansi> getAllInstansi();

    Instansi updateInstansi(
        Long id_instansi,
        String namaInstansi,
        Long idKategoriInstansi,
        Long idJenisInstansi,
        Long idWilayahKerja,
        Long idPokja
    );

    void deleteInstansi(Long id_instansi);
    ResponseGetTotalInstansi getTotalInstansi();
    GetFilter getFilter();
}
