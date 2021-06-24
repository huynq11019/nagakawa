package com.nagakawa.guarantee.model;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Data;

@Data
@Entity
@Table(name = "user_login")
@Builder
public class UserLogin implements Serializable{
	private static final long serialVersionUID = 3192282905748712929L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "ip", length = 75)
	private String ip;
	
	@Column(name = "login_time", length = 7)
	private Instant loginTime;
	
	@Column(name = "success", length = 1)
	private Boolean success;
	
	@Column(name = "username", length = 75)
	private String username;
	
	@Column(name = "description", length = 255)
	private String description;
	
}
