package com.example.Backend.Service;


import com.example.Backend.Model.AdminPanelUserMaster;
import com.example.Backend.Repository.AdminPanelUserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AdminPanelUserService {

    @Autowired
    private AdminPanelUserRepository adminPanelUserRepository;

    @Transactional
    public String saveAdminPanelUser(AdminPanelUserMaster adminPanelUserMaster) {

        AdminPanelUserMaster savedUser = adminPanelUserRepository.save(adminPanelUserMaster);

        return savedUser.getEmail_id();
    }


    public Map<String, Object> getUserDetails(String email_id){
        List<Map<String, Object>> details = adminPanelUserRepository.findUserDetails(email_id);
        return details.get(0);
    }

    public List<String> getLoginRole(String email_id){
        List<Map<String, Object>> output = adminPanelUserRepository.findLoginRole(email_id);
        if(output.isEmpty()){
            List<String> ans = new ArrayList<String>();
            ans.add(email_id);
            ans.add("Unauthorized");
            return ans;
        }
        List<String> final_output = new ArrayList<String>();
        final_output.add(email_id);
        final_output.add((String) output.get(0).get("role"));
        return final_output;
    }

    public List<Map<String, Object>> getAllUsers() {
        List<Object[]> results = adminPanelUserRepository.findAllUsers();
        return results.stream().map(result -> {
            Map<String, Object> map = new HashMap<>();
            map.put("email_id", result[0]); // Safely cast to Integer
            map.put("full_name", result[1] + " " + result[2]);
            map.put("role", result[3]);
            map.put("status", result[4]);
            map.put("contact", result[5]);
            return map;
        }).collect(Collectors.toList());
    }

    public List<Map<String, Object>> getAllCients() {
        List<Object[]> results = adminPanelUserRepository.findAllClients("client");
        return results.stream().map(result -> {
            Map<String, Object> map = new HashMap<>();
            map.put("email_id", result[0]); // Safely cast to Integer
            map.put("full_name", result[1] + " " + result[2]);
            map.put("role", result[3]);
            map.put("status", result[4]);
            map.put("contact", result[5]);
            return map;
        }).collect(Collectors.toList());
    }

    public List<Map<String, Object>> getAllAuditors() {
        List<Object[]> results = adminPanelUserRepository.findAllAuditors("audit");
        return results.stream().map(result -> {
            Map<String, Object> map = new HashMap<>();
            map.put("email_id", result[0]); // Safely cast to Integer
            map.put("full_name", result[1] + " " + result[2]);
            map.put("role", result[3]);
            map.put("status", result[4]);
            map.put("contact", result[5]);
            return map;
        }).collect(Collectors.toList());
    }

    public void updateUser(Map<String, Object> request) {
        String emailId = (String) request.get("email_id");
        if (emailId == null || emailId.isBlank()) {
            throw new IllegalArgumentException("email_id is mandatory");
        }

        AdminPanelUserMaster user = adminPanelUserRepository.findById(emailId)
                .orElseThrow(() -> new RuntimeException("User not found with email_id: " + emailId));

        if (request.containsKey("contact"))
            user.setContact((String) request.get("contact"));

        if (request.containsKey("first_name"))
            user.setFirst_name((String) request.get("first_name"));

        if (request.containsKey("last_name"))
            user.setLast_name((String) request.get("last_name"));

        if (request.containsKey("status"))
            user.setStatus((String) request.get("status"));

        if (request.containsKey("role"))
            user.setRole((String) request.get("role"));

        adminPanelUserRepository.save(user);
    }
}
