package com.nagakawa.guarantee.model;

import java.io.Serializable;
import java.time.Instant;
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
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.Table;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * A user.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "user_")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@NamedEntityGraph(name = "roles", attributeNodes = @NamedAttributeNode("roles"))
public class User extends AbstractAuditingEntity implements Serializable {

	private static final long serialVersionUID = 7020271006196714571L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(unique = true, nullable = false, length = 75)
	private String username;

	@Column(name = "password", nullable = false, length = 255)
	private String password;

	@Column(name = "fullname", length = 255)
	private String fullname;

	@Column(name = "date_of_birth")
	private Instant dateOfBirth;

	@Column(length = 255, unique = true)
	private String email;

	@Column(name = "phone_number", length = 255)
	private String phoneNumber;

	@Column(name = "status", nullable = false, columnDefinition = "tinyint(1) default 1 ")
	private int status;

	@Column(name = "description", length = 255)
	private String description;

	@Column(name = "role_updatable", nullable = false, columnDefinition = "tinyint(1) default 1 ")
	private boolean roleUpdatable;

	@ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.REFRESH})
	@JoinTable(name = "user_roles", joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
			inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
	@BatchSize(size = 20)
	@Fetch(FetchMode.SELECT)
	@ToString.Exclude
	private List<Role> roles = new ArrayList<>();
}
