package com.pusbin.layanan.internal.services.total.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pusbin.layanan.internal.services.total.TotalRepository;
import com.pusbin.layanan.internal.services.total.TotalService;
import com.pusbin.layanan.internal.services.total.dto.TotalData;

@Service
public class TotalServiceImpl implements TotalService {
    

    @Autowired
    TotalRepository repository;

    @Override
    public TotalData totalPegawai () {
        TotalData data = new TotalData("Total Pegawai", repository.getTotalPegawai());
        return data;
    }

    @Override
    public TotalData totalInstansi() {
        TotalData data = new TotalData("Total Instansi", repository.getTotalInstansi());
        return data; 
    }
}
