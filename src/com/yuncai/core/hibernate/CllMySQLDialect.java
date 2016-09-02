package com.yuncai.core.hibernate;

import java.sql.Types;

import org.hibernate.Hibernate;
import org.hibernate.dialect.MySQLDialect;

public class CllMySQLDialect extends MySQLDialect {

	public CllMySQLDialect() {
		super();
		registerHibernateType(Types.LONGVARCHAR, Hibernate.TEXT.getName()); 
		registerHibernateType(Types.DATE, Hibernate.TIMESTAMP.getName()); 
		registerHibernateType(-4, Hibernate.BLOB.getName());
	}
}
