package no.ugland.utransprod.dao.hibernate;

import java.sql.SQLException;
import java.util.List;

import no.ugland.utransprod.dao.TakstolAllVDAO;
import no.ugland.utransprod.model.TakstolAllV;
import no.ugland.utransprod.util.Periode;
import no.ugland.utransprod.util.TakstolInterface;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

public class TakstolAllVDAOHibernate extends BaseDAOHibernate<TakstolAllV>
		implements TakstolAllVDAO {
	public TakstolAllVDAOHibernate() {
		super(TakstolAllV.class);
	}

	public List<TakstolInterface> findProductionByPeriode(final Periode periode) {
		return (List<TakstolInterface>) getHibernateTemplate().execute(
				new HibernateCallback() {

					public Object doInHibernate(final Session session) {
						Query query = session
								.createQuery("select takstolAllV1 from TakstolAllV takstolAllV1 where "
										+ "exists (select 1 from TakstolAllV takstolAllV2 where takstolAllV1.orderId = takstolAllV2.orderId and "
										+ " takstolAllV2.produced between :startDate and :endDate) order by order_nr,default_Article,number_Of_Items desc,produced");
						return query.setParameter("startDate",
								periode.getStartDate()).setParameter("endDate",
								periode.getEndDate()).list();
					}
				});
	}

	public List<TakstolAllV> findAllNotProduced() {
		// TODO Auto-generated method stub
		return (List<TakstolAllV>) getHibernateTemplate().execute(
				new HibernateCallback() {

					public Object doInHibernate(Session session) {
						return session.createCriteria(TakstolAllV.class).add(Restrictions.eq("isDefault", 0)).add(
								Restrictions.isNull("produced")).add(
								Restrictions.eq("sent", 0)).add(Restrictions.eq("probability", 100)).list();
					}
				});
	}

}
