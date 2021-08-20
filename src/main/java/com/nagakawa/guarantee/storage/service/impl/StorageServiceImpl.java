package com.nagakawa.guarantee.storage.service.impl;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.nagakawa.guarantee.api.exception.BadRequestAlertException;
import com.nagakawa.guarantee.api.exception.UnauthorizedException;
import com.nagakawa.guarantee.api.util.ApiConstants;
import com.nagakawa.guarantee.messages.LabelKey;
import com.nagakawa.guarantee.messages.Labels;
import com.nagakawa.guarantee.security.util.SecurityUtils;
import com.nagakawa.guarantee.storage.StorageProperties;
import com.nagakawa.guarantee.storage.exception.StorageException;
import com.nagakawa.guarantee.storage.exception.StorageFileNotFoundException;
import com.nagakawa.guarantee.storage.service.StorageService;
import com.nagakawa.guarantee.util.DateUtils;
import com.nagakawa.guarantee.util.FileUtil;
import com.nagakawa.guarantee.util.StringPool;
import com.nagakawa.guarantee.util.Validator;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class StorageServiceImpl implements StorageService {
	private Path rootLocation;

	private final LoadingCache<String, String> storageCache;

	private final StorageProperties storageProperties;

	@Autowired
	public StorageServiceImpl(StorageProperties storageProperties) {
		this.storageProperties = storageProperties;

		this.rootLocation = Paths.get(storageProperties.getFolderUpload());

		this.storageCache = CacheBuilder.newBuilder()
				.expireAfterWrite(storageProperties.getFileKeyStoreDuration(), TimeUnit.MINUTES)
				.build(new CacheLoader<String, String>() {
					@Override
					public String load(final String key) {
						return StringPool.BLANK;
					}
				});
	}

	@Override
	public void addStoreKey(String key, String data) {
		_log.warn("Store key {} with data {} ", key, data);

		storageCache.put(key, data);
	}

	@Override
	public InputStreamResource download(String filename) {
		try {
			File file = ResourceUtils.getFile(storageProperties.getFolderUpload() + "/" + filename);

			try {
				byte[] data = FileUtils.readFileToByteArray(file);

				InputStream inputStream = new BufferedInputStream(new ByteArrayInputStream(data));

				InputStreamResource inputStreamResource = new InputStreamResource(inputStream);

				return inputStreamResource;
			} catch (IOException e) {
				_log.error("Error: ", e);
			}
		} catch (FileNotFoundException e) {
			_log.error("Error: ", e);
		}

		return null;
	}

	@Override
	public String getDataStore(String key) {
		return storageCache.getIfPresent(key);
	}

	@Override
	public InputStream getExcelTemplateFromResource(String filename) throws IOException {
		return new ClassPathResource(storageProperties.getTemplate() + "/" + filename).getInputStream();
	}

	@Override
	public String getTempDirectory() {
		return System.getProperty("java.io.tmpdir");
	}

	@Override
	public String getTimeStampFileName(String original) {
		return String.format(original, DateUtils.formatDateFlatLong(new Date()));
	}

	@Override
	public void invalidateStoreKey(String key) {
		storageCache.invalidate(key);
	}

	@Override
	public Path load(String filename) {
		return rootLocation.resolve(filename);
	}

	@Override
	public Stream<Path> loadAll() {
		try {
			return Files.walk(rootLocation, 1).filter(path -> !path.equals(rootLocation))
					.map(path -> rootLocation.relativize(path));
		} catch (IOException e) {
			throw new StorageException("Failed to read stored files", e);
		}

	}

	@Override
	public Resource loadAsResource(String filename) {
		if (_log.isDebugEnabled()) {
			_log.debug(filename, rootLocation);
		}
		try {
			Path file = load(filename);

			return new UrlResource(file.toUri());
		} catch (MalformedURLException e) {
			throw new StorageFileNotFoundException("Could not read file: " + filename, e);
		}
	}

	@Override
	public Resource loadAsResourceFolder(String storedFileName) {
		try {
			Path file = loadFolder(storedFileName);

			Resource resource = new UrlResource(file.toUri());

			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new StorageFileNotFoundException("Could not read file: " + storedFileName);

			}
		} catch (MalformedURLException e) {
			throw new StorageFileNotFoundException("Could not read file: " + storedFileName, e);
		}
	}

	@Override
	public Path loadFolder(String filename) {
		String folderName = filename.substring(0, 2);
		return rootLocation.resolve(folderName).resolve(filename);
	}

	@Override
	public String store(MultipartFile file) {
		String newFileName = StringPool.BLANK;

		try {
			String originalFilename = file.getOriginalFilename();

			if (Validator.isNull(originalFilename)) {
				throw new StorageException("Failed to store empty file name");
			}

			if (file.isEmpty()) {
				throw new StorageException("Failed to store empty file: " + originalFilename);
			}

			String fullName = FileUtil.getSafeFileName(file.getOriginalFilename());

			String fileExt = FileUtil.getFileExtension(fullName);

			if (Validator.isNull(fileExt)) {
				throw new StorageException("Failed to store invalid file extension: " + originalFilename);
			}

			newFileName = FileUtil.getFileSHA256Checksum(file.getBytes()) + StringPool.PERIOD + fileExt;

			Path path = rootLocation.resolve(newFileName);

			if (!Files.exists(Paths.get(rootLocation.toString()))) {
				new File(rootLocation.toString()).mkdirs();
			}

			Files.copy(file.getInputStream(), path);

		} catch (FileAlreadyExistsException e) {
			_log.error("Store file error, File already exitsts with name " + newFileName, e);
		} catch (IOException e) {
			throw new StorageException("Failed to store file " + file.getOriginalFilename(), e);
		}

		return newFileName;
	}

	@Override
	public String store(MultipartFile filePart, String nameImage) {
		String filename = StringUtils.cleanPath(filePart.getOriginalFilename());

		String folderName = StringPool.BLANK;
		try {
			if (filePart.isEmpty()) {
				throw new StorageException("Failed to store empty file " + filename);
			}

			if (filename.contains("..")) {
				// This is a security check
				throw new StorageException(
						"Cannot store file with relative path outside current directory " + filename);
			}

			folderName = DateUtils.formatInstantAsString(DateUtils.nowInstant(), "yyyy_MM_dd");

			folderName = folderName + "/" + nameImage.substring(0, 2);

			Path path = rootLocation.resolve(folderName);

			InputStream inputStream = filePart.getInputStream();

			if (!path.toFile().exists()) {
				new File(path.toString()).mkdirs();
			}

			Files.copy(inputStream, path.resolve(nameImage));

			return folderName + "/" + nameImage;
		} catch (FileAlreadyExistsException e) {
			_log.error("Store file error: File already exist", e);

			return folderName + "/" + nameImage;
		} catch (IOException e) {
			throw new StorageException("Failed to store file " + filename, e);
		}
	}

	@Override
	public void validateOwner(String key) {
		String userLogin = SecurityUtils.getCurrentUserLogin()
				.orElseThrow(() -> new UnauthorizedException(
						Labels.getLabels(LabelKey.ERROR_USER_COULD_NOT_BE_FOUND,
								new Object[] {Labels.getLabels(LabelKey.LABEL_USER)}),
						ApiConstants.EntityName.USER, LabelKey.ERROR_USER_COULD_NOT_BE_FOUND));

		String owner = storageCache.getIfPresent(key);

		if (Validator.isNull(owner) || !owner.equals(userLogin)) {
			throw new BadRequestAlertException(
					Labels.getLabels(LabelKey.ERROR_DATA_DOES_NOT_EXIST_OR_YOU_ARE_NOT_ALLOWED_TO_PERFORM_THIS_ACTION,
							new Object[] {Labels.getLabels(LabelKey.LABEL_FILE)}),
					ApiConstants.EntityName.FILE,
					LabelKey.ERROR_DATA_DOES_NOT_EXIST_OR_YOU_ARE_NOT_ALLOWED_TO_PERFORM_THIS_ACTION);
		}
	}

}
