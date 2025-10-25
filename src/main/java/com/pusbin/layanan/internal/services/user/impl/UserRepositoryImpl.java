package com.pusbin.layanan.internal.services.user.impl;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.pusbin.layanan.internal.models.User;
import com.pusbin.layanan.internal.services.user.UserRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<User> findById(long id) {
        String jpql = "SELECT u FROM User u WHERE u.id = :id";
        try {
            User user = em.createQuery(jpql, User.class)
                          .setParameter("id", id)
                          .getSingleResult();
            return Optional.of(user);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findByNip(String nip) {
        String jpql = "SELECT u FROM User u WHERE u.nip = :nip";
        try {
            User user = em.createQuery(jpql, User.class)
                          .setParameter("nip", nip)
                          .getSingleResult();
            return Optional.of(user);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        String jpql = "SELECT u FROM User u WHERE u.email = :email";
        try {
            User user = em.createQuery(jpql, User.class)
                          .setParameter("email", email)
                          .getSingleResult();
            return Optional.of(user);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findByNama(String nama) {
        String jpql = "SELECT u FROM User u WHERE u.nama = :nama";
        try {
            User user = em.createQuery(jpql, User.class)
                          .setParameter("nama", nama)
                          .getSingleResult();
            return Optional.of(user);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public User createUser(String NIP, String email, String nama, String hashPassword) {
        String query = "INSERT INTO users (nip, email, nama, password) VALUES (:nip, :email, :nama, :password)";

            em.createNativeQuery(query)
              .setParameter("nip", NIP)
              .setParameter("email", email)
              .setParameter("nama", nama)
              .setParameter("password", hashPassword)
              .executeUpdate();

            User user = em.createQuery("FROM User WHERE email = :email", User.class)
                          .setParameter("email", email)
                          .getSingleResult();
            return user;
    }
}
