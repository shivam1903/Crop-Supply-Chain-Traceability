package com.example.Backend.Repository;

import com.example.Backend.Model.TransactionAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Repository
public interface TransactionAuditRepository extends JpaRepository<TransactionAudit, Integer>{

    // admin, transaction id, updated_date, project_id
    // second option to view everything
    @Query(value = """
SELECT transaction_id,
updated_date,
project_id,
team_a_status,
team_a_assigned_id
FROM transaction_audit_trail WHERE (team_a_status = :status_1 or team_a_status = :status_2) order by transaction_id""", nativeQuery = true)
    List<Object[]> find_A_PendingTransactions(@Param("status_1") String status_1,
                                              @Param("status_2") String status_2);

    @Query(value = """
SELECT transaction_id,
updated_date,
project_id,
team_b_status,
team_b_assigned_id
FROM transaction_audit_trail WHERE (team_b_status = :status_1 or team_b_status = :status_2) order by transaction_id""", nativeQuery = true)
    List<Object[]> find_B_PendingTransactions(@Param("status_1") String status_1,
                                              @Param("status_2") String status_2);

    @Query(value = """
SELECT transaction_id,
updated_date,
project_id,
team_c_status,
team_c_assigned_id
FROM transaction_audit_trail WHERE (team_c_status = :status_1 or team_c_status = :status_2 or team_c_status = :status_3) order by transaction_id""", nativeQuery = true)
    List<Object[]> find_C_PendingTransactions(@Param("status_1") String status_1,
                                              @Param("status_2") String status_2,
                                              @Param("status_3") String status_3);


    @Query(value = """
    SELECT transaction_id,
           updated_date,
           project_id,
           team_d_status,
           team_d_assigned_id,
           team_a_remark,
           team_b_remark,
           team_c_remark,
           problem_comment
    FROM transaction_audit_trail
    WHERE (team_d_status = :status_1 OR team_d_status = :status_2)
    ORDER BY transaction_id
    """, nativeQuery = true)
    List<Object[]> find_D_PendingTransactions(@Param("status_1") String status_1,
                                              @Param("status_2") String status_2);

    @Query(value = """
SELECT transaction_id,
entry_date,
price_per_unit,
quantity,
total_amount,
transaction_date,
level,
transaction_document,
updated_date,
process_type,
project_id,
measuring_unit,
no_of_units,
actor_id,
comment,
crop,
geographical_coordinates,
status,
parent_node,
payment,
problem_reported,
problem_comment,
team_a_status,
team_a_remark,
team_b_status,
team_b_remark,
team_c_status,
team_c_remark,
team_d_status,
team_d_remark
FROM transaction_audit_trail WHERE transaction_id = :transactionId""", nativeQuery = true)
    Map<String, Object> findTransaction(@Param("transactionId") int transactionId);

    @Query(value = """
SELECT transaction_id,
entry_date,
price_per_unit,
quantity,
total_amount,
transaction_date,
level,
transaction_document,
updated_date,
process_type,
project_id,
measuring_unit,
no_of_units,
actor_id,
comment,
crop,
geographical_coordinates,
status,
parent_node,
payment
FROM transaction_audit_trail WHERE transaction_id = :transactionId""", nativeQuery = true)
    Map<String, Object> findTransactionForBlockchain(@Param("transactionId") int transactionId);


    @Query(value = """
SELECT transaction_id,
updated_date,
project_id,
problem_comment
FROM transaction_audit_trail WHERE team_a_status = :status and team_a_assigned_id = :emailId order by transaction_id""", nativeQuery = true)
    List<Object[]> find_A_PendingMemberTransactions(@Param("status") String status,
                                                    @Param("emailId")String emailId);

    @Query(value = """
SELECT transaction_id,
updated_date,
project_id,
team_a_remark
FROM transaction_audit_trail WHERE team_b_status = :status and team_b_assigned_id = :emailId order by transaction_id""", nativeQuery = true)
    List<Object[]> find_B_PendingMemberTransactions(@Param("status") String status,
                                                    @Param("emailId")String emailId);

    @Query(value = """
SELECT transaction_id,
updated_date,
project_id,
team_a_remark,
team_b_remark
FROM transaction_audit_trail WHERE team_c_status = :status and team_c_assigned_id = :emailId order by transaction_id""", nativeQuery = true)
    List<Object[]> find_C_PendingMemberTransactions(@Param("status") String status,
                                                    @Param("emailId")String emailId);

