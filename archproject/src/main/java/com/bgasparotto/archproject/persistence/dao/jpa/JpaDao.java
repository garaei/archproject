package com.bgasparotto.archproject.persistence.dao.jpa;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;
import javax.persistence.TransactionRequiredException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.Transactional;

import org.slf4j.Logger;

import com.bgasparotto.archproject.infrastructure.interceptor.ExceptionLogging;
import com.bgasparotto.archproject.model.identity.LongIdentifiable;
import com.bgasparotto.archproject.persistence.dao.GenericDao;
import com.bgasparotto.archproject.persistence.exception.GeneralPersistenceException;

/**
 * Abstract {@code DAO} implemented using {@code JPA}.
 * 
 * @author Bruno Gasparotto
 *
 * @param <T>
 *            Type of entity to be persisted by this {@code DAO}
 */
@ExceptionLogging
public abstract class JpaDao<T extends LongIdentifiable>
		implements GenericDao<T> {
	protected EntityManager entityManager;
	protected Class<T> clazz;
	protected Logger logger;

	/**
	 * Constructor.
	 * 
	 * @param entityManager
	 *            The {@code EntityManager} to be used by this {@code DAO}
	 * @param clazz
	 *            The entity class existing on the persistence unit of the given
	 *            {@code EntityManager}
	 * @param logger
	 *            The {@code Logger} to be used by this {@code DAO}
	 * @throws NullPointerException
	 *             If any of the parameters are {@code null}
	 */
	public JpaDao(EntityManager entityManager, Class<T> clazz, Logger logger) {
		this.entityManager = Objects.requireNonNull(entityManager,
				"EntityManager can't be null.");
		this.clazz = Objects.requireNonNull(clazz, "Class type can't be null.");
		this.logger = Objects.requireNonNull(logger, "Logger can't be null.");
	}

	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}

	@Override
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	@Transactional
	public T findById(Long id) {
		if (id == null) {
			return null;
		}

		T type = entityManager.find(clazz, id);
		return type;
	}

	@Override
	@Transactional
	public List<T> findAll() {
		try {
			CriteriaBuilder cBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<T> cQuery = cBuilder.createQuery(clazz);
			cQuery.from(clazz);

			TypedQuery<T> typedQuery = entityManager.createQuery(cQuery);
			List<T> resultList = typedQuery.getResultList();
			return resultList;
		} catch (PersistenceException e) {
			logger.error("Failed to perform a search on database", e);
			return Collections.emptyList();
		}
	}

	@Override
	@Transactional
	public Long persist(T type) throws GeneralPersistenceException {
		if (type == null) {
			throw new GeneralPersistenceException("Can't persist null");
		}

		try {
			entityManager.persist(type);
			Long generatedId = type.getId();
			return generatedId;
		} catch (PersistenceException e) {
			String message = "Failed to persist an entity";
			throw new GeneralPersistenceException(message, e);
		}

	}

	@Override
	@Transactional
	public T merge(T type) throws GeneralPersistenceException {
		if (type == null) {
			throw new GeneralPersistenceException("Can't merge null");
		}

		try {
			T mergedType = entityManager.merge(type);
			return mergedType;
		} catch (IllegalArgumentException | PersistenceException e) {
			String message = "Failed to merge an entity";
			throw new GeneralPersistenceException(message, e);
		}
	}
	
	@Override
	@Transactional
	public T mergeFlush(T type) throws GeneralPersistenceException {
		T t = merge(type);
		entityManager.flush();
		
		return t;
	}

	@Override
	@Transactional
	public void delete(T type) throws GeneralPersistenceException {
		if (type == null) {
			throw new GeneralPersistenceException("Can't delete null");
		}

		try {
			if (entityManager.contains(type)) {
				entityManager.remove(type);
				return;
			}
			Long typeId = type.getId();
			delete(typeId);

		} catch (IllegalArgumentException | TransactionRequiredException e) {
			String message = "Failed to delete an entity";
			throw new GeneralPersistenceException(message, e);
		}
	}

	@Override
	@Transactional
	public void delete(Long id) throws GeneralPersistenceException {
		try {
			T type = entityManager.getReference(clazz, id);
			entityManager.remove(type);
		} catch (IllegalArgumentException | EntityNotFoundException e) {
			String message = "Failed to delete an entity";
			throw new GeneralPersistenceException(message, e);
		}
	}
}