/**
 * Evotek QLNS
 */
package com.nagakawa.guarantee.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author LinhLH
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "file_entry")
public class FileEntry extends AbstractAuditingEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 50)
	private String name;
	
	@Column(length = 255)
	private String originalName;
	
	@Column(length = 255)
	private String contentType;
	
	@Column(length = 100)
	private String className;
	
	@Column
	private Long classPk;
	
	@Column
	private Long size;
	
	@ManyToOne()
    @JoinColumn(name = "folder_id", referencedColumnName = "id")
    private FolderEntry folderEntry;
	
	@Size(max = 500)
	@Column(name = "description", length = 500)
	private String description;
}
