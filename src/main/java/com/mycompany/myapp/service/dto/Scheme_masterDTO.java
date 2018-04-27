package com.mycompany.myapp.service.dto;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the Scheme_master entity.
 */
public class Scheme_masterDTO implements Serializable {

    private UUID id;

    private String name;

    private BigDecimal commision;

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

    public BigDecimal getCommision() {
        return commision;
    }

    public void setCommision(BigDecimal commision) {
        this.commision = commision;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Scheme_masterDTO scheme_masterDTO = (Scheme_masterDTO) o;
        if(scheme_masterDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), scheme_masterDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Scheme_masterDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", commision=" + getCommision() +
            "}";
    }
}
