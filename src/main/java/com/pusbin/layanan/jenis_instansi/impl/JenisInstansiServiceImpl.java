package com.pusbin.layanan.jenis_instansi.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pusbin.common.dto.FilterValue;
import com.pusbin.common.dto.GetFilter;
import com.pusbin.layanan.jenis_instansi.JenisInstansi;
import com.pusbin.layanan.jenis_instansi.JenisInstansiRepository;
import com.pusbin.layanan.jenis_instansi.JenisInstansiService;

@Service
public class JenisInstansiServiceImpl implements JenisInstansiService {

    private final JenisInstansiRepository repository;

    @Autowired
    public JenisInstansiServiceImpl(JenisInstansiRepository repository) {
        this.repository = repository;
    }

    @Override
    public JenisInstansi createJenisInstansi(String jenis_instansi) {
        JenisInstansi dataBaru = new JenisInstansi();
        dataBaru.setJenisInstansi(jenis_instansi);
        return repository.save(dataBaru);
    }

    @Override
    public Optional<JenisInstansi> getJenisInstansiById(Long id_jenis_instansi) {
        return repository.findById(id_jenis_instansi);
    }

    @Override
    public List<JenisInstansi> getAllJenisInstansi() {
        return repository.findAll();
    }

    @Override
    public JenisInstansi updateJenisInstansi(Long id_jenis_instansi, String jenis_instansi) {
        return repository.findById(id_jenis_instansi)
                .map(existing -> {
                    existing.setJenisInstansi(jenis_instansi);
                    return repository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException(
                        "Jenis Instansi tidak ditemukan dengan id: " + id_jenis_instansi));
    }

    @Override
    public void deleteJenisInstansi(Long id_jenis_instansi) {
        repository.deleteById(id_jenis_instansi);
    }

    @Override
public GetFilter getFilters() {  
    // Ambil semua data JenisInstansi
    List<JenisInstansi> listJenisInstansi = repository.findAll();   

    // Convert ke DTO FilterValue
    List<FilterValue> values = listJenisInstansi.stream()
        .map(jenisInstansi-> new FilterValue(jenisInstansi.getJenisInstansi(), jenisInstansi.getIdJenisInstansi().intValue()))
        .collect(Collectors.toList());
        
    // Bungkus ke GetFilter
    return new GetFilter("jenisInstansiId", "JENIS INSTANSI", values);
}

}