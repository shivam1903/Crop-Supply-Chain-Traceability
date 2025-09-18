package com.example.Backend_GI.service;

import com.example.Backend_GI.model.Password;
import com.example.Backend_GI.repository.PasswordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PasswordService {

    @Autowired
    private PasswordRepository passwordRepository;

    public int verifyUser(String number, String passwordHash) {
        List<Object[]> results = passwordRepository.findByNumber(number);


//        System.out.println("Received hash : " + storedHash);
//        System.out.println("Received Level " + level);
        if (results == null || results.isEmpty()) {

            return -1; // User not found
        }
        Object[] result = results.get(0);
        String storedHash = (String) result[0]; // Assuming the first column is the hash
        int level = (Integer) result[1];
        // Compare the stored hash with the provided hash
        if (storedHash.equals(passwordHash)) {
            return level; // Return the user's level if password hash matches
        }

        return -1; // Return -1 if password hash does not match
    }
}
