package com.pusbin.layanan.internal.services.asn;

import java.util.List;
import java.util.Optional;

import com.pusbin.layanan.common.dto.GetFilter;
import com.pusbin.layanan.internal.models.Asn;

public interface AsnService {
    Asn createAsn(String jenisAsn);
    Optional<Asn> getAsnById(Long id_asn);
    List<Asn> getAllAsn();
    Asn updateAsn(Long id_asn, String jenisAsn);
    void deleteAsn(Long id_asn);

    GetFilter getFilters();
}
