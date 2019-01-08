package pers.dzj0821.hus.dao;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.IOUtils;

public abstract class Dao {
	private static String DRIVER_NAME = null;
	private static String DATABASE_URL = null;
	private static String DATABASE_USER = null;
	private static String DATABASE_PASSWORD = null;
	private static boolean inited = false;

	// 从配置文件中加载数据库配置
	public final static void init() {
		if (inited) {
			return;
		}
		// 从WEB-INF中的classes返回到WebContent需要返回2次
		String path = Dao.class.getClassLoader().getResource("..\\..\\hidden\\conf\\sql.json").getPath();
		String driverNameKey = "driver_name";
		String databaseUrlKey = "url";
		String databaseUserKey = "user";
		String databasePasswordKey = "password";
		// 判断文件是否存在
		File file = new File(path);
		if (!file.exists()) {
			Logger.getLogger(Dao.class.getName()).warning("未检测到配置文件，将使用默认配置文件");
			// 不存在的情况下构建路径
			File parentFile = file.getParentFile();
			if (!parentFile.exists()) {
				parentFile.mkdirs();
			}
			//创建配置文件
			FileOutputStream fileOutputStream = null;
			try {
				fileOutputStream = new FileOutputStream(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			if (fileOutputStream != null) {
				// 构建默认json
				JSONObject defaultObject = new JSONObject();
				defaultObject.put(driverNameKey, "com.mysql.jdbc.Driver");
				defaultObject.put(databaseUrlKey,
						"jdbc:mysql://localhost:3306/homework_upload?characterEncoding=utf8&useSSL=false");
				defaultObject.put(databaseUserKey, "root");
				defaultObject.put(databasePasswordKey, "root");
				// 写入配置文件
				BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
				try {
					bufferedWriter.write(defaultObject.toJSONString());
					bufferedWriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		// 防止构建路径失败，再判断一次
		if (file.exists()) {
			JSONObject object = null;
			try {
				object = JSON.parseObject(IOUtils.toString(new FileInputStream(path)));
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (object != null) {
				// 读取配置信息
				DRIVER_NAME = object.getString("driver_name");
				DATABASE_URL = object.getString("url");
				DATABASE_USER = object.getString("user");
				DATABASE_PASSWORD = object.getString("password");
			}
		}
		inited = true;
	}

	protected Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName(DRIVER_NAME);
		return DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
	}

	/**
	 * 获取此结果集中有多少条记录
	 * 
	 * @param set
	 *            需要查询的结果集
	 * @return 此结果集中的记录数
	 * @throws SQLException
	 */
	protected int getRows(ResultSet set) throws SQLException {
		// 记录调用前指针位置
		int currentRow = set.getRow();
		set.last();
		int rows = set.getRow();
		// 恢复状态
		set.absolute(currentRow);
		return rows;
	}
}
