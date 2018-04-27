package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.Scheme_masterDTO;
import java.util.List;

/**
 * Service Interface for managing Scheme_master.
 */
public interface Scheme_masterService {

    /**
     * Save a scheme_master.
     *
     * @param scheme_masterDTO the entity to save
     * @return the persisted entity
     */
    Scheme_masterDTO save(Scheme_masterDTO scheme_masterDTO);

    /**
     * Get all the scheme_masters.
     *
     * @return the list of entities
     */
    List<Scheme_masterDTO> findAll();

    /**
     * Get the "id" scheme_master.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Scheme_masterDTO findOne(String id);

    /**
     * Delete the "id" scheme_master.
     *
     * @param id the id of the entity
     */
    void delete(String id);
}
