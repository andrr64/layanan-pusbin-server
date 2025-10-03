package com.pusbin.layanan.jenis_instansi;

import java.util.List;
import java.util.Optional;
import com.pusbin.common.dto.GetFilter;

public interface JenisInstansiService {
    JenisInstansi createJenisInstansi(String jenis_instansi);
    Optional<JenisInstansi> getJenisInstansiById(Long id_jenis_instansi);
    List<JenisInstansi> getAllJenisInstansi();
    JenisInstansi updateJenisInstansi(Long id_jenis_instansi, String jenis_instansi);
    void deleteJenisInstansi(Long id_jenis_instansi);

    GetFilter getFilters();
}
