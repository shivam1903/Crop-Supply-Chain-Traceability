package com.example.Backend.Repository;

import com.example.Backend.Model.TransactionAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Repository
public interface TransactionAuditRepository extends JpaRepository<TransactionAudit, Integer> {

    @Query(value = """
        SELECT
            t.transaction_id,
            t.entry_date,
            t.actor_id,
            t.parent_node,
            t.quantity,
            t.total_amount,
            t.comment,
            t.level,
            t.measuring_unit,
            t.no_of_units
        FROM transaction_audit_trail t
        WHERE t.project_id = :project_id
    
        UNION ALL
    
        SELECT
            i.transaction_id,
            i.entry_date,
            i.actor_id,
            i.parent_node,
            i.quantity,
            i.total_amount,
            i.comment,
            i.level,
            i.measuring_unit,
            i.no_of_units
        FROM initial_transaction i
        WHERE i.project_id = :project_id
        AND i.transaction_id NOT IN (
            SELECT transaction_id FROM transaction_audit_trail WHERE project_id = :project_id
        )
        ORDER BY transaction_id
        """, nativeQuery = true)
    List<Object[]> findTransactionforProject(@Param("project_id") String project_id);

    @Query(value = "SELECT SUM(total_amount) FROM transaction_audit_trail WHERE parent_node = :farmerId AND project_id = :projectId", nativeQuery = true)
    BigDecimal sumTotalAmountForFarmerAndProject(@Param("farmerId") String farmerId, @Param("projectId") String projectId);

    @Query(value = "SELECT SUM(quantity) FROM transaction_audit_trail WHERE parent_node = :farmerId AND project_id = :projectId", nativeQuery = true)
    BigDecimal sumTotalQuantityForFarmerAndProject(@Param("farmerId") String farmerId, @Param("projectId") String projectId);
}
