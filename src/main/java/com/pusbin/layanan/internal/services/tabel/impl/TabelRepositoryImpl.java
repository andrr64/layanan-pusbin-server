package com.pusbin.layanan.internal.services.tabel.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.pusbin.layanan.internal.common.dto.TabelPegawai;
import com.pusbin.layanan.internal.common.types.FilterDataAgregat;
import com.pusbin.layanan.internal.services.tabel.TabelRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class TabelRepositoryImpl implements TabelRepository {

    @PersistenceContext
    private EntityManager em;

    // ================================================================
    // RECORD QUERY PARTS
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
    // UTILITAS UMUM
    // ================================================================
    private boolean notEmpty(List<?> list) {
        return list != null && !list.isEmpty();
    }

    private String buildWhere(List<String> conditions) {
        return conditions.isEmpty() ? "" : "WHERE " + String.join(" AND ", conditions);
    }

    // ================================================================
    // FILTER BUILDER
    // ================================================================
    private void applyFilters(QueryParts qp, FilterDataAgregat f) {
        if (notEmpty(f.getJenisAsnId())) {
            qp.addJoin("jasn", " JOIN asn jasn ON da.id_jenis_asn = jasn.id_asn ");
            qp.conditions.add("jasn.id_asn IN :jenisAsnId");
            qp.params.put("jenisAsnId", f.getJenisAsnId());
        }

        boolean needInstansi = notEmpty(f.getInstansiId())
                || notEmpty(f.getWilayahKerjaId())
                || notEmpty(f.getKategoriInstansiId())
                || notEmpty(f.getJenisInstansiId())
                || notEmpty(f.getPokjaId());

        if (needInstansi) {
            qp.addJoin("ins", " JOIN instansi ins ON da.id_instansi = ins.id_instansi ");
        }

        if (notEmpty(f.getInstansiId())) {
            qp.conditions.add("ins.id_instansi IN :instansiId");
            qp.params.put("instansiId", f.getInstansiId());
        }

        if (notEmpty(f.getWilayahKerjaId())) {
            qp.addJoin("w", " JOIN wilayah_kerja w ON ins.id_wilayah_kerja = w.id_wilayah ");
            qp.conditions.add("w.id_wilayah IN :wilayahKerjaId");
            qp.params.put("wilayahKerjaId", f.getWilayahKerjaId());
        }

        if (notEmpty(f.getKategoriInstansiId())) {
            qp.addJoin("ki", " JOIN kategori_instansi ki ON ins.id_kategori_instansi = ki.id_kategori_instansi ");
            qp.conditions.add("ki.id_kategori_instansi IN :kategoriInstansiId");
            qp.params.put("kategoriInstansiId", f.getKategoriInstansiId());
        }

        if (notEmpty(f.getJenisInstansiId())) {
            qp.addJoin("ji", " JOIN jenis_instansi ji ON ins.id_jenis_instansi = ji.id_jenis_instansi ");
            qp.conditions.add("ji.id_jenis_instansi IN :jenisInstansiId");
            qp.params.put("jenisInstansiId", f.getJenisInstansiId());
        }

        if (notEmpty(f.getPokjaId())) {
            qp.addJoin("p", " JOIN pokja p ON ins.id_pokja = p.id_pokja ");
            qp.conditions.add("p.id_pokja IN :pokjaId");
            qp.params.put("pokjaId", f.getPokjaId());
        }

        boolean needJabatan = notEmpty(f.getNamaJabatanId())
                || notEmpty(f.getJenjangId())
                || notEmpty(f.getNomenklaturId());

        if (needJabatan) {
            qp.addJoin("jbtn", " JOIN jabatan jbtn ON da.id_jabatan = jbtn.id_jabatan ");
        }

        if (notEmpty(f.getNamaJabatanId())) {
            qp.addJoin("nj", " JOIN nama_jabatan nj ON jbtn.id_nama_jabatan = nj.id_nama_jabatan ");
            qp.conditions.add("nj.id_nama_jabatan IN :namaJabatanId");
            qp.params.put("namaJabatanId", f.getNamaJabatanId());
        }

        if (notEmpty(f.getJenjangId())) {
            qp.addJoin("jnjng", " JOIN jenjang jnjng ON jbtn.id_jenjang = jnjng.id_jenjang ");
            qp.conditions.add("jnjng.id_jenjang IN :jenjangId");
            qp.params.put("jenjangId", f.getJenjangId());
        }

       if (notEmpty(f.getNomenklaturId())) {
    // Pastikan sudah join ke nama_jabatan dulu
    qp.addJoin("nj", " JOIN nama_jabatan nj ON jbtn.id_nama_jabatan = nj.id_nama_jabatan ");
    qp.addJoin("nmk", " JOIN nomenklatur nmk ON nj.id_nomenklatur = nmk.id_nomenklatur ");
    qp.conditions.add("nmk.id_nomenklatur IN :nomenklaturId");
    qp.params.put("nomenklaturId", f.getNomenklaturId());
}

    }

    // ================================================================
    // PEMBANTU COUNT
    // ================================================================
    private long getTotalCount(String sql, QueryParts qp) {
        String countSql = "SELECT COUNT(*) FROM (" + sql + ") AS total";
        var countQuery = em.createNativeQuery(countSql);
        qp.params.forEach(countQuery::setParameter);
        return ((Number) countQuery.getSingleResult()).longValue();
    }

    // ================================================================
    // 1️⃣ INSTANSI
    // ================================================================
    @Override
    public Page<TabelPegawai> getJumlahPegawaiBerdasarkanInstansiWithFilter(FilterDataAgregat filter, Pageable pageable) {
        QueryParts qp = new QueryParts();
        qp.addJoin("ins", " JOIN instansi ins ON da.id_instansi = ins.id_instansi ");
        applyFilters(qp, filter);

        String sql = String.format("""
            SELECT 
                ins.nama_instansi AS nama,
                ins.id_instansi AS id,
                SUM(da.jumlah) AS jumlah
            FROM data_agregat da
            %s
            %s
            GROUP BY ins.nama_instansi, ins.id_instansi
            ORDER BY SUM(da.jumlah) DESC
        """, qp.joins, buildWhere(qp.conditions));

        var query = em.createNativeQuery(sql);
        qp.params.forEach(query::setParameter);

        long total = getTotalCount(sql, qp);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        @SuppressWarnings("unchecked")
        List<Object[]> result = query.getResultList();

        List<TabelPegawai> data = result.stream()
            .map(row -> new TabelPegawai(
                (String) row[0],
                ((Number) row[1]).longValue(),
                ((Number) row[2]).longValue()
            ))
            .collect(Collectors.toList());

        return new PageImpl<>(data, pageable, total);
    }

    // ================================================================
    // 2️⃣ JABATAN
    // ================================================================
    @Override
    public Page<TabelPegawai> getJumlahPegawaiBerdasarkanJabatanWithFilter(FilterDataAgregat filter, Pageable pageable) {
        QueryParts qp = new QueryParts();
        qp.addJoin("jbtn", " JOIN jabatan jbtn ON da.id_jabatan = jbtn.id_jabatan ");
        qp.addJoin("nj", " JOIN nama_jabatan nj ON jbtn.id_nama_jabatan = nj.id_nama_jabatan ");
        applyFilters(qp, filter);

        String sql = String.format("""
            SELECT 
                nj.nama_jabatan AS nama,
                nj.id_nama_jabatan AS id,
                SUM(da.jumlah) AS jumlah
            FROM data_agregat da
            %s
            %s
            GROUP BY nj.nama_jabatan, nj.id_nama_jabatan
            ORDER BY SUM(da.jumlah) DESC
        """, qp.joins, buildWhere(qp.conditions));

        var query = em.createNativeQuery(sql);
        qp.params.forEach(query::setParameter);

        long total = getTotalCount(sql, qp);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        @SuppressWarnings("unchecked")
        List<Object[]> result = query.getResultList();

        List<TabelPegawai> data = result.stream()
            .map(row -> new TabelPegawai(
                (String) row[0],
                ((Number) row[1]).longValue(),
                ((Number) row[2]).longValue()
            ))
            .collect(Collectors.toList());

        return new PageImpl<>(data, pageable, total);
    }

    // ================================================================
    // 3️⃣ WILAYAH KERJA
    // ================================================================
    @Override
    public Page<TabelPegawai> getJumlahPegawaiBerdasarkanWilkerWithFilter(FilterDataAgregat filter, Pageable pageable) {
        QueryParts qp = new QueryParts();
        qp.addJoin("ins", " JOIN instansi ins ON da.id_instansi = ins.id_instansi ");
        qp.addJoin("w", " JOIN wilayah_kerja w ON ins.id_wilayah_kerja = w.id_wilayah ");
        applyFilters(qp, filter);

        String sql = String.format("""
            SELECT 
                w.nama_wilayah AS nama,
                w.id_wilayah AS id,
                SUM(da.jumlah) AS jumlah
            FROM data_agregat da
            %s
            %s
            GROUP BY w.nama_wilayah, w.id_wilayah
            ORDER BY SUM(da.jumlah) DESC
        """, qp.joins, buildWhere(qp.conditions));

        var query = em.createNativeQuery(sql);
        qp.params.forEach(query::setParameter);

        long total = getTotalCount(sql, qp);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        @SuppressWarnings("unchecked")
        List<Object[]> result = query.getResultList();

        List<TabelPegawai> data = result.stream()
            .map(row -> new TabelPegawai(
                (String) row[0],
                ((Number) row[1]).longValue(),
                ((Number) row[2]).longValue()
            ))
            .collect(Collectors.toList());

        return new PageImpl<>(data, pageable, total);
    }
}
