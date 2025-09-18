package com.example.Backend_GI.repository;

import com.example.Backend_GI.model.Password;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PasswordRepository extends JpaRepository<Password, String> {

    @Query(value = "SELECT hash, level FROM password_db WHERE phone_no = :number", nativeQuery = true)
    List<Object[]> findByNumber(@Param("number") String number);

}
