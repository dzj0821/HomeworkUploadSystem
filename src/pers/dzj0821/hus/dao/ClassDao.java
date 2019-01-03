package pers.dzj0821.hus.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import pers.dzj0821.hus.vo.Class;

public class ClassDao extends Dao {
	
	/**
	 * 获取所有的班级
	 * @return 存储所有班级信息的数组，不会返回NULL
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public Class[] getAllClass() throws ClassNotFoundException, SQLException {
		Connection connection = getConnection();
		Statement statement = connection.createStatement();
		ResultSet set = statement.executeQuery("SELECT * FROM class");
		Class[] classes = new Class[getRows(set)];
		for(int i = 0; i < classes.length; i++) {
			set.next();
			classes[i] = new Class(set);
		}
		set.close();
		statement.close();
		connection.close();
		return classes;
	}

}
