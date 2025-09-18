package com.example.Backend.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "farmer_project_master")
public class FarmerProjectMaster {

    @Id
    @Column(name="unique_code")
    private String unique_code; // Primary Key

    @Column(name = "farmer_id")
    private String farmer_id;

    @Column(name = "project_id")
    private String project_id;

    @Column(name = "yield")
    private Float yield;

    @Column(name = "max_quota")
    private BigDecimal max_quota;

    @Column(name = "remaining_quota")
    private BigDecimal remaining_quota;

    public String getUnique_code() {
        return unique_code;
    }

    public void setUnique_code(String unique_code) {
        this.unique_code = unique_code;
    }

    public String getFarmer_id() {
        return farmer_id;
    }

    public void setFarmer_id(String farmer_id) {
        this.farmer_id = farmer_id;
    }

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public Float getYield() {
        return yield;
    }

    public void setYield(Float yield) {
        this.yield = yield;
    }

    public BigDecimal getMax_quota() {
        return max_quota;
    }

    public void setMax_quota(BigDecimal max_quota) {
        this.max_quota = max_quota;
    }

    public BigDecimal getRemaining_quota() {
        return remaining_quota;
    }

    public void setRemaining_quota(BigDecimal remaining_quota) {
        this.remaining_quota = remaining_quota;
    }
}
