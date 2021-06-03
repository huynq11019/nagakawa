package com.nagakawa.guarantee.repository.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nagakawa.guarantee.repository.extend.FileEntryRepositoryExtend;
import com.nagakawa.guarantee.repository.extend.UserLoginRepositoryExtend;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileEntryRepositoryImpl implements FileEntryRepositoryExtend {
	@PersistenceContext
	private EntityManager entityManager;

}
