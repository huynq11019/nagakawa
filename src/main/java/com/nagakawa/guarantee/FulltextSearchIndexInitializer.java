/*
 * FulltextSearchIndexInitializer.java
 *
 * Copyright (C) 2021 by Evotek. All right reserved.
 * This software is the confidential and proprietary information of Evotek
 */
package com.nagakawa.guarantee;

import java.time.Instant;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.hibernate.CacheMode;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * 05/07/2021 - LinhLH: Create new
 *
 * @author LinhLH
 */
@Slf4j
@Component
@Getter
public class FulltextSearchIndexInitializer implements ApplicationListener<ApplicationReadyEvent> {

	private static final Integer DEFAULT_BATCH_INDEX_SIZE = 25;

	@PersistenceContext
	private EntityManager entityManager;

	private FullTextEntityManager fullTextEntityManager;
	
	private Instant lastIndexedTime = Instant.now();
	
	@Transactional
	@Override
	public void onApplicationEvent(final ApplicationReadyEvent event) {
		fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
		
		this.reindexAll(25);
	}

	@Async
	public void reindexAll(int batchSize) {
		_log.info("Start creating search index.");

		if (batchSize <= 0) {
			batchSize = DEFAULT_BATCH_INDEX_SIZE;
		}

		try {
			// // @formatter:off
			fullTextEntityManager.createIndexer()
					// .createIndexer(INDEX_DOMAIN_CLASSES)//
					// .typesToIndexInParallel(INDEX_DOMAIN_CLASSES.length)//
					.batchSizeToLoadObjects(batchSize)//
					.threadsToLoadObjects(5)
					.idFetchSize(150)
					.cacheMode(CacheMode.NORMAL)
					.startAndWait();
			// @formatter:on
			
			this.lastIndexedTime = Instant.now();
			_log.info("Successfully created search index.");
		} catch (InterruptedException e) {
			_log.warn("An error occurred trying to build the search index: {}", e.toString());
		}
	}
}
