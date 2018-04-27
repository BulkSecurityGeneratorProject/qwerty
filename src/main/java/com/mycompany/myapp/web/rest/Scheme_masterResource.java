package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.service.Scheme_masterService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.service.dto.Scheme_masterDTO;
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
 * REST controller for managing Scheme_master.
 */
@RestController
@RequestMapping("/api")
public class Scheme_masterResource {

    private final Logger log = LoggerFactory.getLogger(Scheme_masterResource.class);

    private static final String ENTITY_NAME = "scheme_master";

    private final Scheme_masterService scheme_masterService;

    public Scheme_masterResource(Scheme_masterService scheme_masterService) {
        this.scheme_masterService = scheme_masterService;
    }

    /**
     * POST  /scheme-masters : Create a new scheme_master.
     *
     * @param scheme_masterDTO the scheme_masterDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new scheme_masterDTO, or with status 400 (Bad Request) if the scheme_master has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/scheme-masters")
    @Timed
    public ResponseEntity<Scheme_masterDTO> createScheme_master(@RequestBody Scheme_masterDTO scheme_masterDTO) throws URISyntaxException {
        log.debug("REST request to save Scheme_master : {}", scheme_masterDTO);
        if (scheme_masterDTO.getId() != null) {
            throw new BadRequestAlertException("A new scheme_master cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Scheme_masterDTO result = scheme_masterService.save(scheme_masterDTO);
        return ResponseEntity.created(new URI("/api/scheme-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /scheme-masters : Updates an existing scheme_master.
     *
     * @param scheme_masterDTO the scheme_masterDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated scheme_masterDTO,
     * or with status 400 (Bad Request) if the scheme_masterDTO is not valid,
     * or with status 500 (Internal Server Error) if the scheme_masterDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/scheme-masters")
    @Timed
    public ResponseEntity<Scheme_masterDTO> updateScheme_master(@RequestBody Scheme_masterDTO scheme_masterDTO) throws URISyntaxException {
        log.debug("REST request to update Scheme_master : {}", scheme_masterDTO);
        if (scheme_masterDTO.getId() == null) {
            return createScheme_master(scheme_masterDTO);
        }
        Scheme_masterDTO result = scheme_masterService.save(scheme_masterDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, scheme_masterDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /scheme-masters : get all the scheme_masters.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of scheme_masters in body
     */
    @GetMapping("/scheme-masters")
    @Timed
    public List<Scheme_masterDTO> getAllScheme_masters() {
        log.debug("REST request to get all Scheme_masters");
        return scheme_masterService.findAll();
        }

    /**
     * GET  /scheme-masters/:id : get the "id" scheme_master.
     *
     * @param id the id of the scheme_masterDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the scheme_masterDTO, or with status 404 (Not Found)
     */
    @GetMapping("/scheme-masters/{id}")
    @Timed
    public ResponseEntity<Scheme_masterDTO> getScheme_master(@PathVariable String id) {
        log.debug("REST request to get Scheme_master : {}", id);
        Scheme_masterDTO scheme_masterDTO = scheme_masterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(scheme_masterDTO));
    }

    /**
     * DELETE  /scheme-masters/:id : delete the "id" scheme_master.
     *
     * @param id the id of the scheme_masterDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/scheme-masters/{id}")
    @Timed
    public ResponseEntity<Void> deleteScheme_master(@PathVariable String id) {
        log.debug("REST request to delete Scheme_master : {}", id);
        scheme_masterService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
