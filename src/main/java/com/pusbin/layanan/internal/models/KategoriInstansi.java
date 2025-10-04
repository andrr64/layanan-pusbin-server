package com.pusbin.layanan.internal.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "kategori_instansi")
@Data
@NoArgsConstructor
public class KategoriInstansi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idKategoriInstansi;

    @Column(nullable = false, length = 100)
    private String kategoriInstansi;
}
