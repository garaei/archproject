package com.bgasparotto.archproject.persistence.dao;

import com.bgasparotto.archproject.model.User;
import com.bgasparotto.archproject.persistence.exception.GeneralPersistenceException;

/**
 * {@code DAO - Data Access Object} of {@link User} entity.
 * 
 * @author Bruno Gasparotto
 * 
 * @see GenericDao
 *
 */
public interface UserDao extends GenericDao<User> {

	/**
	 * Find an user by its {@code username}.
	 * 
	 * @param username
	 *            The {@code username} of the user to find
	 * @return User corresponding to the given {@code username}, or {@code null}
	 *         if the is no User on database corresponding to the given
	 *         {@code username}
	 * @throws GeneralPersistenceException
	 *             if an error occurs when trying to find an user by its
	 *             {@code username}
	 */
	User findByUsername(String username) throws GeneralPersistenceException;

	/**
	 * Find an user by its {@code email}.
	 * 
	 * @param email
	 *            The {@code email} of the user to find
	 * @return User corresponding to the given {@code email}, or {@code null} if
	 *         the is no User on database corresponding to the given
	 *         {@code email}
	 * @throws GeneralPersistenceException
	 *             if an error occurs when trying to find an user by its
	 *             {@code email}
	 */
	User findByEmail(String email) throws GeneralPersistenceException;
}