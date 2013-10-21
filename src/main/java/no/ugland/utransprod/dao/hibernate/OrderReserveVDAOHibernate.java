package no.ugland.utransprod.dao.hibernate;

import java.util.List;

import no.ugland.utransprod.dao.OrderReserveVDAO;
import no.ugland.utransprod.model.OrderReserveV;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 * Implementasjon av DAO mot view ORDER_RESERVE_V
 * 
 * @author atle.brekka
 * 
 */
public class OrderReserveVDAOHibernate extends BaseDAOHibernate<OrderReserveV>
		implements OrderReserveVDAO {
	/**
	 * Konstruktør
	 */
	public OrderReserveVDAOHibernate() {
		super(OrderReserveV.class);
	}

	/**
	 * @see no.ugland.utransprod.dao.OrderReserveVDAO#findByProductArea(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<OrderReserveV> findByProductArea(final String productArea) {
		return (List<OrderReserveV>) getHibernateTemplate().execute(
				new HibernateCallback() {

					public Object doInHibernate(Session session)
							throws HibernateException {

						return session.createCriteria(OrderReserveV.class).add(
								Restrictions.eq("orderReserveVPK.productArea",
										productArea)).list();
					}

				});
	}

}
