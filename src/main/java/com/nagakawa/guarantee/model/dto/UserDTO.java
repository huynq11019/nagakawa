package com.nagakawa.guarantee.model.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class UserDTO implements Serializable {

	private static final long serialVersionUID = 2890692332776709259L;

	private Long id;

	private String username;

	private String fullname;

	private String password;
	
	private String confirmPassword;
	
	private Instant dateOfBirth;
	
	private String email;
	
	private String phoneNumber;
	
	private Integer status;
	
	private String description;
	
	private boolean roleUpdatable;
	
	private Integer minRoleLevel;
	
	private List<String> roleNames;
	
	private List<Long> roleIds;

	private String oldPassword;

}
