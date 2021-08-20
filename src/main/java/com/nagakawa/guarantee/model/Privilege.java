package com.nagakawa.guarantee.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "privilege")
@org.hibernate.annotations.Table(appliesTo = "privilege",
		comment = "Bảng lưu Quyền được gán cho người dùng")
public class Privilege extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 3371839880543933059L;

	@Id
	private long id;

	@Column(length = 255)
	private String name;

	@Column(length = 255)
	private String description;

}
