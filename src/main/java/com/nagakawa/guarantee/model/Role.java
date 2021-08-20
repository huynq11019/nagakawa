package com.nagakawa.guarantee.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "role")
public class Role extends AbstractAuditingEntity implements Serializable {

	private static final long serialVersionUID = -1944764877607886017L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(nullable=false, unique = true, length = 255)
    private String name;

	@Column(name = "status", nullable = false, columnDefinition = "tinyint(1) default 1 ")
	private int status;

	@Column(nullable = false, columnDefinition = "tinyint(1) default 0 ")
	private boolean immutable;
	
    @Column(nullable = false, columnDefinition = "tinyint(1) default 1 ")
    private boolean assignable;
    
    @Column(name = "level", nullable = false, columnDefinition = "int(5) default 10")
    private int level;
	
	@Column(name = "description", length = 255)
	private String description;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.REFRESH})
	@JoinTable(name = "roles_privileges", joinColumns = {
			@JoinColumn(name = "role_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "privilege_id", referencedColumnName = "id") })
	@ToString.Exclude
	private List<Privilege> privileges = new ArrayList<>();
}
