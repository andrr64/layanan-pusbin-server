package com.pusbin.layanan.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    // QUERY OTOMATIS DIBUAT OLEH JPA
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);

    // QUERY MANUAL
    // TIDAK ADA....
}
