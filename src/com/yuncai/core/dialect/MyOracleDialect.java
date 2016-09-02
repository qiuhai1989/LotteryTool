package com.yuncai.core.dialect;

import java.sql.Types;

import org.hibernate.Hibernate;

@SuppressWarnings("deprecation")
public class MyOracleDialect extends org.hibernate.dialect.OracleDialect{

	public MyOracleDialect() {
		super();
		registerHibernateType(Types.NVARCHAR, Hibernate.STRING.getName());   
	}

	
}
