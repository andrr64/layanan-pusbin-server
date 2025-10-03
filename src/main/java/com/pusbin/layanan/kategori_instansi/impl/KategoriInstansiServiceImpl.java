package com.pusbin.layanan.kategori_instansi.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pusbin.common.dto.FilterValue;
import com.pusbin.common.dto.GetFilter;
import com.pusbin.layanan.kategori_instansi.KategoriInstansi;
import com.pusbin.layanan.kategori_instansi.KategoriInstansiRepository;
import com.pusbin.layanan.kategori_instansi.KategoriInstansiService;

@Service
public class KategoriInstansiServiceImpl implements KategoriInstansiService {

    private final KategoriInstansiRepository repository;

    @Autowired
    public KategoriInstansiServiceImpl(KategoriInstansiRepository repository) {
        this.repository = repository;
    }

    @Override
    public KategoriInstansi createKategoriInstansi(String kategoriInstansi) {
        KategoriInstansi dataBaru = new KategoriInstansi();
        dataBaru.setKategoriInstansi(kategoriInstansi);
        return repository.save(dataBaru);
    }

    @Override
    public Optional<KategoriInstansi> getKategoriInstansiById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<KategoriInstansi> getAllKategoriInstansi() {
        return repository.findAll();
    }

    @Override
    public KategoriInstansi updateKategoriInstansi(Long id, String kategoriInstansi) {
        return repository.findById(id)
                .map(existing -> {
                    existing.setKategoriInstansi(kategoriInstansi); // pakai parameter langsung
                    return repository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Kategori Instansi tidak ditemukan dengan id: " + id));
    }
    @Override
    public void deleteKategoriInstansi(Long id) {
        repository.deleteById(id);
    }

    @Override
    public GetFilter getFilters() {
        //Ambil semua data Kategori Instansi
    List<KategoriInstansi> listKategoriInstansi = repository.findAll();

        //Covert to DTO FilterValue
    List<FilterValue> values = listKategoriInstansi.stream()
        .map(kategoriInstansi -> new FilterValue(kategoriInstansi.getKategoriInstansi(), kategoriInstansi.getIdKategoriInstansi().intValue()))
        .collect(Collectors.toList());

        //Bungkus ke GetFilter
        return new GetFilter("kategoriInstansiId", "Kategori Instansi", values);
    }
}
