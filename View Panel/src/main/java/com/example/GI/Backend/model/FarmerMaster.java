package com.example.GI.Backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "farmer_master")
public class FarmerMaster {

    @Id
    @Column(name="farmer_id")
    private String farmer_id; // Primary Key

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "farmer_name")
    private String farmer_name;

    @Column(name = "village")
    private String village;

    @Column(name = "enrolled_acre")
    private Integer enrolled_acre;

    @Column(name = "geographical_coordinates")
    private String geographical_coordinates;

    @Column(name = "taluka")
    private String taluka;

    @Column(name = "state")
    private String state;

    @Column(name = "district")
    private String district;

    @Column(name = "father_name")
    private String father_name;

    @Column(name = "agreement_status")
    private String agreement_status;

    public String getFarmer_id() {
        return farmer_id;
    }

    public void setFarmer_id(String farmer_id) {
        this.farmer_id = farmer_id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getFarmer_name() {
        return farmer_name;
    }

    public void setFarmer_name(String farmer_name) {
        this.farmer_name = farmer_name;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public Integer getEnrolled_acre() {
        return enrolled_acre;
    }

    public void setEnrolled_acre(Integer enrolled_acre) {
        this.enrolled_acre = enrolled_acre;
    }

    public String getGeographical_coordinates() {
        return geographical_coordinates;
    }

    public void setGeographical_coordinates(String geographical_coordinates) {
        this.geographical_coordinates = geographical_coordinates;
    }

    public String getTaluka() {
        return taluka;
    }

    public void setTaluka(String taluka) {
        this.taluka = taluka;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getFather_name() {
        return father_name;
    }

    public void setFather_name(String father_name) {
        this.father_name = father_name;
    }

    public String getAgreement_status() {
        return agreement_status;
    }

    public void setAgreement_status(String agreement_status) {
        this.agreement_status = agreement_status;
    }
}
