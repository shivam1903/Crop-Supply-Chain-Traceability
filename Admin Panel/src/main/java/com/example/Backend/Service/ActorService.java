package com.example.Backend.Service;

import com.example.Backend.Model.ActorMaster;
import com.example.Backend.Model.ActorMasterId;
import com.example.Backend.Model.ProjectMaster;
import com.example.Backend.Repository.ActorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ActorService {

    @Autowired
    private ActorRepository actorRepository;


    @Transactional
    public String saveNewActor(ActorMaster actor) {
        // Get the maximum actor_id and increment it
        Integer newActorId = actorRepository.findMaxActorId() + 1;

        // Create a new composite key
        ActorMasterId newId = new ActorMasterId(newActorId, actor.getId().getProject_id());

        // Set the new ID in the actor object
        actor.setId(newId);

        // Save the actor in the database
        ActorMaster savedActor = actorRepository.save(actor);
        return savedActor.getId().getProject_id() + " " + savedActor.getId().getActor_id();

    }

    @Transactional
    public ActorMaster duplicateActorWithNewProject(Integer actorId, String newProjectId, String units, String process, String asset) {
        // Fetch all records for the given actor_id
        List<ActorMaster> existingActors = actorRepository.findByActorId(actorId);

        if (existingActors.isEmpty()) {
            throw new RuntimeException("No actor found with actor_id: " + actorId);
        }

        ActorMaster existingActor = existingActors.get(0);

        // Create a new ActorMasterId with the new project_id
        ActorMasterId newId = new ActorMasterId(actorId, newProjectId);

        // Copy details from the existing actor
        ActorMaster newActor = new ActorMaster();
        newActor.setId(newId);
        newActor.setCrop(existingActor.getCrop());
        newActor.setMeasuring_unit(units);
        newActor.setAddress(existingActor.getAddress());
        newActor.setContact_number(existingActor.getContact_number());
        newActor.setDistrict(existingActor.getDistrict());
        newActor.setFirm_name(existingActor.getFirm_name());
        newActor.setLicense_number(existingActor.getLicense_number());
        newActor.setPoc_name(existingActor.getPoc_name());
        newActor.setProcess(process);
        newActor.setState(existingActor.getState());
        newActor.setLevel(existingActor.getLevel());
        newActor.setAsset(asset);

        // Save the new actor
        return actorRepository.save(newActor);
    }

    public List<Map<String, Object>> getCompleteActorDetails(Integer actor_id) {
        List<Map<String, Object>> final_result = new ArrayList<Map<String, Object>>();

        List<Map<String, Object>> coreDetails = actorRepository.findCoreDetailsByActorId(actor_id);
        if (coreDetails == null) {
            throw new RuntimeException("Actor not found with actor_id: " + actor_id);
        }
        final_result.add(coreDetails.get(0));
        List<Map<String, Object>> projectDetails = actorRepository.findProjectsByActorId(actor_id);
        final_result.addAll(projectDetails);
        return final_result;
    }

    public List<Map<String, Object>> getAllActors() {

        List<Object[]> results=  actorRepository.findAllActors();
        return results.stream().map(result -> {
            Map<String, Object> map = new HashMap<>();
            map.put("ActorId",  result[0]);
            List<Map<String, String>> project_ids = actorRepository.getProjectList((Integer) result[0]);
            map.put("ActorName", result[1]);
            map.put("Projects", project_ids);
            return map;
        }).collect(Collectors.toList());
    }

}
