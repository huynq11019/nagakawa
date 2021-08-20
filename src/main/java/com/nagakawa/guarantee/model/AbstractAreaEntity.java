package com.nagakawa.guarantee.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Base abstract class for entities which will hold definitions for created, last modified by and
 * created, last modified by date.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractAreaEntity extends AbstractAuditingEntity implements Serializable {

	/** The Constant serialVersionUID */
	private static final long serialVersionUID = -3543387943333659061L;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "province_code", referencedColumnName = "province_code")
	@ToString.Exclude
	private Province province;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "district_code", referencedColumnName = "district_code")
	@ToString.Exclude
	private District district;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ward_code", referencedColumnName = "ward_code")
	@ToString.Exclude
	private Ward ward;

	@Column(name = "province_code", insertable = false, updatable = false)
	private String provinceCode;

	@Column(name = "district_code", insertable = false, updatable = false)
	private String districtCode;

	@Column(name = "ward_code", insertable = false, updatable = false)
	private String wardCode;

	@Transient
	private String provinceName;

	@Transient
	private String districtName;

	@Transient
	private String wardName;
}
