package com.pusbin.layanan.internal.services.common.types;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FilterDataAgregat {
    private List<Long> jenisAsnId;
    private List<Long> instansiId;
    private List<Long> namaJabatanId;
    private List<Long> jenjangId;
    private List<Long> nomenklaturId;
    private List<Long> kategoriInstansiId;
    private List<Long> wilayahKerjaId;
    private List<Long> jenisInstansiId;
    private List<Long> pokjaId;
}
