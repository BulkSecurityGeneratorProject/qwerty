package com.mycompany.myapp.domain;

import com.datastax.driver.mapping.annotations.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

/**
 * A Policy_details.
 */
@Table(name = "policy_details")
public class Policy_details implements Serializable {

    private static final long serialVersionUID = 1L;

    @PartitionKey
    private UUID id;

    private LocalDate date;

    private UUID scheme_id;

    private UUID agent_id;

    private String customer_name;

    private Long amount;

    private BigDecimal commision;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public Policy_details date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public UUID getScheme_id() {
        return scheme_id;
    }

    public Policy_details scheme_id(UUID scheme_id) {
        this.scheme_id = scheme_id;
        return this;
    }

    public void setScheme_id(UUID scheme_id) {
        this.scheme_id = scheme_id;
    }

    public UUID getAgent_id() {
        return agent_id;
    }

    public Policy_details agent_id(UUID agent_id) {
        this.agent_id = agent_id;
        return this;
    }

    public void setAgent_id(UUID agent_id) {
        this.agent_id = agent_id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public Policy_details customer_name(String customer_name) {
        this.customer_name = customer_name;
        return this;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public Long getAmount() {
        return amount;
    }

    public Policy_details amount(Long amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public BigDecimal getCommision() {
        return commision;
    }

    public Policy_details commision(BigDecimal commision) {
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
        Policy_details policy_details = (Policy_details) o;
        if (policy_details.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), policy_details.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Policy_details{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", scheme_id='" + getScheme_id() + "'" +
            ", agent_id='" + getAgent_id() + "'" +
            ", customer_name='" + getCustomer_name() + "'" +
            ", amount=" + getAmount() +
            ", commision=" + getCommision() +
            "}";
    }
}
