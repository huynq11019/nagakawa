package com.nagakawa.guarantee.model.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.nagakawa.guarantee.util.Constants;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements Serializable {

	private static final long serialVersionUID = 2890692332776709259L;

	private Long id;

	@NotBlank(message = "Tên đăng nhập không được bỏ trống")
    @Pattern(regexp = Constants.Regex.USERNAME)
    @Size(min = 1, max = 50)
	private String username;

	private String createdBy;

	private Instant createdDate;

	private String lastModifiedBy;

	private Instant lastModifiedDate;

	@Size(max = 50)
	private String fullname;

	@Size(min = 6, max = 20, message = "Mật khẩu phải có độ dài từ 6 đến 20 ký tự")
	private String password;
	
	private Instant date_of_birth;
	
	@Email(regexp = Constants.Regex.EMAIL, message = "Nhập email đúng định dạng")
    @Size(min = 5, max = 254)
	private String email;
	
	private String phoneNumber;
	
	private Integer status;
	
	private String description;
	
	private Integer roleUpdatable;
	
	private Set<String> privileges;

	private String confirmPassword;

	private String oldPassword;

}
