package com.example.Backend_GI.repository;

import com.example.Backend_GI.model.TransactionAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionAuditRepository extends JpaRepository<TransactionAudit, Integer> {
}
