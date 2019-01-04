package pers.dzj0821.hus.vo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Homework {
	private int id;
	private String homeworkName;
	private String text;
	private String suffix;
	private int publisherAccount;
	private int classId;
	private Date deadline;
	
	public Homework() {}
	
	public Homework(int id, String homeworkName, String text, String suffix, int publisherAccount, int classId, Date deadline) {
		this.id = id;
		this.homeworkName = homeworkName;
		this.text = text;
		this.suffix = suffix;
		this.publisherAccount = publisherAccount;
		this.classId = classId;
		this.deadline = deadline;
	}
	
	public Homework(ResultSet set) throws SQLException {
		this.id = set.getInt("id");
		this.homeworkName = set.getString("homework_name");
		this.text = set.getString("text");
		this.suffix = set.getString("suffix");
		this.publisherAccount = set.getInt("publisher_account");
		this.classId = set.getInt("class_id");
		this.deadline = set.getTimestamp("deadline");
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getHomeworkName() {
		return homeworkName;
	}
	public void setHomeworkName(String homeworkName) {
		this.homeworkName = homeworkName;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getSuffix() {
		return suffix;
	}
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	public int getPublisherAccount() {
		return publisherAccount;
	}
	public void setPublisherAccount(int publisherAccount) {
		this.publisherAccount = publisherAccount;
	}
	public int getClassId() {
		return classId;
	}
	public void setClassId(int classId) {
		this.classId = classId;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}
	
	
}
