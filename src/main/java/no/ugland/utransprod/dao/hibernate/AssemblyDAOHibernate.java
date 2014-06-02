package no.ugland.utransprod.dao.hibernate;

import java.util.List;
import java.util.Set;

import no.ugland.utransprod.dao.AssemblyDAO;
import no.ugland.utransprod.model.Assembly;
import no.ugland.utransprod.model.Supplier;

import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 * Implemntasjon av DAO for ASSEMBLY for hibernate
 * 
 * @author atle.brekka
 */
public class AssemblyDAOHibernate extends BaseDAOHibernate<Assembly> implements AssemblyDAO {
    public AssemblyDAOHibernate() {
	super(Assembly.class);
    }

    /**
     * @see no.ugland.utransprod.dao.AssemblyDAO#deleteAssemblies(java.util.Set)
     */
    public final void deleteAssemblies(final Set<Assembly> assemblies) {
	getHibernateTemplate().execute(new HibernateCallback() {

	    public Object doInHibernate(final Session session) {
		if (assemblies != null) {
		    for (Assembly assembly : assemblies) {
			session.delete(assembly);
		    }
		}
		return null;
	    }

	});

    }

    /**
     * @see no.ugland.utransprod.dao.AssemblyDAO#refreshObject(no.ugland.utransprod.model.Assembly)
     */
    public final void refreshObject(final Assembly assembly) {
	getHibernateTemplate().load(assembly, assembly.getAssemblyId());

    }

    @SuppressWarnings("unchecked")
    public final List<Assembly> findBySupplierYearWeek(final Supplier supplier, final Integer year, final Integer week) {
	return (List<Assembly>) getHibernateTemplate().execute(new HibernateCallback() {

	    public Object doInHibernate(final Session session) {

		List<Assembly> assemblies;

		String query = "select new Assembly(assembly.assemblyId, " + "                         assembly.supplier, "
			+ "                         assembly.order," + "                         assembly.assemblyYear, "
			+ "                         assembly.assemblyWeek, " + "                         assembly.planned, "
			+ "                         assembly.firstPlanned, " + "                         assembly.assembliedDate,"
			+ "                         assembly.inactive) " + "from Assembly assembly " + "where assembly.supplier=:supplier and "
			+ "      assembly.assemblyYear=:year and " + "      assembly.assemblyWeek=:week and "
			+ "      assembly.order.doAssembly=1 and " + "     (assembly.inactive is null or " + "      assembly.inactive=0) ";

		assemblies = session.createQuery(query).setParameter("supplier", supplier).setParameter("year", year).setParameter("week", week)
			.list();

		assemblies.addAll(session.createCriteria(Assembly.class).add(Restrictions.eq("supplier", supplier))
			.add(Restrictions.eq("assemblyYear", year)).add(Restrictions.eq("assemblyWeek", week))
			.add(Restrictions.or(Restrictions.isNull("inactive"), Restrictions.eq("inactive", 0))).createCriteria("deviation")
			.add(Restrictions.eq("doAssembly", 1))

			.list());

		return assemblies;
	    }

	});
    }

    public List<Assembly> findByYear(final Integer year) {
	return (List<Assembly>) getHibernateTemplate().execute(new HibernateCallback() {

	    public Object doInHibernate(final Session session) {

		List<Assembly> assemblies = session.createCriteria(Assembly.class).add(Restrictions.eq("assemblyYear", year))
			.addOrder(Order.desc("assemblyWeek")).list();

		for (Assembly assembly : assemblies) {
		    assembly.getOrder().getOrderLines().size();
		    assembly.getOrder().getOrderCosts().size();
		}
		return assemblies;

	    }

	});
    }

}
