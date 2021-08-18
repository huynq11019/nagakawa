package com.nagakawa.guarantee.service.impl;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nagakawa.guarantee.model.User;
import com.nagakawa.guarantee.repository.UserRepository;
import com.nagakawa.guarantee.security.util.SecurityUtils;
import com.nagakawa.guarantee.service.UserService;
import com.nagakawa.guarantee.service.mapper.UserMapper;
import com.nagakawa.guarantee.util.Constants;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	private final UserMapper userMapper;

	@Override
	@Transactional(readOnly = true)
	public Optional<User> findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public Optional<User> getUserWithRoles() {
		Optional<String> currentUserLogin = SecurityUtils.getCurrentUserLogin();

		if (currentUserLogin.isPresent()) {
			return userRepository.findOneWithRolesByUsernameIgnoreCaseAndStatusIs(currentUserLogin.get(),
					Constants.EntityStatus.ACTIVE);
		}

		return Optional.empty();
	}

}
