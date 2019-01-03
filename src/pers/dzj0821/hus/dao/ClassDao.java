package pers.dzj0821.hus.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import pers.dzj0821.hus.util.Util;
import pers.dzj0821.hus.vo.Class;

public class ClassDao extends Dao {
	
	public Class[] getAllClass() throws ClassNotFoundException, SQLException {
		
		Connection connection = getConnection();
		PreparedStatement statement = connection.prepareStatement("SELECT id, class_name FROM class");
		ResultSet set = statement.executeQuery();
		
		Class[] classes = new Class[10];
		int i=0;
		if(set.next()) {
			classes[i] = new Class(set);
			i++;
		}
		set.close();
		statement.close();
		connection.close();
		return classes;
	}

}
