package com.example.Backend.Repository;

import com.example.Backend.Model.QCUserMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface QCUserRepository extends JpaRepository<QCUserMaster, String> {

    @Query(value = """
SELECT first_name,
last_name,
username,
role,
team_id,
email_id,
contact
FROM qc_user_master
ORDER BY team_id""", nativeQuery = true)
    List<Object[]> findAllQC();

    @Query(value = """
            SELECT 
            first_name,
            last_name,
            email_id,
            username,
            contact,
            role,
            team_id
            FROM qc_user_master 
            WHERE username = :username""", nativeQuery = true)
    List<Map<String, Object>> findQCDetails(@Param("username") String username);
}
