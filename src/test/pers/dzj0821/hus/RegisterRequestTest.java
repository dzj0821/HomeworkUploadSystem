package test.pers.dzj0821.hus;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import pers.dzj0821.hus.servlet.RegisterRequest;

class RegisterRequestTest {

	@Test
	void testVerifyWhenAllRight() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		boolean bool = testVerify(null, "2016210172", "123123", "测试", "1");
		Assert.assertEquals("验证失败", bool, true);
	}
	
	@Test
	void testVerifyWhenUserAlreadyLogin() throws NoSuchMethodException, SecurityException, IllegalAccessException,
	IllegalArgumentException, InvocationTargetException  {
		boolean bool = testVerify("2016210172", "2016210172", "123123", "测试", "1");
		Assert.assertEquals("验证失败", bool, false);
	}
	
	@Test
	void testVerifyWhenUserNameFormatIsWrong() throws NoSuchMethodException, SecurityException, IllegalAccessException,
	IllegalArgumentException, InvocationTargetException  {
		boolean bool = testVerify("2016210172", "2016210172", "123123", "121", "1");
		Assert.assertEquals("验证失败", bool, false);
	}
	
	private boolean testVerify(String sessionAccount, String account, String password, String userName, String classId) throws NoSuchMethodException, SecurityException, IllegalAccessException,
	IllegalArgumentException, InvocationTargetException  {
		RegisterRequest registerRequest = new RegisterRequest();
		Method verify = registerRequest.getClass().getDeclaredMethod("verify", String.class, String.class, String.class,
				String.class, String.class);
		verify.setAccessible(true);
		boolean bool = (Boolean) verify.invoke(registerRequest, sessionAccount, account, password, userName, classId);
		return bool;
	}
	
	

}
