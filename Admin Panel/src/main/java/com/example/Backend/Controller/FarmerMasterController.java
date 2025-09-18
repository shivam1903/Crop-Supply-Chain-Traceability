package com.example.Backend.Controller;

import com.example.Backend.Service.FarmerMasterService;
import com.example.Backend.Model.FarmerMaster;
import com.example.Backend.Service.FarmerProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/farmer")
public class FarmerMasterController {

    @Autowired
    private FarmerMasterService farmerMasterService;

    @Autowired
    private FarmerProjectService farmerProjectService;

    @GetMapping("/{farmerId}")
    public ResponseEntity<FarmerMaster> getFarmerDetails(@PathVariable String farmerId) {
        Optional<FarmerMaster> farmerDetails = farmerMasterService.getFarmerDetails(farmerId);
        return farmerDetails.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/getByUniqueCode")
    public ResponseEntity<Map<String,Object>> getDetailsByUniqueCode(@RequestParam String unique_code){
        List<Map<String,Object>> farmerDetails = farmerMasterService.getDetailsByUnique(unique_code);
        return ResponseEntity.ok(farmerDetails.get(0));
    }

    @GetMapping("/by_project")
    public ResponseEntity<List<Map<String, Object>>> getFarmersByProjectId(@RequestParam String project_id) {
        if (project_id == null || project_id.isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        List<Map<String, Object>> farmers = farmerMasterService.getFarmersByProjectId(project_id);
        return ResponseEntity.ok(farmers);
    }

    @GetMapping("/all_unique_farmers")
    public ResponseEntity<List<Map<String, Object>>> getAllUniqueFarmers() {
        List<Map<String, Object>> farmers = farmerMasterService.getAllUniqueFarmers();
        return ResponseEntity.ok(farmers);
    }

    @PostMapping("/assign")
    public ResponseEntity<String> assignFarmerToProject(@RequestBody Map<String, Object> requestBody) {
        try {
            farmerProjectService.saveFarmerProjectAssignment((String) requestBody.get("farmer_id"), (String) requestBody.get("project_id"));
            return ResponseEntity.ok("Farmer assigned to project successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to assign farmer: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteAssignment(
            @RequestParam String project_id,
            @RequestParam String farmer_id) {

        boolean deleted = farmerProjectService.deleteAssignment(project_id, farmer_id);

        if (deleted) {
            return ResponseEntity.ok("Assignment deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No assignment found with the given project_id and farmer_id.");
        }
    }


}