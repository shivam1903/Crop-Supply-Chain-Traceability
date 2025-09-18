package com.example.Backend_GI.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "ka_master")
public class KAMaster {

    @Id
    @Column(name="ka_id")
    private String ka_id; // Primary Key

    @Column(name = "ka_name")
    private String ka_name;

    @Column(name = "ka_mobile")
    private String ka_mobile;

    @Column(name = "level")
    private Integer level;

    public String getKa_id() {
        return ka_id;
    }

    public void setKa_id(String ka_id) {
        this.ka_id = ka_id;
    }

    public String getKa_name() {
        return ka_name;
    }

    public void setKa_name(String ka_name) {
        this.ka_name = ka_name;
    }

    public String getKa_mobile() {
        return ka_mobile;
    }

    public void setKa_mobile(String ka_mobile) {
        this.ka_mobile = ka_mobile;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}
