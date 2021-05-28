package com.nagakawa.guarantee.service;

import java.util.Optional;

import com.nagakawa.guarantee.model.User;

public interface UserService {

	Optional<User> findByUsername(String username);
	
	Optional<User> getUserWithRoles();

}
