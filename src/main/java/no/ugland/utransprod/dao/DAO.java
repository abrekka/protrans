package no.ugland.utransprod.dao;

import java.io.Serializable;
import java.util.List;

import no.ugland.utransprod.service.enums.LazyLoadEnum;

/**
 * Data Access Object (DAO) interface. This is an interface used to tag our DAO
 * classes and to provide common methods to all DAOs.
 * 
 * <p>
 * <a href="DAO.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 * @param <E>
 */
public interface DAO<E> {

	/**
	 * Generic method used to get all objects of a particular type. This is the
	 * same as lookup up all rows in a table.
	 * 
	 * @return List of populated objects
	 */
	List<E> getObjects();

	/**
	 * Henter alle objekter med sortering
	 * 
	 * @param orderBy
	 * @return objekter
	 */
	List<E> getObjects(String orderBy);

	/**
	 * Generic method to get an object based on class and identifier. An
	 * ObjectRetrievalFailureException Runtime Exception is thrown if nothing is
	 * found.
	 * 
	 * @param objectId
	 *            the identifier (primary key) of the class
	 * @return a populated object
	 */
	E getObject(Serializable objectId);

	/**
	 * Generic method to save an object - handles both update and insert.
	 * 
	 * @param object
	 *            the object to save
	 */
	void saveObject(E object);

	/**
	 * Generic method to delete an object based on class and id
	 * 
	 * @param objectId
	 *            the identifier (primary key) of the class
	 */
	void removeObject(Serializable objectId);

	/**
	 * Finner objekter ved hjelp av eksempel
	 * 
	 * @param example
	 * @return objekter
	 */
	List<E> findByExample(E example);

	/**
	 * finner objekter ved hjelp av eksempel og bruk av like
	 * 
	 * @param example
	 * @return objekter
	 */
	List<E> findByExampleLike(E example);

	void removeAll();

	void refreshObject(Object object, Serializable id);

	void lazyLoad(Object object, Serializable id, LazyLoadEnum[][] enums);

}