package no.ugland.utransprod.dao.hibernate;

import java.util.List;

import no.ugland.utransprod.dao.SaleStatusOrderReserveVDAO;
import no.ugland.utransprod.model.ProductArea;
import no.ugland.utransprod.model.SaleStatusOrderReserveV;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

public class SaleStatusOrderReserveVDAOHibernate extends
		BaseDAOHibernate<SaleStatusOrderReserveV> implements
		SaleStatusOrderReserveVDAO {

	public SaleStatusOrderReserveVDAOHibernate() {
		super(SaleStatusOrderReserveV.class);
	}

	public SaleStatusOrderReserveV findByProductArea(
			final ProductArea productArea) {
		return (SaleStatusOrderReserveV) getHibernateTemplate().execute(
				new HibernateCallback() {

					public Object doInHibernate(Session session) {
						List<SaleStatusOrderReserveV> list = session
								.createCriteria(SaleStatusOrderReserveV.class)
								.add(
										Restrictions.eq("deptno", Integer.valueOf(productArea
												.getProductAreaNr()))).list();
						return list != null && list.size() == 1 ? list.get(0)
								: SaleStatusOrderReserveV.UNKNOWN;
					}
				});
	}

}
