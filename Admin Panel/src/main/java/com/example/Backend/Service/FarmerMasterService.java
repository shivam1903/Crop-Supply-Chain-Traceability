package com.example.Backend.Service;

import com.example.Backend.Model.FarmerMaster;
import com.example.Backend.Repository.FarmerMasterRepository;
import com.example.Backend.Repository.FarmerProjectMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FarmerMasterService {

    @Autowired
    private FarmerMasterRepository farmerMasterRepository;

    @Autowired
    private FarmerProjectMasterRepository farmerProjectMasterRepository;


    public Optional<FarmerMaster> getFarmerDetails(String farmerId) {
        return farmerMasterRepository.findById(farmerId);
    }

    public List<Map<String, Object>> getDetailsByUnique(String unique_code) {

        List<String> id_max_qty = farmerProjectMasterRepository.findDetailsbyUniqueCode(unique_code);
        String[] arr = id_max_qty.get(0).split(",");
        List<Object[]> results = farmerMasterRepository.findByFarmerId(arr[0]);
        System.out.println(results);
        return results.stream().map(result -> {
            Map<String, Object> map = new HashMap<>();
            map.put("Farmer_id", String.valueOf(result[0]));
            map.put("Farmer_name", (String) result[1]);
            map.put("Mobile", (String) result[2]);
            map.put("State", (String) result[3]);
            map.put("District", (String) result[4]);
            map.put("Taluka", (String) result[5]);
            map.put("Village", (String) result[6]);
            map.put("Total Acre", result[7]);
            map.put("Max Quota", arr[1]);
            map.put("Remaining Quota",arr[2]);
            return map;
        }).collect(Collectors.toList());
    }

    public List<Map<String, Object>> getFarmersByProjectId(String projectId) {
        return farmerMasterRepository.findFarmersByProjectId(projectId);
    }

    public List<Map<String, Object>> getAllUniqueFarmers() {
        return farmerMasterRepository.findAllUniqueFarmers();
    }

}