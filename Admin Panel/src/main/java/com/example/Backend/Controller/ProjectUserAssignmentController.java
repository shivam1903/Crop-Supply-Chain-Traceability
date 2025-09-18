package com.example.Backend.Controller;

import com.example.Backend.Model.ProjectMaster;
import com.example.Backend.Model.ProjectUserAssignmentMaster;
import com.example.Backend.Service.ProjectUserAssignmentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/projectuserassignment")
public class ProjectUserAssignmentController {

    @Autowired
    private ProjectUserAssignmentService projectUserAssignmentService;


    @PostMapping("/addProjectUser")
    public ResponseEntity<String> saveProjectUser(@RequestBody ProjectUserAssignmentMaster projectUserAssignmentMaster) {
        try {
            System.out.println("Received request to save Project Assignment: {} " + projectUserAssignmentMaster.toString());

            String[] arr = projectUserAssignmentService.saveProject(projectUserAssignmentMaster);


            return ResponseEntity.ok("Project Assignment saved successfully with email ID: " + arr[0] + " and project_id: " + arr[1]);
        } catch (Exception e) {

            return ResponseEntity.status(500).body("Error while saving Project Assignment: " + e.getMessage());
        }
    }

    @GetMapping("/getUserforProject")
    public ResponseEntity<List<Map<String, Object>>> getUsers(@RequestParam String project_id){
        try {
            System.out.println("Received request to get users for project ID: " + project_id);

            List<Map<String, Object>> output = projectUserAssignmentService.getUsersforProject(project_id);


            return ResponseEntity.ok(output);
        } catch (Exception e) {

            return ResponseEntity.status(500).body(null);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateAssignment(
            @RequestParam String project_id,
            @RequestParam String user_email,
            @RequestParam(required = false) String new_project_id,
            @RequestParam(required = false) String new_user_email,
            @RequestParam(required = false) String new_role) {
        try {
            String result = projectUserAssignmentService.updateAssignment(project_id, user_email,
                    new_project_id, new_user_email, new_role);
            return ResponseEntity.ok(result);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error updating record: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteAssignment(
            @RequestParam String project_id,
            @RequestParam String user_email) {

        boolean deleted = projectUserAssignmentService.deleteAssignment(project_id, user_email);

        if (deleted) {
            return ResponseEntity.ok("Assignment deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No assignment found with the given project_id and user_email.");
        }
    }

    @GetMapping("/getProjectforUser")
    public ResponseEntity<List<Map<String, Object>>> getProjects(@RequestParam String user_email){
        try {
            System.out.println("Received request to get projects for user_email: " + user_email);

            List<Map<String, Object>> output = projectUserAssignmentService.getProjectsforUsers(user_email);


            return ResponseEntity.ok(output);
        } catch (Exception e) {

            return ResponseEntity.status(500).body(null);
        }
    }

}
