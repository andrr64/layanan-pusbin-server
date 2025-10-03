package com.pusbin.layanan.instansi;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InstansiRepository extends JpaRepository<Instansi, Long> {

    @Query(value = "SELECT COUNT(*) FROM instansi", nativeQuery = true)
    Long countInstansi();

}
