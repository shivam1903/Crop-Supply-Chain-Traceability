package com.example.Backend.Service;


import com.example.Backend.Model.QCUserMaster;
import com.example.Backend.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public String saveUser(QCUserMaster user) {

        QCUserMaster savedUser = userRepository.save(user);
        return savedUser.getEmailId();
    }

    public Optional<QCUserMaster> getUserDetails(String emailId) {
        return userRepository.findById(emailId);
    }

    public List<Map<String, String>> getmembersbyTeam(String team_id) {

        // Fetch actors using repository
        List<Object[]> results = userRepository.findMembersbyTeam(team_id, "Member");

        // Transform the result into desired format
        return results.stream().map(result -> {
            Map<String, String> map = new HashMap<>();
            map.put("emailId", (String) result[0]);
            map.put("name", ((String) result[1] + " " + (String) result[2] ));
            return map;
        }).collect(Collectors.toList());
    }



}
