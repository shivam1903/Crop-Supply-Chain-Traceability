package com.example.GI.Backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "project_level_master")
public class ProjectLevelMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "project_id")
    private String project_id;

    @Column(name = "level_number")
    private Integer level_number;

    @Column(name = "level_name")
    private String level_name;

    @Column(name = "asset")
    private String asset;

    @Column(name = "process_type")
    private String process_type;

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

    public Integer getLevel_number() {
        return level_number;
    }

    public void setLevel_number(Integer level_number) {
        this.level_number = level_number;
    }

    public String getLevel_name() {
        return level_name;
    }

    public void setLevel_name(String level_name) {
        this.level_name = level_name;
    }

    public String getAsset() {
        return asset;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }

    public String getProcess_type() {
        return process_type;
    }

    public void setProcess_type(String process_type) {
        this.process_type = process_type;
    }
}