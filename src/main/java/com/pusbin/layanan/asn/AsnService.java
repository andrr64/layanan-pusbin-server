package com.pusbin.layanan.asn;

import java.util.List;
import java.util.Optional;

import com.pusbin.common.dto.GetFilter;

public interface AsnService {
    Asn createAsn(String jenisAsn);
    Optional<Asn> getAsnById(Long id_asn);
    List<Asn> getAllAsn();
    Asn updateAsn(Long id_asn, String jenisAsn);
    void deleteAsn(Long id_asn);

    GetFilter getFilters();
}
