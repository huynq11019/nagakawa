package com.nagakawa.guarantee.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "privilege")
public class Privilege implements Serializable {

	private static final long serialVersionUID = 3371839880543933059L;

	@Id
	private Long id;

	@Column
	private String name;

	@Column(length = 255)
	private String description;

}
