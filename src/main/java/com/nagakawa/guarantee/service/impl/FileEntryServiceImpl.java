/**
 * 
 */
package com.nagakawa.guarantee.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nagakawa.guarantee.repository.FileEntryRepository;
import com.nagakawa.guarantee.repository.FolderEntryRepository;
import com.nagakawa.guarantee.service.FileEntryService;
import com.nagakawa.guarantee.service.mapper.FileEntryMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author LinhLH
 *
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class FileEntryServiceImpl implements FileEntryService {
	private final FileEntryRepository fileEntryRepository;
	
	private final FolderEntryRepository folderEntryRepository;
	
	private final FileEntryMapper fileEntryMapper;
}
