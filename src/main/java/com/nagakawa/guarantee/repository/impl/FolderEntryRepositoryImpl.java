/**
 * 
 */
package com.nagakawa.guarantee.repository.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.nagakawa.guarantee.repository.extend.FolderEntryRepositoryExtend;

import lombok.extern.slf4j.Slf4j;

/**
 * @author LinhLH
 *
 */
@Slf4j
public class FolderEntryRepositoryImpl implements FolderEntryRepositoryExtend {
	@PersistenceContext
	private EntityManager entityManager;
}
