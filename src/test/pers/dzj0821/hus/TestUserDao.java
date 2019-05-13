package test.pers.dzj0821.hus;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;

import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;

import pers.dzj0821.hus.dao.UserDao;
import pers.dzj0821.hus.util.Util;
import pers.dzj0821.hus.vo.User;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class TestUserDao {
	
	private UserDao dao = new UserDao();
	private static final String SALT = "SALT";
	private static final int USERNAME = 2016000000;
	private static final String PASSWORD = "123456";
	private static final String NAME = "张三";
	private static final int CLASS_ID = 1;
	private static final User USER = new User(USERNAME, Util.MD5(Util.MD5(PASSWORD + SALT) + SALT), NAME, CLASS_ID);
	
	public TestUserDao() {
		UserDao.init("test/pers/dzj0821/hus/sql.json");
	}
	//注册功能测试
	@Test
	//用户名为空
	void test001() {
		try {
			dao.regist(null, PASSWORD, NAME, Integer.toString(CLASS_ID));
			fail();
		} catch (ClassNotFoundException | SQLException e) {
			assertTrue(e instanceof SQLException);
		}
	}
	
	@Test
	//姓名为空
	void test002() {
		try {
			dao.regist(Integer.toString(USERNAME), PASSWORD, null, Integer.toString(CLASS_ID));
			fail();
		} catch (ClassNotFoundException | SQLException e) {
			assertTrue(e instanceof SQLException);
		}
	}
	
	@Test
	//班级id为空
	void test003() {
		try {
			dao.regist(Integer.toString(USERNAME), PASSWORD, NAME, null);
			fail();
		} catch (ClassNotFoundException | SQLException e) {
			assertTrue(e instanceof SQLException);
		}
	}
	
	@Test
	//班级id不存在
	void test004() {
		try {
			dao.regist(Integer.toString(USERNAME), PASSWORD, NAME, "999");
			fail();
		} catch (ClassNotFoundException | SQLException e) {
			assertTrue(e instanceof SQLException);
		}
	}
	
	@Test
	//成功
	void test005() {
		try {
			dao.regist(Integer.toString(USERNAME), PASSWORD, NAME, Integer.toString(CLASS_ID));
		} catch (ClassNotFoundException | SQLException e) {
			fail();
		}
	}
	//登录功能测试
	@Test
	//用户不存在
	void test006() {
		try {
			assertFalse(dao.login(123, "123"));
		} catch (ClassNotFoundException | SQLException e) {
			fail(e);
		}
	}
	
	@Test
	//密码错误
	void test007() {
		try {
			assertFalse(dao.login(USERNAME, "123"));
		} catch (ClassNotFoundException | SQLException e) {
			fail(e);
		}
	}
	
	@Test
	//登录成功
	void test008() {
		try {
			assertTrue(dao.login(USERNAME, PASSWORD));
		} catch (ClassNotFoundException | SQLException e) {
			fail(e);
		}
	}
	//查看用户资料测试
	@Test
	//用户不存在
	void test009() {
		try {
			assertNull(dao.getUser(123));
		} catch (ClassNotFoundException | SQLException e) {
			fail(e);
		}
	}
	
	@Test
	//查询成功
	void test010() {
		try {
			assertEquals(dao.getUser(USERNAME), USER);
		} catch (ClassNotFoundException | SQLException e) {
			fail(e);
		}
	}
	
}
