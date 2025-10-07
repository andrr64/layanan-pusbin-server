package com.pusbin.layanan.internal.services.tabel.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.pusbin.layanan.internal.common.dto.TabelPegawai;
import com.pusbin.layanan.internal.common.types.FilterDataAgregat;
import com.pusbin.layanan.internal.services.tabel.TabelRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Repository
public class TabelRepositoryImpl implements TabelRepository {

    @PersistenceContext
    private EntityManager em;

    // ================================================================
    // QUERY PARTS RECORD
    // ================================================================
    private record QueryParts(
            StringBuilder joins,
            List<String> conditions,
            Map<String, Object> params,
            Set<String> usedAliases
            ) {

        QueryParts() {
            this(new StringBuilder(), new ArrayList<>(), new HashMap<>(), new HashSet<>());
        }

        void addJoin(String alias, String clause) {
            if (usedAliases.add(alias)) {
                joins.append(clause);
            }
        }
    }

    // ================================================================
    // IMPLEMENTASI REPOSITORY
    // ================================================================
    @Override
    public Page<TabelPegawai> getJumlahPegawaiBerdasarkanInstansiWithFilter(
            FilterDataAgregat filter, Pageable pageable
    ) {
        String selection = "i.namaInstansi";
        String baseFrom = "FROM DataAgregat d JOIN d.instansi i";
        // alias "i" sudah digunakan dari awal
        return buildData(selection, baseFrom, filter, pageable, Set.of("i"));
    }

    @Override
    public Page<TabelPegawai> getJumlahPegawaiBerdasarkanJabatanWithFilter(
            FilterDataAgregat filter, Pageable pageable
    ) {
        String selection = "nj.namaJabatan";
        String baseFrom = """
            FROM DataAgregat d
            JOIN d.jabatan j
            JOIN j.namaJabatan nj
        """;
        // alias "j" dan "nj" sudah digunakan dari awal
        return buildData(selection, baseFrom, filter, pageable, Set.of("j", "nj"));
    }

    @Override
    public Page<TabelPegawai> getJumlahPegawaiBerdasarkanWilkerWithFilter(
            FilterDataAgregat filter, Pageable pageable
    ) {
        String selection = "w.namaWilayah";
        String baseFrom = "FROM DataAgregat d";
        return buildData(selection, baseFrom, filter, pageable, Set.of());
    }

    // ================================================================
    // CORE BUILDER (UTAMA)
    // ================================================================
    private Page<TabelPegawai> buildData(
            String selection,
            String baseFrom,
            FilterDataAgregat filter,
            Pageable pageable,
            Set<String> initialAliases
    ) {
        QueryParts qp = new QueryParts();
        qp.usedAliases.addAll(initialAliases); // cegah join ganda
        applyFilters(qp, filter, selection);

        // Query data utama
        String dataQueryStr = String.format("""
            SELECT new com.pusbin.layanan.internal.common.dto.TabelPegawai(
                %s, SUM(d.jumlah)
            )
            %s
            %s
            %s
            GROUP BY %s
        """, selection, baseFrom, qp.joins, buildWhere(qp.conditions), selection);

        TypedQuery<TabelPegawai> dataQuery = em.createQuery(dataQueryStr, TabelPegawai.class);
        qp.params.forEach(dataQuery::setParameter);
        dataQuery.setFirstResult((int) pageable.getOffset());
        dataQuery.setMaxResults(pageable.getPageSize());

        List<TabelPegawai> results = dataQuery.getResultList();

        // Query count
        String countQueryStr = String.format("""
            SELECT COUNT(DISTINCT %s)
            %s
            %s
            %s
        """, selection, baseFrom, qp.joins, buildWhere(qp.conditions));

        TypedQuery<Long> countQuery = em.createQuery(countQueryStr, Long.class);
        qp.params.forEach(countQuery::setParameter);
        Long total = countQuery.getSingleResult();

        return new PageImpl<>(results, pageable, total);
    }

