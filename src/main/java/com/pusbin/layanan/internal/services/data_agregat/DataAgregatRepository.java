package com.pusbin.layanan.internal.services.data_agregat;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.pusbin.layanan.internal.common.dto.TabelPegawai;
import com.pusbin.layanan.internal.models.DataAgregat;
import com.pusbin.layanan.internal.services.data_agregat.dto.ResponseGetGrafikPresentase;
import com.pusbin.layanan.internal.services.data_agregat.dto.ResponseGetInstansi;
import com.pusbin.layanan.internal.services.data_agregat.dto.ResponseGetJabatan;
import com.pusbin.layanan.internal.services.data_agregat.dto.ResponseGetSebaran;
import com.pusbin.layanan.internal.services.data_agregat.dto.ResponseGetSebaranInstansi;
import com.pusbin.layanan.internal.services.data_agregat.dto.ResponseGetSebaranJenjang;
import com.pusbin.layanan.internal.services.data_agregat.dto.ResponseGetWilayahKerja;

public interface DataAgregatRepository
        extends
        JpaRepository<DataAgregat, Long>,
        JpaSpecificationExecutor<DataAgregat> {

    @Query("SELECT SUM(d.jumlah) FROM DataAgregat d")
    Long getTotalPegawai();

    @Query("""
    SELECT new com.pusbin.layanan.internal.services.data_agregat.dto.ResponseGetJabatan(
        nj.namaJabatan,
        SUM(d.jumlah)
    )
    FROM DataAgregat d
    JOIN d.jabatan j
    JOIN j.namaJabatan nj
    GROUP BY nj.namaJabatan
    """)
    List<ResponseGetJabatan> getJumlahByJabatan();

    @Query("""
        SELECT new com.pusbin.layanan.internal.services.data_agregat.dto.ResponseGetWilayahKerja(
            w.namaWilayah,
            SUM(d.jumlah)
        )
        FROM DataAgregat d
        JOIN d.instansi i
        JOIN i.wilayahKerja w
        GROUP BY w.namaWilayah
    """)
    List<ResponseGetWilayahKerja> getJumlahByWilayahKerja();

    @Query("""
    SELECT new com.pusbin.layanan.internal.services.data_agregat.dto.ResponseGetInstansi(
        i.namaInstansi,
        SUM(d.jumlah)
    )
    FROM DataAgregat d
    JOIN d.instansi i
    GROUP BY i.namaInstansi
""")
    List<ResponseGetInstansi> getJumlahByInstansi();

    @Query("""
    SELECT new com.pusbin.layanan.internal.services.data_agregat.dto.ResponseGetSebaran(
        k.kategoriInstansi,
        jg.jenjang,
        SUM(d.jumlah)
    )
    FROM DataAgregat d
    JOIN d.instansi i
    JOIN i.kategoriInstansi k
    JOIN d.jabatan jb
    JOIN jb.jenjang jg
    GROUP BY k.kategoriInstansi, jg.jenjang
""")
    List<ResponseGetSebaran> getJumlahBySebaran();

    @Query("""
    SELECT new com.pusbin.layanan.internal.services.data_agregat.dto.ResponseGetSebaranInstansi(
        k.kategoriInstansi,
        nj.namaJabatan,
        SUM(d.jumlah)
    )
    FROM DataAgregat d
    JOIN d.instansi i
    JOIN i.kategoriInstansi k
    JOIN d.jabatan j
    JOIN j.namaJabatan nj
    GROUP BY k.kategoriInstansi, nj.namaJabatan
""")
    List<ResponseGetSebaranInstansi> getJumlahBySebaranInstansi();

    @Query("""
    SELECT new com.pusbin.layanan.internal.services.data_agregat.dto.ResponseGetSebaranJenjang(
        jg.jenjang,
        a.jenisAsn,
        SUM(d.jumlah)
    )
    FROM DataAgregat d
    JOIN d.jabatan j
    JOIN j.jenjang jg
    JOIN d.asn a
    GROUP BY jg.jenjang, a.jenisAsn
""")
    List<ResponseGetSebaranJenjang> getJumlahBySebaranJenjang();

    @Query("""
    SELECT new com.pusbin.layanan.internal.services.data_agregat.dto.ResponseGetGrafikPresentase(
        nj.namaJabatan,
        SUM(d.jumlah)
    )
    FROM DataAgregat d
    JOIN d.jabatan j
    JOIN j.namaJabatan nj
    GROUP BY nj.namaJabatan
""")
    List<ResponseGetGrafikPresentase> getJumlahByGrafikPresentase();

    @Query(
    value = """
        SELECT new com.pusbin.layanan.internal.services.common.dto.TabelPegawai(
        nj.namaJabatan,
        SUM(d.jumlah)
    )
    FROM DataAgregat d
    JOIN d.jabatan j
    JOIN j.namaJabatan nj
    GROUP BY nj.namaJabatan
    """,
    countQuery = """
        SELECT COUNT(DISTINCT nj.namaJabatan)
            FROM DataAgregat d
            JOIN d.jabatan j
            JOIN j.namaJabatan nj
    """
    )
    Page<TabelPegawai> getJumlahPegawaiBerdasarkanJabatan(Pageable pageable);

    @Query(
            value = """
        SELECT new com.pusbin.layanan.internal.services.common.dto.TabelPegawai(
            i.namaInstansi,
            SUM(d.jumlah)
        )
        FROM DataAgregat d
        JOIN d.instansi i
        GROUP BY i.namaInstansi
    """,
            countQuery = """
        SELECT COUNT(DISTINCT i.namaInstansi)
        FROM DataAgregat d
        JOIN d.instansi i
    """
    )
    Page<TabelPegawai> getJumlahBerdasarkanInstansi(Pageable pageable);

    @Query(
            value = """
        SELECT new com.pusbin.layanan.internal.services.common.dto.TabelPegawai(
            wk.namaWilayah,
            SUM(d.jumlah)
        )
        FROM DataAgregat d
        JOIN d.instansi di
        JOIN di.wilayahKerja wk
        GROUP BY wk.namaWilayah
    """,
            countQuery = """
        SELECT COUNT(DISTINCT wk.namaWilayah)
        FROM DataAgregat d
        JOIN d.instansi di
        JOIN di.wilayahKerja wk
    """
    )
    Page<TabelPegawai> getJumlahBerdasarkanWilker(Pageable pageable);

}
