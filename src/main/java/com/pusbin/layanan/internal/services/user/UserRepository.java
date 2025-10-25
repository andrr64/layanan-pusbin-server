package com.pusbin.layanan.internal.services.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pusbin.layanan.internal.models.User;

public interface UserRepository extends JpaRepository<User, Long> {

    // QUERY OTOMATIS DIBUAT OLEH JPA
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);

    // QUERY MANUAL
    // TIDAK ADA....
}
