package com.example.Backend_GI.Controller;

import com.example.Backend_GI.model.FarmerMaster;
import com.example.Backend_GI.service.CacheService;
import com.example.Backend_GI.service.FarmerMasterService;
import com.example.Backend_GI.service.FileStorageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/farmer")
public class FarmerMasterController {

    @Autowired
    private FarmerMasterService farmerMasterService;

    @Autowired
    private CacheService cacheService;


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


}
