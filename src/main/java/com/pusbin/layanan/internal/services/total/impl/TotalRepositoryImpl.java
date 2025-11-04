package com.pusbin.layanan.internal.services.total.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.pusbin.layanan.internal.common.types.FilterDataAgregat;
import com.pusbin.layanan.internal.services.total.TotalRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class TotalRepositoryImpl implements TotalRepository {

    @PersistenceContext
    private EntityManager em;

    // ================================================================
    // RECORD QUERY PARTS
    // ================================================================
    private record QueryParts(
            StringBuilder joins,
            List<String> conditions,
            Map<String, Object> params,
            Set<String> usedAliases) {
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
            qp.addJoin("nmk", " JOIN nomenklatur nmk ON nj.id_nomenklatur = nmk.id_nomenklatur ");
            qp.conditions.add("nmk.id_nomenklatur IN :nomenklaturId");
            qp.params.put("nomenklaturId", f.getNomenklaturId());
        }
    }

    // ================================================================
    // TOTAL PEGAWAI DENGAN FILTER
    // ================================================================
    @Override
    public Long getTotalPegawai(FilterDataAgregat filter) {
        QueryParts qp = new QueryParts();
        applyFilters(qp, filter);

        String sql = String.format("""
                    SELECT SUM(da.jumlah)
                    FROM data_agregat da
                    %s
                    %s
                """, qp.joins, buildWhere(qp.conditions));

        var query = em.createNativeQuery(sql);
        qp.params.forEach(query::setParameter);

        Number result = (Number) query.getSingleResult();
        return result != null ? result.longValue() : 0L;
    }

    // ================================================================
    // TOTAL INSTANSI DENGAN FILTER
    // ================================================================
    @Override
    public Long getTotalInstansi(FilterDataAgregat filter) {
        QueryParts qp = new QueryParts();
        applyFilters(qp, filter);

        // Hanya tambahkan join instansi jika belum ada
        if (!qp.usedAliases.contains("ins")) {
            qp.addJoin("ins", " JOIN instansi ins ON da.id_instansi = ins.id_instansi ");
        }

        String sql = String.format("""
                    SELECT COUNT(DISTINCT ins.id_instansi)
                    FROM data_agregat da
                    %s
                    %s
                """, qp.joins, buildWhere(qp.conditions));

        var query = em.createNativeQuery(sql);
        qp.params.forEach(query::setParameter);

        Number result = (Number) query.getSingleResult();
        return result != null ? result.longValue() : 0L;
    }
}
