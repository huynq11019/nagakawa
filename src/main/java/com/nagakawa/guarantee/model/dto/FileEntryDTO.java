/**
 * 
 */
package com.nagakawa.guarantee.model.dto;

import java.io.Serializable;
import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author LinhLH
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileEntryDTO implements Serializable {
	private static final long serialVersionUID = 2105312206084619913L;

	private Long id;

	private String createdBy;

	private Instant createdDate;

	private String lastModifiedBy;

	private Instant lastModifiedDate;

	private String name;

	private String originalName;

	private String contentType;

	private String className;

	private Long classPk;

	private Long folderEntryId;
	
	private Long folderEntryName;
	
	private Long size;

	private String description;
}
