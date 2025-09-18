package com.example.Backend.Repository;

import com.example.Backend.Model.AdminPanelUserMaster;
import com.example.Backend.Model.ProjectUserAssignmentMaster;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ProjectUserAssignmentRepository extends JpaRepository<ProjectUserAssignmentMaster, Integer> {

    @Query(value = "SELECT project_id, user_email, role, assigned_date FROM project_user_assignment_master WHERE project_id = :project_id", nativeQuery = true)
    List<Map<String, Object>> findUsers(@Param("project_id") String project_id);

    @Query(value = """
        SELECT puam.project_id, pm.project_description
        FROM project_user_assignment_master puam
        JOIN project_master pm ON puam.project_id = pm.project_id
        WHERE user_email = :user_email
        """, nativeQuery = true)
    List<Map<String, Object>> findProjects(@Param("user_email") String user_email);

    @Query(value = """
            SELECT *
            FROM project_user_assignment_master
            WHERE project_id = :project_id
              AND user_email = :user_email
            LIMIT 1
            """, nativeQuery = true)
    ProjectUserAssignmentMaster findByProjectIdAndUserEmailNative(
            @Param("project_id") String project_id,
            @Param("user_email") String user_email
    );

    @Modifying
    @Transactional
    @Query(value = """
            DELETE FROM project_user_assignment_master
            WHERE project_id = :project_id
              AND user_email = :user_email
            """, nativeQuery = true)
    int deleteByProjectIdAndUserEmail(@Param("project_id") String projectId, @Param("user_email") String userEmail);
}
