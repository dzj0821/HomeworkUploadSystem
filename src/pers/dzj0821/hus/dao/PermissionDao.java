package pers.dzj0821.hus.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import pers.dzj0821.hus.vo.Class;

public class PermissionDao extends Dao {
	/**
	 * 获取对应account管理的班级列表
	 * 
	 * @param account
	 *            需要获取的帐号
	 * @return 存储了可管理班级的数组，不会返回null
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public Class[] getManageClass(String account) throws ClassNotFoundException, SQLException {
		Connection connection = getConnection();
		PreparedStatement statement = connection.prepareStatement(
				"SELECT * FROM class WHERE id IN (SELECT class_id FROM permission WHERE user_account = ?)");
		statement.setString(1, account);
		ResultSet set = statement.executeQuery();
		Class[] classes = new Class[getRows(set)];
		for(int i = 0; i < classes.length; i++) {
			classes[i] = new Class(set);
			set.next();
		}
		set.close();
		statement.close();
		connection.close();
		return classes;
	}
}
