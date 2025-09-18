package com.example.GI.Backend.model;


import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class ActorMasterId implements Serializable {

    @Column(name = "actor_id")
    private Integer actorId;

    @Column(name = "project_id")
    private String projectId;

    // Default Constructor
    public ActorMasterId() {}

    // Parameterized Constructor
    public ActorMasterId(Integer actorId, String projectId) {
        this.actorId = actorId;
        this.projectId = projectId;
    }

    // Getters and Setters
    public Integer getActorId() {
        return actorId;
    }

    public void setActorId(Integer actorId) {
        this.actorId = actorId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    // Override equals() and hashCode() for proper key comparison
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActorMasterId that = (ActorMasterId) o;
        return Objects.equals(actorId, that.actorId) &&
                Objects.equals(projectId, that.projectId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(actorId, projectId);
    }
}

