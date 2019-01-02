package pers.dzj0821.hus.vo;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Class {
	private int id;
	private String className;
	
	public Class() {}
	
	public Class(int id, String className) {
		this.id = id;
		this.className = className;
	}
	
	public Class(ResultSet set) throws SQLException {
		this.id = set.getInt("id");
		this.className = set.getString("class_name");
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
}
