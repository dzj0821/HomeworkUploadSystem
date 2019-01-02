package pers.dzj0821.hus.vo;

import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
	private int account;
	private String passwordMD5;
	private String userName;
	private int classId;
	
	public User() {}
	
	public User(int account, String passwordMD5, String userName, int classId) {
		this.account = account;
		this.passwordMD5 = passwordMD5;
		this.userName = userName;
		this.classId = classId;
	}
	
	public User(ResultSet set) throws SQLException {
		this.account = set.getInt("account");
		this.passwordMD5 = set.getString("password_md5");
		this.userName = set.getString("user_name");
		this.classId = set.getInt("class_id");
	}
	
	public int getAccount() {
		return account;
	}
	public void setAccount(int account) {
		this.account = account;
	}
	public String getPasswordMD5() {
		return passwordMD5;
	}
	public void setPasswordMD5(String passwordMD5) {
		this.passwordMD5 = passwordMD5;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getClassId() {
		return classId;
	}
	public void setClassId(int classId) {
		this.classId = classId;
	}
	
	
}
