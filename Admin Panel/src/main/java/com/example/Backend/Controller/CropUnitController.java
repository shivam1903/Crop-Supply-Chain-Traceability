package com.example.Backend.Controller;

import com.example.Backend.Service.CropUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cropunitmaster")
public class CropUnitController {

    @Autowired
    private CropUnitService cropUnitService;

    @GetMapping("/getUniqueCrops")
    public ResponseEntity<List<String>> getUniqueCrops(){
        try {
            System.out.println("Received request to get unique crops ");

            List<String> uniqueCrops = cropUnitService.getUniqueCrops();

            return ResponseEntity.ok(uniqueCrops);
        } catch (Exception e) {
            List<String> error_list = new ArrayList<String>();
            error_list.add("Data not found");
            return ResponseEntity.status(500).body(error_list);
        }
    }

    @GetMapping("/getUnitsforCrop")
    public ResponseEntity<List<String>> getUnitsforCrop(@RequestParam String crop){
        try {
            System.out.println("Received request to get units for this crop: " + crop);

            List<String> uniqueCrops = cropUnitService.getUnitsforCrop(crop);

            return ResponseEntity.ok(uniqueCrops);
        } catch (Exception e) {
            List<String> error_list = new ArrayList<String>();
            error_list.add("Data not found");
            return ResponseEntity.status(500).body(error_list);
        }
    }
}
