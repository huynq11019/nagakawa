package com.nagakawa.guarantee.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nagakawa.guarantee.cache.util.CacheConstants;
import com.nagakawa.guarantee.model.User;
import com.nagakawa.guarantee.repository.extend.UserRepositoryExtend;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryExtend {
	List<User> findByUsernameIgnoreCase(String username);

    Optional<User> findByUsername(String username);

    Optional<User> findByUsernameIgnoreCaseAndStatusIs(String username, Integer status);

    Optional<User> findByEmail(String email);
    
    @EntityGraph(attributePaths = "roles")
    @Cacheable(cacheNames = CacheConstants.Entity.USERS_BY_USERNAME)
    Optional<User> findOneWithRolesByUsernameIgnoreCaseAndStatusIs(String Username, Integer status);

    @EntityGraph(attributePaths = "roles")
    @Cacheable(cacheNames = CacheConstants.Entity.USERS_BY_EMAIL)
    Optional<User> findOneWithRolesByEmailIgnoreCaseAndStatusIs(String email, Integer status);
}
