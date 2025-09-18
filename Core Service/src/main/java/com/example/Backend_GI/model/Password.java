package com.example.Backend_GI.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "password_db")
public class Password {

    @Id
    @Column(name = "phone_no", nullable = false, unique = true)
    public String number;

    @Column(name = "hash", nullable = false)
    public String hash;

    @Column(name = "level", nullable = false)
    public Integer level;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}
