package com.example.Backend.Repository;

import com.example.Backend.Model.ActorMaster;
import com.example.Backend.Model.ActorMasterId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ActorRepository extends JpaRepository<ActorMaster, String> {

    @Query(value = "SELECT COALESCE(MAX(a.actor_id), 9999) FROM actor_master a", nativeQuery = true)
    Integer findMaxActorId();

    @Query(value = "SELECT * FROM actor_master a WHERE a.actor_id = :actor_id", nativeQuery = true)
    List<ActorMaster> findByActorId(@Param("actor_id") Integer actor_id);

    @Query(value = """
    SELECT a.actor_id, a.poc_name, a.contact_number,
    a.address, a.crop, a.state, a.district,
    a.license_number, a.firm_name, a.level FROM actor_master a WHERE a.actor_id = :actor_id
    """, nativeQuery = true)
    List<Map<String, Object>> findCoreDetailsByActorId(@Param("actor_id") Integer actor_id);

    @Query(value = """
            SELECT a.project_id,
            a.asset,
            a.process,
            a.measuring_unit
            FROM actor_master a
            WHERE a.actor_id = :actor_id""", nativeQuery = true)
    List<Map<String, Object>> findProjectsByActorId(@Param("actor_id") Integer actor_id);

    @Query(value = """
SELECT DISTINCT(actor_id),
poc_name
FROM actor_master ORDER BY actor_id DESC""", nativeQuery = true)
    List<Object[]> findAllActors();

    @Query(value = """
            SELECT
            project_id
            from actor_master
            where actor_id = :actor_id""", nativeQuery = true)
    List<Map<String, String>> getProjectList(@Param("actor_id") Integer actor_id);

    @Query(value = "SELECT mandi_name from actor_master where actor_id = :actor_id and project_id = :project_id", nativeQuery = true)
    String getMandiName(@Param("actor_id") Integer actor_id, @Param("project_id") String project_id);

    @Query(value = "SELECT firm_name from actor_master where actor_id = :actor_id and project_id = :project_id", nativeQuery = true)
    String getFirmName(@Param("actor_id") Integer actor_id, @Param("project_id") String project_id);
}
