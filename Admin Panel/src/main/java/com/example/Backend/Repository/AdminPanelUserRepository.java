package com.example.Backend.Repository;

import com.example.Backend.Model.AdminPanelUserMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface AdminPanelUserRepository extends JpaRepository<AdminPanelUserMaster, String> {

    @Query(value = "Select * from admin_panel_users where email_id = :email_id", nativeQuery = true)
    List<Map<String, Object>> findUserDetails(@Param("email_id") String email_id);

    @Query(value = "Select email_id, role from admin_panel_users where email_id = :email_id", nativeQuery = true)
    List<Map<String, Object>> findLoginRole(@Param("email_id") String email_id);

    @Query(value = "SELECT email_id, first_name, last_name, role, status, contact FROM admin_panel_users", nativeQuery = true)
    List<Object[]> findAllUsers();

    @Query(value = "SELECT email_id, first_name, last_name, role, status, contact FROM admin_panel_users where role = :role", nativeQuery = true)
    List<Object[]> findAllClients(@Param("role") String role);

    @Query(value = "SELECT email_id, first_name, last_name, role, status, contact FROM admin_panel_users where role = :role", nativeQuery = true)
    List<Object[]> findAllAuditors(@Param("role") String role);
}
