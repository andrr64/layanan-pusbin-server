package com.pusbin.layanan.jabatan.impl;

import com.pusbin.layanan.jabatan.Jabatan;
import com.pusbin.layanan.jabatan.JabatanRepository;
import com.pusbin.layanan.jabatan.JabatanService;
import com.pusbin.layanan.jabatan.dto.RequestTambahJabatan;
import com.pusbin.layanan.jabatan.dto.RequestUpdateJabatan;
import com.pusbin.layanan.jenjang.Jenjang;
import com.pusbin.layanan.nama_jabatan.NamaJabatan;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JabatanServiceImpl implements JabatanService {

    private final JabatanRepository jabatanRepository;

    public JabatanServiceImpl(JabatanRepository jabatanRepository) {
        this.jabatanRepository = jabatanRepository;
    }

    @Override
    public List<RequestTambahJabatan> getAll() {
        return jabatanRepository.findAll().stream()
                .map(jabatan -> {
                    RequestTambahJabatan dto = new RequestTambahJabatan();
                    dto.setNamaJabatan(jabatan.getNamaJabatan().getNamaJabatan());
                    dto.setJenjang(jabatan.getJenjang().getJenjang());
                    return dto;
                })
                .toList();
    }

    @Override
    public RequestTambahJabatan getById(Long id) {
        Jabatan jabatan = jabatanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Jabatan not found"));

        RequestTambahJabatan dto = new RequestTambahJabatan();
        dto.setNamaJabatan(jabatan.getNamaJabatan().getNamaJabatan());
        dto.setJenjang(jabatan.getJenjang().getJenjang());
        return dto;
    }

    @Override
    public RequestTambahJabatan create(RequestTambahJabatan request) {
        Jabatan jabatan = new Jabatan();

        NamaJabatan namaJabatan = new NamaJabatan();
        namaJabatan.setNamaJabatan(request.getNamaJabatan());

        Jenjang jenjang = new Jenjang();
        jenjang.setJenjang(request.getJenjang());

        jabatan.setNamaJabatan(namaJabatan);
        jabatan.setJenjang(jenjang);

        Jabatan saved = jabatanRepository.save(jabatan);

        RequestTambahJabatan dto = new RequestTambahJabatan();
        dto.setNamaJabatan(saved.getNamaJabatan().getNamaJabatan());
        dto.setJenjang(saved.getJenjang().getJenjang());
        return dto;
    }

    @Override
    public RequestTambahJabatan update(Long id, RequestUpdateJabatan request) {
        Jabatan jabatan = jabatanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Jabatan not found"));

        jabatan.getNamaJabatan().setNamaJabatan(request.getNamaJabatan());
        jabatan.getJenjang().setJenjang(request.getJenjang());

        Jabatan updated = jabatanRepository.save(jabatan);

        RequestTambahJabatan dto = new RequestTambahJabatan();
        dto.setNamaJabatan(updated.getNamaJabatan().getNamaJabatan());
        dto.setJenjang(updated.getJenjang().getJenjang());
        return dto;
    }

    @Override
    public void delete(Long id) {
        jabatanRepository.deleteById(id);
    }

}
