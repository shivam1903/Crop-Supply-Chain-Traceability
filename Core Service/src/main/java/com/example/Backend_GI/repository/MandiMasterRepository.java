package com.example.Backend_GI.repository;

import com.example.Backend_GI.model.MandiMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MandiMasterRepository extends JpaRepository<MandiMaster, String> {

    @Query(value = "SELECT mandi_name, id FROM mandi_master WHERE district = :district", nativeQuery = true)
    List<Object[]> findMandibyDistrict(@Param("district") String district);

    @Modifying
    @Query(value = "SELECT setval('mandi_master_id_seq_a', (SELECT MAX(id) FROM mandi_master))", nativeQuery = true)
    Integer resetSequence();

    @Query(value = "SELECT MAX(id) from mandi_master", nativeQuery = true)
    Integer findMaxId();
}
