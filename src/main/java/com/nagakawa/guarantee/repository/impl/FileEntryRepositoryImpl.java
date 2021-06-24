package com.nagakawa.guarantee.repository.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.nagakawa.guarantee.repository.extend.FileEntryRepositoryExtend;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileEntryRepositoryImpl implements FileEntryRepositoryExtend {
	@PersistenceContext
	private EntityManager entityManager;

}
