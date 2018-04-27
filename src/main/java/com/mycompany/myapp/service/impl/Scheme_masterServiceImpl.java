package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.Scheme_masterService;
import com.mycompany.myapp.domain.Scheme_master;
import com.mycompany.myapp.repository.Scheme_masterRepository;
import com.mycompany.myapp.service.dto.Scheme_masterDTO;
import com.mycompany.myapp.service.mapper.Scheme_masterMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Scheme_master.
 */
@Service
public class Scheme_masterServiceImpl implements Scheme_masterService {

    private final Logger log = LoggerFactory.getLogger(Scheme_masterServiceImpl.class);

    private final Scheme_masterRepository scheme_masterRepository;

    private final Scheme_masterMapper scheme_masterMapper;

    public Scheme_masterServiceImpl(Scheme_masterRepository scheme_masterRepository, Scheme_masterMapper scheme_masterMapper) {
        this.scheme_masterRepository = scheme_masterRepository;
        this.scheme_masterMapper = scheme_masterMapper;
    }

    /**
     * Save a scheme_master.
     *
     * @param scheme_masterDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public Scheme_masterDTO save(Scheme_masterDTO scheme_masterDTO) {
        log.debug("Request to save Scheme_master : {}", scheme_masterDTO);
        Scheme_master scheme_master = scheme_masterMapper.toEntity(scheme_masterDTO);
        scheme_master = scheme_masterRepository.save(scheme_master);
        return scheme_masterMapper.toDto(scheme_master);
    }

    /**
     * Get all the scheme_masters.
     *
     * @return the list of entities
     */
    @Override
    public List<Scheme_masterDTO> findAll() {
        log.debug("Request to get all Scheme_masters");
        return scheme_masterRepository.findAll().stream()
            .map(scheme_masterMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one scheme_master by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public Scheme_masterDTO findOne(String id) {
        log.debug("Request to get Scheme_master : {}", id);
        Scheme_master scheme_master = scheme_masterRepository.findOne(UUID.fromString(id));
        return scheme_masterMapper.toDto(scheme_master);
    }

    /**
     * Delete the scheme_master by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Scheme_master : {}", id);
        scheme_masterRepository.delete(UUID.fromString(id));
    }
}
