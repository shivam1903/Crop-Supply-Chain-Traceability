package com.example.Backend_GI.repository;

import com.example.Backend_GI.model.ActorMaster;
import com.example.Backend_GI.model.FarmerMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FarmerMasterRepository extends JpaRepository<FarmerMaster, String> {

    @Query(value = "SELECT farmer_id, farmer_name, mobile, state, district, taluka, village, enrolled_acre FROM farmer_master a WHERE a.farmer_id = :farmer_id", nativeQuery = true)
    List<Object[]> findByFarmerId(@Param("farmer_id") String farmer_id);

}