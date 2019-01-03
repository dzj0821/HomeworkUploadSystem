package pers.dzj0821.hus.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import pers.dzj0821.hus.vo.Upload;

public class UploadDao extends Dao {
	public Upload[] getUpload(int account, int homeworkId) throws ClassNotFoundException, SQLException {
		Connection connection = getConnection();
		PreparedStatement statement = connection.prepareStatement("SELECT * FROM upload WHERE user_account = ? AND homework_id = ?");
		statement.setInt(1, account);
		statement.setInt(2, homeworkId);
		ResultSet set = statement.executeQuery();
		Upload[] uploads = new Upload[getRows(set)];
		for(int i = 0; i < uploads.length; i++) {
			set.next();
			uploads[i] = new Upload(set);
		}
		set.close();
		statement.close();
		connection.close();
		return uploads;
	}
	
	public boolean insert(int account, int homeworkId, String path) throws ClassNotFoundException, SQLException {
		Connection connection = getConnection();
		PreparedStatement statement = connection.prepareStatement("INSERT INTO upload(user_account, homework_id, path, update_time) VALUES(?, ?, ?, NOW())");
		statement.setInt(1, account);
		statement.setInt(2, homeworkId);
		statement.setString(3, path);
		statement.executeUpdate();
		statement.close();
		connection.close();
		return true;
	}
	
	public boolean delete(int id) throws ClassNotFoundException, SQLException {
		Connection connection = getConnection();
		PreparedStatement statement = connection.prepareStatement("DELETE FROM upload WHERE id = ?");
		statement.setInt(1, id);
		statement.executeUpdate();
		statement.close();
		connection.close();
		return true;
	}
}
