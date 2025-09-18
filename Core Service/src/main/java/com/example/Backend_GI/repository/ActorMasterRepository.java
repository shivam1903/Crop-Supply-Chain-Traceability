package com.example.Backend_GI.repository;

import com.example.Backend_GI.model.ActorMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface ActorMasterRepository extends JpaRepository<ActorMaster, String> {
    // No additional methods required, JpaRepository handles CRUD operations

    @Query(value = "SELECT * FROM actor_master a WHERE a.actor_id = :actorId and a.project_id = :project_id", nativeQuery = true)
    List<ActorMaster> findByActorId(@Param("actorId") Integer actorId,
                                    @Param("project_id") String project_id);

    @Query(value = "SELECT actor_id, firm_name FROM actor_master a WHERE a.project_id = :project_id AND level = :level", nativeQuery = true)
    List<Object[]> findActorsByProjectAndLevel(@Param("project_id") String project_id, @Param("level") Integer level);

    @Query(value = "SELECT level FROM actor_master WHERE contact_number = :number", nativeQuery = true)
    List<Integer> findLevelbyNumber(@Param("number") String number);

    @Query(value = """
            SELECT 
                a.actor_id,
                crop,
                level,
                process,
                firm_name,
                contact_number,
                poc_name,
                address,
                state,
                district,
                license_number,
                asset
            FROM 
                actor_master a 
            WHERE 
                contact_number = :number """, nativeQuery = true)
    List<Object[]> findDetailsbyNumber(@Param("number") String number);

    @Query(value = "select project_id from actor_master a where a.actor_id = :actor_id", nativeQuery = true)
    List<String> findProjectIds(@Param("actor_id") Integer actor_id);

    @Query(value = "select measuring_unit from actor_master a where a.actor_id = :actor_id and a.project_id = :project_id", nativeQuery = true)
    String findUnitsforActor(@Param("actor_id") Integer actor_id,
                             @Param("project_id") String project_id);


    @Query(value = """
    SELECT a.actor_id, a.poc_name, a.contact_number,
    a.address, a.crop, a.state, a.district,
    a.license_number, a.firm_name, a.level FROM actor_master a WHERE a.contact_number = :contact_number
    """, nativeQuery = true)
    List<Map<String, Object>> findCoreDetailsByActorId(@Param("contact_number") String contact_number);

}

