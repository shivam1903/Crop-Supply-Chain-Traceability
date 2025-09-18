package com.example.Backend_GI.service;

import com.example.Backend_GI.model.FarmerMaster;
import com.example.Backend_GI.model.FarmerProjectMaster;
import com.example.Backend_GI.repository.FarmerMasterRepository;
import com.example.Backend_GI.repository.FarmerProjectMasterRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FarmerProjectService {

    @Autowired
    private FarmerMasterRepository farmerMasterRepository;

    @Autowired
    private FarmerProjectMasterRepository farmerProjectMasterRepository;


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


}
