package com.nagakawa.guarantee.service;

import java.util.Optional;

import com.nagakawa.guarantee.model.User;
import com.nagakawa.guarantee.model.dto.AccountDTO;

public interface UserService {

	Optional<User> findByUsername(String username);
	
	Optional<User> getUserWithRoles();

	AccountDTO getUserInfo();
}
