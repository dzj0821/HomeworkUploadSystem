package pers.dzj0821.hus.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import pers.dzj0821.hus.vo.Homework;

public class HomeworkDao extends Dao {
	/**
	 * 获取指定班级的作业列表
	 * @param classId 需要获取作业列表的班级id
	 * @return 存储该班级作业的数组，不会返回NULL
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public Homework[] getHomework(int classId) throws ClassNotFoundException, SQLException {
		Connection connection = getConnection();
		PreparedStatement statement = connection.prepareStatement("SELECT * FROM homework WHERE class_id = ?");
		statement.setInt(1, classId);
		ResultSet set = statement.executeQuery();
		Homework[] homeworks = new Homework[getRows(set)];
		for(int i = 0; i < homeworks.length; i++) {
			set.next();
			homeworks[i] = new Homework(set);
		}
		set.close();
		statement.close();
		connection.close();
		return homeworks;
	}
}
