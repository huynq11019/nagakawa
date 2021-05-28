package com.nagakawa.guarantee.service.storage.impl;

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
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.nagakawa.guarantee.configuration.ApplicationProperties;
import com.nagakawa.guarantee.service.storage.StorageService;
import com.nagakawa.guarantee.service.storage.exception.StorageException;
import com.nagakawa.guarantee.service.storage.exception.StorageFileNotFoundException;
import com.nagakawa.guarantee.util.DateUtils;
import com.nagakawa.guarantee.util.FileUtil;
import com.nagakawa.guarantee.util.StringPool;
import com.nagakawa.guarantee.util.Validator;

@Service
public class FileSystemStorageService implements StorageService {

    private final Logger log = LoggerFactory.getLogger(FileSystemStorageService.class);

    private Path rootLocation;

    @Autowired
    private ApplicationProperties applicationProperties;

    @Autowired
    public FileSystemStorageService(ApplicationProperties applicationProperties) {
        this.rootLocation = Paths.get(applicationProperties.getFolderUpload());
    }

    @Override
    public String store(MultipartFile file) {
        String newFileName = StringPool.BLANK;
        
        try {
        	String originalFilename = file.getOriginalFilename();
        	
        	if(Validator.isNull(originalFilename)) {
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
            log.error("Store file error, File already exitsts with name " + newFileName, e);
        } catch (IOException e) {
            throw new StorageException("Failed to store file " + file.getOriginalFilename(), e);
        }
        
        return newFileName;
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(rootLocation, 1)
                    .filter(path -> !path.equals(rootLocation))
                    .map(path -> rootLocation.relativize(path));
        } catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }

    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        if(log.isDebugEnabled()) {
            log.debug(filename, rootLocation);
        }
        try {
            Path file = load(filename);
            
            return new UrlResource(file.toUri());
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
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
                        "Cannot store file with relative path outside current directory "
                                + filename);
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
            log.error("Store file error: File already exist", e);
            
            return folderName + "/" + nameImage;
        } catch (IOException e) {
            throw new StorageException("Failed to store file " + filename, e);
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
                throw new StorageFileNotFoundException(
                        "Could not read file: " + storedFileName);

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
    public InputStreamResource download(String filename) {
        try {
            File file = ResourceUtils.getFile(applicationProperties.getFolderUpload() + "/" + filename);
            
            try {
                byte[] data = FileUtils.readFileToByteArray(file);
                
                InputStream inputStream = new BufferedInputStream(new ByteArrayInputStream(data));
                
                InputStreamResource inputStreamResource = new InputStreamResource(inputStream);
                
                return inputStreamResource;
            } catch (IOException e) {
                log.error("Error: ", e);
            }
        } catch (FileNotFoundException e) {
            log.error("Error: ", e);
        }
        
        return null;
    }

    @Override
    public InputStream downloadExcelTemplateFromResource(String filename) throws IOException {
        return new ClassPathResource(applicationProperties.getTemplate() + "/" + filename).getInputStream();
    }
}
