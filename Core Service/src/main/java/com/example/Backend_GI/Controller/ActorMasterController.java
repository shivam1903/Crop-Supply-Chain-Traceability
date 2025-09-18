package com.example.Backend_GI.Controller;

import com.example.Backend_GI.model.ActorMaster;
import com.example.Backend_GI.service.ActorMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/actor")
public class ActorMasterController {

    @Autowired
    private ActorMasterService actorMasterService;

    // API to save actor details
    @PostMapping("/save")
    public ResponseEntity<String> saveActorDetails(@RequestBody ActorMaster actorMaster) {
        actorMasterService.saveActorDetails(actorMaster);
        return ResponseEntity.ok("Actor details saved successfully!");
    }

    // API to get actor details by ActorID
    @GetMapping("/getActorDetails")
    public ResponseEntity<List<ActorMaster>> getActorDetails(@RequestParam String project_id,
                                                    @RequestParam Integer actor_id) {

        List<ActorMaster> actorDetails = actorMasterService.getActorDetails(actor_id, project_id);
        return ResponseEntity.ok(actorDetails);
    }

    @GetMapping("/getActorProfile")
    public ResponseEntity<Map<String, Object>> getActorProfile(@RequestParam String contact_number){
        Map<String, Object> ActorProfile = actorMasterService.getActorProfileDetails(contact_number);
        return ResponseEntity.ok(ActorProfile);
    }


    @GetMapping("/find/projectlevel")
    public ResponseEntity<List<Map<String, String>>> getActorsByProjectAndLevel(@RequestParam String project_id,
                                                        @RequestParam Integer level) {

        List<Map<String, String>> actors = actorMasterService.getActorsByProjectAndLevel(project_id, level);
        return ResponseEntity.ok(actors);
    }

    // This needs to be deleted
    @GetMapping("/level/{number}")
    public ResponseEntity<Integer> getLevelbyNumber(@PathVariable String number){
        Integer level = actorMasterService.getLevelbyNumber(number);

        return ResponseEntity.ok(level);
    }

    @GetMapping("complete_actor_details")
    public ResponseEntity<Map<String, Object>> getDetailsbyNumber(@RequestParam String number) {
        Map<String, Object> actorDetails = actorMasterService.getDetailsbyNumber(number);
        return ResponseEntity.ok(actorDetails);
    }

    @GetMapping("get_project_list")
    public ResponseEntity<List<String>> getProjectIDs(@RequestParam Integer actor_id){

        List<String> project_ids = actorMasterService.getProjectIds(actor_id);
        return ResponseEntity.ok(project_ids);

    }

    @GetMapping("get_units_list")
    public ResponseEntity<List<String>> getUnitsofActor(@RequestParam String project_id,
                                                        @RequestParam Integer actor_id){
        List<String> units = actorMasterService.getUnitsforActor(actor_id, project_id);
        return ResponseEntity.ok(units);
    }
}
