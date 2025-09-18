package com.example.Backend.Service;

import com.example.Backend.Model.ProjectMaster;
import com.example.Backend.Model.ProjectUserAssignmentMaster;
import com.example.Backend.Repository.ProjectUserAssignmentRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;

@Service
public class ProjectUserAssignmentService {

    @Autowired
    private ProjectUserAssignmentRepository projectUserAssignmentRepository;


    @Transactional
    public String[] saveProject(ProjectUserAssignmentMaster projectUserAssignmentMaster) {

        projectUserAssignmentMaster.setAssigned_date(LocalDate.now(ZoneId.of("Asia/Kolkata")));

        ProjectUserAssignmentMaster savedAssignment = projectUserAssignmentRepository.save(projectUserAssignmentMaster);
        String[] arr = new String[2];
        arr[0] = savedAssignment.getUser_email();
        arr[1] = savedAssignment.getProject_id();
        return arr;
    }

    public List<Map<String, Object>> getUsersforProject(String project_id){
        return projectUserAssignmentRepository.findUsers(project_id);
    }

    public List<Map<String, Object>> getProjectsforUsers(String user_email){
        return projectUserAssignmentRepository.findProjects(user_email);
    }

    @Transactional
    public String updateAssignment(String projectId, String userEmail,
                                   String newProjectId, String newUserEmail, String newRole) {

        ProjectUserAssignmentMaster record = projectUserAssignmentRepository.findByProjectIdAndUserEmailNative(projectId, userEmail);

        if (record == null) {
            throw new EntityNotFoundException("No record found for given project ID and user email");
        }

        // Update only if new values are provided
        if (newProjectId != null && !newProjectId.isBlank()) {
            record.setProject_id(newProjectId);
        }

        if (newUserEmail != null && !newUserEmail.isBlank()) {
            record.setUser_email(newUserEmail);
        }

        if (newRole != null && !newRole.isBlank()) {
            record.setRole(newRole);
        }
        record.setAssigned_date(LocalDate.now(ZoneId.of("Asia/Kolkata")));

        projectUserAssignmentRepository.save(record);

        return "Record updated successfully.";
    }

    @Transactional
    public boolean deleteAssignment(String projectId, String userEmail) {
        int deletedCount = projectUserAssignmentRepository.deleteByProjectIdAndUserEmail(projectId, userEmail);
        return deletedCount > 0;
    }


}
