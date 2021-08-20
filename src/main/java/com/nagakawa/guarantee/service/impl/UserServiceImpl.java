package com.nagakawa.guarantee.service.impl;

import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.nagakawa.guarantee.api.exception.UnauthorizedException;
import com.nagakawa.guarantee.api.util.ApiConstants;
import com.nagakawa.guarantee.messages.LabelKey;
import com.nagakawa.guarantee.messages.Labels;
import com.nagakawa.guarantee.model.User;
import com.nagakawa.guarantee.model.dto.AccountDTO;
import com.nagakawa.guarantee.repository.UserRepository;
import com.nagakawa.guarantee.security.util.SecurityUtils;
import com.nagakawa.guarantee.service.UserService;
import com.nagakawa.guarantee.service.mapper.AccountMapper;
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
	
	private final AccountMapper accountMapper;

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

	@Override
	public AccountDTO getUserInfo() {
		User user = SecurityUtils.getUserLogin()
				.orElseThrow(() -> new UnauthorizedException(
						Labels.getLabels(LabelKey.ERROR_USER_COULD_NOT_BE_FOUND),
						ApiConstants.EntityName.ACCOUNT, LabelKey.ERROR_USER_COULD_NOT_BE_FOUND));

		AccountDTO dto = this.accountMapper.toDto(user);

		dto.setPrivileges(SecurityUtils.getPrivileges());

		return dto;
	}
}
