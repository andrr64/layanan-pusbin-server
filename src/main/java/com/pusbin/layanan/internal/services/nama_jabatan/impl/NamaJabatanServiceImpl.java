package com.pusbin.layanan.internal.services.nama_jabatan.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.pusbin.layanan.common.dto.FilterValue;
import com.pusbin.layanan.common.dto.GetFilter;
import com.pusbin.layanan.internal.models.NamaJabatan;
import com.pusbin.layanan.internal.models.Nomenklatur;
import com.pusbin.layanan.internal.services.nama_jabatan.NamaJabatanRepository;
import com.pusbin.layanan.internal.services.nama_jabatan.NamaJabatanService;
import com.pusbin.layanan.internal.services.nomenlaktur.NomenklaturRepository;

@Service
public class NamaJabatanServiceImpl implements NamaJabatanService {

    private final NamaJabatanRepository namaJabatanRepository;
    private final NomenklaturRepository nomenklaturRepository;

    public NamaJabatanServiceImpl(
            NamaJabatanRepository namaJabatanRepository,
            NomenklaturRepository nomenklaturRepository
    ) {
        this.namaJabatanRepository = namaJabatanRepository;
        this.nomenklaturRepository = nomenklaturRepository;
    }

    @Override
    public NamaJabatan createNamaJabatan(String namaJabatan, Long idNomenklatur) {
        Nomenklatur nomenklatur = nomenklaturRepository.findById(idNomenklatur)
                .orElseThrow(() -> new RuntimeException("Nomenklatur tidak ditemukan"));

        NamaJabatan entity = new NamaJabatan();
        entity.setNamaJabatan(namaJabatan);
        entity.setNomenklatur(nomenklatur);

        return namaJabatanRepository.save(entity);
    }

    @Override
    public Optional<NamaJabatan> getNamaJabatanById(Long idNamaJabatan) {
        return namaJabatanRepository.findById(idNamaJabatan);
    }

    @Override
    public List<NamaJabatan> getAllNamaJabatan() {
        return namaJabatanRepository.findAll();
    }

    @Override
    public NamaJabatan updateNamaJabatan(Long idNamaJabatan, String namaJabatan, Long idNomenklatur) {
        Nomenklatur nomenklatur = nomenklaturRepository.findById(idNomenklatur)
                .orElseThrow(() -> new RuntimeException("Nomenklatur tidak ditemukan"));

        return namaJabatanRepository.findById(idNamaJabatan)
                .map(existing -> {
                    existing.setNamaJabatan(namaJabatan);
                    existing.setNomenklatur(nomenklatur);
                    return namaJabatanRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Nama Jabatan tidak ditemukan dengan id: " + idNamaJabatan));
    }

    @Override
    public void deleteNamaJabatan(Long idNamaJabatan) {
        namaJabatanRepository.deleteById(idNamaJabatan);
    }

    @Override
    public GetFilter getFilters() {
        // Ambil semua data Nama Jabatan
        List<NamaJabatan> listNamaJabatan = namaJabatanRepository.findAll();

        // Convert ke DTO FilterValue
        List<FilterValue> values = listNamaJabatan.stream()
                .map(namaJabatan -> new FilterValue(
                        namaJabatan.getNamaJabatan(),
                        namaJabatan.getIdNamaJabatan().intValue()
                ))
                .collect(Collectors.toList());

        // Bungkus ke GetFilter
        return new GetFilter("namaJabatanId", "NAMA JABATAN", values);
    }
}
