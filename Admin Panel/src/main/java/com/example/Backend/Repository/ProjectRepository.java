package com.example.Backend.Repository;

import com.example.Backend.Model.ProjectMaster;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectMaster, Integer> {

    @Query(value = """
SELECT project_id,
project_description,
crop,
yield,
no_of_level,
project_start_date,
project_end_date,
premium
FROM project_master WHERE project_id = :project_id""", nativeQuery = true)
    Map<String, Object> findProjectbyProjectID(@Param("project_id") String project_id);

    @Query(value = """
SELECT project_id,
project_description
FROM project_master""", nativeQuery = true)
    List<Object[]> findAllProjects();

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM project_master p WHERE p.project_id = :projectId", nativeQuery = true)
    void deleteByProjectId(@Param("projectId") String projectId);

    @Query(value = "SELECT yield FROM project_master WHERE project_id = :projectId", nativeQuery = true)
    Float getYieldByProjectId(@Param("projectId") String projectId);

    @Query(value = "SELECT COUNT(*) FROM project_master WHERE project_id = :projectId", nativeQuery = true)
    long countByProjectId(@Param("projectId") String projectId);

    @Query(value = "SELECT premium FROM project_master WHERE project_id = :projectId", nativeQuery = true)
    BigDecimal findPremiumByProjectId(@Param("projectId") String projectId);

    @Query(value = "SELECT nodes FROM project_master WHERE project_id = :projectId", nativeQuery = true)
    Integer findNodesByProjectId(@Param("projectId") String projectId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE project_master SET nodes = :nodes WHERE project_id = :projectId", nativeQuery = true)
    int updateNodes(@Param("projectId") String projectId, @Param("nodes") Integer nodes);

    @Query(value = "SELECT * FROM project_master WHERE project_id = :projectId", nativeQuery = true)
    ProjectMaster findByProjectId(@Param("projectId") String projectId);
}
