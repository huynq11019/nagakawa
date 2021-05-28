package com.nagakawa.guarantee.model;

import java.time.Instant;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(UserLogin.class)
public abstract class UserLogin_ {

	public static volatile SingularAttribute<UserLogin, Instant> loginTime;
	public static volatile SingularAttribute<UserLogin, Boolean> success;
	public static volatile SingularAttribute<UserLogin, String> ip;
	public static volatile SingularAttribute<UserLogin, Long> id;
	public static volatile SingularAttribute<UserLogin, String> username;

	public static final String LOGIN_TIME = "loginTime";
	public static final String SUCCESS = "success";
	public static final String IP = "ip";
	public static final String ID = "id";
	public static final String USERNAME = "username";

}

