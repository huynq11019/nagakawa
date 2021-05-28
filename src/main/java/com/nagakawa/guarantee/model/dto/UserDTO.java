package com.nagakawa.guarantee.model.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements Serializable {

	private static final long serialVersionUID = 2890692332776709259L;

	private Long id;

	private String username;

	private String createdBy;

	private Instant createdDate;

	private String lastModifiedBy;

	private Instant lastModifiedDate;

	private String fullname;

	private String password;
	
	private Instant date_of_birth;
	
	private String email;
	
	private String phoneNumber;
	
	private Integer status;
	
	private String description;
	
	private Integer roleUpdatable;
	
	private Set<String> privileges;

	private String confirmPassword;

	private String oldPassword;

}
