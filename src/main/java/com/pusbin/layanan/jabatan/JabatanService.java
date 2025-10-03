package com.pusbin.layanan.jabatan;

import java.util.List;

import com.pusbin.layanan.jabatan.dto.RequestTambahJabatan;
import com.pusbin.layanan.jabatan.dto.RequestUpdateJabatan;
public interface JabatanService {
    List<RequestTambahJabatan> getAll();
    RequestTambahJabatan getById(Long id);
    RequestTambahJabatan create(RequestTambahJabatan request);
    RequestTambahJabatan update(Long id, RequestUpdateJabatan request);
    void delete(Long id);


  
    
}
