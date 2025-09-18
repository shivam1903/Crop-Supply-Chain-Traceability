package com.example.Backend_GI.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "facilitation_survey")
public class FacilitationSurvey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "facilitation_id", nullable = false)
    private Integer facilitation_id;

    @Column(name = "farmer_id")
    private String farmer_id;

    @Column(name = "sustainable_acre")
    private Integer sustainable_acre;

    @Column(name = "mandi_id")
    private String mandi_id;

    @Column(name = "consent")
    private String consent;

    @Column(name = "remark")
    private String remark;

    @Column(name = "aarhtiya_name")
    private String aarhtiya_name;

    @Column(name = "aarhtiya_number")
    private String aarhtiya_number;

    @Column(name = "harvesting_date")
    private LocalDate harvesting_date;

    @Column(name = "geographical_coordinates")
    private String geographical_coordinates;

    @Column(name = "ka_id")
    private String ka_id;

    @Column(name = "transaction_date")
    private LocalDate transaction_date;

    public Integer getFacilitation_id() {
        return facilitation_id;
    }

    public void setFacilitation_id(Integer facilitation_id) {
        this.facilitation_id = facilitation_id;
    }

    public String getFarmer_id() {
        return farmer_id;
    }

    public void setFarmer_id(String farmer_id) {
        this.farmer_id = farmer_id;
    }

    public Integer getSustainable_acre() {
        return sustainable_acre;
    }

    public void setSustainable_acre(Integer sustainable_acre) {
        this.sustainable_acre = sustainable_acre;
    }

    public String getMandi_id() {
        return mandi_id;
    }

    public void setMandi_id(String mandi_id) {
        this.mandi_id = mandi_id;
    }

    public String getConsent() {
        return consent;
    }

    public void setConsent(String consent) {
        this.consent = consent;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAarhtiya_name() {
        return aarhtiya_name;
    }

    public void setAarhtiya_name(String aarhtiya_name) {
        this.aarhtiya_name = aarhtiya_name;
    }

    public String getAarhtiya_number() {
        return aarhtiya_number;
    }

    public void setAarhtiya_number(String aarhtiya_number) {
        this.aarhtiya_number = aarhtiya_number;
    }

    public LocalDate getHarvesting_date() {
        return harvesting_date;
    }

    public void setHarvesting_date(LocalDate harvesting_date) {
        this.harvesting_date = harvesting_date;
    }

    public String getGeographical_coordinates() {
        return geographical_coordinates;
    }

    public void setGeographical_coordinates(String geographical_coordinates) {
        this.geographical_coordinates = geographical_coordinates;
    }

    public String getKa_id() {
        return ka_id;
    }

    public void setKa_id(String ka_id) {
        this.ka_id = ka_id;
    }

    public LocalDate getTransaction_date() {
        return transaction_date;
    }

    public void setTransaction_date(LocalDate transaction_date) {
        this.transaction_date = transaction_date;
    }
}
