package com.pusbin.layanan.internal.services.user;

import java.util.Optional;
import com.pusbin.layanan.internal.models.User;

public interface UserRepository {

    // QUERY OTOMATIS DIBUAT OLEH JPA
    Optional<User> findById(long id);
    Optional<User> findByNip(String nip);
    Optional<User> findByEmail(String email);
    Optional<User> findByNama(String nama);
    
    // untuk create biasanya tidak perlu Optional karena pasti return user yang berhasil disimpan
    User createUser(String NIP, String email, String nama, String password);
}
