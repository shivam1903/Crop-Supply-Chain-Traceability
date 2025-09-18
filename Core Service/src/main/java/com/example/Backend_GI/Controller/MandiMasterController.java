package com.example.Backend_GI.Controller;

import com.example.Backend_GI.model.MandiMaster;
import com.example.Backend_GI.service.MandiMasterService;
import com.example.Backend_GI.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/mandi")
public class MandiMasterController {


    @Autowired
    private MandiMasterService mandiMasterService;

    @GetMapping("/{district}")
    public ResponseEntity<List<Map<String, Object>>> getMandibyDistrict(@PathVariable String district) {
        List<Map<String, Object>> mandis = mandiMasterService.getMandibyDistrict(district);
        return ResponseEntity.ok(mandis);
    }

    @PostMapping("/save")
    public ResponseEntity<Integer> saveNewMandi(@RequestBody MandiMaster mandiMaster){
        Integer mandiId = mandiMasterService.saveMandi(mandiMaster);
        return ResponseEntity.ok(mandiId);
    }
}
