package com.example.Backend.Service;


import com.example.Backend.Model.ProjectLevelMaster;
import com.example.Backend.Model.ProjectMaster;
import com.example.Backend.Repository.ProjectLevelRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProjectLevelService {

    @Autowired
    private ProjectLevelRepository projectLevelRepository;

    @Transactional
    public Integer saveProjectLevel(ProjectLevelMaster projectLevelMaster) {

        ProjectLevelMaster savedProjectLevelMaster = projectLevelRepository.save(projectLevelMaster);

        return savedProjectLevelMaster.getId();
    }

    public List<Map<String, Object>> getLevelList(String project_id) {
        List<Object[]> results = projectLevelRepository.findLevelDetails(project_id);
        return results.stream().map(result -> {
            Map<String, Object> map = new HashMap<>();
            map.put("project_id",  result[0]); // Safely cast to Integer
            map.put("level_name", "Level " + result[1] + " : " + result[2]);
            map.put("asset" , result[3]);
            map.put("process_type", result[4]);
            return map;
        }).collect(Collectors.toList());
    }

    @Transactional
    public void deleteByProjectId(String projectId) {
        projectLevelRepository.deleteByProjectId(projectId);
    }

    @Transactional
    public void deleteLevel(String project_id, Integer level_number) {
        projectLevelRepository.deleteLevel(project_id, level_number);
        projectLevelRepository.shiftLevelsAfterDeletion(project_id, level_number);
    }

    public void addLevel(ProjectLevelMaster newLevel) {
        projectLevelRepository.save(newLevel);
    }

    public void updateLevel(String project_id, Integer level_number, Map<String, Object> updates) {
        Optional<ProjectLevelMaster> optional = projectLevelRepository.findByProjectIdAndLevelNumber(project_id, level_number);

        if (optional.isEmpty()) {
            throw new RuntimeException("Level not found for given project_id and level_number.");
        }

        ProjectLevelMaster level = optional.get();

        if (updates.containsKey("level_name")) {
            level.setLevel_name((String) updates.get("level_name"));
        }
        if (updates.containsKey("asset")) {
            level.setAsset((String) updates.get("asset"));
        }
        if (updates.containsKey("process_type")) {
            level.setProcess_type((String) updates.get("process_type"));
        }

        projectLevelRepository.save(level);
    }


}
