package com.pusbin.layanan.jenjang;

import java.util.List;
import java.util.Optional;

import com.pusbin.common.dto.GetFilter;

public interface JenjangService {
    Jenjang createJenjang(String Jenjang);
    Optional<Jenjang> getJenjangById(Long id);
    List<Jenjang> getAllJenjang();
    Jenjang updateJenjang(Long id, String Jenjang);
    void deleteJenjang(Long id);

    GetFilter getFilters();
}
