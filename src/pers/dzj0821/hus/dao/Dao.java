package pers.dzj0821.hus.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class Dao {
	private final static String DRIVER_NAME = "com.mysql.jdbc.Driver";
	private final static String DATABASE_URL = "jdbc:mysql://localhost:3306/homework_upload?characterEncoding=utf8";
	private final static String DATABASE_USER = "root";
	private final static String DATABASE_PASSWORD = "root";
	
	protected Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName(DRIVER_NAME);
		return DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
	}
	
	/**
	 * 获取此结果集中有多少条记录
	 * @param set 需要查询的结果集
	 * @return 此结果集中的记录数
	 * @throws SQLException
	 */
	protected int getRows(ResultSet set) throws SQLException {
		//记录调用前指针位置
		int currentRow = set.getRow();
		set.last();
		int rows = set.getRow();
		//恢复状态
		set.absolute(currentRow);
		return rows;
	}
}
