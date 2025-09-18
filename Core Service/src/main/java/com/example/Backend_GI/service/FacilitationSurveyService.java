package com.example.Backend_GI.service;


import com.example.Backend_GI.model.FacilitationSurvey;
import com.example.Backend_GI.repository.FacilitationSurveyRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class FacilitationSurveyService {

    @Autowired
    private FacilitationSurveyRepository facilitationSurveyRepository;

    @Transactional
    public Integer saveSurvey(FacilitationSurvey facilitationSurvey) {
        System.out.println("Enmter");
        facilitationSurvey.setTransaction_date(LocalDate.now());
        FacilitationSurvey savedSurvey = facilitationSurveyRepository.save(facilitationSurvey);
        System.out.println(savedSurvey.getFacilitation_id());
        return savedSurvey.getFacilitation_id();
    }

    public Optional<FacilitationSurvey> getSurveyDetails(int facilitationId) {
        return facilitationSurveyRepository.findById(facilitationId);
    }
}
