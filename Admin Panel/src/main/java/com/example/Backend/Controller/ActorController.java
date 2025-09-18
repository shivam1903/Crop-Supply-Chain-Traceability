package com.example.Backend.Controller;

import com.example.Backend.Model.ActorMaster;
import com.example.Backend.Service.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/actor")
public class ActorController {

    @Autowired
    private ActorService actorService;

    @PostMapping("/saveNew")
    public ResponseEntity<String> createActor(@RequestBody ActorMaster actor) {
        String actor_id = actorService.saveNewActor(actor);
        return ResponseEntity.ok("Saved with actor_id: " + actor_id);
    }

    @PostMapping("/saveExisting")
    public ResponseEntity<String> duplicateActor(@RequestBody Map<String, Object> payload) {
        int actor_id = (Integer) payload.get("actor_id");
        String project_id = (String) payload.get("project_id");
        String units = (String) payload.get("measuring_unit");
        String process = (String) payload.get("process");
        String asset = (String) payload.get("asset");
        ActorMaster newActor = actorService.duplicateActorWithNewProject(actor_id, project_id, units, process, asset);
        return ResponseEntity.ok("New Actor saved with actor_id " + newActor.getId().getActor_id());
    }

    @GetMapping("/getCompleteActorDetails")
    public ResponseEntity<List<Map<String, Object>>> getActorDetails(@RequestParam Integer actor_id){
//        int actor_id = (Integer) payload.get("actor_id");
        try {

            List<Map<String, Object>> actorDetails = actorService.getCompleteActorDetails(actor_id);

            if (actorDetails == null) {
                List<Map<String, Object>> responseList = new ArrayList<>();
                Map<String, Object> noDataResponse = new HashMap<>();
                noDataResponse.put("message", "No actors found for the given actor_id.");
                responseList.add(noDataResponse);
                return ResponseEntity.ok(responseList);
            }

            return ResponseEntity.ok(actorDetails);
        } catch (Exception e) {
            List<Map<String, Object>> responseList = new ArrayList<>();
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "An error occurred while fetching project details: " + e.getMessage());
            responseList.add(errorResponse);
            return ResponseEntity.status(500).body(responseList);
        }

    }

    @GetMapping(value = "/getAllActors")
    public ResponseEntity<List<Map<String, Object>>> getAllProjects(){

        try {
            List<Map<String, Object>> actorList = actorService.getAllActors();

            if (actorList == null) {
                List<Map<String, Object>> responseList = new ArrayList<>();
                Map<String, Object> noDataResponse = new HashMap<>();
                noDataResponse.put("message", "No actors have been created yet.");
                responseList.add(noDataResponse);
                return ResponseEntity.ok(responseList);
            }

            return ResponseEntity.ok(actorList);
        } catch (Exception e) {
            List<Map<String, Object>> responseList = new ArrayList<>();
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "An error occurred while fetching project details: " + e.getMessage());
            responseList.add(errorResponse);
            return ResponseEntity.status(500).body(responseList);
        }
    }

}


