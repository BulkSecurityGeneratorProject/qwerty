package com.mycompany.myapp.service.dto;


import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the Agent_master entity.
 */
public class Agent_masterDTO implements Serializable {

    private UUID id;

    private String name;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Agent_masterDTO agent_masterDTO = (Agent_masterDTO) o;
        if(agent_masterDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), agent_masterDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Agent_masterDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
