package com.nagakawa.guarantee.service.mapper;

import org.mapstruct.Mapper;

import com.nagakawa.guarantee.model.User;
import com.nagakawa.guarantee.model.dto.UserDTO;

@Mapper(componentModel = "spring")
public interface UserMapper extends EntityMapper<UserDTO, User> {
	User toEntity(UserDTO userDTO);

	UserDTO toDto(User user);

	default User fromId(Long id) {
		if (id == null) {
			return null;
		}
		
		User user = new User();
		
		user.setId(id);
				
		return user;
	}
}
