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
@Table(name = "folder_entry")
@org.hibernate.annotations.Table(appliesTo = "folder_entry", comment = "Bảng lưu thông tin Thư mục")
public class FolderEntry extends AbstractAuditingEntity implements Serializable {

	private static final long serialVersionUID = 2843101061149340055L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(length = 255)
	private String name;

	@Column(name = "file_count", length = 5)
	private Integer fileCount;

	@ManyToOne()
	@JoinColumn(name = "parent_folder_id", referencedColumnName = "id")
	@ToString.Exclude
	private FolderEntry parentFolderEntry;

	@Column(name = "description", length = 255)
	private String description;
}
