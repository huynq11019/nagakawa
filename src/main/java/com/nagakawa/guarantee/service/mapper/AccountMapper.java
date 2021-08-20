package com.nagakawa.guarantee.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import com.nagakawa.guarantee.model.User;
import com.nagakawa.guarantee.model.dto.AccountDTO;

@Mapper(componentModel = "spring")
public interface AccountMapper extends EntityMapper<AccountDTO, User> {
	@Override
	User toEntity(AccountDTO accountDTO);

	@Override
	@Mappings({@Mapping(target = "password", ignore = true)})

	AccountDTO toDto(User user);

	default User fromId(Long id) {
		if (id == null) {
			return null;
		}

		User user = new User();

		user.setId(id);

		return user;
	}
}
