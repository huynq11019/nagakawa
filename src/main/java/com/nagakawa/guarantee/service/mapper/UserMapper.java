package com.nagakawa.guarantee.service.mapper;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import com.nagakawa.guarantee.model.Privilege;
import com.nagakawa.guarantee.model.Role;
import com.nagakawa.guarantee.model.User;
import com.nagakawa.guarantee.model.dto.UserDTO;

@Mapper(componentModel = "spring")
public interface UserMapper extends EntityMapper<UserDTO, User> {
	User toEntity(UserDTO userDTO);

	@Mappings({ @Mapping(source = "user", target = "privileges", qualifiedByName = "toPrivileges") })
	UserDTO toDto(User user);

	default User fromId(Long id) {
		if (id == null) {
			return null;
		}

		User user = new User();

		user.setId(id);

		return user;
	}
	
	@Named("toPrivileges")
	default Set<String> privilegesToStrings(User user) {
		Set<String> privilegeNames = new HashSet<>();

		if (user.getRoles() != null) {
			for (Role role : user.getRoles()) {
				privilegeNames
						.addAll(role.getPrivileges().stream().map(Privilege::getName).collect(Collectors.toSet()));
			}
		}

		return privilegeNames;
	}
}
