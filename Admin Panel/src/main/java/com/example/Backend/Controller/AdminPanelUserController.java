package com.example.Backend.Controller;


import com.example.Backend.Model.AdminPanelUserMaster;
import com.example.Backend.Model.ProjectMaster;
import com.example.Backend.Service.AdminPanelUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/adminpaneluser")
public class AdminPanelUserController {

    @Autowired
    private AdminPanelUserService adminPanelUserService;

    @PostMapping("/save")
    public ResponseEntity<String> saveTransaction(@RequestBody AdminPanelUserMaster adminPanelUserMaster) {
        try {
            System.out.println("Received request to save transaction: {} " + adminPanelUserMaster.toString());

            String email_id = adminPanelUserService.saveAdminPanelUser(adminPanelUserMaster);


            return ResponseEntity.ok("Project saved successfully with ID: " + email_id);
        } catch (Exception e) {

            return ResponseEntity.status(500).body("Error while saving admin panel user: " + e.getMessage());
        }
    }

    @GetMapping("/getDetails")
    public ResponseEntity<Map<String, Object>> getUserDetail(@RequestParam String email_id){
        try {
            System.out.println("Received request to get details of " + email_id );

            Map<String, Object> userdetails = adminPanelUserService.getUserDetails(email_id);

            return ResponseEntity.ok(userdetails);
        } catch (Exception e) {
            Map<String, Object> error_map = new HashMap<String, Object>();
            error_map.put("Error", "User Not found");
            return ResponseEntity.status(500).body(error_map);
        }
    }

    @GetMapping("/getLoginRole")
    public ResponseEntity<List<String>> getLoginInfo(@RequestParam String email_id){
        try {
            System.out.println("Received request to get role of " + email_id );

            List<String> userdetails = adminPanelUserService.getLoginRole(email_id);

            return ResponseEntity.ok(userdetails);
        } catch (Exception e) {
            List<String> error_list = new ArrayList<String>();
            error_list.add("Data not found");
            return ResponseEntity.status(500).body(error_list);
        }
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<List<Map<String, Object>>> getAllUsers(){
        try {
            System.out.println("Received request to get All users ");

            List<Map<String, Object>> allUsers = adminPanelUserService.getAllUsers();

            return ResponseEntity.ok(allUsers);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/getAllClients")
    public ResponseEntity<List<Map<String, Object>>> getAllClients(){
        try {
            System.out.println("Received request to get all Clients ");

            List<Map<String, Object>> allClients = adminPanelUserService.getAllCients();

            return ResponseEntity.ok(allClients);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/getAllAuditors")
    public ResponseEntity<List<Map<String, Object>>> getAllAuditors(){
        try {
            System.out.println("Received request to get all Auditors ");

            List<Map<String, Object>> allAuditors = adminPanelUserService.getAllAuditors();

            return ResponseEntity.ok(allAuditors);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PutMapping("/update-user")
    public ResponseEntity<String> updateAdminPanelUser(@RequestBody Map<String, Object> request) {
        try {
            adminPanelUserService.updateUser(request);
            return ResponseEntity.ok("User updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Update failed: " + e.getMessage());
        }
    }

}
