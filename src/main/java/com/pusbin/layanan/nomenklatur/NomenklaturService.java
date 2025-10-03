package com.pusbin.layanan.nomenklatur;

import java.util.List;
import java.util.Optional;

import com.pusbin.common.dto.GetFilter; 

public interface NomenklaturService {
    Nomenklatur createNomenklatur(String namaNomenklatur);
    Optional<Nomenklatur> getNomenklaturById(Long id_nomenklatur);
    List<Nomenklatur> getAllNomenklatur();
    Nomenklatur updateNomenklatur(Long id_nomenklatur, String namaNomenklatur);
    void deleteNomenklatur(Long id_nomenklatur);

    GetFilter getFilters();

}
