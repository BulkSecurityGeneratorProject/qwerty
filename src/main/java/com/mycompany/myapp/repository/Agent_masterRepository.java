package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Agent_master;
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
 * Cassandra repository for the Agent_master entity.
 */
@Repository
public class Agent_masterRepository {

    private final Session session;

    private final Validator validator;

    private Mapper<Agent_master> mapper;

    private PreparedStatement findAllStmt;

    private PreparedStatement truncateStmt;

    public Agent_masterRepository(Session session, Validator validator) {
        this.session = session;
        this.validator = validator;
        this.mapper = new MappingManager(session).mapper(Agent_master.class);
        this.findAllStmt = session.prepare("SELECT * FROM agent_master");
        this.truncateStmt = session.prepare("TRUNCATE agent_master");
    }

    public List<Agent_master> findAll() {
        List<Agent_master> agent_mastersList = new ArrayList<>();
        BoundStatement stmt = findAllStmt.bind();
        session.execute(stmt).all().stream().map(
            row -> {
                Agent_master agent_master = new Agent_master();
                agent_master.setId(row.getUUID("id"));
                agent_master.setName(row.getString("name"));
                return agent_master;
            }
        ).forEach(agent_mastersList::add);
        return agent_mastersList;
    }

    public Agent_master findOne(UUID id) {
        return mapper.get(id);
    }

    public Agent_master save(Agent_master agent_master) {
        if (agent_master.getId() == null) {
            agent_master.setId(UUID.randomUUID());
        }
        Set<ConstraintViolation<Agent_master>> violations = validator.validate(agent_master);
        if (violations != null && !violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        mapper.save(agent_master);
        return agent_master;
    }

    public void delete(UUID id) {
        mapper.delete(id);
    }

    public void deleteAll() {
        BoundStatement stmt = truncateStmt.bind();
        session.execute(stmt);
    }
}
