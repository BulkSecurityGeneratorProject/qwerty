package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.Policy_detailsDTO;
import java.util.List;

/**
 * Service Interface for managing Policy_details.
 */
public interface Policy_detailsService {

    /**
     * Save a policy_details.
     *
     * @param policy_detailsDTO the entity to save
     * @return the persisted entity
     */
    Policy_detailsDTO save(Policy_detailsDTO policy_detailsDTO);

    /**
     * Get all the policy_details.
     *
     * @return the list of entities
     */
    List<Policy_detailsDTO> findAll();

    /**
     * Get the "id" policy_details.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Policy_detailsDTO findOne(String id);

    /**
     * Delete the "id" policy_details.
     *
     * @param id the id of the entity
     */
    void delete(String id);
}
