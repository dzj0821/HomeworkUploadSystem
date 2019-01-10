package pers.dzj0821.hus.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import pers.dzj0821.hus.util.Util;
import pers.dzj0821.hus.vo.User;

public class UserDao extends Dao {
	private final static String SALT = "SALT";
	/**
	 * 登录的查询操作
	 * @param account 用于登录的用户名
	 * @param password 用于登录的密码
	 * @return 登录是否成功
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public boolean login(int account, String password) throws ClassNotFoundException, SQLException {
		String passwordMD5 = Util.MD5(Util.MD5(password + SALT) + SALT);
		Connection connection = getConnection();
		PreparedStatement statement = connection.prepareStatement("SELECT user_name FROM user WHERE account = ? AND password_md5 = ?");
		statement.setInt(1, account);
		statement.setString(2, passwordMD5);
		ResultSet set = statement.executeQuery();
		boolean result = set.next();
		set.close();
		statement.close();
		connection.close();
		return result;
	}
	
	
	public void regist(String account, String password, String userName, String classId) throws ClassNotFoundException, SQLException {
		String passwordMD5 = Util.MD5(Util.MD5(password + SALT) + SALT);
		Connection connection = getConnection();
		PreparedStatement statement = connection.prepareStatement("INSERT INTO user(account, password_md5, user_name, class_id) VALUES(?, ?, ?, ?)");
		statement.setString(1, account);
		statement.setString(2, passwordMD5);
		statement.setString(3, userName);
		statement.setString(4, classId);
		statement.executeUpdate();
		statement.close();
		connection.close();
		return;
	}
	/**
	 * 根据帐号查找用户
	 * @param account 需要查找的帐号
	 * @return 用户对象，如果未找到返回NULL
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public User getUser(int account) throws ClassNotFoundException, SQLException {
		Connection connection = getConnection();
		PreparedStatement statement = connection.prepareStatement("SELECT * FROM user WHERE account = ?");
		statement.setInt(1, account);
		ResultSet set = statement.executeQuery();
		User user = null;
		if(set.next()) {
			user = new User(set);
		}
		set.close();
		statement.close();
		connection.close();
		return user;
	}
	
	/**
	 * 查询指定班级的用户
	 * @param classId
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public User[] getUsers(int classId) throws ClassNotFoundException, SQLException {
		Connection connection = getConnection();
		PreparedStatement statement = connection.prepareStatement("SELECT * FROM user WHERE class_id = ?");
		statement.setInt(1, classId);
		ResultSet set = statement.executeQuery();
		User[] users = new User[getRows(set)];
		for(int i = 0; i < users.length; i++) {
			set.next();
			users[i] = new User(set);
		}
		set.close();
		statement.close();
		connection.close();
		return users;
	}
}
