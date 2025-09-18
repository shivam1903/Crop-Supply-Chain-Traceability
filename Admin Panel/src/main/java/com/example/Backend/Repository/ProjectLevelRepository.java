package com.example.Backend.Repository;

import com.example.Backend.Model.ProjectLevelMaster;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectLevelRepository extends JpaRepository<ProjectLevelMaster, Integer> {

    @Query(value = """
SELECT project_id,
level_number,
level_name,
asset,
process_type
FROM project_level_master WHERE project_id = :project_id""", nativeQuery = true)
    List<Object[]> findLevelDetails(@Param("project_id") String project_id);


    @Modifying
    @Transactional
    @Query(value = "DELETE FROM project_level_master p WHERE p.project_id = :projectId", nativeQuery = true)
    void deleteByProjectId(@Param("projectId") String projectId);

    @Modifying
    @Query(value = "DELETE FROM project_level_master WHERE project_id = :project_id AND level_number = :level_number", nativeQuery = true)
    void deleteLevel(@Param("project_id") String project_id, @Param("level_number") Integer level_number);

    @Modifying
    @Query(value = """
        UPDATE project_level_master
        SET level_number = level_number - 1
        WHERE project_id = :project_id AND level_number > :deleted_level
    """, nativeQuery = true)
    void shiftLevelsAfterDeletion(@Param("project_id") String project_id, @Param("deleted_level") Integer deleted_level);

    @Query(value = """
    SELECT * FROM project_level_master
    WHERE project_id = :project_id AND level_number = :level_number
    """, nativeQuery = true)
    Optional<ProjectLevelMaster> findByProjectIdAndLevelNumber(
            @Param("project_id") String project_id,
            @Param("level_number") Integer level_number);

}
