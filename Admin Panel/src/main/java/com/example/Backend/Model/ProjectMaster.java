package com.example.Backend.Model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "project_master")
public class ProjectMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "project_id")
    private String project_id;

    @Column(name = "project_description")
    private String project_description;

    @Column(name = "crop")
    private String crop;

    @Column(name = "yield")
    private Float yield;

    @Column(name = "no_of_level")
    private Integer no_of_level;

    @Column(name = "project_start_date")
    private LocalDate start_date;

    @Column(name = "project_end_date")
    private LocalDate end_date;

    @Column(name = "premium")
    private BigDecimal premium;

    @Column(name = "nodes")
    private Integer nodes;

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

    public String getProject_description() {
        return project_description;
    }

    public void setProject_description(String project_description) {
        this.project_description = project_description;
    }

    public String getCrop() {
        return crop;
    }

    public void setCrop(String crop) {
        this.crop = crop;
    }

    public Float getYield() {
        return yield;
    }

    public void setYield(Float yield) {
        this.yield = yield;
    }

    public Integer getNo_of_level() {
        return no_of_level;
    }

    public void setNo_of_level(Integer no_of_level) {
        this.no_of_level = no_of_level;
    }

    public LocalDate getStart_date() {
        return start_date;
    }

    public void setStart_date(LocalDate start_date) {
        this.start_date = start_date;
    }

    public LocalDate getEnd_date() {
        return end_date;
    }

    public void setEnd_date(LocalDate end_date) {
        this.end_date = end_date;
    }

    public BigDecimal getPremium() {
        return premium;
    }

    public void setPremium(BigDecimal premium) {
        this.premium = premium;
    }

    public Integer getNodes() {
        return nodes;
    }

    public void setNodes(Integer nodes) {
        this.nodes = nodes;
    }
}
