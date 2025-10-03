package com.pusbin.layanan.asn.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pusbin.common.dto.FilterValue;
import com.pusbin.common.dto.GetFilter;
import com.pusbin.layanan.asn.Asn;
import com.pusbin.layanan.asn.AsnRepository;
import com.pusbin.layanan.asn.AsnService;



@Service
public class AsnServiceImpl implements AsnService {

    private final AsnRepository repository;

    @Autowired
    public AsnServiceImpl(AsnRepository repository) {
        this.repository = repository;
    }

    @Override
    public Asn createAsn(String jenisAsn) {
        Asn dataBaru = new Asn();
        dataBaru.setJenisAsn(jenisAsn);
        return repository.save(dataBaru);
    }

    @Override
    public Optional<Asn> getAsnById(Long id_asn) {
        return repository.findById(id_asn);
    }

    @Override
    public List<Asn> getAllAsn() {
        return repository.findAll();
    }

    @Override
    public Asn updateAsn(Long id_asn, String jenisAsn) {
        return repository.findById(id_asn)
                .map(existing -> {
                    existing.setJenisAsn(jenisAsn); //pakai parameter langsung
                    return repository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Jenis ASN tidak ditemukan dengan id: " + id_asn));
    }

    @Override
    public void deleteAsn(Long id_asn) {
        repository.deleteById(id_asn);
    }

    @Override
    public GetFilter getFilters() {
        //  Ambil semua data ASN
    List<Asn> listAsn = repository.findAll();

    // Covert ke DTO FilterValue
    List<FilterValue> values = listAsn.stream()
        .map(asn -> new FilterValue(asn.getJenisAsn(), asn.getIdAsn().intValue()))
        .collect(Collectors.toList());

        // Bungkus ke GetFilter
        return new GetFilter("jenisAsnId", "Jenis ASN", values);

}
}