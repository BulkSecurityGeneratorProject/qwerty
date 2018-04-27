package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.Agent_masterDTO;
import java.util.List;

/**
 * Service Interface for managing Agent_master.
 */
public interface Agent_masterService {

    /**
     * Save a agent_master.
     *
     * @param agent_masterDTO the entity to save
     * @return the persisted entity
     */
    Agent_masterDTO save(Agent_masterDTO agent_masterDTO);

    /**
     * Get all the agent_masters.
     *
     * @return the list of entities
     */
    List<Agent_masterDTO> findAll();

    /**
     * Get the "id" agent_master.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Agent_masterDTO findOne(String id);

    /**
     * Delete the "id" agent_master.
     *
     * @param id the id of the entity
     */
    void delete(String id);
}
