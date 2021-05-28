package com.nagakawa.guarantee.model;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedAttributeNode;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.nagakawa.guarantee.util.Constants;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * A user.
 */
@Data
@Entity
@Table(name = "users")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@EqualsAndHashCode(callSuper=false)
@NamedEntityGraph(name = "roles", attributeNodes = @NamedAttributeNode("roles"))
public class User extends AbstractAuditingEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Pattern(regexp = Constants.Regex.USERNAME)
	@Size(min = 1, max = 50)
	@Column(length = 50, unique = true, nullable = false)
	private String username;

	@Size(min = 5, max = 100)
	@Column(name = "password", length = 60, nullable = false)
	private String password;

	@Size(max = 200)
	@Column(name = "fullname", length = 200)
	private String fullname;

	@Column(name = "date_of_birth")
	private Instant date_of_birth;

	@Size(max = 254)
	@Column(length = 254, unique = true)
	private String email;

	@Size(max = 50)
	@Column(name = "phone_number", length = 50)
	private String phoneNumber;

	@Column(name = "status", length = 1, columnDefinition = "integer default 1")
	private int status;

	@Column(name = "description", length = 255)
	private String description;

	@Column(name = "role_updatable", length = 1, columnDefinition = "integer default 1")
	private Integer roleUpdatable;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
	@BatchSize(size = 20)
	private Set<Role> roles = new HashSet<>();
}
