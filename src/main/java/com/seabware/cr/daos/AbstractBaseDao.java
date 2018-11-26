package com.seabware.cr.daos;

import java.sql.Connection;
import java.sql.DriverManager;

public class AbstractBaseDao
{
	private Connection connection;

	// -----------------------------------------------------------------------------------------------------------------
	public AbstractBaseDao()
	{
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			this.connection = DriverManager.getConnection("jdbc:mysql://localhost/clashroyale?", "root", "123456");
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	// -----------------------------------------------------------------------------------------------------------------
	public Connection getConnection()
	{
		return connection;
	}
}
