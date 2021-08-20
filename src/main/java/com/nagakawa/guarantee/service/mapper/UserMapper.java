package com.nagakawa.guarantee.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import com.nagakawa.guarantee.model.User;
import com.nagakawa.guarantee.model.dto.UserDTO;

@Mapper(componentModel = "spring")
public interface UserMapper extends EntityMapper<UserDTO, User> {
	User toEntity(UserDTO userDTO);

	@Mappings({@Mapping(target = "password", ignore = true)})
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
