package com.pusbin.layanan.internal.services.filter.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pusbin.layanan.common.dto.GetFilter;
import com.pusbin.layanan.internal.services.filter.FilterRepository;
import com.pusbin.layanan.internal.services.filter.FilterService;

@Service
public class FilterServiceImpl implements FilterService {
    @Autowired
    FilterRepository filterRepo;

    @Override
    public GetFilter getDataAgregatFilterByKey(String key) {
        return filterRepo.getDataAgregatFilterByKey(key);
    }

    @Override
    public List<GetFilter> getDataAgregatFilter() {
        return filterRepo.getDataAgregatFilter();
    }
}