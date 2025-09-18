package com.example.GI.Backend.Repository;

import com.example.GI.Backend.model.TransactionAuditTrail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionAuditTrail, Integer>{

    @Query(value = "SELECT parent_node, actor_id, transaction_hash, transaction_id, process_type, transaction_date, quantity, total_amount, transaction_document, payment FROM transaction_audit_trail WHERE project_id = :projectId ORDER BY level ASC" , nativeQuery = true)
    List<Object[]> findTransactionsForNode(@Param("projectId") String projectId);

    @Query(value = "SELECT DISTINCT actor_id FROM transaction_audit_trail WHERE project_id = :projectId", nativeQuery = true)
    List<String> findActorsByProjectId(@Param("projectId") String projectId);

    @Query(value = "SELECT transaction_id, geographical_coordinates, process_type, crop, actor_id, level FROM transaction_audit_trail WHERE project_id = :projectId ORDER BY level ASC" , nativeQuery = true)
    List<Object[]> findTransactionsForGeographical(@Param("projectId") String projectId);

    @Query(value = """
            SELECT DISTINCT process_type, actor_id
            FROM transaction_audit_trail
            WHERE project_id = :projectId
            """, nativeQuery = true)
    List<Object[]> findProcessTypeAndActorIdsByProjectId(@Param("projectId") String projectId);

    @Query(value = "SELECT parent_node, actor_id FROM transaction_audit_trail WHERE project_id = :projectId AND parent_node IS NOT NULL AND actor_id IS NOT NULL", nativeQuery = true)
    List<Object[]> findActorIdsGroupedByParentNode(@Param("projectId") String projectId);

    @Query(value = "SELECT DISTINCT actor_id FROM transaction_audit_trail WHERE project_id = :projectId AND actor_id IS NOT NULL", nativeQuery = true)
    List<Integer> findActorIdsByProjectId(@Param("projectId") String projectId);

    @Query(value = """
        SELECT parent_node, actor_id 
        FROM transaction_audit_trail 
        WHERE project_id = :projectId 
          AND parent_node IN (:parentNodes) 
          AND actor_id IS NOT NULL
    """, nativeQuery = true)
    List<Object[]> findActorIdsByParentNodes(
            @Param("projectId") String projectId,
            @Param("parentNodes") List<String> parentNodes
    );

    @Query(value = """
            SELECT total_amount, quantity, measuring_unit, no_of_units
            FROM transaction_audit_trail 
            WHERE parent_node = :parentNode AND actor_id = :actorId AND project_id = :projectId
            """, nativeQuery = true)
    List<Object[]> findTransactionsByParentNodeAndActorId(@Param("parentNode") String parentNode,
                                                          @Param("actorId") Integer actorId,
                                                          @Param("projectId") String projectId);

    @Query(value = """
            SELECT transaction_hash, transaction_id, process_type, transaction_date, quantity, total_amount, no_of_units, measuring_unit
            FROM transaction_audit_trail 
            WHERE parent_node = :parentNode AND actor_id = :actorId AND project_id = :projectId
            """, nativeQuery = true)
    List<Object[]> findDetailedTransactionsByParentNodeAndActorId(@Param("parentNode") String parentNode,
                                                                  @Param("actorId") Integer actorId,
                                                                  @Param("projectId") String projectId);

    @Query(value = """
        SELECT 
            DISTINCT f.farmer_id AS actor_id,
            f.farmer_name AS actor_name,
            f.geographical_coordinates
        FROM transaction_audit_trail t
        JOIN farmer_master f ON f.farmer_id = t.parent_node
        WHERE t.level = 1 AND t.project_id = :project_id
        GROUP BY f.farmer_id, f.farmer_name, f.geographical_coordinates
        """, nativeQuery = true)
    List<Map<String, Object>> findFarmersByProject(@Param("project_id") String project_id);

    @Query(value = """
        SELECT 
            DISTINCT a.actor_id AS actor_id,
            a.poc_name AS actor_name,
            t.geographical_coordinates
        FROM transaction_audit_trail t
        JOIN actor_master a ON a.actor_id = t.actor_id
        WHERE t.level != 1 AND t.project_id = :project_id
        GROUP BY a.actor_id, a.poc_name, t.geographical_coordinates
        """, nativeQuery = true)
    List<Map<String, Object>> findActorsByProject(@Param("project_id") String project_id);

    @Query(value = """
        SELECT f.farmer_name
        FROM farmer_project_master fp
        JOIN farmer_master f ON f.farmer_id = fp.farmer_id
        WHERE fp.project_id = :project_id
        """, nativeQuery = true)
    List<String> getFarmerNamesByProjectId(@Param("project_id") String project_id);

    @Query(value = """
        SELECT level_number, level_name, asset, process_type
        FROM project_level_master
        WHERE project_id = :project_id
        ORDER BY level_number
        """, nativeQuery = true)
    List<Map<String, Object>> getLevelsByProjectId(@Param("project_id") String project_id);

    @Query(value = """
        SELECT DISTINCT a.poc_name
        FROM transaction_audit_trail t
        JOIN actor_master a ON a.actor_id = t.actor_id
        WHERE t.project_id = :project_id AND t.level = :level_number
        """, nativeQuery = true)
    List<String> getActorNamesByProjectAndLevel(@Param("project_id") String project_id,
                                                @Param("level_number") Integer level_number);


}
