package com.pusbin.layanan.internal.services.total;

import com.pusbin.layanan.internal.common.types.FilterDataAgregat;

public interface TotalRepository {
    Long getTotalInstansi(FilterDataAgregat filter);
    Long getTotalPegawai(FilterDataAgregat filter);
}
