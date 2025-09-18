package com.example.Backend_GI.service;

import com.example.Backend_GI.model.ActorMaster;
import com.example.Backend_GI.model.InitialTransaction;
import com.example.Backend_GI.model.TransactionAudit;
import com.example.Backend_GI.repository.TransactionAuditRepository;
import com.example.Backend_GI.repository.TransactionRepository;
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
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionAuditRepository transactionAuditRepository;

    @Autowired
    private FarmerProjectService farmerProjectService;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Integer saveTransaction(InitialTransaction transaction) {
        this.resetSequence();
        transaction.setEntry_date(LocalDate.now());
        transaction.setUpdated_date(LocalDate.now());

        // Calculate totalAmount
        if(transaction.getQuantity() != null & transaction.getPrice_per_unit() != null){
            transaction.setTotal_amount(transaction.getQuantity().multiply(transaction.getPrice_per_unit()));
            if(transaction.getLevel() == 1) {
                BigDecimal cur_quantity = transaction.getQuantity();
                String project_id = transaction.getProject_id();
                String farmer_id = transaction.getParent_node();
                String resp = farmerProjectService.deductQuota(farmer_id, project_id, cur_quantity);
                System.out.println(resp);
            }
        }
        this.resetSequence();
        InitialTransaction savedTransaction = transactionRepository.save(transaction);

        return savedTransaction.getTransaction_id();
    }



    public Optional<InitialTransaction> getTransactionDetails(int transactionId) {
        return transactionRepository.findById(transactionId);
    }

    public List<Map<String, Object>> getTransactionsByActorId(Integer actorId) {
        List<Object[]> results = transactionRepository.findTransactionsByActorId(actorId);
        return results.stream().map(result -> {
            Map<String, Object> map = new HashMap<>();
            map.put("transactionId", ((Number) result[0]).intValue()); // Safely cast to Integer
            map.put("entryDate", result[1]);
            map.put("status", result[2]);
            map.put("project_id", result[3]);
            map.put("parent_node", result[4]);
            return map;
        }).collect(Collectors.toList());
    }

    public List<Map<String, Object>> getCoorAndLevelbyProj(String projectId) {
        List<Object[]> results = transactionRepository.findGCandLevel(projectId);
        return results.stream().map(result -> {
            Map<String, Object> map = new HashMap<>();
            map.put("Geographical Coordinates", result[0]); // Safely cast to Integer
            map.put("Level", ((Number) result[1]).intValue());
            return map;
        }).collect(Collectors.toList());
    }

    @Transactional
    public String updateTransaction(Integer transactionId, String transactionDocument, String payment, String comment, String status, boolean problemreported, String problemcomment) {
        int rowsUpdated = transactionRepository.updateTransactionDetails(transactionId, transactionDocument, payment, comment, status);

        if (rowsUpdated > 0) {
            InitialTransaction t = transactionRepository.findById(transactionId).get();
            TransactionAudit ta = new TransactionAudit();
            ta.setTransaction_id(t.getTransaction_id());
            ta.setUpdatedDate(t.getUpdated_date());
            ta.setComment(t.getComment());
            ta.setLevel(t.getLevel());
            ta.setActor_id(t.getActor_id());
            ta.setPayment(t.getPayment());
            ta.setStatus(t.getStatus());
            ta.setParentNode(t.getParent_node());
            ta.setTransaction_date(t.getTransaction_date());
            ta.setTransaction_document(t.getTransaction_document());
            ta.setQuantity(t.getQuantity());
            ta.setCrop(t.getCrop());
            ta.setEntry_date(t.getEntry_date());
            ta.setGeographical_coordinates(t.getGeographical_coordinates());
            ta.setProcess_type(t.getProcess_type());
            ta.setPrice_per_unit(t.getPrice_per_unit());
            ta.setProject_id(t.getProject_id());
            ta.setTotal_amount(t.getTotal_amount());
            ta.setMeasuring_unit(t.getMeasuring_unit());
            ta.setNo_of_units(t.getNo_of_units());
            if(problemreported == false){
                ta.setTeam_a_status("Skipped");
                ta.setTeam_b_status("Pending_Assignment");
            }
            else {
                ta.setTeam_a_status("Pending_Assignment");
                ta.setTeam_b_status("Waiting");
            }
            ta.setTeam_a_timestamp(LocalDate.now());
            ta.setTeam_b_timestamp(LocalDate.now());
            ta.setTeam_c_timestamp(LocalDate.now());
            ta.setTeam_d_timestamp(LocalDate.now());
            ta.setTeam_c_status("Waiting");
            ta.setTeam_d_status("Waiting");
            ta.setProblem_reported(problemreported);
            ta.setProblem_comment(problemcomment);
            transactionAuditRepository.save(ta);


            return "Transaction updated successfully. Last updated date: " + java.time.LocalDate.now();
        } else {
            throw new IllegalArgumentException("Transaction ID " + transactionId + " does not exist.");
        }
    }

    public static class TransactionResponse {
        private Long transactionId;
        private String entryDate;

        public TransactionResponse(Long transactionId, String entryDate) {
            this.transactionId = transactionId;
            this.entryDate = entryDate;
        }

        // Getters and Setters
        public Long getTransactionId() {
            return transactionId;
        }

        public void setTransactionId(Long transactionId) {
            this.transactionId = transactionId;
        }

        public String getEntryDate() {
            return entryDate;
        }

        public void setEntryDate(String entryDate) {
            this.entryDate = entryDate;
        }
    }

    @Transactional
    public void resetSequence() {
        entityManager.createNativeQuery(
                "DO $$ BEGIN PERFORM setval('initial_transaction_id_seq_a', COALESCE((SELECT MAX(transaction_id) FROM initial_transaction), 1)); END $$;"
        ).executeUpdate();
    }


}
