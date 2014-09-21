package no.ugland.utransprod.dao.hibernate;

import java.util.List;

import no.ugland.utransprod.dao.AssemblyVDAO;
import no.ugland.utransprod.model.AssemblyV;

import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class AssemblyVDAOHibernate extends HibernateDaoSupport implements AssemblyVDAO {

    public List<AssemblyV> findByYear(final int aar) {
	return (List<AssemblyV>) getHibernateTemplate().execute(new HibernateCallback() {

	    public Object doInHibernate(final Session session) {

		List<AssemblyV> assemblies = session.createCriteria(AssemblyV.class).add(Restrictions.eq("assemblyYear", aar))
			.addOrder(Order.desc("assemblyWeek")).list();

		return assemblies;

	    }

	});
    }

}
