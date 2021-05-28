package com.nagakawa.guarantee.model.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class PrivilegeDTO implements Serializable{

	private static final long serialVersionUID = 6064148066221899014L;
	
	private Long id;
	
	private String name;
	
	private String description;

}
