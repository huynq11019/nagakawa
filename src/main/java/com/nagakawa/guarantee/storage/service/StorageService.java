package com.nagakawa.guarantee.storage.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.stream.Stream;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

	void addStoreKey(String key, String data);

	InputStreamResource download(String filename);

	String getDataStore(String key);

	InputStream getExcelTemplateFromResource(String filename) throws IOException;

	String getTempDirectory();

	String getTimeStampFileName(String original);
	
	void invalidateStoreKey(String key);

	Path load(String filename);

	Stream<Path> loadAll();

	Resource loadAsResource(String filename);

	Resource loadAsResourceFolder(String filename);

	Path loadFolder(String filename);

	String store(MultipartFile file);

	String store(MultipartFile filePart, String destName);
	
	void validateOwner(String key);
}
