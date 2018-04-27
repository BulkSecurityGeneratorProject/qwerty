package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.Policy_detailsService;
import com.mycompany.myapp.domain.Policy_details;
import com.mycompany.myapp.repository.Policy_detailsRepository;
import com.mycompany.myapp.service.dto.Policy_detailsDTO;
import com.mycompany.myapp.service.mapper.Policy_detailsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Policy_details.
 */
@Service
public class Policy_detailsServiceImpl implements Policy_detailsService {

    private final Logger log = LoggerFactory.getLogger(Policy_detailsServiceImpl.class);

    private final Policy_detailsRepository policy_detailsRepository;

    private final Policy_detailsMapper policy_detailsMapper;

    public Policy_detailsServiceImpl(Policy_detailsRepository policy_detailsRepository, Policy_detailsMapper policy_detailsMapper) {
        this.policy_detailsRepository = policy_detailsRepository;
        this.policy_detailsMapper = policy_detailsMapper;
    }

    /**
     * Save a policy_details.
     *
     * @param policy_detailsDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public Policy_detailsDTO save(Policy_detailsDTO policy_detailsDTO) {
        log.debug("Request to save Policy_details : {}", policy_detailsDTO);
        Policy_details policy_details = policy_detailsMapper.toEntity(policy_detailsDTO);
        policy_details = policy_detailsRepository.save(policy_details);
        return policy_detailsMapper.toDto(policy_details);
    }

    /**
     * Get all the policy_details.
     *
     * @return the list of entities
     */
    @Override
    public List<Policy_detailsDTO> findAll() {
        log.debug("Request to get all Policy_details");
        return policy_detailsRepository.findAll().stream()
            .map(policy_detailsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one policy_details by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public Policy_detailsDTO findOne(String id) {
        log.debug("Request to get Policy_details : {}", id);
        Policy_details policy_details = policy_detailsRepository.findOne(UUID.fromString(id));
        return policy_detailsMapper.toDto(policy_details);
    }

    /**
     * Delete the policy_details by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Policy_details : {}", id);
        policy_detailsRepository.delete(UUID.fromString(id));
    }
}
