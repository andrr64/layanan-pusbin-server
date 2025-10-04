package com.pusbin.layanan.internal.services.jenjang;

import java.util.List;
import java.util.Optional;

import com.pusbin.layanan.common.dto.GetFilter;
import com.pusbin.layanan.internal.models.Jenjang;

public interface JenjangService {
    Jenjang createJenjang(String Jenjang);
    Optional<Jenjang> getJenjangById(Long id);
    List<Jenjang> getAllJenjang();
    Jenjang updateJenjang(Long id, String Jenjang);
    void deleteJenjang(Long id);

    GetFilter getFilters();
}
