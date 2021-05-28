package com.nagakawa.guarantee.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Role.class)
public abstract class Role_ extends com.nagakawa.guarantee.model.AbstractAuditingEntity_ {

	public static volatile SetAttribute<Role, Privilege> privileges;
	public static volatile SingularAttribute<Role, String> name;
	public static volatile SingularAttribute<Role, String> description;
	public static volatile SingularAttribute<Role, Long> id;
	public static volatile SetAttribute<Role, User> users;
	public static volatile SingularAttribute<Role, Integer> status;

	public static final String PRIVILEGES = "privileges";
	public static final String NAME = "name";
	public static final String DESCRIPTION = "description";
	public static final String ID = "id";
	public static final String USERS = "users";
	public static final String STATUS = "status";

}

