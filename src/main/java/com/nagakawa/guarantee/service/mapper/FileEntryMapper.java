package com.nagakawa.guarantee.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.nagakawa.guarantee.model.FileEntry;
import com.nagakawa.guarantee.model.dto.FileEntryDTO;

@Mapper(componentModel = "spring")
public interface FileEntryMapper extends EntityMapper<FileEntryDTO, FileEntry> {
	FileEntry toEntity(FileEntryDTO fileEntryDTO);

	@Mappings({ @Mapping(source = "folderEntry.id", target = "folderEntryId"),
			@Mapping(source = "folderEntry.name", target = "folderEntryName") })
	FileEntryDTO toDto(FileEntry fileEntry);

	default FileEntry fromId(Long id) {
		if (id == null) {
			return null;
		}

		FileEntry fileEntry = new FileEntry();

		fileEntry.setId(id);

		return fileEntry;
	}
}
