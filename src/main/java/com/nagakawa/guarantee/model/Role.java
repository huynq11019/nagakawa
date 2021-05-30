package com.nagakawa.guarantee.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "role")
public class Role extends AbstractAuditingEntity implements Serializable {

	private static final long serialVersionUID = -1944764877607886017L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String name;
//	
//	@ManyToMany(mappedBy = "roles")
//	private Set<User> users = new HashSet<>();

	@Column(name = "status", length = 1, columnDefinition = "integer default 1")
	private int status;

	@Column(name = "description", length = 255)
	private String description;
	
	@ManyToMany
	@JoinTable(name = "roles_privileges", joinColumns = {
			@JoinColumn(name = "role_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "privilege_id", referencedColumnName = "id") })
	private Set<Privilege> privileges = new HashSet<>();
}
