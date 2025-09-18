package com.example.Backend.Controller;

import com.example.Backend.Model.QCUserMaster;
import com.example.Backend.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
@RequestMapping("/api/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @PostMapping("/save")
    public ResponseEntity<String> saveUser(@RequestBody QCUserMaster user) {
        try {
//            logger.info("Received request to save transaction: {}", user);

            String userEmailId = userService.saveUser(user);
//            logger.info("User saved successfully with ID: {}", userEmailId);

            return ResponseEntity.ok("User saved successfully with ID: " + userEmailId);
        } catch (Exception e) {
//            logger.error("Error while saving User: {}", user, e);
            return ResponseEntity.status(500).body("Error while saving user: " + e.getMessage());
        }
    }

    @GetMapping("getDetails/{emailId}")
    public ResponseEntity<QCUserMaster> getUserDetails(@PathVariable String emailId) {
        try {
//            logger.info("Fetching details for email ID: {}", emailId);

            Optional<QCUserMaster> userDetails = userService.getUserDetails(emailId);
            if (userDetails.isPresent()) {
//                logger.info("User details found: {}", userDetails.get());
                return ResponseEntity.ok(userDetails.get());
            } else {
//                logger.warn("User not found for ID: {}", emailId);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
//            logger.error("Error while fetching User details for ID: {}", emailId, e);
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("getMembersDropDown")
    public ResponseEntity<List<Map<String, String>>> getMembersDropDown(@RequestParam String emailId) {
        try {

            List<Map<String, String>> responseList = new ArrayList<>();
            // Extract email ID from request body
//            String emailId = requestBody.get("emailId");
            if (emailId == null || emailId.isBlank()) {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "Email ID is required");
                responseList.add(errorResponse);
                return ResponseEntity.badRequest().body(responseList);
            }

            // Fetch user details from UserService
            Optional<QCUserMaster> userDetails = userService.getUserDetails(emailId);
            if (userDetails.isEmpty()) {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "User does not exist");
                responseList.add(errorResponse);
                return ResponseEntity.status(404).body(responseList);
            }

            QCUserMaster user = userDetails.get();
            String team_id = user.getTeam_id();
            String role = user.getRole();

            if (!role.equals("Leader")){
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "User is not Leader");
                responseList.add(errorResponse);
                return ResponseEntity.status(404).body(responseList);
            }

            List<Map<String, String>> members = userService.getmembersbyTeam(team_id);

            if (members == null) {
                Map<String, String> noDataResponse = new HashMap<>();
                noDataResponse.put("message", "No members found for the given team.");
                responseList.add(noDataResponse);
                return ResponseEntity.ok(responseList);
            }

            return ResponseEntity.ok(members);
        } catch (Exception e) {
            List<Map<String, String>> errorList = new ArrayList<>();
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "An error occurred while fetching transactions: " + e.getMessage());
            errorList.add(errorResponse);
            return ResponseEntity.status(500).body(errorList);
        }
    }


}
