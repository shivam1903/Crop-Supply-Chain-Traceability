package com.example.Backend.Controller;


import com.example.Backend.Model.ProjectMaster;
import com.example.Backend.Service.ProjectLevelService;
import com.example.Backend.Service.ProjectService;
import com.example.Backend.Service.TransactionAuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectLevelService projectLevelService;

    @Autowired
    private TransactionAuditService transactionAuditService;

    @PostMapping("/save")
    public ResponseEntity<String> saveTransaction(@RequestBody ProjectMaster projectMaster) {
        try {
            System.out.println("Received request to save transaction: {} " + projectMaster.toString());

            String project_id = projectService.saveProject(projectMaster);

            return ResponseEntity.ok("Project saved successfully with ID: " + project_id);
        } catch (IllegalArgumentException e) {
            // Catch the exception and return a meaningful error response
            return ResponseEntity.status(400).body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error while saving transaction: " + e.getMessage());
        }
    }

    @GetMapping(value = "/getProjectDetails")
    public ResponseEntity<Map<String, Object>> getProjectDetails(@RequestParam String project_id){

        try {

//            String project_id = requestBody.get("project_id");
            if (project_id == null || project_id.isBlank()) {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("error", "Project_id is required");
                return ResponseEntity.badRequest().body(errorResponse);
            }

            Map<String, Object> projectDetails = projectService.getProjectbyProjectID(project_id);

            if (projectDetails == null) {
                Map<String, Object> noDataResponse = new HashMap<>();
                noDataResponse.put("message", "No projects found for the given project_id.");
                return ResponseEntity.ok(noDataResponse);
            }

            return ResponseEntity.ok(projectDetails);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "An error occurred while fetching project details: " + e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    @GetMapping(value = "/getAllProjects")
    public ResponseEntity<List<Map<String, Object>>> getAllProjects(){

        try {

            List<Map<String, Object>> projectDetails = projectService.getAllProjects();

            if (projectDetails == null) {
                List<Map<String, Object>> responseList = new ArrayList<>();
                Map<String, Object> noDataResponse = new HashMap<>();
                noDataResponse.put("message", "No projects found for the given project_id.");
                responseList.add(noDataResponse);
                return ResponseEntity.ok(responseList);
            }

            return ResponseEntity.ok(projectDetails);
        } catch (Exception e) {
            List<Map<String, Object>> responseList = new ArrayList<>();
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "An error occurred while fetching project details: " + e.getMessage());
            responseList.add(errorResponse);
            return ResponseEntity.status(500).body(responseList);
        }
    }

    @DeleteMapping("/deleteByProjectId")
    public ResponseEntity<String> deleteByProjectId(@RequestParam String project_id) {
        try {
            if (project_id == null || project_id.isBlank()) {
                return ResponseEntity.badRequest().body("Project ID is required");
            }
            projectService.deleteByProjectId(project_id);
            projectLevelService.deleteByProjectId(project_id);
            return ResponseEntity.ok("Project Levels deleted successfully for project_id: " + project_id);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error while deleting project levels: " + e.getMessage());
        }
    }

    @GetMapping("/transactions")
    public ResponseEntity<List<Map<String, Object>>> getTransactions(@RequestParam String project_id){
        try {
            if (project_id == null || project_id.isBlank()) {
                return ResponseEntity.status(500).body(null);
            }
            List<Map<String, Object>> output = transactionAuditService.getTransactionforProject(project_id);
            return ResponseEntity.ok(output);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/farmer-premiums")
    public ResponseEntity<List<Map<String, Object>>> getFarmerPremiumDetails(@RequestParam String project_id) {
        try {
            List<Map<String, Object>> result = projectService.getFarmerPremiums(project_id);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping("/update_nodes")
    public ResponseEntity<String> updateProjectNodes(@RequestBody Map<String, Object> request) {
        try {
            String projectId = (String) request.get("project_id");
            Integer nodes = (Integer) request.get("nodes");

            if (projectId == null || nodes == null) {
                return ResponseEntity.badRequest().body("project_id and nodes are required.");
            }

            boolean updated = projectService.updateNodesForProject(projectId, nodes);
            if (updated) {
                return ResponseEntity.ok("Nodes updated successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Project not found.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateProject(@RequestBody Map<String, Object> requestBody) {
        try {
            String message = projectService.updateProject(requestBody);
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating project: " + e.getMessage());
        }
    }

    @GetMapping("/getNodes")
    public ResponseEntity<Integer> getNodes(@RequestParam String project_id) {
        try {
            Integer nodes = projectService.getNodesbyProject(project_id);
            return ResponseEntity.ok(nodes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


}
