package com.pusbin.layanan.internal.services.pokja;

import java.util.List;
import java.util.Optional;

import com.pusbin.layanan.common.dto.GetFilter;
import com.pusbin.layanan.internal.models.Pokja;

public interface PokjaService {
    Pokja createPokja(String namaPokja);
    Optional<Pokja> getPokjaById(Long id);
    List<Pokja> getAllPokja();
    Pokja updatePokja(Long id, String namaPokja);
    void deletePokja(Long id);

    GetFilter getFilters();
}
