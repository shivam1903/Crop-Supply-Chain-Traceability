package com.example.Backend.Controller;

import com.example.Backend.Model.QCUserMaster;
import com.example.Backend.Model.TransactionAudit;
import com.example.Backend.Service.BlockchainService;
import com.example.Backend.Service.TransactionAuditService;
import com.example.Backend.Service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.ProjectedPayload;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
@RequestMapping("/api/transaction_audit")
public class TransactionalAuditController {

    private static final Logger logger = LoggerFactory.getLogger(TransactionalAuditController.class);

    @Autowired
    private TransactionAuditService transactionAuditService;

    @Autowired
    private BlockchainService blockchainService;

    @Autowired
    private UserService userService;

    @GetMapping("getAdminTransactions")
    public ResponseEntity<List<Map<String, Object>>> getAdminTransactions(@RequestParam String emailId) {
        try {

            List<Map<String, Object>> responseList = new ArrayList<>();
            // Extract email ID from request body
//            String emailId = requestBody.get("email_id");
            if (emailId == null || emailId.isBlank()) {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("error", "Email ID is required");
                responseList.add(errorResponse);
                return ResponseEntity.badRequest().body(responseList);
            }

            // Fetch user details from UserService
            Optional<QCUserMaster> userDetails = userService.getUserDetails(emailId);
            if (userDetails.isEmpty()) {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("error", "User does not exist");
                responseList.add(errorResponse);
                return ResponseEntity.status(404).body(responseList);
            }

            QCUserMaster user = userDetails.get();
            String team_id = user.getTeam_id();
            String role = user.getRole();

            if (!role.equals("Leader")){
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("error", "User is not Leader");
                responseList.add(errorResponse);
                return ResponseEntity.status(404).body(responseList);
            }

            List<Map<String, Object>> transactions = transactionAuditService.getAdminTransactions(team_id);

            if (transactions == null) {
                Map<String, Object> noDataResponse = new HashMap<>();
                noDataResponse.put("message", "No transactions found for the given user.");
                responseList.add(noDataResponse);
                return ResponseEntity.ok(responseList);
            }

            return ResponseEntity.ok(transactions);
        } catch (Exception e) {
            List<Map<String, Object>> errorList = new ArrayList<>();
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "An error occurred while fetching transactions: " + e.getMessage());
            errorList.add(errorResponse);
            return ResponseEntity.status(500).body(errorList);
        }
    }

    @GetMapping("getCompleteTransaction")
    public ResponseEntity<Map<String, Object>> getCompleteTransaction(@RequestParam Integer transactionId){
        try {

            // Extract email ID from request body
//            int transactionId =  Integer.parseInt(requestBody.get("transaction_id"));

            Map<String, Object> transaction = transactionAuditService.getCompleteTransaction(transactionId);

            if (transaction == null) {
                Map<String, Object> noDataResponse = new HashMap<>();
                noDataResponse.put("message", "No transactions found for the given user.");
                return ResponseEntity.ok(noDataResponse);
            }

            return ResponseEntity.ok(transaction);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "An error occurred while fetching transactions: " + e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }

    }

    //remark and comment of previous member
    @GetMapping("getMemberTransactions")
    public ResponseEntity<List<Map<String, Object>>> getMemberTransactions(@RequestParam String emailId) {
        try {

            List<Map<String, Object>> responseList = new ArrayList<>();
            // Extract email ID from request body
//            String emailId = requestBody.get("email_id");
            if (emailId == null || emailId.isBlank()) {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("error", "Email ID is required");
                responseList.add(errorResponse);
                return ResponseEntity.badRequest().body(responseList);
            }

            // Fetch user details from UserService
            Optional<QCUserMaster> userDetails = userService.getUserDetails(emailId);
            if (userDetails.isEmpty()) {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("error", "User does not exist");
                responseList.add(errorResponse);
                return ResponseEntity.status(404).body(responseList);
            }

            QCUserMaster user = userDetails.get();
            String team_id = user.getTeam_id();
            String role = user.getRole();
            String username = user.getUsername();

            if ((team_id.equals("A") || team_id.equals("B") || team_id.equals("C")) && role.equals("Leader")){
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("error", "User is not Member");
                responseList.add(errorResponse);
                return ResponseEntity.status(404).body(responseList);
            }

            List<Map<String, Object>> transactions = transactionAuditService.getMemberTransactions(team_id, username);

            if (transactions == null) {
                Map<String, Object> noDataResponse = new HashMap<>();
                noDataResponse.put("message", "No transactions found for the given user.");
                responseList.add(noDataResponse);
                return ResponseEntity.ok(responseList);
            }

            return ResponseEntity.ok(transactions);
        } catch (Exception e) {
            List<Map<String, Object>> errorList = new ArrayList<>();
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "An error occurred while fetching transactions: " + e.getMessage());
            errorList.add(errorResponse);
            return ResponseEntity.status(500).body(errorList);
        }
    }

    @PutMapping(value = "/assignMemberEmail")
    public ResponseEntity<String> assignMemberEmail(@RequestBody Map<String, String> requestBody) {
        try {
            int transactionId = Integer.parseInt(requestBody.get("transaction_id"));
            String admin_emailId = requestBody.get("admin_email_id");
            String member_emailId = requestBody.get("member_email_id");

            if (admin_emailId == null || admin_emailId.isBlank()) {
                return ResponseEntity.status(500).body("Admin Email ID cannot be null");
            }

            if (member_emailId == null || member_emailId.isBlank()) {
                return ResponseEntity.status(500).body("Member Email ID cannot be null");
            }

            Optional<QCUserMaster> adminDetails = userService.getUserDetails(admin_emailId);
            if (adminDetails.isEmpty()) {
                return ResponseEntity.status(500).body("Admin does not exist in the system");
            }
            Optional<QCUserMaster> memberDetails = userService.getUserDetails(member_emailId);
            if (memberDetails.isEmpty()) {
                return ResponseEntity.status(500).body("Member does not exist in the system");
            }

            QCUserMaster admin = adminDetails.get();
            String admin_team_id = admin.getTeam_id();
            String admin_role = admin.getRole();

            QCUserMaster member = memberDetails.get();
            String member_team_id = member.getTeam_id();
            String member_role = member.getRole();
            String member_username = member.getUsername();

            if(!admin_role.equals("Leader")){
                return ResponseEntity.status(500).body("Given Leader email id is not a Leader");
            }

            if(!member_role.equals("Member")){
                return ResponseEntity.status(500).body("Given member email id is not a member:  " + member_role);
            }

            if(!admin_team_id.equals(member_team_id)){
                return ResponseEntity.status(500).body("Leader and Member are not from the same team, cannot assign transaction to different team members");
            }

            String result = transactionAuditService.assignTransaction(transactionId, member_team_id, member_username);

            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            logger.warn("Invalid request to update transaction: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            logger.error("Error while updating transaction", e);
            return ResponseEntity.status(500).body("An unexpected error occurred: " + e.getMessage());
        }
    }

    @PutMapping(value = "/updateTransaction")
    public ResponseEntity<String> updateTransaction(@RequestBody Map<String, String> requestBody) {
        try {
            int transactionId = Integer.parseInt(requestBody.get("transaction_id"));

            String member_emailId = requestBody.get("member_email_id");



            if (member_emailId == null || member_emailId.isBlank()) {
                return ResponseEntity.status(500).body("Member Email ID cannot be null");
            }

            Optional<QCUserMaster> memberDetails = userService.getUserDetails(member_emailId);
            if (memberDetails.isEmpty()) {
                return ResponseEntity.status(500).body("Member does not exist in the system");
            }

            QCUserMaster member = memberDetails.get();
            String member_team_id = member.getTeam_id();
            String member_role = member.getRole();
            String member_username = member.getUsername();

//            if(!member_role.equals("Member")){
//                return ResponseEntity.status(500).body("Given member email id is not a member:  " + member_role);
//            }

            String result = transactionAuditService.updateTransaction(requestBody, transactionId, member_team_id, member_username);

            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            logger.warn("Invalid request to update transaction: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            logger.error("Error while updating transaction", e);
            return ResponseEntity.status(500).body("An unexpected error occurred: " + e.getMessage());
        }
    }

    @PutMapping(value = "/revertTransaction")
    public ResponseEntity<String> revertTransaction(@RequestBody Map<String, String> requestBody){
        try {
            int transactionId = Integer.parseInt(requestBody.get("transaction_id"));

            String admin_emailId = requestBody.get("admin_email_id");
            String remark = requestBody.getOrDefault("remark", "Sending back to C");

            if (admin_emailId == null || admin_emailId.isBlank()) {
                return ResponseEntity.status(500).body("Member Email ID cannot be null");
            }

            Optional<QCUserMaster> adminDetails = userService.getUserDetails(admin_emailId);
            if (adminDetails.isEmpty()) {
                return ResponseEntity.status(500).body("Member does not exist in the system");
            }

            QCUserMaster admin = adminDetails.get();
            String admin_team_id = admin.getTeam_id();
            String admin_role = admin.getRole();

            if(!admin_role.equals("Leader")){
                return ResponseEntity.status(500).body("Given Leader email id is not an Leader:  " + admin_role);
            }

            if(!admin_team_id.equals("D")){
                return ResponseEntity.status(500).body("Given admin email id is not the admin for team D:  " + admin_team_id);
            }

            String result = transactionAuditService.revertTransaction(transactionId, remark);

            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            logger.warn("Invalid request to update transaction: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            logger.error("Error while updating transaction", e);
            return ResponseEntity.status(500).body("An unexpected error occurred: " + e.getMessage());
        }
    }

    @PutMapping(value = "/sendToBlockchain")
    public ResponseEntity<String> sendToBlockchain(@RequestBody Map<String, String> requestBody) {
        try {
            int transactionId = Integer.parseInt(requestBody.get("transaction_id"));

            String admin_emailId = requestBody.get("admin_email_id");

            if (admin_emailId == null || admin_emailId.isBlank()) {
                return ResponseEntity.status(500).body("Member Email ID cannot be null");
            }

            Optional<QCUserMaster> adminDetails = userService.getUserDetails(admin_emailId);
            if (adminDetails.isEmpty()) {
                return ResponseEntity.status(500).body("Member does not exist in the system");
            }

            QCUserMaster admin = adminDetails.get();
            String admin_teamid = admin.getTeam_id();
            String admin_role = admin.getRole();

            if(!admin_teamid.equals("D")){
                return ResponseEntity.status(500).body("Given admin email id is not from team D, it is from :  " + admin_teamid);
            }

            if(!admin_role.equals("Leader")){
                return ResponseEntity.status(500).body("Given admin email id is not a admin, it is :  " + admin_role);
            }

            Map<String, Object> transaction = transactionAuditService.getTransactionForBlockchain(transactionId);
            String data = mapToString(transaction);
            String result = blockchainService.addData(BigInteger.valueOf(transactionId), data).get();
            String hash_add = transactionAuditService.updateBlockchainHash(transactionId, result);
            String final_result = hash_add + " with hash " + result;
            return ResponseEntity.ok(final_result);
        } catch (IllegalArgumentException e) {
            logger.warn("Invalid request to update transaction: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            logger.error("Error while updating transaction", e);
            return ResponseEntity.status(500).body("An unexpected error occurred: " + e.getMessage());
        }
    }

    public String mapToString(Map<String, Object> transactionMap) {
        if (transactionMap == null || transactionMap.isEmpty()) {
            return "{}";
        }
        StringJoiner joiner = new StringJoiner(", ", "{", "}");
        for (Map.Entry<String, Object> entry : transactionMap.entrySet()) {
            joiner.add(entry.getKey() + "=" + entry.getValue());
        }
        return joiner.toString();
    }

    @GetMapping(value = "/getMembersList")
    public ResponseEntity<List<Map<String, Object>>> getMembersList(@RequestParam String emailId) {
        try {

            List<Map<String, Object>> responseList = new ArrayList<>();
            // Extract email ID from request body
//            String emailId = requestBody.get("email_id");
            if (emailId == null || emailId.isBlank()) {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("error", "Email ID is required");
                responseList.add(errorResponse);
                return ResponseEntity.badRequest().body(responseList);
            }

            // Fetch user details from UserService
            Optional<QCUserMaster> userDetails = userService.getUserDetails(emailId);
            if (userDetails.isEmpty()) {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("error", "User does not exist");
                responseList.add(errorResponse);
                return ResponseEntity.status(404).body(responseList);
            }

            QCUserMaster user = userDetails.get();
            String team_id = user.getTeam_id();
            String role = user.getRole();

            if (!role.equals("Leader")){
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("error", "User is not Leader");
                responseList.add(errorResponse);
                return ResponseEntity.status(404).body(responseList);
            }

            List<Map<String, Object>> memberList = transactionAuditService.getMemberList(team_id);

            if (memberList == null) {
                Map<String, Object> noDataResponse = new HashMap<>();
                noDataResponse.put("message", "No transactions found for the given user.");
                responseList.add(noDataResponse);
                return ResponseEntity.ok(responseList);
            }

            return ResponseEntity.ok(memberList);
        } catch (Exception e) {
            List<Map<String, Object>> errorList = new ArrayList<>();
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "An error occurred while fetching member list: " + e.getMessage());
            errorList.add(errorResponse);
            return ResponseEntity.status(500).body(errorList);
        }
    }


    @GetMapping("getRejectedTransactions")
    public ResponseEntity<List<Map<String, Object>>> getRejectedTransactions(@RequestParam String emailId) {
        try {

            List<Map<String, Object>> responseList = new ArrayList<>();
            // Extract email ID from request body
//            String emailId = requestBody.get("email_id");
            if (emailId == null || emailId.isBlank()) {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("error", "Email ID is required");
                responseList.add(errorResponse);
                return ResponseEntity.badRequest().body(responseList);
            }

            // Fetch user details from UserService
            Optional<QCUserMaster> userDetails = userService.getUserDetails(emailId);
            if (userDetails.isEmpty()) {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("error", "User does not exist");
                responseList.add(errorResponse);
                return ResponseEntity.status(404).body(responseList);
            }

            QCUserMaster user = userDetails.get();
            String team_id = user.getTeam_id();
            String role = user.getRole();

            if (!role.equals("Leader")){
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("error", "User is not Leader");
                responseList.add(errorResponse);
                return ResponseEntity.status(404).body(responseList);
            }

            if (!team_id.equals("D")){
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("error", "User is not part of team D");
                responseList.add(errorResponse);
                return ResponseEntity.status(404).body(responseList);
            }

            List<Map<String, Object>> transactions = transactionAuditService.getRejectTransactions();

            if (transactions == null) {
                Map<String, Object> noDataResponse = new HashMap<>();
                noDataResponse.put("message", "No transactions found for the given user.");
                responseList.add(noDataResponse);
                return ResponseEntity.ok(responseList);
            }

            return ResponseEntity.ok(transactions);
        } catch (Exception e) {
            List<Map<String, Object>> errorList = new ArrayList<>();
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "An error occurred while fetching transactions: " + e.getMessage());
            errorList.add(errorResponse);
            return ResponseEntity.status(500).body(errorList);
        }
    }

}



