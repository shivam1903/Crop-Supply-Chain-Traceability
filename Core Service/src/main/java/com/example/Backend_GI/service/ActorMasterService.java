package com.example.Backend_GI.service;

import com.example.Backend_GI.model.ActorMaster;
import com.example.Backend_GI.repository.ActorMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ActorMasterService {

    @Autowired
    private ActorMasterRepository actorMasterRepository;

    // Save actor details
    public ActorMaster saveActorDetails(ActorMaster actorMaster) {
        return actorMasterRepository.save(actorMaster);
    }

    // Retrieve actor details by ActorID
    public List<ActorMaster> getActorDetails(Integer actorId, String project_id) {
        return actorMasterRepository.findByActorId(actorId, project_id);
    }

    public Integer getLevelbyNumber(String number){
        List<Integer> output = actorMasterRepository.findLevelbyNumber(number);
        if(output.get(0) != null){
            return output.get(0);
        }
        return -1;
    }

    public Map<String, Object> getActorProfileDetails(String contact_number){
        List<Map<String, Object>> coreDetails = actorMasterRepository.findCoreDetailsByActorId(contact_number);
        if (coreDetails == null) {
            throw new RuntimeException("Actor not found with actor_id: " + contact_number);
        }
        return coreDetails.get(0);
    }

    public Map<String, Object> getDetailsbyNumber(String number){
        List<Object[]> results = actorMasterRepository.findDetailsbyNumber(number);
        List<Map<String, Object>> output = results.stream().map(result -> {
            Map<String, Object> map = new HashMap<>();
            map.put("actor_id", result[0]);
            map.put("crop", (String) result[1]);
            map.put("level", result[2]);
            map.put("process", (String) result[3]);
            map.put("firm_name", (String) result[4]);
            map.put("contact_number", (String) result[5]);
            map.put("poc_name", (String) result[6]);
            map.put("address", (String) result[7]);
            map.put("state", (String) result[8]);
            map.put("district", (String) result[9]);
            map.put("license_number", result[10]);
            map.put("asset", result[11]);
            return map;
        }).collect(Collectors.toList());
        return output.get(0);
    }

    public List<Map<String, String>> getActorsByProjectAndLevel(String project_id, Integer level) {
        // Adjust level to level-1
        Integer adjustedLevel = level - 1;

        // Fetch actors using repository
        List<Object[]> results = actorMasterRepository.findActorsByProjectAndLevel(project_id, adjustedLevel);

        // Transform the result into desired format
        return results.stream().map(result -> {
            Map<String, String> map = new HashMap<>();
            map.put("actorId", (String) result[0]);
            map.put("firmName", (String) result[1]);
            return map;
        }).collect(Collectors.toList());
    }

    public List<String> getProjectIds(Integer actor_id){
        List<String> output = actorMasterRepository.findProjectIds(actor_id);
        return output;
    }

    public List<String> getUnitsforActor(Integer actor_id, String project_id){
        String unit_string = actorMasterRepository.findUnitsforActor(actor_id, project_id);
        return Arrays.asList(unit_string.split(",", -1));
    }
}
