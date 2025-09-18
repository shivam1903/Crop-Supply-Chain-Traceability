package com.example.GI.Backend.Repository;

import com.example.GI.Backend.model.ActorMaster;
import com.example.GI.Backend.model.FarmerMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface FarmerMasterRepository extends JpaRepository<FarmerMaster, String> {

    @Query(value = "SELECT farmer_id, farmer_name, mobile, state, district, taluka, village, enrolled_acre FROM farmer_master a WHERE a.farmer_id = :farmer_id", nativeQuery = true)
    List<Object[]> findByFarmerId(@Param("farmer_id") String farmer_id);

//    @Query(value = "SELECT farmer_id, farmer_name, mobile, village, taluka, district, state from farmer_master where project_id = :project_id", nativeQuery = true)
//    List<Object[]> findFarmerDetailsforNode(@Param("project_id") String project_id);

    @Query(value = """
            SELECT farmer_id, farmer_name, mobile, village, taluka, district, state
            FROM farmer_master
            WHERE farmer_id IN (:farmerIds)
            """, nativeQuery = true)
    List<Object[]> findFarmerDetailsforNode(@Param("farmerIds") List<String> farmerIds);
}