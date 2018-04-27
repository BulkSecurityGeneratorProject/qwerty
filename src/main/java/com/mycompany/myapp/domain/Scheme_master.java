package com.mycompany.myapp.domain;

import com.datastax.driver.mapping.annotations.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

/**
 * A Scheme_master.
 */
@Table(name = "scheme_master")
public class Scheme_master implements Serializable {

    private static final long serialVersionUID = 1L;

    @PartitionKey
    private UUID id;

    private String name;

    private BigDecimal commision;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Scheme_master name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getCommision() {
        return commision;
    }

    public Scheme_master commision(BigDecimal commision) {
        this.commision = commision;
        return this;
    }

    public void setCommision(BigDecimal commision) {
        this.commision = commision;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Scheme_master scheme_master = (Scheme_master) o;
        if (scheme_master.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), scheme_master.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Scheme_master{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", commision=" + getCommision() +
            "}";
    }
}
