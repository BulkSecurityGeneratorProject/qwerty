package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.service.Agent_masterService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.service.dto.Agent_masterDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * REST controller for managing Agent_master.
 */
@RestController
@RequestMapping("/api")
public class Agent_masterResource {

    private final Logger log = LoggerFactory.getLogger(Agent_masterResource.class);

    private static final String ENTITY_NAME = "agent_master";

    private final Agent_masterService agent_masterService;

    public Agent_masterResource(Agent_masterService agent_masterService) {
        this.agent_masterService = agent_masterService;
    }

    /**
     * POST  /agent-masters : Create a new agent_master.
     *
     * @param agent_masterDTO the agent_masterDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new agent_masterDTO, or with status 400 (Bad Request) if the agent_master has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/agent-masters")
    @Timed
    public ResponseEntity<Agent_masterDTO> createAgent_master(@RequestBody Agent_masterDTO agent_masterDTO) throws URISyntaxException {
        log.debug("REST request to save Agent_master : {}", agent_masterDTO);
        if (agent_masterDTO.getId() != null) {
            throw new BadRequestAlertException("A new agent_master cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Agent_masterDTO result = agent_masterService.save(agent_masterDTO);
        return ResponseEntity.created(new URI("/api/agent-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /agent-masters : Updates an existing agent_master.
     *
     * @param agent_masterDTO the agent_masterDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated agent_masterDTO,
     * or with status 400 (Bad Request) if the agent_masterDTO is not valid,
     * or with status 500 (Internal Server Error) if the agent_masterDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/agent-masters")
    @Timed
    public ResponseEntity<Agent_masterDTO> updateAgent_master(@RequestBody Agent_masterDTO agent_masterDTO) throws URISyntaxException {
        log.debug("REST request to update Agent_master : {}", agent_masterDTO);
        if (agent_masterDTO.getId() == null) {
            return createAgent_master(agent_masterDTO);
        }
        Agent_masterDTO result = agent_masterService.save(agent_masterDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, agent_masterDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /agent-masters : get all the agent_masters.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of agent_masters in body
     */
    @GetMapping("/agent-masters")
    @Timed
    public List<Agent_masterDTO> getAllAgent_masters() {
        log.debug("REST request to get all Agent_masters");
        return agent_masterService.findAll();
        }

    /**
     * GET  /agent-masters/:id : get the "id" agent_master.
     *
     * @param id the id of the agent_masterDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the agent_masterDTO, or with status 404 (Not Found)
     */
    @GetMapping("/agent-masters/{id}")
    @Timed
    public ResponseEntity<Agent_masterDTO> getAgent_master(@PathVariable String id) {
        log.debug("REST request to get Agent_master : {}", id);
        Agent_masterDTO agent_masterDTO = agent_masterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(agent_masterDTO));
    }

    /**
     * DELETE  /agent-masters/:id : delete the "id" agent_master.
     *
     * @param id the id of the agent_masterDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/agent-masters/{id}")
    @Timed
    public ResponseEntity<Void> deleteAgent_master(@PathVariable String id) {
        log.debug("REST request to delete Agent_master : {}", id);
        agent_masterService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
