package com.pusbin.layanan.instansi.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pusbin.common.dto.FilterValue;
import com.pusbin.common.dto.GetFilter;
import com.pusbin.layanan.instansi.Instansi;
import com.pusbin.layanan.instansi.InstansiRepository;
import com.pusbin.layanan.instansi.InstansiService;
import com.pusbin.layanan.instansi.dto.ResponseGetTotalInstansi;
import com.pusbin.layanan.jenis_instansi.JenisInstansi;
import com.pusbin.layanan.jenis_instansi.JenisInstansiRepository;
import com.pusbin.layanan.kategori_instansi.KategoriInstansi;
import com.pusbin.layanan.kategori_instansi.KategoriInstansiRepository;
import com.pusbin.layanan.pokja.Pokja;
import com.pusbin.layanan.pokja.PokjaRepository;
import com.pusbin.layanan.wilayah_kerja.WilayahKerja;
import com.pusbin.layanan.wilayah_kerja.WilayahKerjaRepository;


@Service
public class InstansiServiceImpl implements InstansiService {

    private final InstansiRepository repository;
    private final KategoriInstansiRepository kategoriRepo;
    private final JenisInstansiRepository jenisRepo;
    private final WilayahKerjaRepository wilayahRepo;
    private final PokjaRepository pokjaRepo;

    @Autowired
    public InstansiServiceImpl(
            InstansiRepository repository,
            KategoriInstansiRepository kategoriRepo,
            JenisInstansiRepository jenisRepo,
            WilayahKerjaRepository wilayahRepo,
            PokjaRepository pokjaRepo
    ) {
        this.repository = repository;
        this.kategoriRepo = kategoriRepo;
        this.jenisRepo = jenisRepo;
        this.wilayahRepo = wilayahRepo;
        this.pokjaRepo = pokjaRepo;
    }

    @Override
    public Instansi createInstansi(String namaInstansi,
                                   Long idKategoriInstansi,
                                   Long idJenisInstansi,
                                   Long idWilayahKerja,
                                   Long idPokja) {

        KategoriInstansi kategori = kategoriRepo.findById(idKategoriInstansi)
                .orElseThrow(() -> new RuntimeException("Kategori Instansi tidak ditemukan"));
        JenisInstansi jenis = jenisRepo.findById(idJenisInstansi)
                .orElseThrow(() -> new RuntimeException("Jenis Instansi tidak ditemukan"));
        WilayahKerja wilayah = wilayahRepo.findById(idWilayahKerja)
                .orElseThrow(() -> new RuntimeException("Wilayah Kerja tidak ditemukan"));
        Pokja pokja = pokjaRepo.findById(idPokja)
                .orElseThrow(() -> new RuntimeException("Pokja tidak ditemukan"));

        Instansi dataBaru = new Instansi();
        dataBaru.setNamaInstansi(namaInstansi);
        dataBaru.setKategoriInstansi(kategori);
        dataBaru.setJenisInstansi(jenis);
        dataBaru.setWilayahKerja(wilayah);
        dataBaru.setPokja(pokja);

        return repository.save(dataBaru);
    }

    @Override
    public Optional<Instansi> getInstansiById(Long idInstansi) {
        return repository.findById(idInstansi);
    }

    @Override
    public List<Instansi> getAllInstansi() {
        return repository.findAll();
    }

    @Override
    public Instansi updateInstansi(Long idInstansi,
                                   String namaInstansi,
                                   Long idKategoriInstansi,
                                   Long idJenisInstansi,
                                   Long idWilayahKerja,
                                   Long idPokja) {

        KategoriInstansi kategori = kategoriRepo.findById(idKategoriInstansi)
                .orElseThrow(() -> new RuntimeException("Kategori Instansi tidak ditemukan"));
        JenisInstansi jenis = jenisRepo.findById(idJenisInstansi)
                .orElseThrow(() -> new RuntimeException("Jenis Instansi tidak ditemukan"));
        WilayahKerja wilayah = wilayahRepo.findById(idWilayahKerja)
                .orElseThrow(() -> new RuntimeException("Wilayah Kerja tidak ditemukan"));
        Pokja pokja = pokjaRepo.findById(idPokja)
                .orElseThrow(() -> new RuntimeException("Pokja tidak ditemukan"));

        return repository.findById(idInstansi)
                .map(existing -> {
                    existing.setNamaInstansi(namaInstansi);
                    existing.setKategoriInstansi(kategori);
                    existing.setJenisInstansi(jenis);
                    existing.setWilayahKerja(wilayah);
                    existing.setPokja(pokja);
                    return repository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Instansi tidak ditemukan dengan id: " + idInstansi));
    }

    @Override
    public void deleteInstansi(Long idInstansi) {
        repository.deleteById(idInstansi);
    }

    @Override
    public ResponseGetTotalInstansi getTotalInstansi() {
        Long total = repository.countInstansi();
        ResponseGetTotalInstansi response = new ResponseGetTotalInstansi();
        response.setJumlah(total);
        return response;
    }

        @Override
        public GetFilter getFilter() {
                //  Ambil semua data Instansi
        List<Instansi> listInstansi = repository.findAll();

        // Covert ke DTO FilterValue
        List<FilterValue> values = listInstansi.stream()
                .map(instansi -> new FilterValue(instansi.getNamaInstansi(), instansi.getIdInstansi().intValue()))
                .collect(Collectors.toList());           

                // Bungkus ke GetFilter
                return new GetFilter("instansiId", "INSTANSI", values);                                 
        }
}
