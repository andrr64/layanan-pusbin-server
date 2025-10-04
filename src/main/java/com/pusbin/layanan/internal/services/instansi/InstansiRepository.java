package com.pusbin.layanan.internal.services.instansi;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pusbin.layanan.internal.models.Instansi;

public interface InstansiRepository extends JpaRepository<Instansi, Long> {

    @Query(value = "SELECT COUNT(*) FROM instansi", nativeQuery = true)
    Long countInstansi();

}
