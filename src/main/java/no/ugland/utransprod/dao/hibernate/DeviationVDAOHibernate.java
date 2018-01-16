package no.ugland.utransprod.dao.hibernate;

import java.util.Collection;
import java.util.List;

import no.ugland.utransprod.dao.DeviationVDAO;
import no.ugland.utransprod.model.ApplicationUser;
import no.ugland.utransprod.model.Deviation;
import no.ugland.utransprod.model.DeviationV;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.util.excel.ExcelReportSettingDeviation;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

public class DeviationVDAOHibernate extends BaseDAOHibernate<DeviationV> implements DeviationVDAO {
	/**
	 * Konstruktør
	 */
	public DeviationVDAOHibernate() {
		super(DeviationV.class);
	}

	@SuppressWarnings("unchecked")
	public List<DeviationV> findByParams(final ExcelReportSettingDeviation params) {
		return (List<DeviationV>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session session) {
				Criteria criteria = session.createCriteria(DeviationV.class)
						.add(Restrictions.eq("registrationYear", params.getYear()));

				if (params.getWeekFrom() != null && params.getWeekTo() == null) {
					criteria.add(Restrictions.ge("registrationWeek", params.getWeekFrom()));
				} else if (params.getWeekFrom() == null && params.getWeekTo() != null) {
					criteria.add(Restrictions.le("registrationWeek", params.getWeekTo()));
				} else {
					criteria.add(Restrictions.between("registrationWeek", params.getWeekFrom(), params.getWeekTo()));
				}

				if (params.getDeviationFunction() != null) {
					criteria.add(
							Restrictions.eq("deviationFunction", params.getDeviationFunction().getJobFunctionName()));
				}

				if (params.getFunctionCategory() != null) {
					criteria.add(Restrictions.eq("functionCategoryName",
							params.getFunctionCategory().getFunctionCategoryName()));
				}
				if (params.getDeviationStatus() != null) {
					criteria.add(Restrictions.eq("deviationStatusName",
							params.getDeviationStatus().getDeviationStatusName()));
				}

				return criteria.list();
			}

		});

	}

	public Collection findByOrder(final Order order) {
		return (List<Deviation>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session session) throws HibernateException {
				return session.createCriteria(Deviation.class).add(Restrictions.eq("orderNr", order.getOrderNr()))
						.list();
			}

		});
	}

	public Collection findByManager(final ApplicationUser applicationUser) {
		return (List<Deviation>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session session) throws HibernateException {
				return session.createCriteria(Deviation.class).createCriteria("deviationFunction")
						.add(Restrictions.eq("manager", applicationUser)).list();
			}

		});
	}

	public DeviationV findByDeviationId(final Integer deviationId) {
		return (DeviationV) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session session) throws HibernateException {
				List<DeviationV> deviations = session.createCriteria(DeviationV.class)
						.add(Restrictions.eq("deviationId", deviationId)).list();
				return deviations.isEmpty() ? null : deviations.get(0);
			}

		});
	}

}
