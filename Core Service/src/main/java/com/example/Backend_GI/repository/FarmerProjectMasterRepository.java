package com.example.Backend_GI.repository;

import com.example.Backend_GI.model.FarmerProjectMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FarmerProjectMasterRepository extends JpaRepository<FarmerProjectMaster, String>{

    @Query(value = "SELECT farmer_id, max_quota, remaining_quota FROM farmer_project_master WHERE unique_code = :unique_code", nativeQuery = true)
    List<String> findDetailsbyUniqueCode(@Param("unique_code") String unique_code);

    @Query(value = "SELECT * FROM farmer_project_master WHERE farmer_id = :farmerId AND project_id = :projectId", nativeQuery = true)
    FarmerProjectMaster findByFarmerIdAndProjectId(@Param("farmerId") String farmerId, @Param("projectId") String projectId);
}
