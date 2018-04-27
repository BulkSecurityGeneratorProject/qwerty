package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.Agent_masterDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Agent_master and its DTO Agent_masterDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface Agent_masterMapper extends EntityMapper<Agent_masterDTO, Agent_master> {


}
