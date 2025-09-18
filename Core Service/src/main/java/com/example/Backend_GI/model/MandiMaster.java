package com.example.Backend_GI.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "mandi_master")
public class MandiMaster {

    @Id
    @SequenceGenerator(name="mandi_master_id_seq_a",
            sequenceName="mandi_master_id_seq_a",
            allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator="mandi_master_id_seq_a")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    private Integer id; // Primary Key

    @Column(name = "mandi_name")
    private String mandi_name;

    @Column(name = "district")
    private String district;

    @Column(name = "state")
    private String state;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMandi_name() {
        return mandi_name;
    }

    public void setMandi_name(String mandi_name) {
        this.mandi_name = mandi_name;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
