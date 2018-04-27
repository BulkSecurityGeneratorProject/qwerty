package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Scheme_master;
import org.springframework.stereotype.Repository;

import com.datastax.driver.core.*;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Cassandra repository for the Scheme_master entity.
 */
@Repository
public class Scheme_masterRepository {

    private final Session session;

    private final Validator validator;

    private Mapper<Scheme_master> mapper;

    private PreparedStatement findAllStmt;

    private PreparedStatement truncateStmt;

    public Scheme_masterRepository(Session session, Validator validator) {
        this.session = session;
        this.validator = validator;
        this.mapper = new MappingManager(session).mapper(Scheme_master.class);
        this.findAllStmt = session.prepare("SELECT * FROM scheme_master");
        this.truncateStmt = session.prepare("TRUNCATE scheme_master");
    }

    public List<Scheme_master> findAll() {
        List<Scheme_master> scheme_mastersList = new ArrayList<>();
        BoundStatement stmt = findAllStmt.bind();
        session.execute(stmt).all().stream().map(
            row -> {
                Scheme_master scheme_master = new Scheme_master();
                scheme_master.setId(row.getUUID("id"));
                scheme_master.setName(row.getString("name"));
                scheme_master.setCommision(row.getDecimal("commision"));
                return scheme_master;
            }
        ).forEach(scheme_mastersList::add);
        return scheme_mastersList;
    }

    public Scheme_master findOne(UUID id) {
        return mapper.get(id);
    }

    public Scheme_master save(Scheme_master scheme_master) {
        if (scheme_master.getId() == null) {
            scheme_master.setId(UUID.randomUUID());
        }
        Set<ConstraintViolation<Scheme_master>> violations = validator.validate(scheme_master);
        if (violations != null && !violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        mapper.save(scheme_master);
        return scheme_master;
    }

    public void delete(UUID id) {
        mapper.delete(id);
    }

    public void deleteAll() {
        BoundStatement stmt = truncateStmt.bind();
        session.execute(stmt);
    }
}
