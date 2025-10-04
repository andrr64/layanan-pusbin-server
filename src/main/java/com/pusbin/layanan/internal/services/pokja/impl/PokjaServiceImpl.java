package com.pusbin.layanan.internal.services.pokja.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pusbin.layanan.common.dto.FilterValue;
import com.pusbin.layanan.common.dto.GetFilter;
import com.pusbin.layanan.internal.models.Pokja;
import com.pusbin.layanan.internal.services.pokja.PokjaRepository;
import com.pusbin.layanan.internal.services.pokja.PokjaService;

@Service
public class PokjaServiceImpl implements PokjaService {

    private final PokjaRepository repository;

    @Autowired
    public PokjaServiceImpl(PokjaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Pokja createPokja(String namaPokja) {
        Pokja dataBaru = new Pokja();
        dataBaru.setNamaPokja(namaPokja);
        return repository.save(dataBaru);
    }

    @Override
    public Optional<Pokja> getPokjaById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Pokja> getAllPokja() {
        return repository.findAll();
    }

    @Override
    public Pokja updatePokja(Long id, String namaPokja) {
        return repository.findById(id)
                .map(existing -> {
                    existing.setNamaPokja(namaPokja); // pakai parameter langsung
                    return repository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Pokja tidak ditemukan dengan id: " + id));
    }

    @Override
    public void deletePokja(Long id) {
        repository.deleteById(id);
    }

    @Override
    public GetFilter getFilters() {
        List<Pokja> listPokja = repository.findAll();

        List<FilterValue> values = listPokja.stream()
                .map(pokja -> new FilterValue(pokja.getNamaPokja(), pokja.getIdPokja().intValue()))
                .collect(Collectors.toList());

        return new GetFilter("pokjaId", "Pokja", values);
    }
}
