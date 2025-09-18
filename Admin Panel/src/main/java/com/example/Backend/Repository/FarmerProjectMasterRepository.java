package com.example.Backend.Repository;

import com.example.Backend.Model.FarmerProjectMaster;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface FarmerProjectMasterRepository extends JpaRepository<FarmerProjectMaster, String> {

    @Query(value = "SELECT farmer_id, max_quota, remaining_quota FROM farmer_project_master WHERE unique_code = :unique_code", nativeQuery = true)
    List<String> findDetailsbyUniqueCode(@Param("unique_code") String unique_code);

    @Query(value = "SELECT * FROM farmer_project_master WHERE farmer_id = :farmerId AND project_id = :projectId", nativeQuery = true)
    FarmerProjectMaster findByFarmerIdAndProjectId(@Param("farmerId") String farmerId, @Param("projectId") String projectId);

    @Query(value = "SELECT DISTINCT farmer_id FROM farmer_project_master WHERE project_id = :projectId", nativeQuery = true)
    List<String> findDistinctFarmerIdsByProjectId(@Param("projectId") String projectId);

    @Modifying
    @Transactional
    @Query(value = """
            DELETE FROM farmer_project_master
            WHERE project_id = :project_id
              AND farmer_id = :farmer_id
            """, nativeQuery = true)
    int deleteFarmerAssignment(@Param("project_id") String projectId, @Param("farmer_id") String farmer_id);

    @Query(value = "SELECT * FROM farmer_project_master WHERE project_id = :projectId", nativeQuery = true)
    List<FarmerProjectMaster> findByProjectId(@Param("projectId") String projectId);

}