package no.ugland.utransprod.dao.hibernate;

import no.ugland.utransprod.dao.FunctionCategoryDAO;
import no.ugland.utransprod.model.FunctionCategory;

/**
 * Implementasjon av AO for hibernate
 * 
 * @author atle.brekka
 * 
 */
public class FunctionCategoryDAOHibernate extends
		BaseDAOHibernate<FunctionCategory> implements FunctionCategoryDAO {
	/**
	 * Konstruktør
	 */
	public FunctionCategoryDAOHibernate() {
		super(FunctionCategory.class);
	}

	/**
	 * @see no.ugland.utransprod.dao.FunctionCategoryDAO#refreshObject(no.ugland.utransprod.model.FunctionCategory)
	 */
	public void refreshObject(FunctionCategory functionCategory) {
		getHibernateTemplate().load(functionCategory,
				functionCategory.getFunctionCategoryId());

	}

}
