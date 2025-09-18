package com.example.Backend_GI.repository;

import com.example.Backend_GI.model.FacilitationSurvey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacilitationSurveyRepository extends JpaRepository<FacilitationSurvey, Integer> {

}