    // ================================================================
    // FILTER BUILDER
    // ================================================================
    private void applyFilters(QueryParts qp, FilterDataAgregat f, String selection) {
        // -- ASN
        if (notEmpty(f.getJenisAsnId())) {
            qp.addJoin("a", " JOIN d.asn a ");
            qp.conditions.add("a.idAsn IN :jenisAsnId");
            qp.params.put("jenisAsnId", f.getJenisAsnId());
        }

        // -- INSTANSI
        boolean needInstansi = notEmpty(f.getInstansiId())
                || notEmpty(f.getWilayahKerjaId())
                || notEmpty(f.getKategoriInstansiId())
                || notEmpty(f.getJenisInstansiId())
                || notEmpty(f.getPokjaId())
                || selection.startsWith("w.");

        if (needInstansi) {
            qp.addJoin("i", " JOIN d.instansi i ");
        }

        if (notEmpty(f.getInstansiId())) {
            qp.conditions.add("i.idInstansi IN :instansiId");
            qp.params.put("instansiId", f.getInstansiId());
        }

        // -- WILKER
        if (notEmpty(f.getWilayahKerjaId()) || selection.startsWith("w.")) {
            qp.addJoin("w", " JOIN i.wilayahKerja w ");
            if (notEmpty(f.getWilayahKerjaId())) {
                qp.conditions.add("w.idWilayah IN :wilayahKerjaId");
                qp.params.put("wilayahKerjaId", f.getWilayahKerjaId());
            }
        }

        // -- KATEGORI INSTANSI
        if (notEmpty(f.getKategoriInstansiId())) {
            qp.addJoin("k", " JOIN i.kategoriInstansi k ");
            qp.conditions.add("k.idKategoriInstansi IN :kategoriInstansiId");
            qp.params.put("kategoriInstansiId", f.getKategoriInstansiId());
        }

        // -- JENIS INSTANSI
        if (notEmpty(f.getJenisInstansiId())) {
            qp.addJoin("ji", " JOIN i.jenisInstansi ji ");
            qp.conditions.add("ji.idJenisInstansi IN :jenisInstansiId");
            qp.params.put("jenisInstansiId", f.getJenisInstansiId());
        }

        // -- POKJA
        if (notEmpty(f.getPokjaId())) {
            qp.addJoin("p", " JOIN i.pokja p ");
            qp.conditions.add("p.idPokja IN :pokjaId");
            qp.params.put("pokjaId", f.getPokjaId());
        }

        // -- JABATAN
        boolean needJabatan = notEmpty(f.getNamaJabatanId())
                || notEmpty(f.getJenjangId())
                || notEmpty(f.getNomenklaturId());

        if (needJabatan) {
            qp.addJoin("j", " JOIN d.jabatan j ");
        }

        if (notEmpty(f.getNamaJabatanId())) {
            qp.addJoin("nj", " JOIN j.namaJabatan nj ");
            qp.conditions.add("nj.idNamaJabatan IN :namaJabatanId");
            qp.params.put("namaJabatanId", f.getNamaJabatanId());
        }

        if (notEmpty(f.getJenjangId())) {
            qp.addJoin("jj", " JOIN j.jenjang jj ");
            qp.conditions.add("jj.idJenjang IN :jenjangId");
            qp.params.put("jenjangId", f.getJenjangId());
        }

        if (notEmpty(f.getNomenklaturId())) {
            qp.addJoin("nj", " JOIN j.namaJabatan nj ");
            qp.addJoin("nmk", " JOIN nj.nomenklatur nmk ");
            qp.conditions.add("nmk.idNomenklatur IN :nomenklaturId");
            qp.params.put("nomenklaturId", f.getNomenklaturId());
        }
    }

    // ================================================================
    // UTILITIES
    // ================================================================
    private boolean notEmpty(List<?> list) {
        return list != null && !list.isEmpty();
    }

    private String buildWhere(List<String> conditions) {
        return conditions.isEmpty() ? "" : "WHERE " + String.join(" AND ", conditions);
    }
}
