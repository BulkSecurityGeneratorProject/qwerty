package com.mycompany.myapp.service.dto;


import java.time.LocalDate;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the Policy_details entity.
 */
public class Policy_detailsDTO implements Serializable {

    private UUID id;

    private LocalDate date;

    private UUID scheme_id;

    private UUID agent_id;

    private String customer_name;

    private Long amount;

    private BigDecimal commision;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public UUID getScheme_id() {
        return scheme_id;
    }

    public void setScheme_id(UUID scheme_id) {
        this.scheme_id = scheme_id;
    }

    public UUID getAgent_id() {
        return agent_id;
    }

    public void setAgent_id(UUID agent_id) {
        this.agent_id = agent_id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
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

        Policy_detailsDTO policy_detailsDTO = (Policy_detailsDTO) o;
        if(policy_detailsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), policy_detailsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Policy_detailsDTO{" +
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
