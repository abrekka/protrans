package no.ugland.utransprod.dao.hibernate;

import java.util.List;

import no.ugland.utransprod.dao.TakstolProbability90VDAO;
import no.ugland.utransprod.model.TakstolProbability90V;
import no.ugland.utransprod.service.enums.ProductAreaGroupEnum;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

public class TakstolProbability90VDAOHibernate extends
		BaseDAOHibernate<TakstolProbability90V> implements
		TakstolProbability90VDAO {
	/**
	 * Konstruktør
	 */
	public TakstolProbability90VDAOHibernate() {
		super(TakstolProbability90V.class);
	}

	public List<TakstolProbability90V> findAllTakstoler() {
		return (List<TakstolProbability90V>) getHibernateTemplate().execute(
				new HibernateCallback() {

					public Object doInHibernate(Session session) {
						return session
								.createCriteria(TakstolProbability90V.class)
								.add(Restrictions.eq("productAreaNr",
										ProductAreaGroupEnum.TAKSTOL
												.getDeptNo())).list();
					}
				});
	}

}
