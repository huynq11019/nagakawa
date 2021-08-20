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
@Table(name = "ex_province")
@org.hibernate.annotations.Table(appliesTo = "ex_province", comment = "Bảng lưu thông tin tỉnh thành phố trực thuộc TƯ")
public class Province extends AbstractAuditingEntity implements Serializable {

	private static final long serialVersionUID = -1947569699290688383L;

	@Id
	@Column(name = "province_code", unique = true, nullable = false, length = 5)
	private String provinceCode;

	@Column(nullable = false, length = 255)
	private String name;
	
	@Column(name = "short_name", length = 255)
	private String shortName;

	@Column(nullable = false, length = 30)
	private String type;

	@Column(length = 255)
	private String description;

	@Column(length = 255)
	private String coordinate;
	
	@Column(name = "status", nullable = false, columnDefinition = "tinyint(1) default 1 ")
	private int status;
}
