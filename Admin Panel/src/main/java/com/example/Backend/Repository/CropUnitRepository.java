package com.example.Backend.Repository;

import com.example.Backend.Model.CropUnitMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CropUnitRepository extends JpaRepository<CropUnitMaster, Integer> {

    @Query(value = "SELECT DISTINCT crop from crop_unit_master", nativeQuery = true)
    List<String> findUniqueCrops();

    @Query(value = "SELECT unit from crop_unit_master where crop = :crop", nativeQuery = true)
    List<String> findUnitsforCrop(@Param("crop") String crop);

}
