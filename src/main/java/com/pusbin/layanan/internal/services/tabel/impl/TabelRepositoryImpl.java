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

    /**
     * Helper record untuk mengelola bagian-bagian query yang dinamis.
     * Ini membuat kode lebih bersih daripada mengirim banyak argumen terpisah.
     */
    private record QueryParts(
            StringBuilder joinBuilder,
            List<String> conditions,
            Map<String, Object> params,
            Set<String> joinedAliases
    ) {
        QueryParts() {
            this(new StringBuilder(), new ArrayList<>(), new HashMap<>(), new HashSet<>());
        }

        /**
         * Menambahkan klausa JOIN secara cerdas, memastikan tidak ada duplikasi.
         * @param alias Alias untuk entitas yang di-join (misal: "i" untuk instansi).
         * @param joinClause Klausa JPQL untuk JOIN (misal: " JOIN d.instansi i ").
         */
        void addJoin(String alias, String joinClause) {
            if (joinedAliases.add(alias)) { // Hanya menambahkan jika alias belum ada
                joinBuilder.append(joinClause);
            }
        }
    }

    @Override
    public Page<TabelPegawai> getJumlahPegawaiBerdasarkanInstansiWithFilter(
            FilterDataAgregat filter, Pageable pageable
    ) {
        // Grup berdasarkan nama instansi
        String selectionField = "i.namaInstansi";
        // Query dasar, join ke instansi wajib ada
        String fromClause = "FROM DataAgregat d JOIN d.instansi i";
        return getAggregatedDataPage(selectionField, fromClause, filter, pageable);
    }

    @Override
    public Page<TabelPegawai> getJumlahPegawaiBerdasarkanJabatanWithFilter(
            FilterDataAgregat filter, Pageable pageable
    ) {
        // Grup berdasarkan nama jabatan
        String selectionField = "nj.namaJabatan";
        // Query dasar, join ke jabatan dan namaJabatan wajib ada
        String fromClause = """
            FROM DataAgregat d
            JOIN d.jabatan j
            JOIN j.namaJabatan nj
        """;
        return getAggregatedDataPage(selectionField, fromClause, filter, pageable);
    }

    @Override
    public Page<TabelPegawai> getJumlahPegawaiBerdasarkanWilkerWithFilter(
            FilterDataAgregat filter, Pageable pageable
    ) {
        // Grup berdasarkan nama wilayah kerja
        String selectionField = "w.namaWilayah";
        // Query dasar, join ke instansi dan wilayahKerja akan ditambahkan dinamis oleh filter
        String fromClause = "FROM DataAgregat d";
        return getAggregatedDataPage(selectionField, fromClause, filter, pageable);
    }

    /**
     * Metode privat generik untuk membangun dan mengeksekusi query agregat.
     * Semua logika yang berulang (filtering, join, pagination, count) disatukan di sini.
     */
    private Page<TabelPegawai> getAggregatedDataPage(
            String selectionField, String fromClause, FilterDataAgregat filter, Pageable pageable
    ) {
        QueryParts queryParts = new QueryParts();

        // 1. Terapkan semua filter dan bangun JOIN serta WHERE clause secara dinamis
        applyFilters(queryParts, filter, selectionField);

        // 2. Bangun string query untuk mengambil data (dengan paginasi)
        String dataQueryStr = String.format("""
            SELECT new com.pusbin.layanan.internal.services.common.dto.TabelPegawai(
                %s,
                SUM(d.jumlah)
            )
            %s
            %s
            %s
            GROUP BY %s
        """, selectionField, fromClause, queryParts.joinBuilder.toString(), buildWhereClause(queryParts.conditions), selectionField);

        // 3. Eksekusi query data
        TypedQuery<TabelPegawai> dataQuery = em.createQuery(dataQueryStr, TabelPegawai.class);
        queryParts.params.forEach(dataQuery::setParameter);
        dataQuery.setFirstResult((int) pageable.getOffset());
        dataQuery.setMaxResults(pageable.getPageSize());
        List<TabelPegawai> resultList = dataQuery.getResultList();

        // 4. Bangun string query untuk menghitung total hasil (untuk paginasi)
        String countQueryStr = String.format("""
            SELECT COUNT(DISTINCT %s)
            %s
            %s
            %s
        """, selectionField, fromClause, queryParts.joinBuilder.toString(), buildWhereClause(queryParts.conditions));
        
        // 5. Eksekusi query count
        TypedQuery<Long> countQuery = em.createQuery(countQueryStr, Long.class);
        queryParts.params.forEach(countQuery::setParameter);
        Long total = countQuery.getSingleResult();

        return new PageImpl<>(resultList, pageable, total);
    }

    /**
     * Menerapkan semua filter yang relevan dari DTO ke dalam QueryParts.
     * Metode ini secara cerdas mengelola dependensi antar JOIN.
     */
    private void applyFilters(QueryParts parts, FilterDataAgregat filter, String selectionField) {
        // Filter Jenis ASN
        if (filter.getJenisAsnId() != null && !filter.getJenisAsnId().isEmpty()) {
            parts.addJoin("a", " JOIN d.asn a ");
            parts.conditions.add("a.idAsn IN :jenisAsnId");
            parts.params.put("jenisAsnId", filter.getJenisAsnId());
        }
        
        // Cek apakah JOIN ke 'instansi' (alias 'i') dibutuhkan oleh filter lain
        boolean needsInstansiJoin = (filter.getInstansiId() != null && !filter.getInstansiId().isEmpty())
                || (filter.getWilayahKerjaId() != null && !filter.getWilayahKerjaId().isEmpty())
                || (filter.getKategoriInstansiId() != null && !filter.getKategoriInstansiId().isEmpty())
                || (filter.getJenisInstansiId() != null && !filter.getJenisInstansiId().isEmpty())
                || (filter.getPokjaId() != null && !filter.getPokjaId().isEmpty())
                || selectionField.startsWith("w."); // Wajib jika grup by Wilker

        if (needsInstansiJoin) {
            parts.addJoin("i", " JOIN d.instansi i ");
        }

        // Filter Instansi
        if (filter.getInstansiId() != null && !filter.getInstansiId().isEmpty()) {
            parts.conditions.add("i.idInstansi IN :instansiId");
            parts.params.put("instansiId", filter.getInstansiId());
        }

        // Filter Wilayah Kerja (membutuhkan join ke instansi 'i')
        // Join 'w' juga dibutuhkan jika kita melakukan select/group by 'w.namaWilayah'
        if ((filter.getWilayahKerjaId() != null && !filter.getWilayahKerjaId().isEmpty()) || selectionField.startsWith("w.")) {
            parts.addJoin("w", " JOIN i.wilayahKerja w ");
            if (filter.getWilayahKerjaId() != null && !filter.getWilayahKerjaId().isEmpty()){
                parts.conditions.add("w.idWilayah IN :wilayahKerjaId");
                parts.params.put("wilayahKerjaId", filter.getWilayahKerjaId());
            }
        }

        // Filter Kategori Instansi (membutuhkan join ke instansi 'i')
        if (filter.getKategoriInstansiId() != null && !filter.getKategoriInstansiId().isEmpty()) {
            parts.addJoin("k", " JOIN i.kategoriInstansi k ");
            parts.conditions.add("k.idKategoriInstansi IN :kategoriInstansiId");
            parts.params.put("kategoriInstansiId", filter.getKategoriInstansiId());
        }

        // Filter Jenis Instansi (membutuhkan join ke instansi 'i')
        if (filter.getJenisInstansiId() != null && !filter.getJenisInstansiId().isEmpty()) {
            parts.addJoin("ji", " JOIN i.jenisInstansi ji ");
            parts.conditions.add("ji.idJenisInstansi IN :jenisInstansiId");
            parts.params.put("jenisInstansiId", filter.getJenisInstansiId());
        }

        // Filter Pokja (membutuhkan join ke instansi 'i')
        if (filter.getPokjaId() != null && !filter.getPokjaId().isEmpty()) {
            parts.addJoin("p", " JOIN i.pokja p ");
            parts.conditions.add("p.idPokja IN :pokjaId");
            parts.params.put("pokjaId", filter.getPokjaId());
        }

        // Filter terkait Jabatan (membutuhkan join ke jabatan 'j' dan namaJabatan 'nj')
        boolean needsJabatanJoin = (filter.getNamaJabatanId() != null && !filter.getNamaJabatanId().isEmpty())
                || (filter.getJenjangId() != null && !filter.getJenjangId().isEmpty())
                || (filter.getNomenklaturId() != null && !filter.getNomenklaturId().isEmpty());
        
        if (needsJabatanJoin) {
            parts.addJoin("j", " JOIN d.jabatan j ");
        }

        // Filter Nama Jabatan (hanya membutuhkan join ke 'j')
        if (filter.getNamaJabatanId() != null && !filter.getNamaJabatanId().isEmpty()) {
             parts.addJoin("nj", " JOIN j.namaJabatan nj ");
            parts.conditions.add("nj.idNamaJabatan IN :namaJabatanId");
            parts.params.put("namaJabatanId", filter.getNamaJabatanId());
        }

        // Filter Jenjang (membutuhkan join ke 'j')
        if (filter.getJenjangId() != null && !filter.getJenjangId().isEmpty()) {
            parts.addJoin("jj", " JOIN j.jenjang jj ");
            parts.conditions.add("jj.idJenjang IN :jenjangId");
            parts.params.put("jenjangId", filter.getJenjangId());
        }

        // Filter Nomenklatur (membutuhkan join 'j' -> 'nj' -> 'nmk')
        if (filter.getNomenklaturId() != null && !filter.getNomenklaturId().isEmpty()) {
            parts.addJoin("nj", " JOIN j.namaJabatan nj ");
            parts.addJoin("nmk", " JOIN nj.nomenklatur nmk ");
            parts.conditions.add("nmk.idNomenklatur IN :nomenklaturId");
            parts.params.put("nomenklaturId", filter.getNomenklaturId());
        }
    }

    /**
     * Membangun klausa WHERE dari daftar kondisi.
     * Mengembalikan string kosong jika tidak ada kondisi.
     */
    private String buildWhereClause(List<String> conditions) {
        if (conditions.isEmpty()) {
            return "";
        }
        return "WHERE " + String.join(" AND ", conditions);
    }
}