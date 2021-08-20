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
@Table(name = "ex_ward")
@org.hibernate.annotations.Table(appliesTo = "ex_ward", comment = "Bảng lưu thông tin Xã/Phường/Thị trấn")
public class Ward extends AbstractAuditingEntity implements Serializable {

	private static final long serialVersionUID = -8705735052600997770L;

	@Id
	@Column(name = "ward_code", unique = true, nullable = false, length = 5)
	private String wardCode;

	@Column(nullable = false, length = 255)
	private String name;

	@Column(name = "short_name", length = 255)
	private String shortName;
	
	@Column(nullable = false, length = 30)
	private String type;

	@Column(name = "district_code", nullable = false, length = 5)
	private String districtCode;

	@Column(length = 255)
	private String description;

	@Column(name = "status", nullable = false, columnDefinition = "tinyint(1) default 1")
	private int status;
}
