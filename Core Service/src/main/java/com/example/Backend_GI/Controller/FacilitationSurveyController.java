package com.example.Backend_GI.Controller;


import com.example.Backend_GI.model.FacilitationSurvey;
import com.example.Backend_GI.service.CacheService;
import com.example.Backend_GI.service.FacilitationSurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/facilitation")
public class FacilitationSurveyController {

    @Autowired
    private FacilitationSurveyService facilitationSurveyService;

    @Autowired
    private CacheService cacheService;

    @PostMapping("/save")
    public ResponseEntity<String> saveSurvey(@RequestBody FacilitationSurvey facilitationSurvey) {
        cacheService.clearCache();

        Integer facilitationId = facilitationSurveyService.saveSurvey(facilitationSurvey);
        return ResponseEntity.ok("Transaction saved successfully with ID: " + facilitationId);
    }

    @GetMapping("/{facilitationId}")
    public ResponseEntity<FacilitationSurvey> getSurveyDetails(@PathVariable int facilitationId) {
        Optional<FacilitationSurvey> surveyDetails = facilitationSurveyService.getSurveyDetails(facilitationId);
        return surveyDetails.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

}
