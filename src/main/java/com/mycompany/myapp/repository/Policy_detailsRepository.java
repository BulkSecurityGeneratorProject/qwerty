package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Policy_details;
import org.springframework.stereotype.Repository;

import com.datastax.driver.core.*;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Cassandra repository for the Policy_details entity.
 */
@Repository
public class Policy_detailsRepository {

    private final Session session;

    private final Validator validator;

    private Mapper<Policy_details> mapper;

    private PreparedStatement findAllStmt;

    private PreparedStatement truncateStmt;

    public Policy_detailsRepository(Session session, Validator validator) {
        this.session = session;
        this.validator = validator;
        this.mapper = new MappingManager(session).mapper(Policy_details.class);
        this.findAllStmt = session.prepare("SELECT * FROM policy_details");
        this.truncateStmt = session.prepare("TRUNCATE policy_details");
    }

    public List<Policy_details> findAll() {
        List<Policy_details> policy_detailsList = new ArrayList<>();
        BoundStatement stmt = findAllStmt.bind();
        session.execute(stmt).all().stream().map(
            row -> {
                Policy_details policy_details = new Policy_details();
                policy_details.setId(row.getUUID("id"));
                policy_details.setDate(row.get("date", LocalDate.class));
                policy_details.setScheme_id(row.getUUID("scheme_id"));
                policy_details.setAgent_id(row.getUUID("agent_id"));
                policy_details.setCustomer_name(row.getString("customer_name"));
                policy_details.setAmount(row.getLong("amount"));
                policy_details.setCommision(row.getDecimal("commision"));
                return policy_details;
            }
        ).forEach(policy_detailsList::add);
        return policy_detailsList;
    }

    public Policy_details findOne(UUID id) {
        return mapper.get(id);
    }

    public Policy_details save(Policy_details policy_details) {
        if (policy_details.getId() == null) {
            policy_details.setId(UUID.randomUUID());
        }
        Set<ConstraintViolation<Policy_details>> violations = validator.validate(policy_details);
        if (violations != null && !violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        mapper.save(policy_details);
        return policy_details;
    }

    public void delete(UUID id) {
        mapper.delete(id);
    }

    public void deleteAll() {
        BoundStatement stmt = truncateStmt.bind();
        session.execute(stmt);
    }
}
