package com.nagakawa.guarantee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nagakawa.guarantee.model.UserLogin;
import com.nagakawa.guarantee.repository.extend.UserLoginRepositoryExtend;

@Repository
public interface UserLoginRepository extends JpaRepository<UserLogin, Long>, UserLoginRepositoryExtend {

}
