package com.example.Backend.Service;

import com.example.Backend.Model.FarmerMaster;
import com.example.Backend.Model.FarmerProjectMaster;
import com.example.Backend.Model.ProjectMaster;
import com.example.Backend.Repository.FarmerMasterRepository;
import com.example.Backend.Repository.FarmerProjectMasterRepository;
import com.example.Backend.Repository.ProjectRepository;
import com.example.Backend.Repository.TransactionAuditRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private FarmerProjectMasterRepository farmerProjectMasterRepository;

    @Autowired
    private FarmerMasterRepository farmerMasterRepository;

    @Autowired
    private TransactionAuditRepository transactionAuditRepository;

    @Transactional
    public String saveProject(ProjectMaster projectMaster) {

        long count = projectRepository.countByProjectId(projectMaster.getProject_id());

        if (count > 0) {
            // Throw an exception or return an error message if duplicate exists
            throw new IllegalArgumentException("Duplicate project_id exists: " + projectMaster.getProject_id());
        }

        // Save the project if no duplicate found
        ProjectMaster savedProject = projectRepository.save(projectMaster);

        return savedProject.getProject_id();
    }

    public Map<String, Object> getProjectbyProjectID(String project_id) {
        return projectRepository.findProjectbyProjectID(project_id);

    }

    public List<Map<String, Object>> getAllProjects() {
        List<Object[]> results=  projectRepository.findAllProjects();
        return results.stream().map(result -> {
            Map<String, Object> map = new HashMap<>();
            map.put("project_id",  result[0]);
            map.put("project_description", result[1]);
            return map;
        }).collect(Collectors.toList());
    }

    @Transactional
    public void deleteByProjectId(String projectId) {
        projectRepository.deleteByProjectId(projectId);
    }

    public Integer getNodesbyProject(String project_id){
        Integer nodes = projectRepository.findNodesByProjectId(project_id);
        return nodes;
    }

    public List<Map<String, Object>> getFarmerPremiums(String projectId) {
        // Step 1: Fetch all unique farmer_ids from farmer_project_master
        List<String> farmerIds = farmerProjectMasterRepository.findDistinctFarmerIdsByProjectId(projectId);

        // Step 2: Fetch premium from project_master
        BigDecimal premium = projectRepository.findPremiumByProjectId(projectId);
        if (premium == null) premium = BigDecimal.ZERO;

        // Step 3: For each farmer_id, calculate total_amount, total_quantity, and premium_amount
        List<Map<String, Object>> result = new ArrayList<>();

        for (String farmerId : farmerIds) {
            Map<String, Object> record = new HashMap<>();

            BigDecimal totalAmount = transactionAuditRepository.sumTotalAmountForFarmerAndProject(farmerId, projectId);
            BigDecimal totalQuantity = transactionAuditRepository.sumTotalQuantityForFarmerAndProject(farmerId, projectId);

            if (totalAmount == null) totalAmount = BigDecimal.ZERO;
            if (totalQuantity == null) totalQuantity = BigDecimal.ZERO;

            BigDecimal premiumAmount = totalAmount.multiply(premium).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

            String farmerName = farmerMasterRepository.findFarmerNameByFarmerId(farmerId);

            record.put("farmer_id", farmerId);
            record.put("farmer_name", farmerName);
            record.put("total_amount", totalAmount);
            record.put("total_quantity", totalQuantity);
            record.put("premium_amount", premiumAmount);

            result.add(record);
        }

        return result;
    }

    public boolean updateNodesForProject(String projectId, Integer nodes) {
        int rowsUpdated = projectRepository.updateNodes(projectId, nodes);
        return rowsUpdated > 0;
    }

    public String updateProject(Map<String, Object> requestBody) {
        String projectId = (String) requestBody.get("project_id");

        if (projectId == null || projectId.isBlank()) {
            throw new IllegalArgumentException("project_id is required");
        }

        ProjectMaster project = projectRepository.findByProjectId(projectId);
        if (project == null) throw new RuntimeException("Project not found");

        if (requestBody.containsKey("project_description"))
            project.setProject_description((String) requestBody.get("project_description"));

        if (requestBody.containsKey("crop"))
            project.setCrop((String) requestBody.get("crop"));

        if (requestBody.containsKey("yield")) {
            Float newYield = Float.parseFloat(requestBody.get("yield").toString());
            project.setYield(newYield);

            // Update max_quota in farmer_project_master
            List<FarmerProjectMaster> mappings = farmerProjectMasterRepository.findByProjectId(projectId);
            for (FarmerProjectMaster mapping : mappings) {
                FarmerMaster farmer = farmerMasterRepository.findByFarmerIdUnit(mapping.getFarmer_id());
                if (farmer != null) {
                    BigDecimal maxQuota = BigDecimal.valueOf(newYield).multiply(BigDecimal.valueOf(farmer.getEnrolled_acre()));
                    mapping.setYield(newYield);
                    mapping.setMax_quota(maxQuota);
//                    mapping.setRemaining_quota(maxQuota); // Optional: only if you want to reset
                    farmerProjectMasterRepository.save(mapping);
                }
            }
        }

        if (requestBody.containsKey("no_of_level"))
            project.setNo_of_level((Integer) requestBody.get("no_of_level"));

        if (requestBody.containsKey("project_start_date"))
            project.setStart_date(LocalDate.parse((String) requestBody.get("project_start_date")));

        if (requestBody.containsKey("project_end_date"))
            project.setEnd_date(LocalDate.parse((String) requestBody.get("project_end_date")));

        if (requestBody.containsKey("premium"))
            project.setPremium(new BigDecimal(requestBody.get("premium").toString()));

        if (requestBody.containsKey("nodes"))
            project.setNodes((Integer) requestBody.get("nodes"));

        projectRepository.save(project);
        return "Project updated successfully";
    }

}
