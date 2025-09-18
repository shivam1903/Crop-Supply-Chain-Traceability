package com.example.Backend.Repository;

import com.example.Backend.Model.QCUserMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<QCUserMaster, String> {

    @Query(value = "SELECT email_id, first_name, last_name FROM qc_user_master WHERE team_id = :team_id AND role = :role", nativeQuery = true)
    List<Object[]> findMembersbyTeam(@Param("team_id") String team_id, @Param("role") String role);
}