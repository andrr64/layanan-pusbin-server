package com.pusbin.layanan.nomenklatur.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pusbin.common.dto.FilterValue;
import com.pusbin.common.dto.GetFilter;
import com.pusbin.layanan.nomenklatur.Nomenklatur;
import com.pusbin.layanan.nomenklatur.NomenklaturRepository;
import com.pusbin.layanan.nomenklatur.NomenklaturService;

@Service
public class NomenklaturServiceImpl implements NomenklaturService {
    private final NomenklaturRepository repository;

    @Autowired
    public NomenklaturServiceImpl(NomenklaturRepository repository) {
        this.repository = repository;
    }

    @Override
    public Nomenklatur createNomenklatur(String namaNomenklatur) {
        Nomenklatur dataBaru = new Nomenklatur();
        dataBaru.setNamaNomenklatur(namaNomenklatur);
        return repository.save(dataBaru);
    }

    @Override
    public Optional<Nomenklatur> getNomenklaturById(Long id_nomenklatur) {
        return repository.findById(id_nomenklatur);
    }

    @Override
    public List<Nomenklatur> getAllNomenklatur() {
        return repository.findAll();
    }

    @Override
    public Nomenklatur updateNomenklatur(Long id_nomenklatur, String namaNomenklatur) {
        return repository.findById(id_nomenklatur)
                .map(existing -> {
                    existing.setNamaNomenklatur(namaNomenklatur);
                    return repository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException(
                        "Nomenklatur tidak ditemukan dengan id: " + id_nomenklatur));
    }

    @Override
    public void deleteNomenklatur(Long id_nomenklatur) {
        repository.deleteById(id_nomenklatur);
    }

    @Override
    public GetFilter getFilters() {
        // Ambil semua data Nomenklatur
        List<Nomenklatur> listNomenklatur = repository.findAll();

        // Convert ke DTO FilterValue
        List<FilterValue> filterValues = listNomenklatur.stream()
                .map(nomenklatur -> new FilterValue(nomenklatur.getNamaNomenklatur(), nomenklatur.getIdNomenklatur().intValue()))
                .collect(Collectors.toList());
        return new GetFilter("nomenklaturId", "Nomenklatur", filterValues);
    }
}
