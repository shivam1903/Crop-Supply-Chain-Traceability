package com.example.GI.Backend.Repository;

import com.example.GI.Backend.model.FarmerProjectMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FarmerProjectMasterRepository extends JpaRepository<FarmerProjectMaster, String>{

    @Query(value = "SELECT farmer_id FROM farmer_project_master WHERE project_id = :project_id", nativeQuery = true)
    List<String> findFarmerIDsbyProject(@Param("project_id") String project_id);

    @Query(value = "SELECT DISTINCT farmer_id FROM farmer_project_master WHERE project_id = :projectId", nativeQuery = true)
    List<String> findFarmerIdsByProjectId(@Param("projectId") String projectId);
}