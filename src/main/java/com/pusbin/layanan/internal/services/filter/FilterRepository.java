package com.pusbin.layanan.internal.services.filter;

import java.util.List;

import com.pusbin.layanan.common.dto.GetFilter;

public interface FilterRepository {

    GetFilter getDataAgregatFilterByKey(String key);

    List<GetFilter> getDataAgregatFilter(); // Method baru

}
