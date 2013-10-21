package no.ugland.utransprod.dao.hibernate;

import java.util.List;

import no.ugland.utransprod.dao.JobFunctionDAO;
import no.ugland.utransprod.model.ApplicationUser;
import no.ugland.utransprod.model.JobFunction;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 * Implementasjon av DAO mot tabell JOB_FUNCTION for hibernate
 * 
 * @author atle.brekka
 * 
 */
public class JobFunctionDAOHibernate extends BaseDAOHibernate<JobFunction>
		implements JobFunctionDAO {
	/**
	 * Konstruktør
	 */
	public JobFunctionDAOHibernate() {
		super(JobFunction.class);
	}

	/**
	 * @see no.ugland.utransprod.dao.JobFunctionDAO#refreshObject(no.ugland.utransprod.model.JobFunction)
	 */
	public void refreshObject(JobFunction jobFunction) {
		getHibernateTemplate()
				.load(jobFunction, jobFunction.getJobFunctionId());

	}

	/**
	 * @see no.ugland.utransprod.dao.JobFunctionDAO#lazyLoad(no.ugland.utransprod.model.JobFunction,
	 *      no.ugland.utransprod.service.enums.LazyLoadJobFunctionEnum[])
	 */
	/*public void lazyLoad(final JobFunction jobFunction,
			final LazyLoadJobFunctionEnum[] enums) {
		if (jobFunction != null && jobFunction.getJobFunctionId() != null) {
			getHibernateTemplate().execute(new HibernateCallback() {

				public Object doInHibernate(Session session)
						throws HibernateException {
					// if (!session.contains(orderLine)) {
					session.load(jobFunction, jobFunction.getJobFunctionId());
					// }
					Set<?> set;

					for (LazyLoadJobFunctionEnum lazyEnum : enums) {
						switch (lazyEnum) {
						case FUNCTION_CATEGORY:
							set = jobFunction.getFunctionCategories();
							set.iterator();
							break;
						}
					}
					return null;
				}

			});

		}

	}*/

	/**
	 * @see no.ugland.utransprod.dao.JobFunctionDAO#isFunctionManager(no.ugland.utransprod.model.ApplicationUser)
	 */
	public Boolean isFunctionManager(final ApplicationUser user) {
		return (Boolean)getHibernateTemplate().execute(new HibernateCallback() {
		
			@SuppressWarnings("unchecked")
			public Object doInHibernate(Session session) throws HibernateException {
				List<JobFunction> list =session.createCriteria(JobFunction.class).add(Restrictions.eq("manager", user)).list();
				if(list!=null&&list.size()>0){
					return true;
				}
				return false;
			}
		
		});
	}

}
