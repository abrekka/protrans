package no.ugland.utransprod.dao.hibernate;

import java.util.List;

import no.ugland.utransprod.dao.DeviationSumJobFunctionVDAO;
import no.ugland.utransprod.model.DeviationSumJobFunctionV;
import no.ugland.utransprod.model.JobFunction;
import no.ugland.utransprod.model.ProductAreaGroup;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

public class DeviationSumJobFunctionVDAOHibernate extends
		BaseDAOHibernate<DeviationSumJobFunctionV> implements
		DeviationSumJobFunctionVDAO {

	public DeviationSumJobFunctionVDAOHibernate() {
		super(DeviationSumJobFunctionV.class);
	}

	public List<DeviationSumJobFunctionV> findByYearAndDeviationFunctionAndProductAreaGroup(
			final Integer year, final JobFunction deviationFunction,
			final ProductAreaGroup productAreaGroup) {
		if(productAreaGroup.getProductAreaGroupName().equalsIgnoreCase("Alle")){
			return getAllProductAreaGroup(
					year, deviationFunction,
					productAreaGroup);
		}else{
			return getPerProductAreaGroup(
					year, deviationFunction,
					productAreaGroup);
		}
		
	}

	@SuppressWarnings("unchecked")
	private List<DeviationSumJobFunctionV> getAllProductAreaGroup(final Integer year,
			final JobFunction deviationFunction, final ProductAreaGroup productAreaGroup) {
		return (List<DeviationSumJobFunctionV>) getHibernateTemplate().execute(
				new HibernateCallback() {

					public Object doInHibernate(final Session session) {
						String query="select new DeviationSumJobFunctionV(''," +
								"                                         deviationSumJobFunctionVPK.registrationYear," +
								"                                         deviationSumJobFunctionVPK.registrationWeek," +
								"                                         deviationSumJobFunctionVPK.deviationFunction," +
								"                                         deviationSumJobFunctionVPK.functionCategoryName," +
								"                                         sum(countDeviations))" +
								"         from DeviationSumJobFunctionV" +
								"         where deviationSumJobFunctionVPK.registrationYear=:year and " +
								"               deviationSumJobFunctionVPK.deviationFunction=:deviationFunction " +
								"         group by deviationSumJobFunctionVPK.registrationYear," +
								"                                         deviationSumJobFunctionVPK.registrationWeek," +
								"                                         deviationSumJobFunctionVPK.deviationFunction," +
								"                                         deviationSumJobFunctionVPK.functionCategoryName";
						return session.createQuery(query).setParameter("year", year).setParameter("deviationFunction", deviationFunction.getJobFunctionName()).list();
					}
				});
	}

	@SuppressWarnings("unchecked")
	private List<DeviationSumJobFunctionV> getPerProductAreaGroup(
			final Integer year, final JobFunction deviationFunction,
			final ProductAreaGroup productAreaGroup) {
		return (List<DeviationSumJobFunctionV>) getHibernateTemplate().execute(
				new HibernateCallback() {

					public Object doInHibernate(final Session session) {
						return session
								.createCriteria(DeviationSumJobFunctionV.class)
								.add(
										Restrictions
												.eq(
														"deviationSumJobFunctionVPK.registrationYear",
														year))
								.add(
										Restrictions
												.eq(
														"deviationSumJobFunctionVPK.deviationFunction",
														deviationFunction
																.getJobFunctionName()))
								.add(
										Restrictions
												.eq(
														"deviationSumJobFunctionVPK.productAreaGroupName",
														productAreaGroup
																.getProductAreaGroupName()))
								.list();
					}
				});
	}

}
