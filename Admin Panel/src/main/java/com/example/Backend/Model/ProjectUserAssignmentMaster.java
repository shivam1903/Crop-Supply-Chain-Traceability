package com.example.Backend.Model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "project_user_assignment_master")
public class ProjectUserAssignmentMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "project_id")
    private String project_id;

    @Column(name = "user_email")
    private String user_email;

    @Column(name = "assigned_date")
    private LocalDate assigned_date;

    @Column(name = "role")
    private String role;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public LocalDate getAssigned_date() {
        return assigned_date;
    }

    public void setAssigned_date(LocalDate assigned_date) {
        this.assigned_date = assigned_date;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
