package no.ugland.utransprod.dao.hibernate;

import java.util.List;

import no.ugland.utransprod.dao.NotInvoicedVDAO;
import no.ugland.utransprod.model.NotInvoicedV;
import no.ugland.utransprod.util.excel.ExcelReportSetting;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 * Implementasjon av DAO for view NOT_INVOICED_V
 * 
 * @author atle.brekka
 * 
 */
public class NotInvoicedVDAOHibernate extends BaseDAOHibernate<NotInvoicedV>
		implements NotInvoicedVDAO {
	/**
	 * Konstruktør
	 */
	public NotInvoicedVDAOHibernate() {
		super(NotInvoicedV.class);
	}

	/**
	 * @see no.ugland.utransprod.dao.NotInvoicedVDAO#findByParams(no.ugland.utransprod.util.excel.ExcelReportSetting)
	 */
	@SuppressWarnings("unchecked")
	public List<NotInvoicedV> findByParams(final ExcelReportSetting params) {
		return (List) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session session)
					throws HibernateException {
				return session.createCriteria(NotInvoicedV.class).add(Restrictions.eq("productArea", params.getProductAreaName())).list();
			}

		});
	}

}
