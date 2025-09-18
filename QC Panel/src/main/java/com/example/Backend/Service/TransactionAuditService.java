package com.example.Backend.Service;

import com.example.Backend.Model.TransactionAudit;
import com.example.Backend.Repository.TransactionAuditRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionAuditService {

    @Autowired
    private TransactionAuditRepository transactionAuditRepository;

    public int safeParseInt(Object value, int defaultValue) {
        if (value == null) return defaultValue;
        if (value instanceof Number) return ((Number) value).intValue();
        try {
            return Integer.parseInt(value.toString());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public List<Map<String, Object>> getAdminTransactions(String team_id) {
        List<Object[]> results = List.of();

        if(team_id.equals("A")) {
            results = transactionAuditRepository.find_A_PendingTransactions("Pending_Assignment", "Pending_Check");
        }
        else if (team_id.equals("B")){
            results = transactionAuditRepository.find_B_PendingTransactions("Pending_Assignment", "Pending_Check");
        }
        else if(team_id.equals("C")){
            results = transactionAuditRepository.find_C_PendingTransactions("Pending_Assignment", "Pending_Check", "Reverted");
        }
        else if(team_id.equals("D")){
            results = transactionAuditRepository.find_D_PendingTransactions("Pending_Assignment", "Pending_Check");
        } else {
            return null;
        }

        return results.stream().map(result -> {
            Map<String, Object> map = new HashMap<>();
            map.put("transactionId", ((Number) result[0]).intValue());
            map.put("LastUpdatedDate", result[1]);
            map.put("projectId", result[2]);
            map.put("status", result[3]);
            map.put("assigned_id", result[4]);

            // Add additional statuses if team_id == D
            if (team_id.equals("D")) {
                map.put("teamA_remark", result[5]);
                map.put("teamB_remark", result[6]);
                map.put("teamC_remark", result[7]);
                map.put("problemComment", result[8]);
            }

            return map;
        }).collect(Collectors.toList());
    }

    public Map<String, Object> getCompleteTransaction(Integer transactionId) {
        return transactionAuditRepository.findTransaction(transactionId);

    }

    public Map<String, Object> getTransactionForBlockchain(Integer transactionId) {
        return transactionAuditRepository.findTransactionForBlockchain(transactionId);

    }


    public List<Map<String, Object>> getMemberTransactions(String team_id, String emailId) {
        List<Object[]> results = List.of();
        if(team_id.equals("A")) {
            results = transactionAuditRepository.find_A_PendingMemberTransactions("Pending_Check", emailId);
            return results.stream().map(result -> {
                Map<String, Object> map = new HashMap<>();
                map.put("transaction_id", ((Number) result[0]).intValue()); // Safely cast to Integer
                map.put("LastUpdated_date", result[1]);
                map.put("project_id", result[2]);
                map.put("problem_comment", result[3]);
                return map;
            }).collect(Collectors.toList());
        }
        else if (team_id.equals("B")){
            results = transactionAuditRepository.find_B_PendingMemberTransactions("Pending_Check", emailId);
            return results.stream().map(result -> {
                Map<String, Object> map = new HashMap<>();
                map.put("transaction_id", ((Number) result[0]).intValue()); // Safely cast to Integer
                map.put("LastUpdated_date", result[1]);
                map.put("project_id", result[2]);
                map.put("team_a_remark", result[3]);
                return map;
            }).collect(Collectors.toList());
        }
        else if(team_id.equals("C")){
            results = transactionAuditRepository.find_C_PendingMemberTransactions("Pending_Check", emailId);
            return results.stream().map(result -> {
                Map<String, Object> map = new HashMap<>();
                map.put("transaction_id", ((Number) result[0]).intValue()); // Safely cast to Integer
                map.put("LastUpdated_date", result[1]);
                map.put("project_id", result[2]);
                map.put("team_a_remark", result[3]);
                map.put("team_b_remark", result[4]);
                return map;
            }).collect(Collectors.toList());
        }
        else if(team_id.equals("D")){
            results = transactionAuditRepository.find_D_PendingMemberTransactions("Pending_Check", emailId);
            return results.stream().map(result -> {
                Map<String, Object> map = new HashMap<>();
                map.put("transaction_id", ((Number) result[0]).intValue()); // Safely cast to Integer
                map.put("LastUpdated_date", result[1]);
                map.put("project_id", result[2]);
                map.put("problem_comment", result[3]);
                map.put("team_a_remark", result[4]);
                map.put("team_b_remark", result[5]);
                map.put("team_c_remark", result[6]);
                return map;
            }).collect(Collectors.toList());
        }
        else{
            return null;
        }

    }

    public List<Map<String, Object>> getMemberList(String team_id) {
        List<Object[]> results = transactionAuditRepository.find_Members("Member", team_id);
        return results.stream().map(result -> {
            Map<String, Object> map = new HashMap<>();
            map.put("username",  result[0]); // Safely cast to Integer
            map.put("email_id", result[1]);
            map.put("full_name", result[2] + " " + result[3]);
            return map;
        }).collect(Collectors.toList());
    }

    @Transactional
    public String assignTransaction(Integer transactionId, String member_teamId, String member_emailId) {
        int rowsUpdated = 0;
        if(member_teamId.equals("A")){
            rowsUpdated = transactionAuditRepository.assignTeamA(transactionId, member_emailId, "Pending_Check");
        }
        else if(member_teamId.equals("B")){
            rowsUpdated = transactionAuditRepository.assignTeamB(transactionId, member_emailId, "Pending_Check");
        }
        else if(member_teamId.equals("C")){
            rowsUpdated = transactionAuditRepository.assignTeamC(transactionId, member_emailId, "Pending_Check");
        }
        else if(member_teamId.equals("D")){
            rowsUpdated = transactionAuditRepository.assignTeamD(transactionId, member_emailId, "Pending_Check");
        }
        if (rowsUpdated > 0) {
            return "Transaction updated successfully. Last updated date: " + java.time.LocalDate.now();
        } else {
            throw new IllegalArgumentException("Transaction ID " + transactionId + " does not exist.");
        }
    }

    @Transactional
    public String updateBlockchainHash(int transactionId, String hash){
        int rowsUpdated = transactionAuditRepository.addBlockchainHash(transactionId ,hash, "Approved");
        if (rowsUpdated > 0) {
            return "Transaction updated successfully. Last updated date: " + java.time.LocalDate.now();
        } else {
            throw new IllegalArgumentException("Transaction ID " + transactionId + " does not exist.");
        }
    }

    @Transactional
    public String updateTransaction(Map<String, String> requestBody, int transactionId, String team_Id, String approver_username){
        Map<String, Object> transaction = getCompleteTransaction(transactionId);

        BigDecimal price_per_unit = requestBody.containsKey("price_per_unit")
                ? new BigDecimal(requestBody.get("price_per_unit"))
                : safeParseBigDecimal(transaction.get("price_per_unit"), BigDecimal.ZERO);

        BigDecimal quantity = requestBody.containsKey("quantity")
                ? new BigDecimal(requestBody.get("quantity"))
                : safeParseBigDecimal(transaction.get("quantity"), BigDecimal.ZERO);

        BigDecimal total_amount = requestBody.containsKey("total_amount")
                ? new BigDecimal(requestBody.get("total_amount"))
                : safeParseBigDecimal(transaction.get("total_amount"), BigDecimal.ZERO);

        int level = requestBody.containsKey("level")
                ? Integer.parseInt(requestBody.get("level"))
                : safeParseInt(transaction.get("level"), 0);

        int no_of_units = requestBody.containsKey("no_of_units")
                ? Integer.parseInt(requestBody.get("no_of_units"))
                : safeParseInt(transaction.get("no_of_units"), 0);

        int actorId = requestBody.containsKey("actor_id")
                ? Integer.parseInt(requestBody.get("actor_id"))
                : safeParseInt(transaction.get("actor_id"), 9999);


        String process_type = requestBody.getOrDefault("process_type", (String) transaction.getOrDefault("process_type", ""));
        String project_id = requestBody.getOrDefault("project_id", (String) transaction.getOrDefault("project_id", ""));
        String measuring_unit = requestBody.getOrDefault("measuring_unit", (String) transaction.getOrDefault("measuring_unit", ""));
        String comment = requestBody.getOrDefault("comment", (String) transaction.getOrDefault("comment", ""));
        String crop = requestBody.getOrDefault("crop", (String) transaction.getOrDefault("crop", ""));
        String geographical_coordinates = requestBody.getOrDefault("geographical_coordinates", (String) transaction.getOrDefault("geographical_coordinates", ""));
        String status = requestBody.getOrDefault("status", (String) transaction.getOrDefault("status", ""));
        String parent_node = requestBody.getOrDefault("parent_node", (String) transaction.getOrDefault("parent_node", ""));
        String remark = requestBody.get("remark");
        int rowsUpdated = 0;
        if(team_Id.equals("A")){
            rowsUpdated = transactionAuditRepository.updateByTeamA(transactionId,
                    approver_username,
                                                        "Approved",
                                                        "Pending_Assignment",
                                                                    remark,
                                                                    price_per_unit,
                                                                    quantity,
                                                                    total_amount,
                                                                    level,
                                                                    process_type,
                                                                    project_id,
                                                                    measuring_unit,
                                                                    no_of_units,
                                                                    actorId,
                                                                    comment,
                                                                    crop,
                                                                    geographical_coordinates,
                                                                    status,
                                                                    parent_node);
        }
        else if(team_Id.equals("B")){
            rowsUpdated = transactionAuditRepository.updateByTeamB(transactionId,
                    approver_username,
                    "Approved",
                    "Pending_Assignment",
                    remark,
                    price_per_unit,
                    quantity,
                    total_amount,
                    level,
                    process_type,
                    project_id,
                    measuring_unit,
                    no_of_units,
                    actorId,
                    comment,
                    crop,
                    geographical_coordinates,
                    status,
                    parent_node);
        }
        else if(team_Id.equals("C")){
            rowsUpdated = transactionAuditRepository.updateByTeamC(transactionId,
                    approver_username,
                    "Approved",
                    "Pending_Assignment",
                    remark,
                    price_per_unit,
                    quantity,
                    total_amount,
                    level,
                    process_type,
                    project_id,
                    measuring_unit,
                    no_of_units,
                    actorId,
                    comment,
                    crop,
                    geographical_coordinates,
                    status,
                    parent_node);
        }

        if (rowsUpdated > 0) {
            return "Transaction updated successfully. Last updated date: " + java.time.LocalDate.now();
        } else {
            throw new IllegalArgumentException("Transaction ID " + transactionId + " does not exist.");
        }
    }

    @Transactional
    public String revertTransaction(int transactionId, String remark){
        Map<String, Object> transaction = getCompleteTransaction(transactionId);


        int rowsUpdated = transactionAuditRepository.revertTransactionByD(transactionId, remark, "Reverted", "Reverted");

        if (rowsUpdated > 0) {
            return "Transaction updated successfully. Last updated date: " + java.time.LocalDate.now();
        } else {
            throw new IllegalArgumentException("Transaction ID " + transactionId + " does not exist.");
        }
    }

    public List<Map<String, Object>> getRejectTransactions() {
        List<Object[]> results = transactionAuditRepository.findRejectedTransactions("Reverted");
        return results.stream().map(result -> {
            Map<String, Object> map = new HashMap<>();
            map.put("transaction_id", ((Number) result[0]).intValue()); // Safely cast to Integer
            map.put("LastUpdated_date", result[1]);
            map.put("project_id", result[2]);
            map.put("problem_comment", result[3]);
            map.put("team_a_remark", result[4]);
            map.put("team_b_remark", result[5]);
            map.put("team_c_remark", result[6]);
            map.put("team_d_remark", result[7]);
            return map;
        }).collect(Collectors.toList());
    }

    private BigDecimal safeParseBigDecimal(Object value, BigDecimal defaultValue) {
        try {
            if (value == null) return defaultValue;
            return new BigDecimal(value.toString());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }


}
