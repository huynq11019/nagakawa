package com.nagakawa.guarantee.model.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class RoleDTO implements Serializable{

	private static final long serialVersionUID = -2314914359527351852L;

	private Long id;
	
	private String name;
	
	private Integer status;
	
	private String description;
}
