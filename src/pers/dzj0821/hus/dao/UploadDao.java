package pers.dzj0821.hus.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import pers.dzj0821.hus.vo.Upload;

public class UploadDao extends Dao {
	public Upload[] getUpload(int account, int homeworkId) throws ClassNotFoundException, SQLException {
		Connection connection = getConnection();
		PreparedStatement statement = connection.prepareStatement("SELECT * FROM upload WHERE userAccount = ? AND homework_id = ?");
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
}
