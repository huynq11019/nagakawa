package com.nagakawa.guarantee.service.mapper;

import org.mapstruct.Mapper;
import com.nagakawa.guarantee.model.ContentTemplate;
import com.nagakawa.guarantee.model.dto.ContentTemplateDTO;

@Mapper(componentModel = "spring")
public interface ContentTemplateMapper extends EntityMapper<ContentTemplateDTO, ContentTemplate>{

}
