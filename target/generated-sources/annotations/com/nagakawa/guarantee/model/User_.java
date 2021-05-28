package com.nagakawa.guarantee.model;

import java.time.Instant;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(User.class)
public abstract class User_ extends com.nagakawa.guarantee.model.AbstractAuditingEntity_ {

	public static volatile SingularAttribute<User, String> password;
	public static volatile SingularAttribute<User, String> phoneNumber;
	public static volatile SingularAttribute<User, Instant> date_of_birth;
	public static volatile SingularAttribute<User, Integer> roleUpdatable;
	public static volatile SetAttribute<User, Role> roles;
	public static volatile SingularAttribute<User, String> description;
	public static volatile SingularAttribute<User, Long> id;
	public static volatile SingularAttribute<User, String> fullname;
	public static volatile SingularAttribute<User, String> email;
	public static volatile SingularAttribute<User, String> username;
	public static volatile SingularAttribute<User, Integer> status;

	public static final String PASSWORD = "password";
	public static final String PHONE_NUMBER = "phoneNumber";
	public static final String DATE_OF_BIRTH = "date_of_birth";
	public static final String ROLE_UPDATABLE = "roleUpdatable";
	public static final String ROLES = "roles";
	public static final String DESCRIPTION = "description";
	public static final String ID = "id";
	public static final String FULLNAME = "fullname";
	public static final String EMAIL = "email";
	public static final String USERNAME = "username";
	public static final String STATUS = "status";

}

