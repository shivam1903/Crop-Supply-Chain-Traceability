package com.example.GI.Backend.Repository;


import com.example.GI.Backend.model.ActorMaster;
import com.example.GI.Backend.model.ActorMasterId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface ActorMasterRepository extends JpaRepository<ActorMaster, ActorMasterId> {
    // No additional methods required, JpaRepository handles CRUD operations

    @Query(value = "SELECT firm_name FROM actor_master WHERE actor_id = :actorId and project_id = :projectId", nativeQuery = true)
    String findFirmName(@Param("actorId") String actorId,
                        @Param("projectId") String projectId);

//    @Query(value = "SELECT actor_id, firm_name, level, crop FROM actor_master WHERE actor_id = :actorId" , nativeQuery = true)
//    List<Object[]> findActorDetails(@Param("actorId") String actorId);

    @Query(value = "SELECT firm_name AS firm_name, level AS level, crop AS crop FROM actor_master WHERE actor_id = :actorId and project_id = :projectId", nativeQuery = true)
    Map<String, Object> findActorDetails(@Param("actorId") String actorId,
                                         @Param("projectId") String projectId);

    @Query(value = "SELECT actor_id, firm_name, contact_number, address, process from actor_master WHERE project_id = :project_id ORDER BY actor_id", nativeQuery = true)
    List<Map<String, Object>> findActorDetailsforNode(@Param("project_id") String project_id);

    @Query(value = """
        SELECT 
            actor_id,
            firm_name,
            contact_number,
            license_number,
            address,
            process
        FROM actor_master 
        WHERE project_id = :projectId AND actor_id = :actorId
    """, nativeQuery = true)
    Map<String, Object> findActorDetailsNative(@Param("projectId") String projectId, @Param("actorId") Integer actorId);

}

