package com.pusbin.layanan.internal.services.jenis_instansi;

import java.util.List;
import java.util.Optional;

import com.pusbin.layanan.common.dto.GetFilter;
import com.pusbin.layanan.internal.models.JenisInstansi;

public interface JenisInstansiService {
    JenisInstansi createJenisInstansi(String jenis_instansi);
    Optional<JenisInstansi> getJenisInstansiById(Long id_jenis_instansi);
    List<JenisInstansi> getAllJenisInstansi();
    JenisInstansi updateJenisInstansi(Long id_jenis_instansi, String jenis_instansi);
    void deleteJenisInstansi(Long id_jenis_instansi);

    GetFilter getFilters();
}
