package com.pusbin.layanan.internal.services.grafik.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.pusbin.layanan.internal.common.types.FilterDataAgregat;
import com.pusbin.layanan.internal.services.grafik.GrafikRepository;
import com.pusbin.layanan.internal.services.grafik.dto.GrafikKategori;
import com.pusbin.layanan.internal.services.grafik.dto.GrafikSebaranASNJFK;
import com.pusbin.layanan.internal.services.grafik.dto.GrafikSebaranJFK;
import com.pusbin.layanan.internal.services.grafik.dto.GrafikSebaranJFMASN;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class GrafikRepositoryImpl implements GrafikRepository {

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
    // UTILITIES
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
        // -- ASN
        if (notEmpty(f.getJenisAsnId())) {
            qp.addJoin("jasn", " JOIN asn jasn ON da.id_jenis_asn = jasn.id_asn ");
            qp.conditions.add("jasn.id_asn IN :jenisAsnId");
            qp.params.put("jenisAsnId", f.getJenisAsnId());
        }

        // -- INSTANSI
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

        // -- WILKER
        if (notEmpty(f.getWilayahKerjaId())) {
            qp.addJoin("w", " JOIN wilayah_kerja w ON ins.id_wilayah_kerja = w.id_wilayah ");
            qp.conditions.add("w.id_wilayah IN :wilayahKerjaId");
            qp.params.put("wilayahKerjaId", f.getWilayahKerjaId());
        }

        // -- KATEGORI INSTANSI
        if (notEmpty(f.getKategoriInstansiId())) {
            qp.addJoin("ki", " JOIN kategori_instansi ki ON ins.id_kategori_instansi = ki.id_kategori_instansi ");
            qp.conditions.add("ki.id_kategori_instansi IN :kategoriInstansiId");
            qp.params.put("kategoriInstansiId", f.getKategoriInstansiId());
        }

        // -- JENIS INSTANSI
        if (notEmpty(f.getJenisInstansiId())) {
            qp.addJoin("ji", " JOIN jenis_instansi ji ON ins.id_jenis_instansi = ji.id_jenis_instansi ");
            qp.conditions.add("ji.id_jenis_instansi IN :jenisInstansiId");
            qp.params.put("jenisInstansiId", f.getJenisInstansiId());
        }

        // -- POKJA
        if (notEmpty(f.getPokjaId())) {
            qp.addJoin("p", " JOIN pokja p ON ins.id_pokja = p.id_pokja ");
            qp.conditions.add("p.id_pokja IN :pokjaId");
            qp.params.put("pokjaId", f.getPokjaId());
        }

        // -- JABATAN
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
            qp.addJoin("nmk", " JOIN nomenklatur nmk ON nj.id_nomenklatur = nmk.id_nomenklatur ");
            qp.conditions.add("nmk.id_nomenklatur IN :nomenklaturId");
            qp.params.put("nomenklaturId", f.getNomenklaturId());
        }
    }

    // ================================================================
    // IMPLEMENTASI REPOSITORY
    // ================================================================
    @Override
    public List<GrafikKategori> readGrafikPersentaseJFMASN(FilterDataAgregat filter) {
        QueryParts qp = new QueryParts();
        qp.addJoin("j", " JOIN jabatan j ON da.id_jabatan = j.id_jabatan ");
        qp.addJoin("nj", " JOIN nama_jabatan nj ON j.id_nama_jabatan = nj.id_nama_jabatan ");
        applyFilters(qp, filter);

        String sql = String.format("""
            SELECT nj.nama_jabatan AS nama,
                   nj.id_nama_jabatan,
                   SUM(da.jumlah) AS jumlah
            FROM data_agregat da
            %s
            %s
            GROUP BY nj.nama_jabatan, nj.id_nama_jabatan
            ORDER BY SUM(da.jumlah) DESC
        """, qp.joins, buildWhere(qp.conditions));

        var query = em.createNativeQuery(sql);
        qp.params.forEach(query::setParameter);

        @SuppressWarnings("unchecked")
        List<Object[]> result = query.getResultList();

        return result.stream()
                .map(row -> new GrafikKategori(
                (String) row[0], // nama_jabatan
                ((Number) row[1]).longValue(), // id_nama_jabatan
                ((Number) row[2]).longValue() // jumlah
        ))
                .collect(Collectors.toList());
    }

    @Override
    public List<GrafikSebaranJFMASN> readGrafikSebaranJFMASN(FilterDataAgregat filter) {
        QueryParts qp = new QueryParts();
        qp.addJoin("jbtn", " JOIN jabatan jbtn ON da.id_jabatan = jbtn.id_jabatan ");
        qp.addJoin("jnjng", " JOIN jenjang jnjng ON jbtn.id_jenjang = jnjng.id_jenjang ");
        qp.addJoin("ins", " JOIN instansi ins ON da.id_instansi = ins.id_instansi ");
        qp.addJoin("ki", " JOIN kategori_instansi ki ON ins.id_kategori_instansi = ki.id_kategori_instansi ");
        applyFilters(qp, filter);

        String sql = String.format("""
            SELECT 
                jnjng.jenjang AS nama_jenjang,
                jnjng.id_jenjang,
                ki.kategori_instansi,
                ki.id_kategori_instansi,
                SUM(da.jumlah) AS jumlah
            FROM data_agregat da
            %s
            %s
            GROUP BY jnjng.jenjang, jnjng.id_jenjang, ki.kategori_instansi, ki.id_kategori_instansi
            ORDER BY jnjng.jenjang, ki.kategori_instansi
        """, qp.joins, buildWhere(qp.conditions));

        var query = em.createNativeQuery(sql);
        qp.params.forEach(query::setParameter);

        @SuppressWarnings("unchecked")
        List<Object[]> result = query.getResultList();

        return result.stream()
                .map(row -> new GrafikSebaranJFMASN(
                (String) row[0], // nama_jenjang
                ((Number) row[1]).longValue(), // id_jenjang
                ((Number) row[4]).longValue(), // jumlah
                (String) row[2], // kategori_instansi
                ((Number) row[3]).longValue() // id_kategori_instansi
        ))
                .collect(Collectors.toList());
    }

    @Override
    public List<GrafikSebaranJFK> readGrafikSebaranJFK(FilterDataAgregat filter) {
        QueryParts qp = new QueryParts();
        qp.addJoin("jbtn", " JOIN jabatan jbtn ON da.id_jabatan = jbtn.id_jabatan ");
        qp.addJoin("nmjbtn", " JOIN nama_jabatan nmjbtn ON jbtn.id_nama_jabatan = nmjbtn.id_nama_jabatan ");
        qp.addJoin("ins", " JOIN instansi ins ON da.id_instansi = ins.id_instansi ");
        qp.addJoin("ki", " JOIN kategori_instansi ki ON ins.id_kategori_instansi = ki.id_kategori_instansi ");
        applyFilters(qp, filter);

        String sql = String.format("""
            SELECT
                ki.kategori_instansi AS kategori_instansi,
                ki.id_kategori_instansi,
                nmjbtn.nama_jabatan AS nama_jabatan,
                nmjbtn.id_nama_jabatan,
                SUM(da.jumlah) AS jumlah
            FROM data_agregat da
            %s
            %s
            GROUP BY ki.kategori_instansi, ki.id_kategori_instansi, nmjbtn.nama_jabatan, nmjbtn.id_nama_jabatan
            ORDER BY nmjbtn.nama_jabatan, ki.kategori_instansi
        """, qp.joins, buildWhere(qp.conditions));

        var query = em.createNativeQuery(sql);
        qp.params.forEach(query::setParameter);

        @SuppressWarnings("unchecked")
        List<Object[]> result = query.getResultList();

        return result.stream()
                .map(row -> new GrafikSebaranJFK(
                (String) row[0], // kategori_instansi
                ((Number) row[1]).longValue(), // id_kategori_instansi
                ((Number) row[4]).longValue(), // jumlah
                (String) row[2],
                ((Number) row[3]).longValue() // id_nama_jabatan // nama_jabatan
        ))
                .collect(Collectors.toList());
    }

    @Override
    public List<GrafikSebaranASNJFK> readGrafikSebaranASNJFK(FilterDataAgregat filter) {
        QueryParts qp = new QueryParts();
        qp.addJoin("jasn", " JOIN asn jasn ON da.id_jenis_asn = jasn.id_asn ");
        qp.addJoin("jbtn", " JOIN jabatan jbtn ON da.id_jabatan = jbtn.id_jabatan ");
        qp.addJoin("jnjng", " JOIN jenjang jnjng ON jbtn.id_jenjang = jnjng.id_jenjang ");
        applyFilters(qp, filter);

        String sql = String.format("""
            SELECT
                jasn.jenis_asn AS asn,
                jasn.id_asn,
                jnjng.jenjang AS jenjang,
                jnjng.id_jenjang,
                SUM(da.jumlah) AS jumlah
            FROM data_agregat da
            %s
            %s
            GROUP BY jasn.jenis_asn, jasn.id_asn, jnjng.jenjang, jnjng.id_jenjang
            ORDER BY jasn.jenis_asn, jnjng.jenjang
        """, qp.joins, buildWhere(qp.conditions));

        var query = em.createNativeQuery(sql);
        qp.params.forEach(query::setParameter);

        @SuppressWarnings("unchecked")
        List<Object[]> result = query.getResultList();

        return result.stream()
                .map(row -> new GrafikSebaranASNJFK(
                (String) row[0], // jenis_asn
                ((Number) row[1]).longValue(), // id_asn
                ((Number) row[4]).longValue(), // jumlah
                (String) row[2], // jenjang
                ((Number) row[3]).longValue() // id_jenjang
        ))
                .collect(Collectors.toList());
    }
}
