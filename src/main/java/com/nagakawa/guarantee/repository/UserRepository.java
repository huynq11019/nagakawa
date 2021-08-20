package com.nagakawa.guarantee.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.nagakawa.guarantee.cache.util.CacheConstants;
import com.nagakawa.guarantee.model.User;
import com.nagakawa.guarantee.repository.extend.UserRepositoryExtend;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryExtend {
	List<User> findByUsernameIgnoreCase(String username);

	@Cacheable(cacheNames = CacheConstants.Entity.USER, key = "{#root.methodName, #username}",
			unless = "#result == null")
    Optional<User> findByUsername(String username);

	@Cacheable(cacheNames = CacheConstants.Entity.USER, key = "{#root.methodName, #email}",
			unless = "#result == null")
    Optional<User> findByEmail(String email);
    
    @EntityGraph(attributePaths = "roles")
	@Cacheable(cacheNames = CacheConstants.Entity.USER, key = "{#root.methodName, #email}",
			unless = "#result == null")
	@Transactional
	Optional<User> findOneWithRolesByEmailIgnoreCaseAndStatusIs(String email, Integer status);

	@EntityGraph(attributePaths = "roles")
	@Cacheable(cacheNames = CacheConstants.Entity.USER, key = "{#root.methodName, #username}",
			unless = "#result == null")
	@Transactional
	Optional<User> findOneWithRolesByUsernameIgnoreCaseAndStatusIs(String username, Integer status);
}
