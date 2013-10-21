package no.ugland.utransprod.dao.hibernate;

import java.util.List;

import no.ugland.utransprod.dao.CostUnitDAO;
import no.ugland.utransprod.model.CostUnit;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 * Implementasjon av DAO for kostnadsenhet for hibernate
 * 
 * @author atle.brekka
 * 
 */
public class CostUnitDAOHibernate extends BaseDAOHibernate<CostUnit> implements
		CostUnitDAO {
	/**
	 * 
	 */
	public CostUnitDAOHibernate() {
		super(CostUnit.class);
	}

	/**
	 * @see no.ugland.utransprod.dao.CostUnitDAO#findAll()
	 */
	@SuppressWarnings("unchecked")
	public List<CostUnit> findAll() {
		return getHibernateTemplate().find("from CostUnit");
	}

	/**
	 * @see no.ugland.utransprod.dao.CostUnitDAO#refreshObject(no.ugland.utransprod.model.CostUnit)
	 */
	public void refreshObject(CostUnit costUnit) {
		getHibernateTemplate().load(costUnit, costUnit.getCostUnitId());

	}

	/**
	 * @see no.ugland.utransprod.dao.CostUnitDAO#findByName(java.lang.String)
	 */
	public CostUnit findByName(final String name) {
		return (CostUnit) getHibernateTemplate().execute(
				new HibernateCallback() {

					@SuppressWarnings("unchecked")
					public Object doInHibernate(Session session)
							throws HibernateException {
						List<CostUnit> list = session.createCriteria(
								CostUnit.class).add(
								Restrictions.ilike("costUnitName", name))
								.list();
						if (list != null && list.size() == 1) {
							return list.get(0);
						}
						return null;
					}

				});
	}

	/**
	 * @see no.ugland.utransprod.dao.CostUnitDAO#lazyLoad(no.ugland.utransprod.model.CostUnit,
	 *      no.ugland.utransprod.service.enums.LazyLoadCostUnitEnum[])
	 */
	/*public void lazyLoad(final CostUnit costUnit,
			final LazyLoadCostUnitEnum[] enums) {
		if (costUnit != null && costUnit.getCostUnitId() != null) {
			getHibernateTemplate().execute(new HibernateCallback() {

				public Object doInHibernate(Session session)
						throws HibernateException {
					if (!session.contains(costUnit)) {
						session.load(costUnit, costUnit.getCostUnitId());
					}
					Set<?> set;

					for (LazyLoadCostUnitEnum lazyEnum : enums) {
						switch (lazyEnum) {
						case ORDER_COSTS:
							set = costUnit.getOrderCosts();
							set.iterator();
							break;
						}
					}
					return null;
				}

			});
		}

	}*/

}
