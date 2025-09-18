package com.example.Backend.Model;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ActorMasterId implements Serializable {

    @Column(name = "actor_id")
    private Integer actor_id;

    @Column(name = "project_id")
    private String project_id;

    // Default Constructor
    public ActorMasterId() {}

    // Parameterized Constructor
    public ActorMasterId(Integer actor_id, String project_id) {
        this.actor_id = actor_id;
        this.project_id = project_id;
    }

    // Getters and Setters


    public Integer getActor_id() {
        return actor_id;
    }

    public void setActor_id(Integer actor_id) {
        this.actor_id = actor_id;
    }

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    // Override equals() and hashCode() for proper key comparison
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActorMasterId that = (ActorMasterId) o;
        return Objects.equals(actor_id, that.actor_id) &&
                Objects.equals(project_id, that.project_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(actor_id, project_id);
    }
}

