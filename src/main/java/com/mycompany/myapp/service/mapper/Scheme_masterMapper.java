package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.Scheme_masterDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Scheme_master and its DTO Scheme_masterDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface Scheme_masterMapper extends EntityMapper<Scheme_masterDTO, Scheme_master> {


}
