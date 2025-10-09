package com.pusbin.layanan.internal.services.grafik.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.pusbin.layanan.internal.services.grafik.GrafikRepository;
import com.pusbin.layanan.internal.services.grafik.dto.GrafikKategori;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class GrafikRepositoryImpl implements GrafikRepository {

    @PersistenceContext
    private EntityManager em; // pastikan ini bukan EntitiyManager (typo)

    @Override
    public List<GrafikKategori> readGrafikPersentaseJFMASN() {
        String sql = """
            SELECT nj.nama_jabatan AS nama,
                   SUM(da.jumlah) AS jumlah
            FROM data_agregat da
            JOIN jabatan j ON da.id_jabatan = j.id_jabatan
            JOIN nama_jabatan nj ON j.id_nama_jabatan = nj.id_nama_jabatan
            GROUP BY nj.nama_jabatan
            ORDER BY SUM(da.jumlah) DESC
        """;

        @SuppressWarnings("unchecked")
        List<Object[]> result = em.createNativeQuery(sql).getResultList();

        return result.stream()
            .map(row -> new GrafikKategori(
                (String) row[0],
                ((Number) row[1]).longValue()
            ))
            .collect(Collectors.toList());
    }
}