    @Query(value = """
SELECT transaction_id,
updated_date,
project_id,
problem_comment,
team_a_remark,
team_b_remark,
team_c_remark
FROM transaction_audit_trail WHERE team_d_status = :status and team_d_assigned_id = :emailId order by transaction_id""", nativeQuery = true)
    List<Object[]> find_D_PendingMemberTransactions(@Param("status") String status,
                                                    @Param("emailId")String emailId);


    @Modifying
    @Query(value = "UPDATE transaction_audit_trail SET team_a_assigned_id = :emailId, team_a_status = :status, team_a_timestamp = CURRENT_DATE WHERE transaction_id = :transactionId", nativeQuery = true)
    int assignTeamA(@Param("transactionId") Integer transactionId,
                     @Param("emailId") String emailId,
                     @Param("status") String status);

    @Modifying
    @Query(value = "UPDATE transaction_audit_trail SET team_b_assigned_id = :emailId, team_b_status = :status, team_b_timestamp = CURRENT_DATE WHERE transaction_id = :transactionId", nativeQuery = true)
    int assignTeamB(@Param("transactionId") Integer transactionId,
                    @Param("emailId") String emailId,
                    @Param("status") String status);

    @Modifying
    @Query(value = "UPDATE transaction_audit_trail SET team_c_assigned_id = :emailId, team_c_status = :status, team_c_timestamp = CURRENT_DATE WHERE transaction_id = :transactionId", nativeQuery = true)
    int assignTeamC(@Param("transactionId") Integer transactionId,
                    @Param("emailId") String emailId,
                    @Param("status") String status);

    @Modifying
    @Query(value = "UPDATE transaction_audit_trail SET team_d_assigned_id = :emailId, team_d_status = :status, team_d_timestamp = CURRENT_DATE WHERE transaction_id = :transactionId", nativeQuery = true)
    int assignTeamD(@Param("transactionId") Integer transactionId,
                    @Param("emailId") String emailId,
                    @Param("status") String status);


    @Modifying
    @Query(value = "UPDATE transaction_audit_trail SET transaction_hash = :hash, team_d_status = :status, team_d_timestamp = CURRENT_DATE WHERE transaction_id = :transactionId", nativeQuery = true)
    int addBlockchainHash(@Param("transactionId") Integer transactionId,
                          @Param("hash") String hash,
                          @Param("status") String status);

    @Modifying
    @Query(value = """
                        UPDATE transaction_audit_trail SET 
                        team_a_assigned_id = :team_a_assigned_id,
                        team_a_status = :team_a_status,
                        team_a_remark = :team_a_remark,
                        team_b_status = :team_b_status,
                        team_a_timestamp = CURRENT_DATE,
                        team_b_timestamp = CURRENT_DATE,
                        price_per_unit = :price_per_unit,
                        quantity = :quantity,
                        total_amount = :total_amount,
                        level = :level,
                        process_type = :process_type,
                        project_id = :project_id,
                        measuring_unit = :measuring_unit,
                        no_of_units = :no_of_units,
                        actor_id = :actor_id,
                        comment = :comment,
                        crop = :crop,
                        geographical_coordinates = :geographical_coordinates,
                        status = :status,
                        parent_node = :parent_node WHERE transaction_id = :transactionId""", nativeQuery = true)
    int updateByTeamA(@Param("transactionId") Integer transactionId,
                      @Param("team_a_assigned_id") String team_a_assigned_id,
                      @Param("team_a_status") String team_a_status,
                      @Param("team_b_status") String team_b_status,
                      @Param("team_a_remark") String team_a_remark,
                      @Param("price_per_unit") BigDecimal price_per_unit,
                      @Param("quantity") BigDecimal quantity,
                      @Param("total_amount") BigDecimal total_amount,
                      @Param("level") Integer level,
                      @Param("process_type") String process_type,
                      @Param("project_id") String project_id,
                      @Param("measuring_unit") String measuring_unit,
                      @Param("no_of_units") Integer no_of_units,
                      @Param("actor_id") Integer actor_id,
                      @Param("comment") String comment,
                      @Param("crop") String crop,
                      @Param("geographical_coordinates") String geographical_coordinates,
                      @Param("status") String status,
                      @Param("parent_node") String parent_node);

