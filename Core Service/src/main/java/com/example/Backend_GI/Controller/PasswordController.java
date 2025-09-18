package com.example.Backend_GI.Controller;

import com.example.Backend_GI.service.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/verify")
public class PasswordController {

    @Autowired
    private PasswordService passwordService;

    @PostMapping("/v1")
    public ResponseEntity<Map<String, Integer>> verifyUser(@RequestBody Map<String, Object> payload) {
        try {
            String number = (String) payload.get("number");
            String passwordHash = (String) payload.get("hash");
//            System.out.println("This is the input number " + number);
//            System.out.println("This is the input hash " + passwordHash);

            int level = passwordService.verifyUser(number, passwordHash);
            return ResponseEntity.ok(Map.of("level", level));

        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("level", -1));
        }
    }
}
