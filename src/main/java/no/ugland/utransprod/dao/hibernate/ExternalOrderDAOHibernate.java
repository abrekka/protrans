package no.ugland.utransprod.dao.hibernate;

import java.util.List;

import no.ugland.utransprod.dao.ExternalOrderDAO;
import no.ugland.utransprod.model.ExternalOrder;
import no.ugland.utransprod.model.Order;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 * Implementasjon av DAO mot tabell EXTERNAL_ORDER
 * 
 * @author atle.brekka
 * 
 */
public class ExternalOrderDAOHibernate extends BaseDAOHibernate<ExternalOrder>
		implements ExternalOrderDAO {
	/**
	 * 
	 */
	public ExternalOrderDAOHibernate() {
		super(ExternalOrder.class);
	}

	/**
	 * @see no.ugland.utransprod.dao.ExternalOrderDAO#refreshObject(no.ugland.utransprod.model.ExternalOrder)
	 */
	public void refreshObject(ExternalOrder externalOrder) {
		getHibernateTemplate().load(externalOrder,
				externalOrder.getExternalOrderId());

	}

	/**
	 * @see no.ugland.utransprod.dao.ExternalOrderDAO#findByOrder(no.ugland.utransprod.model.Order)
	 */
	@SuppressWarnings("unchecked")
	public List<ExternalOrder> findByOrder(final Order order) {
		return (List<ExternalOrder>) getHibernateTemplate().execute(
				new HibernateCallback() {

					public Object doInHibernate(Session session)
							throws HibernateException {
						return session.createCriteria(ExternalOrder.class).add(
								Restrictions.eq("order", order)).list();
					}

				});
	}

	/**
	 * @see no.ugland.utransprod.dao.ExternalOrderDAO#lazyLoad(no.ugland.utransprod.model.ExternalOrder,
	 *      no.ugland.utransprod.service.enums.LazyLoadExternalOrderEnum[])
	 */
	/*public void lazyLoad(final ExternalOrder externalOrder,
			final LazyLoadExternalOrderEnum[] enums) {
		if (externalOrder != null && externalOrder.getExternalOrderId() != null) {
			getHibernateTemplate().execute(new HibernateCallback() {

				public Object doInHibernate(Session session)
						throws HibernateException {
					if (!session.contains(externalOrder)) {
						session.load(externalOrder, externalOrder
								.getExternalOrderId());
					}
					Set<?> set;

					for (LazyLoadExternalOrderEnum lazyEnum : enums) {
						switch (lazyEnum) {
						case EXTERNAL_ORDER_LINES:
							set = externalOrder.getExternalOrderLines();
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
