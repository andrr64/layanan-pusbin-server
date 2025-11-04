package com.pusbin.layanan.internal.services.total;

import com.pusbin.layanan.internal.common.types.FilterDataAgregat;
import com.pusbin.layanan.internal.services.total.dto.TotalData;

public interface TotalService {
    TotalData totalPegawai(FilterDataAgregat filterDataAgregat);
    TotalData totalInstansi(FilterDataAgregat filterDataAgregat);
}
