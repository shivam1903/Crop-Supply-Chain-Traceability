package com.example.Backend.Repository;

import com.example.Backend.Model.FarmerMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface FarmerMasterRepository extends JpaRepository<FarmerMaster, String> {

    @Query(value = "SELECT farmer_id, farmer_name, mobile, state, district, taluka, village, enrolled_acre FROM farmer_master a WHERE a.farmer_id = :farmer_id", nativeQuery = true)
    List<Object[]> findByFarmerId(@Param("farmer_id") String farmer_id);

    @Query(value = """
        SELECT
            fpm.project_id, 
            fpm.farmer_id, 
            fm.farmer_name,
            fm.state,
            fm.district,
            fm.taluka,
            fm.village
        FROM farmer_project_master fpm
        JOIN farmer_master fm ON fpm.farmer_id = fm.farmer_id
        WHERE fpm.project_id = :projectId
    """, nativeQuery = true)
    List<Map<String, Object>> findFarmersByProjectId(@Param("projectId") String projectId);

    @Query(value = """
        SELECT DISTINCT
            farmer_id, 
            farmer_name,
            state,
            district,
            taluka,
            village 
        FROM farmer_master
    """, nativeQuery = true)
    List<Map<String, Object>> findAllUniqueFarmers();

    @Query(value = "SELECT enrolled_acre FROM farmer_master WHERE farmer_id = :farmerId", nativeQuery = true)
    Integer getEnrolledAcreByFarmerId(@Param("farmerId") String farmerId);

    @Query(value = "SELECT farmer_name FROM farmer_master WHERE farmer_id = :farmerId", nativeQuery = true)
    String findFarmerNameByFarmerId(@Param("farmerId") String farmerId);

    @Query(value = "SELECT * FROM farmer_master WHERE farmer_id = :farmerId", nativeQuery = true)
    FarmerMaster findByFarmerIdUnit(@Param("farmerId") String farmerId);

}