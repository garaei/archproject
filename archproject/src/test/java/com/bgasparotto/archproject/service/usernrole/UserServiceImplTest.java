package com.bgasparotto.archproject.service.usernrole;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import com.bgasparotto.archproject.model.User;
import com.bgasparotto.archproject.persistence.dao.UserDao;
import com.bgasparotto.archproject.persistence.exception.GeneralPersistenceException;
import com.bgasparotto.archproject.service.AbstractServiceTest;
import com.bgasparotto.archproject.service.exception.ServiceException;

/**
 * General unit service tests for {@link UserServiceImpl}.
 * 
 * @author Bruno Gasparotto
 *
 */
public class UserServiceImplTest
		extends AbstractServiceTest<User, UserServiceImpl, UserDao> {

	/**
	 * Constructor.
	 */
	public UserServiceImplTest() {
		super(UserServiceImpl.class, UserDao.class);
	}

	@Override
	protected User getExpectedEntity() {
		return TestingUserFactory.newUserInstance();
	}

	@Override
	protected int getExpectedListSize() {
		return 8;
	}
	
	@Test
	public void shouldFindExistingUsername() throws Exception {
		String username = "someuser";
		Mockito.when(daoMock.findByUsername(username))
				.thenReturn(getExpectedEntity());

		User user = service.findByUsername(username);
		Assert.assertNotNull(user);
	}
	
	@Test
	public void shouldReturnNullWhenUsernameDoesntExist() throws Exception {
		String username = "nonexistent";
		Mockito.when(daoMock.findByUsername(username)).thenReturn(null);

		User user = service.findByUsername(username);
		Assert.assertNull(user);
	}
	
	@Test
	public void shouldReturnNullWhenPassesNullUsername() throws Exception {
		Mockito.when(daoMock.findByUsername(null)).thenReturn(null);
		
		User user = service.findByUsername(null);
		Assert.assertNull(user);
	}
	
	@Test
	public void shouldReturnNullWhenPassesEmptyUsername() throws Exception {
		Mockito.when(daoMock.findByUsername("")).thenReturn(null);
		
		User user = service.findByUsername("");
		Assert.assertNull(user);
	}
	
	@Test(expected = ServiceException.class)
	public void shouldThrowExceptionOnUsernamePersistenceError()
			throws Exception {
		
		Mockito.when(daoMock.findByUsername(Mockito.anyString()))
				.thenThrow(new GeneralPersistenceException());

		service.findByUsername("anything");
	}
	
	@Test
	public void shouldFindExistingEmail() throws Exception {
		String email = "someuser@gmail.com";
		Mockito.when(daoMock.findByEmail(email))
				.thenReturn(getExpectedEntity());

		User user = service.findByEmail(email);
		Assert.assertNotNull(user);
	}
	
	@Test
	public void shouldReturnNullWhenEmailDoesntExist() throws Exception {
		String email = "nonexistent@gmail.com";
		Mockito.when(daoMock.findByEmail(email)).thenReturn(null);

		User user = service.findByEmail(email);
		Assert.assertNull(user);
	}
	
	@Test
	public void shouldReturnNullWhenPassesNullEmail() throws Exception {
		Mockito.when(daoMock.findByEmail(null)).thenReturn(null);
		
		User user = service.findByEmail(null);
		Assert.assertNull(user);
	}
	
	@Test
	public void shouldReturnNullWhenPassesEmptyEmail() throws Exception {
		Mockito.when(daoMock.findByEmail("")).thenReturn(null);
		
		User user = service.findByEmail("");
		Assert.assertNull(user);
	}
	
	@Test(expected = ServiceException.class)
	public void shouldThrowExceptionOnEmailPersistenceError()
			throws Exception {

		Mockito.when(daoMock.findByEmail(Mockito.anyString()))
				.thenThrow(new GeneralPersistenceException());

		service.findByEmail("anything");
	}
}