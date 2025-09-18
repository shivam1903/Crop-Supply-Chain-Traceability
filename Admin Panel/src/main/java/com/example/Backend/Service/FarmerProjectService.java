package com.example.Backend.Service;

import com.example.Backend.Model.FarmerProjectMaster;
import com.example.Backend.Repository.FarmerMasterRepository;
import com.example.Backend.Repository.FarmerProjectMasterRepository;
import com.example.Backend.Repository.ProjectRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class FarmerProjectService {

    @Autowired
    private FarmerMasterRepository farmerMasterRepository;

    @Autowired
    private FarmerProjectMasterRepository farmerProjectMasterRepository;

    @Autowired
    private ProjectRepository projectRepository;


    @Transactional
    public String deductQuota(String farmerId, String projectId, BigDecimal quantity) {
        FarmerProjectMaster record = farmerProjectMasterRepository.findByFarmerIdAndProjectId(farmerId, projectId);
        if (record == null) {
            return "No record found for given farmer and project ID";
        }

        BigDecimal currentQuota = record.getRemaining_quota();
        if (currentQuota == null) currentQuota = BigDecimal.ZERO;

        if (quantity.compareTo(currentQuota) > 0) {
            return "Insufficient quota. Available: " + currentQuota;
        }

        record.setRemaining_quota(currentQuota.subtract(quantity));
        farmerProjectMasterRepository.save(record);

        return "Quota updated successfully. Remaining: " + record.getRemaining_quota();
    }

    public void saveFarmerProjectAssignment(String farmerId, String projectId) {
        String uniqueCode = projectId + farmerId;

        Float yield = projectRepository.getYieldByProjectId(projectId);
        if (yield == null) throw new RuntimeException("Yield not found for project");

        Integer enrolledAcre = farmerMasterRepository.getEnrolledAcreByFarmerId(farmerId);
        if (enrolledAcre == null) throw new RuntimeException("Enrolled acre not found for farmer");

        BigDecimal maxQuota = BigDecimal.valueOf(yield).multiply(BigDecimal.valueOf(enrolledAcre));

        FarmerProjectMaster entity = new FarmerProjectMaster();
        entity.setUnique_code(uniqueCode);
        entity.setFarmer_id(farmerId);
        entity.setProject_id(projectId);
        entity.setYield(yield);
        entity.setMax_quota(maxQuota);
        entity.setRemaining_quota(maxQuota);

        farmerProjectMasterRepository.save(entity);
    }


    @Transactional
    public boolean deleteAssignment(String projectId, String farmer_id) {
        int deletedCount = farmerProjectMasterRepository.deleteFarmerAssignment(projectId, farmer_id);
        return deletedCount > 0;
    }


}