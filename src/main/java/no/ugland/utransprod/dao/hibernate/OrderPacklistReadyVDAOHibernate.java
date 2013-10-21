package no.ugland.utransprod.dao.hibernate;

import java.util.List;

import no.ugland.utransprod.dao.OrderPacklistReadyVDAO;
import no.ugland.utransprod.model.OrderPacklistReadyV;
import no.ugland.utransprod.util.excel.ExcelReportSetting;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 * Implementasjon av DAO mot view ORDER_PACKLIST_READY_V
 * 
 * @author atle.brekka
 * 
 */
public class OrderPacklistReadyVDAOHibernate extends
		BaseDAOHibernate<OrderPacklistReadyV> implements OrderPacklistReadyVDAO {
	/**
	 * Konstruktør
	 */
	public OrderPacklistReadyVDAOHibernate() {
		super(OrderPacklistReadyV.class);
	}

	/**
	 * @see no.ugland.utransprod.dao.OrderPacklistReadyVDAO#findByParams(no.ugland.utransprod.util.excel.ExcelReportSetting)
	 */
	@SuppressWarnings("unchecked")
	public List<OrderPacklistReadyV> findByParams(
			final ExcelReportSetting params) {
		return (List<OrderPacklistReadyV>) getHibernateTemplate().execute(
				new HibernateCallback() {

					public Object doInHibernate(Session session)
							throws HibernateException {

						return session
								.createQuery(
										"select new OrderPacklistReadyV(orderPacklistReadyV.orderPacklistReadyVPK.packlistYear,"
												+ "orderPacklistReadyV.orderPacklistReadyVPK.packlistWeek,"
												+ "sum(orderPacklistReadyV.orderCount),"
												+ "sum(orderPacklistReadyV.customerCost)) "
												+ "from OrderPacklistReadyV orderPacklistReadyV "
												+ "where orderPacklistReadyV.orderPacklistReadyVPK.productArea=:productArea and " 
												+ "			orderPacklistReadyV.orderPacklistReadyVPK.packlistYear=:year and "
												+ "			orderPacklistReadyV.orderPacklistReadyVPK.packlistWeek between :weekFrom and :weekTo group by orderPacklistReadyV.orderPacklistReadyVPK.packlistYear,orderPacklistReadyV.orderPacklistReadyVPK.packlistWeek")
								.setParameter("year", params.getYear())
								.setParameter("weekFrom", params.getWeekFrom())
								.setParameter("weekTo", params.getWeekTo())
								.setParameter("productArea", params.getProductAreaName())
								.list();
					}

				});
	}

}
