package com.nagakawa.guarantee.repository.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.nagakawa.guarantee.repository.extend.UserLoginRepositoryExtend;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserLoginRepositoryImpl implements UserLoginRepositoryExtend {
	@PersistenceContext
	private EntityManager entityManager;
}
