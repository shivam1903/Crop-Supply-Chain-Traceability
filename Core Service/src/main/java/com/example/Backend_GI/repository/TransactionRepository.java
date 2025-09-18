package com.example.Backend_GI.repository;

import com.example.Backend_GI.model.InitialTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<InitialTransaction, Integer> {

    @Modifying
    @Query(value = "SELECT setval('initial_transaction_id_seq_a', (SELECT MAX(transaction_id) FROM initial_transaction))", nativeQuery = true)
    Integer resetSequence();


    @Query(value = "SELECT transaction_id, entry_date, status, project_id, parent_node FROM initial_transaction WHERE actor_id = :actorId", nativeQuery = true)
    List<Object[]> findTransactionsByActorId(@Param("actorId") Integer actorId);

    @Modifying
    @Query(value = "UPDATE initial_transaction SET transaction_document = :transactionDocument, payment = :payment, updated_date = CURRENT_DATE, comment = :comment, status = :status WHERE transaction_id = :transactionId", nativeQuery = true)
    int updateTransactionDetails(@Param("transactionId") Integer transactionId,
                                 @Param("transactionDocument") String transactionDocument,
                                 @Param("payment") String payment,
                                 @Param("comment") String comment,
                                 @Param("status") String status);

    @Query(value = "SELECT geographical_coordinates, level FROM initial_transaction WHERE project_id = :projectId", nativeQuery = true)
    List<Object[]> findGCandLevel(@Param("projectId") String projectId);
}