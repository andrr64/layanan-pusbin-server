package com.pusbin.layanan.internal.services.total.impl;

import org.springframework.stereotype.Repository;

import com.pusbin.layanan.internal.services.total.TotalRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class TotalRepositoryImpl implements TotalRepository{
    
@PersistenceContext
private EntityManager em;

    @Override
    public Long getTotalPegawai() {
        Long count = em.createQuery("SELECT SUM(d.jumlah) FROM DataAgregat d", Long.class)
        .getSingleResult();
        return count;
    }

    @Override
    public Long getTotalInstansi() {
       Long count = em.createQuery("SELECT COUNT(i) FROM Instansi i", Long.class)
       .getSingleResult();
       return count;
    }
}
