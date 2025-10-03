package com.pusbin.layanan.jenjang.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pusbin.common.dto.FilterValue;
import com.pusbin.common.dto.GetFilter;
import com.pusbin.layanan.jenjang.Jenjang;
import com.pusbin.layanan.jenjang.JenjangRepository;
import com.pusbin.layanan.jenjang.JenjangService;

@Service
public class JenjangServiceImpl implements JenjangService {

    private final JenjangRepository repository;

    @Autowired
    public JenjangServiceImpl(JenjangRepository repository) {
        this.repository = repository;
    }

    @Override
    public Jenjang createJenjang(String jenjang) {
        Jenjang dataBaru = new Jenjang();
        dataBaru.setJenjang(jenjang);
        return repository.save(dataBaru);
    }

    @Override
    public Optional<Jenjang> getJenjangById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Jenjang> getAllJenjang() {
        return repository.findAll();
    }

    @Override
    public Jenjang updateJenjang(Long id, String jenjang) {
        return repository.findById(id)
                .map(existing ->{
                    existing.setJenjang(jenjang); // pakai parameter langsung
                    return repository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Jenjang tidak ditemukan dengan id: " + id));
    }
    @Override
    public void deleteJenjang(Long id) {
        repository.deleteById(id);
    }

    @Override
    public GetFilter getFilters() {
        //Ambiil semua data ASN
    List<Jenjang> listJenjang = repository.findAll();

        //Covert to DTO Filter Value
    List<FilterValue> values = listJenjang.stream()
        .map(jenjang -> new FilterValue(jenjang.getJenjang(), jenjang.getIdJenjang().intValue()))
        .collect(Collectors.toList());

        //Bungkus ke GetFilter
        return new GetFilter("jenjangId", "Jenjang", values);
    }

}
