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
@Table(name = "ex_district")
@org.hibernate.annotations.Table(appliesTo = "ex_district", comment = "Bảng lưu thông tin Quận/Huyện/Thành phố trực thuộc tỉnh")
public class District extends AbstractAuditingEntity implements Serializable {

	private static final long serialVersionUID = -8222046062694892290L;

	@Id
	@Column(name = "district_code", unique = true, nullable = false, length = 5)
	private String districtCode;

	@Column(nullable = false, length = 255)
	private String name;

	@Column(name = "short_name", length = 255)
	private String shortName;
	
	@Column(nullable = false, length = 30)
	private String type;

	@Column(name = "province_code", nullable = false, length = 5)
	private String provinceCode;

	@Column(length = 255)
	private String description;

	@Column(name = "status", nullable = false, columnDefinition = "tinyint(1) default 1 ")
	private int status;

}
