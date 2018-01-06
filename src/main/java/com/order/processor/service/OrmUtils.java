package com.order.processor.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import com.order.processor.util.PropertyUtil;

public class OrmUtils {
	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private PropertyUtil prop = new PropertyUtil();

	public static void main(String[] args) {
		System.out.println(new PropertyUtil().getProperty("DB_HOST_NAME"));
	}

	public Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName(JDBC_DRIVER);
		String USER = prop.getProperty("DB_USER_NAME");
		String PASS = prop.getProperty("DB_PASSWORD");
		String DB_URL = "jdbc:mysql://" + prop.getProperty("DB_HOST_NAME") + ":" + prop.getProperty("DB_PORT") + "/"
				+ prop.getProperty("DB_NAME");
		return DriverManager.getConnection(DB_URL, USER, PASS);
	}

	public Timestamp getCurrentTimeStamp() {
		return new Timestamp(new Date().getTime());
	}

}
