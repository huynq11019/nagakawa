package com.nagakawa.guarantee.repository.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nagakawa.guarantee.repository.extend.UserRepositoryExtend;

public class UserRepositoryImpl implements UserRepositoryExtend {
	@PersistenceContext
	private EntityManager entityManager;

	private final Logger _log = LoggerFactory.getLogger(UserRepositoryImpl.class);
}
