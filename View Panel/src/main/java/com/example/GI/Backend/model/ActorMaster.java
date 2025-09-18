package com.example.GI.Backend.model;


import jakarta.persistence.*;

@Entity
@Table(name = "actor_master")
public class ActorMaster {

    @EmbeddedId
    private ActorMasterId id;

    @Column(name = "crop")
    private String crop;

    @Column(name = "level")
    private Integer level;

    @Column(name = "process")
    private String process;

    @Column(name = "firm_name")
    private String firm_name;

    @Column(name = "contact_number")
    private String contact_number;

    @Column(name = "poc_name")
    private String poc_name;

    @Column(name = "address")
    private String address;

    @Column(name = "state")
    private String state;

    @Column(name = "district")
    private String district;

    @Column(name = "license_number")
    private String license_number;

    @Column(name = "measuring_unit")
    private String measuring_unit;

    @Column(name = "asset")
    private String asset;

    @Column(name = "mandi_name")
    private String mandi_name;


    // Getters and Setters

    public ActorMasterId getId() {
        return id;
    }

    public void setId(ActorMasterId id) {
        this.id = id;
    }

    public String getCrop() {
        return crop;
    }

    public void setCrop(String crop) {
        this.crop = crop;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getAsset() {
        return asset;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }

    public String getFirm_name() {
        return firm_name;
    }

    public void setFirm_name(String firm_name) {
        this.firm_name = firm_name;
    }

    public String getContact_number() {
        return contact_number;
    }

    public void setContact_number(String contact_number) {
        this.contact_number = contact_number;
    }

    public String getPoc_name() {
        return poc_name;
    }

    public void setPoc_name(String poc_name) {
        this.poc_name = poc_name;
    }

    public String getLicense_number() {
        return license_number;
    }

    public void setLicense_number(String license_number) {
        this.license_number = license_number;
    }

    public String getMeasuring_unit() {
        return measuring_unit;
    }

    public void setMeasuring_unit(String measuring_unit) {
        this.measuring_unit = measuring_unit;
    }

    public String getMandi_name() {
        return mandi_name;
    }

    public void setMandi_name(String mandi_name) {
        this.mandi_name = mandi_name;
    }
}
