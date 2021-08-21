// Generated with g9.

package com.nagakawa.guarantee.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "ex_content_template")
@org.hibernate.annotations.Table(appliesTo = "ex_content_template", comment = "Bảng lưu mẫu SMS, Email")
public class ContentTemplate extends AbstractAuditingEntity implements Serializable {

	private static final long serialVersionUID = -1947569699290688383L;

	@Id
	@Column(name = "template_code", unique = true, nullable = false, columnDefinition = "varchar(30) COMMENT 'Mã template'")
	private String templateCode;

	@Column(nullable = false, columnDefinition = "varchar(255) COMMENT 'Tên template'")
	private String name;
	
	@Column(columnDefinition = "varchar(255) COMMENT 'Tiêu đề'")
	private String title;
	
	@Column(columnDefinition = "varchar(1000) COMMENT 'Tên template'")
	private String template;

	@Column(length = 255)
	private String description;
	
	@Column(name = "status", nullable = false, columnDefinition = "tinyint(1) default 1 "
			+ "COMMENT 'Trạng thái, -1/0/1, Xóa/Không hoạt động/Hoạt động'")
	private int status;
}
