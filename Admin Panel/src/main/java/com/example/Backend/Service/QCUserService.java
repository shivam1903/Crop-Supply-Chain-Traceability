package com.example.Backend.Service;

import com.example.Backend.Model.ProjectMaster;
import com.example.Backend.Model.QCUserMaster;
import com.example.Backend.Repository.QCUserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class QCUserService {

    @Autowired
    private QCUserRepository qcUserRepository;

    @Transactional
    public String saveQCUser(QCUserMaster qcMaster) {

        QCUserMaster savedQC = qcUserRepository.save(qcMaster);

        return savedQC.getEmailId();
    }

    public List<Map<String, Object>> getAllQC() {
        List<Object[]> results=  qcUserRepository.findAllQC();
        return results.stream().map(result -> {
            Map<String, Object> map = new HashMap<>();
            map.put("name",  result[0] + " " + result[1]);
            map.put("username", result[2]);
            map.put("role", result[3]);
            map.put("team", result[4]);
            map.put("email_id", result[5]);
            map.put("contact", result[6]);
            return map;
        }).collect(Collectors.toList());
    }

    public List<Map<String, Object>> getCompleteQCDetails(String username) {
        return qcUserRepository.findQCDetails(username);

    }

    public void updateQCUser(String emailId, Map<String, Object> updates) {
        Optional<QCUserMaster> optional = qcUserRepository.findById(emailId);
        if (optional.isEmpty()) {
            throw new RuntimeException("User not found with emailId: " + emailId);
        }

        QCUserMaster user = optional.get();

        if (updates.containsKey("username")) {
            user.setUsername((String) updates.get("username"));
        }
        if (updates.containsKey("first_name")) {
            user.setFirst_name((String) updates.get("first_name"));
        }
        if (updates.containsKey("last_name")) {
            user.setLast_name((String) updates.get("last_name"));
        }
        if (updates.containsKey("contact")) {
            user.setContact((String) updates.get("contact"));
        }
        if (updates.containsKey("team_id")) {
            user.setTeam_id((String) updates.get("team_id"));
        }
        if (updates.containsKey("role")) {
            user.setRole((String) updates.get("role"));
        }

        qcUserRepository.save(user);
    }

    public void deleteQCUser(String emailId) {
        if (!qcUserRepository.existsById(emailId)) {
            throw new RuntimeException("User with emailId " + emailId + " does not exist.");
        }
        qcUserRepository.deleteById(emailId);
    }
}