    @Modifying
    @Query(value = """
                        UPDATE transaction_audit_trail SET 
                        team_b_assigned_id = :team_b_assigned_id,
                        team_b_status = :team_b_status, 
                        team_b_remark = :team_b_remark, 
                        team_c_status = :team_c_status, 
                        team_b_timestamp = CURRENT_DATE, 
                        team_c_timestamp = CURRENT_DATE,
                        price_per_unit = :price_per_unit,
                        quantity = :quantity,
                        total_amount = :total_amount,
                        level = :level,
                        process_type = :process_type,
                        project_id = :project_id,
                        measuring_unit = :measuring_unit,
                        no_of_units = :no_of_units,
                        actor_id = :actor_id,
                        comment = :comment,
                        crop = :crop,
                        geographical_coordinates = :geographical_coordinates,
                        status = :status,
                        parent_node = :parent_node WHERE transaction_id = :transactionId""", nativeQuery = true)
    int updateByTeamB(@Param("transactionId") Integer transactionId,
                      @Param("team_b_assigned_id") String team_b_assigned_id,
                      @Param("team_b_status") String team_b_status,
                      @Param("team_c_status") String team_c_status,
                      @Param("team_b_remark") String team_b_remark,
                      @Param("price_per_unit") BigDecimal price_per_unit,
                      @Param("quantity") BigDecimal quantity,
                      @Param("total_amount") BigDecimal total_amount,
                      @Param("level") Integer level,
                      @Param("process_type") String process_type,
                      @Param("project_id") String project_id,
                      @Param("measuring_unit") String measuring_unit,
                      @Param("no_of_units") Integer no_of_units,
                      @Param("actor_id") Integer actor_id,
                      @Param("comment") String comment,
                      @Param("crop") String crop,
                      @Param("geographical_coordinates") String geographical_coordinates,
                      @Param("status") String status,
                      @Param("parent_node") String parent_node);

    @Modifying
    @Query(value = """
                        UPDATE transaction_audit_trail SET 
                        team_c_assigned_id = :team_c_assigned_id,
                        team_c_status = :team_c_status, 
                        team_c_remark = :team_c_remark, 
                        team_d_status = :team_d_status, 
                        team_c_timestamp = CURRENT_DATE, 
                        team_d_timestamp = CURRENT_DATE,
                        price_per_unit = :price_per_unit,
                        quantity = :quantity,
                        total_amount = :total_amount,
                        level = :level,
                        process_type = :process_type,
                        project_id = :project_id,
                        measuring_unit = :measuring_unit,
                        no_of_units = :no_of_units,
                        actor_id = :actor_id,
                        comment = :comment,
                        crop = :crop,
                        geographical_coordinates = :geographical_coordinates,
                        status = :status,
                        parent_node = :parent_node WHERE transaction_id = :transactionId""", nativeQuery = true)
    int updateByTeamC(@Param("transactionId") Integer transactionId,
                      @Param("team_c_assigned_id") String team_c_assigned_id,
                      @Param("team_c_status") String team_c_status,
                      @Param("team_d_status") String team_d_status,
                      @Param("team_c_remark") String team_c_remark,
                      @Param("price_per_unit") BigDecimal price_per_unit,
                      @Param("quantity") BigDecimal quantity,
                      @Param("total_amount") BigDecimal total_amount,
                      @Param("level") Integer level,
                      @Param("process_type") String process_type,
                      @Param("project_id") String project_id,
                      @Param("measuring_unit") String measuring_unit,
                      @Param("no_of_units") Integer no_of_units,
                      @Param("actor_id") Integer actor_id,
                      @Param("comment") String comment,
                      @Param("crop") String crop,
                      @Param("geographical_coordinates") String geographical_coordinates,
                      @Param("status") String status,
                      @Param("parent_node") String parent_node);


    @Modifying
    @Query(value = "UPDATE transaction_audit_trail SET team_d_remark = :remark, team_d_status = :status_d, team_d_timestamp = CURRENT_DATE, team_c_status = :status_c WHERE transaction_id = :transactionId", nativeQuery = true)
    int revertTransactionByD(@Param("transactionId") Integer transactionId,
                    @Param("remark") String remark,
                    @Param("status_c") String status_c,
                    @Param("status_d") String status_d);

    @Query(value = """
SELECT username,
email_id,
first_name,
last_name
FROM qc_user_master WHERE role = :role and team_id = :team_id""", nativeQuery = true)
    List<Object[]> find_Members(@Param("role") String status,
                                  @Param("team_id") String team_id);


    @Query(value = """
SELECT transaction_id,
updated_date,
project_id,
problem_comment,
team_a_remark,
team_b_remark,
team_c_remark,
team_d_remark
FROM transaction_audit_trail WHERE team_d_status = :status order by transaction_id""", nativeQuery = true)
    List<Object[]> findRejectedTransactions(@Param("status") String status);





}
