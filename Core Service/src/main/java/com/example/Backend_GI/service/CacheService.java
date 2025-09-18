package com.example.Backend_GI.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

@Service
public class CacheService {

    @PersistenceContext
    private EntityManager entityManager;

    // Method to clear the Hibernate cache
    public void clearCache() {
        entityManager.unwrap(Session.class).clear();
    }
}
