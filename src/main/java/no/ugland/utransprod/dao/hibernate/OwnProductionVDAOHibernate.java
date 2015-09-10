package no.ugland.utransprod.dao.hibernate;

import java.util.List;

import no.ugland.utransprod.dao.OwnProductionVDAO;
import no.ugland.utransprod.model.OwnProductionV;
import no.ugland.utransprod.util.excel.ExcelReportSetting;
import no.ugland.utransprod.util.excel.ExcelReportSettingOwnProduction;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 * Implementasjon av DAO mot view OWN_PRODUCTION_V
 * 
 * @author atle.brekka
 */
public class OwnProductionVDAOHibernate extends BaseDAOHibernate<OwnProductionV> implements OwnProductionVDAO {
    /**
     * Konstruktør
     */
    public OwnProductionVDAOHibernate() {
	super(OwnProductionV.class);
    }

    /**
     * @see no.ugland.utransprod.dao.OwnProductionVDAO#findByParams(no.ugland.utransprod.util.excel.ExcelReportSetting)
     */
    @SuppressWarnings("unchecked")
    public List<OwnProductionV> findByParams(final ExcelReportSetting params) {
	return (List) getHibernateTemplate().execute(new HibernateCallback() {

	    public Object doInHibernate(Session session) throws HibernateException {
		Criteria criteria = session.createCriteria(OwnProductionV.class).add(Restrictions.eq("orderReadyYear", params.getYear()))
			.add(Restrictions.eq("orderReadyWeek", params.getWeekFrom()));

		String productAreaGroupName = ((ExcelReportSettingOwnProduction) params).getProductAreaGroupName();
		if (!productAreaGroupName.equalsIgnoreCase("Alle")) {
		    criteria.add(Restrictions.eq("productAreaGroupName", productAreaGroupName));
		}
		criteria.addOrder(Order.desc("productArea")).addOrder(Order.asc("orderReadyDay"));
		return criteria.list();
	    }

	});
    }

    /**
     * @see no.ugland.utransprod.dao.OwnProductionVDAO#findPacklistReady(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public List<OwnProductionV> findPacklistReady(final String productAreaGroupName) {
	return (List) getHibernateTemplate().execute(new HibernateCallback() {

	    public Object doInHibernate(Session session) throws HibernateException {
		return session.createCriteria(OwnProductionV.class).add(Restrictions.isNull("sent")).add(Restrictions.isNotNull("packlistReady"))
			.add(Restrictions.isNull("orderReadyDay")).add(Restrictions.eq("productAreaGroupName", productAreaGroupName))
			.addOrder(Order.asc("productArea")).addOrder(Order.asc("transportYear")).addOrder(Order.asc("transportWeek")).list();
	    }

	});
    }

    /**
     * @see no.ugland.utransprod.dao.OwnProductionVDAO#findPacklistNotReady(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public List<OwnProductionV> findPacklistNotReady(final String productAreaName, final String productAreaGroupName) {
	return (List) getHibernateTemplate().execute(new HibernateCallback() {

	    public Object doInHibernate(Session session) throws HibernateException {
		Criteria criteria = session.createCriteria(OwnProductionV.class).add(Restrictions.isNull("sent"))
			.add(Restrictions.isNull("packlistReady")).add(Restrictions.isNull("orderReadyDay"));
		if (productAreaName != null) {
		    criteria.add(Restrictions.eq("productArea", productAreaName));
		}
		if (productAreaGroupName != null) {
		    criteria.add(Restrictions.eq("productAreaGroupName", productAreaGroupName));
		}
		criteria.addOrder(Order.asc("productArea")).addOrder(Order.asc("transportYear")).addOrder(Order.asc("transportWeek")).list();
		return criteria.list();
	    }

	});
    }

}
