package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.Policy_detailsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Policy_details and its DTO Policy_detailsDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface Policy_detailsMapper extends EntityMapper<Policy_detailsDTO, Policy_details> {


}
