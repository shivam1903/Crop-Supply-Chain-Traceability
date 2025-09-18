package com.example.GI.Backend.model;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Map;

@Entity
@Table(name = "product_view_master")
public class ProductViewMaster {

    @Id
    @Column(name="project_id")
    private String project_id;

    @Column(name = "json_file", columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> jsonFile;

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public Map<String, Object> getJsonFile() {
        return jsonFile;
    }

    public void setJsonFile(Map<String, Object> jsonFile) {
        this.jsonFile = jsonFile;
    }

}
