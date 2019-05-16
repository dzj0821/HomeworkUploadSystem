package test.pers.dzj0821.hus;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.Date;

import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;

import pers.dzj0821.hus.dao.HomeworkDao;
import pers.dzj0821.hus.vo.Homework;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class TestHomeworkDao {
	
	private HomeworkDao dao = new HomeworkDao();
	private static final String HOMEWORK_NAME = "test";
	private static final String TEXT = "this is text";
	private static final String SUFFIX = "doc";
	private static final int PUBLISH_ACCOUNT = 2016210172;
	private static final int CLASS_ID = 1;
	private static final Date DEADLINE = new Date(System.currentTimeMillis());
	
	public TestHomeworkDao() {
		HomeworkDao.init("test/pers/dzj0821/hus/sql.json");
	}
	//发布作业功能测试
	@Test
	//作业名为空
	void test001() {
		try {
			dao.insert(null, TEXT, SUFFIX, PUBLISH_ACCOUNT, CLASS_ID, DEADLINE);
			fail();
		} catch (ClassNotFoundException | SQLException e) {
			assertTrue(e instanceof SQLException);
		}
	}
	@Test
	//发布者账号不存在
	void test002() {
		try {
			dao.insert(HOMEWORK_NAME, TEXT, SUFFIX, 0, CLASS_ID, DEADLINE);
			fail();
		} catch (ClassNotFoundException | SQLException e) {
			assertTrue(e instanceof SQLException);
		}
	}
	@Test
	//班级ID不存在
	void test003() {
		try {
			dao.insert(HOMEWORK_NAME, TEXT, SUFFIX, PUBLISH_ACCOUNT, 0, DEADLINE);
			fail();
		} catch (ClassNotFoundException | SQLException e) {
			assertTrue(e instanceof SQLException);
		}
	}
	@Test
	//没有正文
	void test004() {
		try {
			assertTrue(dao.insert(HOMEWORK_NAME, null, SUFFIX, PUBLISH_ACCOUNT, CLASS_ID, DEADLINE));
			dao.delete(dao.getHomeworks(CLASS_ID)[0].getId());
		} catch (ClassNotFoundException | SQLException | IndexOutOfBoundsException e) {
			fail(e);
		}
	}
	@Test
	//没有后缀限制
	void test005() {
		try {
			dao.insert(HOMEWORK_NAME, TEXT, null, PUBLISH_ACCOUNT, CLASS_ID, DEADLINE);
			fail();
		} catch (ClassNotFoundException | SQLException | IndexOutOfBoundsException e) {
			assertTrue(e instanceof SQLException);
		}
	}
	@Test
	//没有日期限制
	void test006() {
		try {
			assertTrue(dao.insert(HOMEWORK_NAME, TEXT, SUFFIX, PUBLISH_ACCOUNT, CLASS_ID, null));
			dao.delete(dao.getHomeworks(CLASS_ID)[0].getId());
		} catch (ClassNotFoundException | SQLException | IndexOutOfBoundsException e) {
			fail(e);
		}
	}
	@Test
	//正常发布
	void test007() {
		try {
			assertTrue(dao.insert(HOMEWORK_NAME, TEXT, SUFFIX, PUBLISH_ACCOUNT, CLASS_ID, DEADLINE));
		} catch (ClassNotFoundException | SQLException e) {
			fail(e);
		}
	}
	//查询作业测试
	@Test
	//查询不存在的id
	void test008() {
		try {
			assertNull(dao.getHomework(999));
		} catch (ClassNotFoundException | SQLException | IndexOutOfBoundsException e) {
			fail(e);
		}
	}
	@Test
	//普通查询
	void test009() {
		try {
			Homework homework = dao.getHomework(dao.getHomeworks(CLASS_ID)[0].getId());
			assert homework.getHomeworkName().equals(HOMEWORK_NAME);
			assert TEXT.equals(homework.getText());
			assert SUFFIX.equals(homework.getSuffix());
			assert PUBLISH_ACCOUNT == homework.getPublisherAccount();
			assert CLASS_ID == homework.getClassId();
			assert DEADLINE.getTime() / 1000 == homework.getDeadline().getTime() / 1000;
		} catch (ClassNotFoundException | SQLException | IndexOutOfBoundsException e) {
			fail(e);
		}
	}
	//删除作业测试
	@Test
	//作业ID不存在
	void test010() {
		try {
			dao.delete(999);
		} catch (ClassNotFoundException | SQLException | IndexOutOfBoundsException e) {
			fail(e);
		}
	}
	@Test
	//删除作业
	void test011() {
		try {
			assertTrue(dao.delete(dao.getHomeworks(CLASS_ID)[0].getId()));
		} catch (ClassNotFoundException | SQLException | IndexOutOfBoundsException e) {
			fail(e);
		}
	}
}
