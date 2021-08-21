package com.nagakawa.guarantee.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.nagakawa.guarantee.model.dto.ContentTemplateDTO;

public interface ContentTemplateService {
  Page<ContentTemplateDTO> getAllContentTemplate(Pageable pageable);

  ContentTemplateDTO update(ContentTemplateDTO contentTemplate);

  ContentTemplateDTO findTemplateByTemplateCode(String templateCode, int status);
}
