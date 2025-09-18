package com.example.Backend.Controller;

import com.example.Backend.Model.ProjectLevelMaster;
import com.example.Backend.Model.ProjectMaster;
import com.example.Backend.Service.ProjectLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/projectlevel")
public class ProjectLevelController {

    @Autowired
    private ProjectLevelService projectLevelService;

    @PostMapping("/save")
    public ResponseEntity<String> saveTransaction(@RequestBody List<Map<String, Object>> requestBody) {
        try {
            int count = 0;
            for(Map<String, Object> map : requestBody){
                ProjectLevelMaster projectLevelMaster = new ProjectLevelMaster();
                projectLevelMaster.setProject_id((String) map.get("project_id"));
                projectLevelMaster.setLevel_name((String) map.get("level_name"));
                projectLevelMaster.setAsset((String) map.get("asset"));
                projectLevelMaster.setProcess_type((String) map.get("process_type"));
                projectLevelMaster.setLevel_number((int) map.get("level_number"));
                int saved_id = projectLevelService.saveProjectLevel(projectLevelMaster);
                count++;
            }

            return ResponseEntity.ok("Total no of level saved successfully are: " + count);
        } catch (Exception e) {

            return ResponseEntity.status(500).body("Error while saving levels: " + e.getMessage());
        }
    }

    @GetMapping(value = "/getLevelList")
    public ResponseEntity<List<Map<String, Object>>> getLevelList(@RequestParam String project_id) {
        try {

            List<Map<String, Object>> responseList = new ArrayList<>();

//            String project_id = requestBody.get("project_id");
            if (project_id == null || project_id.isBlank()) {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("error", "Project ID is required");
                responseList.add(errorResponse);
                return ResponseEntity.badRequest().body(responseList);
            }

            List<Map<String, Object>> level_list = projectLevelService.getLevelList(project_id);

            if (level_list == null) {
                Map<String, Object> noDataResponse = new HashMap<>();
                noDataResponse.put("message", "No details found for the given project_id.");
                responseList.add(noDataResponse);
                return ResponseEntity.ok(responseList);
            }

            return ResponseEntity.ok(level_list);
        } catch (Exception e) {
            List<Map<String, Object>> errorList = new ArrayList<>();
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "An error occurred while fetching level list: " + e.getMessage());
            errorList.add(errorResponse);
            return ResponseEntity.status(500).body(errorList);
        }
    }

    @DeleteMapping("/deleteProjectLevel")
    public ResponseEntity<String> deleteLevel(
            @RequestParam String project_id,
            @RequestParam Integer level_number) {
        try {
            projectLevelService.deleteLevel(project_id, level_number);
            return ResponseEntity.ok("Level deleted and remaining levels updated.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/addNewProject")
    public ResponseEntity<String> addLevel(@RequestBody ProjectLevelMaster newLevel) {
        try {
            projectLevelService.addLevel(newLevel);
            return ResponseEntity.ok("Level added successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @PutMapping("/updateProjectLevel")
    public ResponseEntity<String> updateLevel(
            @RequestParam String project_id,
            @RequestParam Integer level_number,
            @RequestBody Map<String, Object> updateFields) {
        try {
            projectLevelService.updateLevel(project_id, level_number, updateFields);
            return ResponseEntity.ok("Level updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

}
