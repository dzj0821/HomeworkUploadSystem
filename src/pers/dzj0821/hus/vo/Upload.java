package pers.dzj0821.hus.vo;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Upload {
	private int id;
	private int userAccount;
	private int homeworkId;
	private String path;
	private Date updateTime;
	
	public Upload() {}
	
	public Upload(int id, int userAccount, int homeworkId, String path, Date updateTime) {
		this.id = id;
		this.userAccount = userAccount;
		this.homeworkId = homeworkId;
		this.path = path;
		this.updateTime = updateTime;
	}
	
	public Upload(ResultSet set) throws SQLException {
		this.id = set.getInt("id");
		this.userAccount = set.getInt("user_account");
		this.homeworkId = set.getInt("homework_id");
		this.path = set.getString("path");
		this.updateTime = set.getDate("update_time");
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(int userAccount) {
		this.userAccount = userAccount;
	}
	public int getHomeworkId() {
		return homeworkId;
	}
	public void setHomeworkId(int homeworkId) {
		this.homeworkId = homeworkId;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	
}
