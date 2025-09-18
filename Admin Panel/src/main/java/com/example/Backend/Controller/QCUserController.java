package com.example.Backend.Controller;


import com.example.Backend.Model.ProjectLevelMaster;
import com.example.Backend.Model.QCUserMaster;
import com.example.Backend.Repository.QCUserRepository;
import com.example.Backend.Service.QCUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/qcuser")
public class QCUserController {

    @Autowired
    private QCUserService qcUserService;

    @PostMapping("/save")
    public ResponseEntity<String> saveTransaction(@RequestBody QCUserMaster qcUserMaster) {
        try {
            String saved_email = qcUserService.saveQCUser(qcUserMaster);

            return ResponseEntity.ok("QC user saved with email id: " + saved_email);
        } catch (Exception e) {

            return ResponseEntity.status(500).body("Error while saving QC Master: " + e.getMessage());
        }
    }

    @GetMapping(value = "/getAllQCUsers")
    public ResponseEntity<List<Map<String, Object>>> getAllQCUsers(){

        try {

            List<Map<String, Object>> QCDetails = qcUserService.getAllQC();

            if (QCDetails == null) {
                List<Map<String, Object>> responseList = new ArrayList<>();
                Map<String, Object> noDataResponse = new HashMap<>();
                noDataResponse.put("message", "No QC found.");
                responseList.add(noDataResponse);
                return ResponseEntity.ok(responseList);
            }

            return ResponseEntity.ok(QCDetails);
        } catch (Exception e) {
            List<Map<String, Object>> responseList = new ArrayList<>();
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "An error occurred while fetching project details: " + e.getMessage());
            responseList.add(errorResponse);
            return ResponseEntity.status(500).body(responseList);
        }
    }

    @GetMapping("/getCompleteQCDetail")
    public ResponseEntity<List<Map<String, Object>>> getCompleteQCDetail(@RequestParam String username){
//        int actor_id = (Integer) payload.get("actor_id");
        try {

            List<Map<String, Object>> qcDetail = qcUserService.getCompleteQCDetails(username);

            if (qcDetail == null) {
                List<Map<String, Object>> responseList = new ArrayList<>();
                Map<String, Object> noDataResponse = new HashMap<>();
                noDataResponse.put("message", "No QC Users found for the given username.");
                responseList.add(noDataResponse);
                return ResponseEntity.ok(responseList);
            }

            return ResponseEntity.ok(qcDetail);
        } catch (Exception e) {
            List<Map<String, Object>> responseList = new ArrayList<>();
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "An error occurred while fetching project details: " + e.getMessage());
            responseList.add(errorResponse);
            return ResponseEntity.status(500).body(responseList);
        }

    }

    @PutMapping("/update")
    public ResponseEntity<String> updateQCUser(@RequestBody Map<String, Object> request) {
        try {
            String emailId = (String) request.get("emailId");
            if (emailId == null || emailId.isBlank()) {
                return ResponseEntity.badRequest().body("emailId is mandatory.");
            }

            qcUserService.updateQCUser(emailId, request);
            return ResponseEntity.ok("User updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Update failed: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{emailId}")
    public ResponseEntity<String> deleteQCUser(@PathVariable String emailId) {
        try {
            qcUserService.deleteQCUser(emailId);
            return ResponseEntity.ok("User deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Deletion failed: " + e.getMessage());
        }
    }

}
