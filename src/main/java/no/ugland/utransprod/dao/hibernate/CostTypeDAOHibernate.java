package no.ugland.utransprod.dao.hibernate;

import java.util.List;

import no.ugland.utransprod.dao.CostTypeDAO;
import no.ugland.utransprod.model.CostType;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 * Implmentasjon av DAO for kostnadstyper
 * 
 * @author atle.brekka
 * 
 */
public class CostTypeDAOHibernate extends BaseDAOHibernate<CostType> implements
		CostTypeDAO {
	/**
	 * 
	 */
	public CostTypeDAOHibernate() {
		super(CostType.class);
	}

	/**
	 * @see no.ugland.utransprod.dao.CostTypeDAO#findAll()
	 */
	@SuppressWarnings("unchecked")
	public List<CostType> findAll() {
		return getHibernateTemplate().find("from CostType");
	}

	/**
	 * @see no.ugland.utransprod.dao.CostTypeDAO#refreshObject(no.ugland.utransprod.model.CostType)
	 */
	public void refreshObject(CostType costType) {
		getHibernateTemplate().load(costType, costType.getCostTypeId());

	}

	/**
	 * @see no.ugland.utransprod.dao.CostTypeDAO#findByName(java.lang.String)
	 */
	public CostType findByName(final String name) {
		return (CostType) getHibernateTemplate().execute(
				new HibernateCallback() {

					@SuppressWarnings("unchecked")
					public Object doInHibernate(Session session)
							throws HibernateException {
						List<CostType> list = session.createCriteria(
								CostType.class).add(
								Restrictions.ilike("costTypeName", name))
								.list();

						if (list != null && list.size() == 1) {
							return list.get(0);
						}
						return null;
					}

				});
	}

}
