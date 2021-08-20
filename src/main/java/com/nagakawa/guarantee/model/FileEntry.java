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
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author LinhLH
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "file_entry")
@org.hibernate.annotations.Table(appliesTo = "file_entry", comment = "Bảng lưu thông tin file upload lên hệ thông")
public class FileEntry extends AbstractAuditingEntity implements Serializable {

	private static final long serialVersionUID = 2088438092039673359L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(length = 255)
	private String name;

	@Column(name = "original_name", length = 255)
	private String originalName;

	@Column(name = "content_type", length = 255)
	private String contentType;

	@Column(name = "class_name", length = 100)
	private String className;

	@Column(name = "class_pk", length = 255)
	private Long classPk;

	@Column(length = 19)
	private Long size;

	@ManyToOne()
	@JoinColumn(name = "folder_id", referencedColumnName = "id")
	@ToString.Exclude
	private FolderEntry folderEntry;

	@Column(name = "description", length = 255)
	private String description;
}
