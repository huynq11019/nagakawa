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
@Table(name = "folder_entry")
public class FolderEntry extends AbstractAuditingEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 50)
	private String name;
	
	@Column(name = "file_count")
	private Integer fileCount;
	
	@ManyToOne()
    @JoinColumn(name = "parent_folder_id", referencedColumnName = "id")
    private FolderEntry parentFolderEntry;
	
	@Size(max = 500)
	@Column(name = "description", length = 500)
	private String description;
}
