package no.ugland.utransprod.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import no.ugland.utransprod.dao.PostShipmentCountVDAO;
import no.ugland.utransprod.model.PostShipmentCountSum;
import no.ugland.utransprod.model.PostShipmentCountV;
import no.ugland.utransprod.util.excel.ExcelReportSetting;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 * Implementasjon av DAO for view POST_SHIPMENT_COUNT_V
 * 
 * @author atle.brekka
 * 
 */
public class PostShipmentCountVDAOHibernate extends BaseDAOHibernate<PostShipmentCountV> implements PostShipmentCountVDAO {
    /**
     * Konstruktør
     */
    public PostShipmentCountVDAOHibernate() {
	super(PostShipmentCountV.class);
    }

    /**
     * @see no.ugland.utransprod.dao.PostShipmentCountVDAO#findByParams(no.ugland.utransprod.util.excel.ExcelReportSetting)
     */
    @SuppressWarnings("unchecked")
    public List<PostShipmentCountSum> findByParams(final ExcelReportSetting params) {
	return (List<PostShipmentCountSum>) getHibernateTemplate().execute(new HibernateCallback() {

	    public Object doInHibernate(Session session) throws HibernateException {
		List<PostShipmentCountV> list = getCountPostShipment(params, session);

		return setFunctionCount(list);
	    }

	});
    }

    List<PostShipmentCountSum> setFunctionCount(List<PostShipmentCountV> list) {
	List<PostShipmentCountSum> countSumList = new ArrayList<PostShipmentCountSum>();
	PostShipmentCountSum countSum = null;
	PostShipmentCountSum.clearColumnNames();
	for (PostShipmentCountV element : list) {
	    if (countSum == null || !element.getSentWeek().equals(countSum.getWeek())) {
		countSum = new PostShipmentCountSum(element.getSentYear(), element.getSentWeek());
		countSumList.add(countSum);
	    }
	    countSum.setFunctionCount(element.getJobFunctionName(), element.getPostShipmentCount());

	}
	return countSumList;
    }

    @SuppressWarnings("unchecked")
    List<PostShipmentCountV> getCountPostShipment(final ExcelReportSetting params, Session session) {
	List<PostShipmentCountV> list = session.createCriteria(PostShipmentCountV.class)
		.add(Restrictions.eq("postShipmentCountVPK.sentYear", params.getYear()))
		.add(Restrictions.between("postShipmentCountVPK.sentWeek", params.getWeekFrom(), params.getWeekTo()))
		.add(Restrictions.eq("postShipmentCountVPK.productArea", params.getProductAreaName()))
		.addOrder(Order.asc("postShipmentCountVPK.sentWeek")).list();
	return list;
    }

}
