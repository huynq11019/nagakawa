package com.nagakawa.guarantee.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_NULL)
public class ContentTemplateDTO {
  private String templateCode;

  private String name;
  
  private String title;
  
  private String template;

  private String description;
  
  private int status;
}
