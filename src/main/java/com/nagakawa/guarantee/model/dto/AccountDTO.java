/**
 *
 */
package com.nagakawa.guarantee.model.dto;

import java.io.Serializable;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author LinhLH
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountDTO implements Serializable {
	private static final long serialVersionUID = 3028971155064306009L;

	private Long id;

	private String username;

	private String fullname;

	private String name;

	private String phoneNumber;

	private String email;

	private String password;

	private String confirmPassword;

	private List<String> privileges;

    private String oldPassword;
}
